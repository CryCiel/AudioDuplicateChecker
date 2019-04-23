/*
 * 작업 진행 시 옵션 창 보여주는 패널
 * */
package audio.frame.option;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import audio.frame.option.tappanel.SelectSection;
import audio.frame.option.tappanel.SelectSimilarity;
import audio.frame.option.tappanel.SelectThread;
import audio.main.UsedData;

public class OptionView extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();

	private int threadCnt = SelectThread.SIMINIT; // 사용할 쓰레드 갯수
	private int wavSection = SelectSection.SECTION20;
	private int allowSimilarity = SelectSimilarity.SIMINIT; // 얼마나 일치정도 까지 허용할지 결정해주는 변수

	public OptionView(UsedData usedData) {
		this.setLayout(new BorderLayout());
		tabbedPane.addTab("정밀도", new SelectSimilarity(this));
		tabbedPane.setSelectedIndex(0);

		tabbedPane.addTab("쓰레드", new SelectThread(this));
		add(tabbedPane);

		tabbedPane.addTab("비교구간", new SelectSection(this));
		add(tabbedPane);
	}

	public void setThreadCnt(int count) {
		threadCnt = count;
	}

	public int getThreadCnt() {
		return threadCnt;
	}

	public void setSimilarity(int s) {
		this.allowSimilarity = (int) s;
	}

	public int getSimilarity() {
		return this.allowSimilarity;
	}

	public void setSection(int s) {
		this.wavSection = s;
	}

	public int getSection() {
		return this.wavSection;
	}

}
