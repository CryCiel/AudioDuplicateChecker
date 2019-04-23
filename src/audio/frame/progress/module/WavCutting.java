package audio.frame.progress.module;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class WavCutting {
	public void cutAudio(File sourceFile, File destinationFile, int startSecond, int secondsToCopy) {
		AudioInputStream inputStream = null;
		AudioInputStream shortenedStream = null;
		try {
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(sourceFile);
			AudioFormat format = fileFormat.getFormat();
			inputStream = AudioSystem.getAudioInputStream(sourceFile);
			int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();
			inputStream.skip(startSecond * bytesPerSecond);
			long framesOfAudioToCopy = secondsToCopy * (int) format.getFrameRate();
			shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
			AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStream(inputStream);
			closeStream(shortenedStream);
			System.gc();
		}
	}

	private void closeStream(AudioInputStream stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
