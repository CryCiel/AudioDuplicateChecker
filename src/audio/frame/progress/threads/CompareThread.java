/*
 * 파일 변환 완료 후 비교 실행될때 사용하는 쓰레드 클래스
 * */
package audio.frame.progress.threads;

import java.io.File;

import audio.frame.logview.LogView;
import audio.frame.progress.Compare;
import audio.frame.progress.module.MatchChecker;
import audio.main.UsedData;

public class CompareThread extends ThreadSuper {
	int createNum;
	Object fileObject[][];
	MatchChecker matchChecker;

	public CompareThread(UsedData usedData, Compare compare, int createNum) {
		super(compare, usedData);
		this.createNum = createNum;
	}

	public void run() {
		try {
			sleep(createNum*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (UsedData.DEBUGMODE) {
			System.out.println(createNum + "번째 CompareThread 시작");
		}

		int index = 0;
		int dataLength = compare.getDataLength();
		fileObject = compare.getFileData();
		matchChecker = new MatchChecker();

		while (compare.isProcessing() && index < dataLength - 1) {
			if ((boolean) fileObject[index][Compare.PROCESSED] == false) { // 매칭 진행되지 않으면 진행
				fileObject[index][Compare.PROCESSED] = true; // 검사 진행된걸로 변경
				File requestFile = (File) fileObject[index][Compare.CONVERTFILE];
				
				usedData.logView.addLog((File) fileObject[index][Compare.ORIGINALFILE], LogView.LOG_COMPARE);
				
				for (int i = index + 1; i < dataLength; i++) {// 다음 파일부터 검사 진행
					File acceptFile = (File) fileObject[i][Compare.CONVERTFILE];
					int result = matchChecker.match(requestFile, acceptFile);
					
					if (result > usedData.optionView.getSimilarity()) {// 유사한 파일 발생하면 동작
						setDuplicate(index, i);
					}
					if(compare.isProcessing() == false) {// 중지 누르면 반복문 빠져나가게
						break;
					}
					
					if (UsedData.DEBUGMODE) { // 디버깅용
						System.out.println(requestFile.getName() + " / " + acceptFile.getName() + " / " + result);
					}
				}
				
			}
			usedData.progressView.setProgressBar(); // 프로그레스바 설정
			index++;
		}
		
		fileObject = null;
		matchChecker = null;
		endFlag = true;

		if (UsedData.DEBUGMODE) {
			System.out.println(createNum + "번째 CompareThread 동작 완료");
		}
	}

	// 중복파일 있을때 그룹번호 설정
	private void setDuplicate(int file1Num, int file2Num) {
		if (UsedData.DEBUGMODE) {
			File file1 = (File) fileObject[file1Num][Compare.ORIGINALFILE];
			File file2 = (File) fileObject[file2Num][Compare.ORIGINALFILE];
			System.out.println(file1.getName() + " / " + file2.getName() + " 중복 의심");
		}
		int groupNum = -1;
		int file1NumGroup = (Integer) fileObject[file1Num][Compare.DUPLICATEGROUP];
		int file2NumGroup = (Integer) fileObject[file2Num][Compare.DUPLICATEGROUP];

		if (file1NumGroup == -1 && file2NumGroup == -1) { // 둘다 그룹 없으면
			groupNum = compare.getMaxGroupNumAfter();
		} else if (file1NumGroup == -1) {
			groupNum = file2NumGroup;
		} else {
			groupNum = file1NumGroup;
		}

		fileObject[file1Num][Compare.DUPLICATEGROUP] = groupNum;
		fileObject[file2Num][Compare.DUPLICATEGROUP] = groupNum;
	}

}
