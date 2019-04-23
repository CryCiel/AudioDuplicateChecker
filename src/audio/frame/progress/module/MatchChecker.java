/*
 * 얼마나 일치하는지 반환해주는 클래스
 * */
package audio.frame.progress.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;

public class MatchChecker {
	// 얼마나 일치하는지 비교해서 반환해주는 메서드
	FileInputStream fis1;
	FileInputStream fis2;
	Wave wave1;
	Wave wave2;
	FingerprintSimilarity similarity;

	public int match(File file1, File file2) {
		float result = 0.0f;
		try {
			fis1 = new FileInputStream(file1);
			fis2 = new FileInputStream(file2);
			wave1 = new Wave(fis1);
			wave2 = new Wave(fis2);
			similarity = wave1.getFingerprintSimilarity(wave2);
			result = (float) (similarity.getSimilarity() * 100.0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeFis(fis1);
			closeFis(fis2);
			fis1 = null;
			fis2 = null;
			wave1 = null;
			wave2 = null;
			similarity = null;
			System.gc();
		}

		return (int) result;
	}

	public void closeFis(FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
