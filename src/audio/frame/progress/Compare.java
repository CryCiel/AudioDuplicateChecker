/*
 * 비교 분석 시작/종료를 담당하는 클래스
 * */
package audio.frame.progress;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import audio.frame.logview.LogView;
import audio.frame.progress.module.DeleteConvertFile;
import audio.frame.progress.module.ReadFile;
import audio.frame.progress.threads.CompareThread;
import audio.frame.progress.threads.ConverterThread;
import audio.frame.progress.threads.ThreadSuper;
import audio.main.UsedData;

public class Compare extends Thread {
	public static final int ORIGINALFILE = 0;
	public static final int CONVERTFILE = 1;
	public static final int PROCESSED = 2;
	public static final int DUPLICATEGROUP = 3;

	private boolean processing = false; // 동작하는지 안하는지 체크할 변수
	
	private Object[][] fileData = null; // 사용하는 파일 데이터 가지고 있는 변수.
	UsedData usedData;
	ArrayList<ThreadSuper> threadList;
	int threadCnt;
	int dataCnt;

	public Compare(UsedData usedData) {
		this.usedData = usedData;
	}

	// 비교 작업 시작
	public void run() {
		// 원본파일, 변환된 파일, 진행여부(F,T), 중복된 그룹번호(int),

		threadCnt = usedData.optionView.getThreadCnt(); // 사용할 쓰레드 갯수 받아옴
		threadList = new ArrayList<ThreadSuper>();

		ReadFile readFile = new ReadFile(usedData, this);
		usedData.logView.addLog(LogView.LOG_FILEREAD);
		readFile.readFile();

		// 파일 변환 진행
		for (int i = 0; i < threadCnt; i++) {
			threadList.add(new ConverterThread(usedData, this, i));
			threadList.get(i).start();
		}

		processWait(); // 대기
		usedData.logView.addLog(LogView.LOG_CONVERT_END);

		// 파일 비교 진행
		for (int i = 0; i < threadCnt; i++) {
			threadList.add(new CompareThread(usedData, this, i));
			threadList.get(i).start();
		}

		processWait(); // 대기
		usedData.logView.addLog(LogView.LOG_COMPARE_END);

		// 변환된 파일 삭제
		usedData.logView.addLog(LogView.LOG_DELETE);
		DeleteConvertFile deleteConvertFile = new DeleteConvertFile(this);
		deleteConvertFile.delConvertFile();

		// 중복 파일 HashMap으로 변경
		HashMap<Integer, ArrayList<File>> dupliFile = new HashMap<Integer, ArrayList<File>>();
		// 맵에 키 추가
		for (int i = 0; i <= getMaxGrupNum(); i++) {
			ArrayList<File> tmpList = new ArrayList<File>();
			dupliFile.put(i, tmpList);
		}
		// 저장된 중복 음악 저장하는 맵의 키에 파일 추가
		for (int i = 0; i < getDataLength(); i++) {
			Object[] tmpData = fileData[i];
			if ((Integer) tmpData[DUPLICATEGROUP] != -1) {
				dupliFile.get(tmpData[DUPLICATEGROUP]).add((File) tmpData[ORIGINALFILE]);
			}
		}
		
		// 중복 파일 표시
		usedData.duplicateFileView.setDupliFileData(dupliFile);

		// 프로그램 동작 종료
		usedData.logView.addLog(LogView.LOG_PROCESS_END);

		this.fileData = null;
		usedData.progressView.compareStop();
		JOptionPane.showMessageDialog(usedData.audioCheckerFrame, "작업이 완료되었습니다!");
	}

	public int getFlagCount(ArrayList<ThreadSuper> list) {
		int cnt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isEndFlag()) {
				cnt++;
			}
		}
		return cnt;
	}

	// 해당 프로세스 완료될때까지 대기
	public void processWait() {
		while (true) { // 변환 전부 완료될때까지 대기
			if (getFlagCount(threadList) == threadCnt) {
				break;
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < threadCnt; i++) {
			threadList.set(0, null);
			threadList.remove(0); // 리스트 삭제
		}

		System.gc();
	}

	// 작업 시작
	public void compareStart() {
		usedData.logView.logClear();
		setProcessing(true);
		start();
	}

	// 작업 중지
	public void compareStop() {
		setProcessing(false);
	}

	// FileData 관련
	public void setFileData(Object[][] data) {
		this.fileData = data;
	}

	public Object[][] getFileData() {
		return this.fileData;
	}

	public void setDataLength(int cnt) {
		usedData.progressView.setProcessMaxLabel(cnt);
		this.dataCnt = cnt;
	}

	public int getDataLength() {
		return this.dataCnt;
	}

	public int getNowDataLength() {
		int cnt = 1;
		for (int i = 0; i < getDataLength(); i++) {
			if ((boolean) fileData[i][PROCESSED]) {
				cnt++;
			}
		}
		return cnt;
	}

	public int getSimiDataCnt() {
		int cnt = 0;
		for (int i = 0; i < getDataLength(); i++) {
			if ((Integer) fileData[i][DUPLICATEGROUP] != -1) {
				cnt++;
			}
		}
		return cnt;
	}

	// 배열 내 하나의 값 변경
	public void setFileDataElement(int index, int arrayNum, Object value) {
		this.fileData[index][arrayNum] = value;
	}

	// 마지막 중복 그룹 번호 다음 번호 반환
	public synchronized int getMaxGroupNumAfter() {
		return getMaxGrupNum() + 1;
	}

	public synchronized int getMaxGrupNum() {
		int index = -1;
		for (int i = 0; i < fileData.length; i++) {
			int nowGroupNum = (Integer) fileData[i][DUPLICATEGROUP];
			index = (nowGroupNum > index) ? nowGroupNum : index;
		}
		return index;
	}
	// FileData 관련 끝
	

	public void setProcessing(boolean b) {
		this.processing = b;
	}

	public boolean isProcessing() {
		return processing;
	}

}
