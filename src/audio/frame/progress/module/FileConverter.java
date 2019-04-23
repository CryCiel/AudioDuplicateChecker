/*
 * MP3 파일 WAV 파일로 변경해주는 클래스
 * */
package audio.frame.progress.module;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class FileConverter {
	private final int convert_bps = 8; // bitsPerSample, 8 or 16

	public void convert(File source, File output) {
		Converter myConverter = new Converter();
		File tmpFile = new File(source.getPath() + "_ACDTEMP");
		try {
			myConverter.convert(source.getPath(), tmpFile.getPath()); // MP3 -> WAV 변환
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

		changeBitrate(tmpFile, output); // WAV -> WAV 변환
		while (tmpFile.exists()) { // tmpFile 남아있으면 삭제
			tmpFile.delete();
			System.gc(); // 가비지 컬렉터 호출
		}
		myConverter = null;
	}

	// wav파일 형식 변경해줌
	public void changeBitrate(File source, File output) {
		AudioInputStream in = null;
		AudioInputStream convert = null;
		try {
			AudioFormat format = new AudioFormat(8000, convert_bps, 1, true, true);
			in = AudioSystem.getAudioInputStream(source);
			convert = AudioSystem.getAudioInputStream(format, in);
			AudioSystem.write(convert, AudioFileFormat.Type.WAVE, output);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			streamClose(in);
			streamClose(convert);
			in = null;
			convert = null;
		}
	}

	private void streamClose(AudioInputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
