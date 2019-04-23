/*
 * 폴더에서 파일을 읽어 usedData의 fileData에 저장
 * */
package audio.frame.progress.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import audio.frame.progress.Compare;
import audio.main.UsedData;

public class ReadFile {
	private static final int FILEARRAYSIZE = 4;// 파일 정보 저장되는 배열 열 크기
	Compare compare;
	UsedData usedData;

	public ReadFile(UsedData usedData, Compare compare) {
		this.compare = compare;
		this.usedData = usedData;
	}

	public void readFile() {
		ArrayList<File> getFiles = usedData.selFileListView.getSelFiles();
		ArrayList<File> filesList = new ArrayList<File>();
		for (int i = 0; i < getFiles.size(); i++) {
			if (getFiles.get(i).isDirectory()) { // 디렉토리면 요기
				readFile(getFiles.get(i), filesList);// 파일들을 리스트에 추가
			} else { // 파일이면 요기
				filesList.add(getFiles.get(i));
			}
		}
		int cnt = filesList.size();
		compare.setDataLength(cnt);
		Object[][] tmpObject = new Object[cnt][FILEARRAYSIZE]; // 정보 담는 배열 초기화
		for (int i = 0; i < cnt; i++) {
			tmpObject[i][Compare.ORIGINALFILE] = filesList.get(i);// 원본 파일
			tmpObject[i][Compare.CONVERTFILE] = null;// 변환된 파일
			tmpObject[i][Compare.PROCESSED] = false; // 비교 진행 여부
			tmpObject[i][Compare.DUPLICATEGROUP] = -1; // 중복된 그룹 번호 선택
		}

		// 디버깅용
		if (UsedData.DEBUGMODE) {
			System.out.println("==== FileRead파일 리스트 출력 ====");
			for (int i = 0; i < cnt; i++) {
				for (int j = 0; j < FILEARRAYSIZE; j++) {
					System.out.print(tmpObject[i][j] + "\t");
				}
				System.out.println();
			}

			System.out.println("FileRead 종료");
		}

		compare.setFileData(tmpObject);
	}

	private void readFile(File folder, List<File> addList) {
		File[] fileNames = folder.listFiles();

		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].isFile()) { // 파일일 경우에만 추가 함
				String ext = getFileExtension(fileNames[i]);
				if (ext.equals("mp3") || ext.equals("wav")) { // mp3 혹은 wav파일만 받아옴
					addList.add(fileNames[i]);
				}
			}
		}
	}

	// 파일 확장자 반환해주는 메서드
	public static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		} else {
			return "";
		}
	}

}
