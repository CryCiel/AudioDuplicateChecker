/*
 * 공통으로 사용하는 데이터 저장하는 클래스
 * */
package audio.main;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import audio.frame.AudioCheckerFrame;
import audio.frame.duplicatefile.DuplicateFileView;
import audio.frame.filetree.FileTreeView;
import audio.frame.logview.LogView;
import audio.frame.option.OptionView;
import audio.frame.progress.ProgressView;
import audio.frame.selfilelist.SelFileListView;

public class UsedData {
	public static final boolean DEBUGMODE = false;

	public static final String APPTITLE = "ADC";
	public static final Image APPICON = (new ImageIcon("res/frame_icon.png")).getImage();

	public AudioCheckerFrame audioCheckerFrame;
	public DuplicateFileView duplicateFileView;
	public FileTreeView fileTreeView;
	public SelFileListView selFileListView;
	public ProgressView progressView;
	public OptionView optionView;
	public LogView logView;

	// 패널들 설정해주는 메서드
	public void setPanel(AudioCheckerFrame frame, JPanel[] panel) {
		audioCheckerFrame = frame;
		duplicateFileView = (DuplicateFileView) panel[AudioCheckerFrame.DUPLICATEFILE];
		fileTreeView = (FileTreeView) panel[AudioCheckerFrame.FILETREE];
		selFileListView = (SelFileListView) panel[AudioCheckerFrame.SELFILELIST];
		progressView = (ProgressView) panel[AudioCheckerFrame.PROGRESS];
		optionView = (OptionView) panel[AudioCheckerFrame.OPTION];
		logView = (LogView) panel[AudioCheckerFrame.LOGVIEW];

	}


	public static void deleteFile(File file) {
		while (file.exists()) {
			file.delete();
		}
	}

}