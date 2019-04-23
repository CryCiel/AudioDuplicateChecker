package audio.frame.option.tappanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import audio.frame.option.OptionView;

public class SelectThread extends JPanel {
	public final static int SIMMIN = 1;
	public final static int SIMMAX = Runtime.getRuntime().availableProcessors();
	public final static int SIMINIT = Runtime.getRuntime().availableProcessors() / 2;
	OptionView optionView;
	JSlider threadSlider;
	JTextArea textArea;
	Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();

	public SelectThread(OptionView optionView) {
		this.optionView = optionView;
		textArea = new JTextArea();
		this.setLayout(new BorderLayout());
		this.add(new JLabel("작업시 사용할 쓰레드 갯수를 설정", JLabel.CENTER), BorderLayout.NORTH);
		textArea.setBackground(new Color(238, 238, 238));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		setTextArea();
		this.add(textArea);

		threadSlider = new JSlider(JSlider.VERTICAL, SIMMIN, SIMMAX, SIMINIT);
		threadSlider.setMajorTickSpacing(1);
		threadSlider.setPaintTicks(true);
		labelTable.put(new Integer(SIMMIN), new JLabel(SIMMIN + "    "));
		labelTable.put(new Integer(SIMINIT), new JLabel(SIMINIT + "(추천)    "));
		labelTable.put(new Integer(SIMMAX), new JLabel(SIMMAX + "    "));
		threadSlider.setLabelTable(labelTable);
		threadSlider.setPaintLabels(true);

		threadSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				optionView.setThreadCnt(threadSlider.getValue());
			}
		});

		add(threadSlider, BorderLayout.WEST);
	}

	public void setTextArea() {
		textArea.append("프로세스 동작 시 사용할 쓰레드 갯수를 설정합니다. \n");
		textArea.append("※ 주의)\n");
		textArea.append(" - 과도한 쓰레드 사용 설정은 컴퓨터 속도 저하의 원인이 될 수 있습니다.");
	}
}
