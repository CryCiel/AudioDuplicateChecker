/*
 * 화면에서 프레임 레이아웃 정의
 * */
package audio.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import audio.frame.duplicatefile.DuplicateFileView;
import audio.frame.filetree.FileTreeView;
import audio.frame.logview.LogView;
import audio.frame.option.OptionView;
import audio.frame.progress.ProgressView;
import audio.frame.selfilelist.SelFileListView;
import audio.main.UsedData;

public class AudioCheckerFrame extends JFrame {
	private int WIDTH = 900;
	private int HEIGHT = 700;
	UsedData usedData;

	public static final int DUPLICATEFILE = 0;
	public static final int FILETREE = 1;
	public static final int SELFILELIST = 2;
	public static final int PROGRESS = 3;
	public static final int OPTION = 4;
	public static final int LOGVIEW = 5;

	private final int PANEL_CNT = 6;

	JPanel[] panel = new JPanel[PANEL_CNT];
	JScrollPane scroll_FileTree;
	JLabel frameInfo;

	public AudioCheckerFrame(UsedData usedData) {
		this.setIconImage(UsedData.APPICON);
		this.setTitle(UsedData.APPTITLE);
		this.usedData = usedData;
		frameInfo = new JLabel(" ");
		setLayout(new BorderLayout());
		add(frameInfo, BorderLayout.SOUTH);

		// 패널 초기화
		addPanelArray();

		// 패널 정보 입력
		usedData.setPanel(this, panel);

		// 프레임에 패널 셋팅
		setPanels();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setSize(WIDTH, HEIGHT);
		centerWindow(this);
		setVisible(true);

	}

	private void centerWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	// 배열에 패널 정보 입력
	private void addPanelArray() {
		for (int i = 0; i < PANEL_CNT; i++) {
			JPanel tmp;
			switch (i) {
			case DUPLICATEFILE:
				tmp = new DuplicateFileView(usedData);
				break;
			case FILETREE:
				tmp = new FileTreeView();
				break;
			case SELFILELIST:
				tmp = new SelFileListView(usedData);
				break;
			case PROGRESS:
				tmp = new ProgressView(usedData);
				break;
			case OPTION:
				tmp = new OptionView(usedData);
				break;
			case LOGVIEW:
				tmp = new LogView();
				break;
			default:
				tmp = null;
			}
			panel[i] = tmp;
		}
	}

	// 프레임에 패널 셋팅
	private void setPanels() {
		JPanel p_center = new JPanel(); // 나머지 들어가는 패널
		JPanel p_center_center = new JPanel(); // 나머지 들어가는 패널
		JPanel p_center_center_center = new JPanel(); // 나머지 들어가는 패널
		JPanel p_center_center_center_south = new JPanel(); // 나머지 들어가는 부분
		p_center.setLayout(new BorderLayout());
		p_center_center.setLayout(new BorderLayout());
		p_center_center_center.setLayout(new BorderLayout());
		p_center_center_center_south.setLayout(new BorderLayout());

		scroll_FileTree = new JScrollPane(((FileTreeView) panel[FILETREE]).getGui()); // tree를 바로 scroll에 붙여서 사용하기 위해
																						// 이렇게 작성

		// 중복된 파일 리스트 출력
		panel[DUPLICATEFILE].setPreferredSize(new Dimension(WIDTH / 4, HEIGHT));
		this.add(panel[DUPLICATEFILE], BorderLayout.WEST);
		this.add(p_center);

		p_center.add(p_center_center);
		// 로그 보기 화면 출력
		panel[LOGVIEW].setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 3));
		p_center.add(panel[LOGVIEW], BorderLayout.SOUTH);
		scroll_FileTree.setPreferredSize(new Dimension(WIDTH / 4, HEIGHT * 2 / 3));
		p_center_center.add(scroll_FileTree, BorderLayout.WEST);
		p_center_center.add(p_center_center_center);

		p_center_center_center.add(panel[SELFILELIST]);
		p_center_center_center.add(p_center_center_center_south, BorderLayout.SOUTH);
		// 컴퓨터 파일 리스트 출력

		// 진행사항 보여주는 패널
		p_center_center_center_south.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT * 7 / 25));
		p_center_center_center_south.add(panel[PROGRESS], BorderLayout.WEST);

		// 작업 진행 시 옵션 화면 출력
		p_center_center_center_south.add(panel[OPTION]);

	}

	public void setFrmaeInfo(String str) {
		frameInfo.setText(str);
	}
}
