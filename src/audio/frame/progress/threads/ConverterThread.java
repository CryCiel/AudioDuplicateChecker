/*
 * 파일 변환 시 사용하는 쓰레드
 * */
package audio.frame.progress.threads;

import java.io.File;

import audio.frame.logview.LogView;
import audio.frame.option.tappanel.SelectSection;
import audio.frame.progress.Compare;
import audio.frame.progress.module.FileConverter;
import audio.frame.progress.module.ReadFile;
import audio.frame.progress.module.WavCutting;
import audio.main.UsedData;

public class ConverterThread extends ThreadSuper {
	public static final int CONVERTPROCESSED = 1;
	FileConverter fileConverter;
	WavCutting wavCutting;
	Object[][] tmpObject;
	int createNum; // 생성된 쓰레드 번호
	int cutTime;

	public ConverterThread(UsedData usedData, Compare compare, int createNum) {
		super(compare, usedData);
		fileConverter = new FileConverter();
		wavCutting = new WavCutting();
		this.createNum = createNum;
	}

	public void run() {
		switch (usedData.optionView.getSection()) {
		case SelectSection.SECTION5:
			cutTime = 5;
			break;
		case SelectSection.SECTION10:
			cutTime = 10;
			break;
		case SelectSection.SECTION20:
			cutTime = 20;
			break;
		case SelectSection.SECTION30:
			cutTime = 30;
			break;
		case SelectSection.SECTION60:
			cutTime = 60;
			break;
		}

		try {
			sleep(createNum * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endFlag = false;
		if (UsedData.DEBUGMODE) {
			System.out.println(createNum + "번째 ConverterThread 시작");
		}

		int index = 0;
		tmpObject = compare.getFileData();
		while (compare.isProcessing() && index < compare.getDataLength()) {
			if (tmpObject[index][Compare.CONVERTFILE] == null) { // 변환한 파일이 없을때만 실행
				compare.setFileDataElement(index, Compare.CONVERTFILE, CONVERTPROCESSED);// 진행중을 표시
				File convertFile = (File) tmpObject[index][Compare.ORIGINALFILE];
				File resultFile = new File(convertFile + ".ADC.wav");
				usedData.logView.addLog(convertFile, LogView.LOG_CONVERT);
				String ext = ReadFile.getFileExtension(convertFile);
				if (ext.equals("mp3")) { // mp3 파일일 경우 동작
					fileConverter.convert(convertFile, resultFile);
				} else if (ext.equals("wav")) { // wav 파일일 경우 동작
					fileConverter.changeBitrate(convertFile, resultFile);
				}
				if (UsedData.DEBUGMODE) {
					System.out.println(convertFile.getName() + " -> " + resultFile.getName());
				}

				if (usedData.optionView.getSection() == SelectSection.SECTIONALL) { // 전구간에서 비교 시 바로 넣음
					compare.setFileDataElement(index, Compare.CONVERTFILE, resultFile);
				} else { // 구간 있으면 진입
					File cutFile = new File(convertFile + "cut.ADC.wav");
					wavCutting.cutAudio(resultFile, cutFile, 0, cutTime);
					compare.setFileDataElement(index, Compare.CONVERTFILE, cutFile);
					UsedData.deleteFile(resultFile); // 파일 다 잘
				}
			}
			index++;
		}

		tmpObject = null;
		wavCutting = null;
		fileConverter = null;
		endFlag = true;

		if (UsedData.DEBUGMODE) {
			System.out.println(createNum + "번째 ConvrterThread 동작 완료");
		}
	}
}
