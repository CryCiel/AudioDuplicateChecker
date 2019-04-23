package audio.frame.logview;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogView extends JPanel {
	public static final int LOG_FILEREAD = 1;
	public static final int LOG_CONVERT = 2;
	public static final int LOG_CONVERT_END = 3;
	public static final int LOG_COMPARE = 4;
	public static final int LOG_COMPARE_END = 5;
	public static final int LOG_DELETE = 6;
	public static final int LOG_PROCESS_END = 7;

	JTextArea log;
	JScrollPane logScroll;
	JScrollBar bar;

	public LogView() {
		log = new JTextArea();
		logScroll = new JScrollPane(log);
		bar = logScroll.getVerticalScrollBar();

		setLayout(new BorderLayout());

		log.setEditable(false);// 글자 입력 못하도록 설정

		add(new JLabel("진행 사항"), BorderLayout.NORTH);
		add(logScroll);

	}

	private void addLog(String msg) {
		log.append(msg + "\n");
		bar.setValue(bar.getMaximum());
	}

	public void addLog(File file, int log_num) {
		switch (log_num) {
		case LOG_CONVERT:
			addLog(file.getName() + " 변환 작업이 진행중입니다.");
			break;
		case LOG_COMPARE:
			addLog(file.getName() + " 를 기준으로 비교 작업중입니다.");
			break;
		}

	}

	public void addLog(int log_num) {
		switch (log_num) {
		case LOG_FILEREAD:
			addLog("===== 선택한 폴더에서 파일을 읽고 있습니다. =====");
			break;
		case LOG_DELETE:
			addLog("===== 비교시 사용된 파일을 삭제합니다. =====");
			break;
		case LOG_CONVERT_END:
			addLog("===== 파일 변환 작업 완료 =====");
			break;
		case LOG_COMPARE_END:
			addLog("===== 파일 비교 작업 완료 =====");
			break;
		case LOG_PROCESS_END:
			addLog("===== 작업이 완료되었습니다! =====");
			break;
		}
	}

	public void logClear() {
		log.setText("");
	}

}
