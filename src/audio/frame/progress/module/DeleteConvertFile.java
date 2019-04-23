package audio.frame.progress.module;

import java.io.File;
import java.util.ArrayList;

import audio.frame.progress.Compare;
import audio.main.UsedData;

public class DeleteConvertFile {
	Compare compare;
	ArrayList<File> delFileList = new ArrayList<File>();

	public DeleteConvertFile(Compare compare) {
		this.compare = compare;
		System.gc();
	}

	public void delConvertFile() {
		Object[][] data = compare.getFileData();
		if (UsedData.DEBUGMODE) {
			System.out.println("파일 삭제 시작");
		}
		for (int i = 0; i < compare.getDataLength(); i++) { // 삭제할 파일 리스트에 담기
			File delFile = (File) data[i][Compare.CONVERTFILE];
			UsedData.deleteFile(delFile);
			compare.setFileDataElement(i, Compare.CONVERTFILE, null);
			delFile = null;
		}
		System.gc();
	}
}
