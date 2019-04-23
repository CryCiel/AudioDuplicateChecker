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

public class SelectSection extends JPanel {

	public static final int SECTION5 = 1;
	public static final int SECTION10 = 2;
	public static final int SECTION20 = 3;
	public static final int SECTION30 = 4;
	public static final int SECTION60 = 5;
	public static final int SECTIONALL = 6;

	OptionView optionView;
	JSlider threadSlider;
	JTextArea textArea;
	Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();

	public SelectSection(OptionView optionView) {
		this.optionView = optionView;
		textArea = new JTextArea();
		this.setLayout(new BorderLayout());
		this.add(new JLabel("비교 작업할 구간 선택", JLabel.CENTER), BorderLayout.NORTH);
		textArea.setBackground(new Color(238, 238, 238));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		setTextArea();
		this.add(textArea);

		threadSlider = new JSlider(JSlider.VERTICAL, SECTION5, SECTIONALL, SECTION20);
		threadSlider.setMajorTickSpacing(1);
		threadSlider.setPaintTicks(true);
		labelTable.put(new Integer(SECTION5), new JLabel("5초     "));
		labelTable.put(new Integer(SECTION10), new JLabel("10초     "));
		labelTable.put(new Integer(SECTION20), new JLabel("20초     "));
		labelTable.put(new Integer(SECTION30), new JLabel("30초     "));
		labelTable.put(new Integer(SECTION60), new JLabel("60초     "));
		labelTable.put(new Integer(SECTIONALL), new JLabel("전구간     "));
		threadSlider.setLabelTable(labelTable);
		threadSlider.setPaintLabels(true);

		threadSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				optionView.setSection(threadSlider.getValue());
			}
		});

		add(threadSlider, BorderLayout.WEST);
	}

	public void setTextArea() {
		textArea.append("음악의 어느 구간에서 비교할지 정합니다.\n");
		textArea.append("※ 주의)\n");
		textArea.append(" - 전구간에서 비교 진행 시 비교에 많은 시간이 걸릴 수 있습니다. \n");
		textArea.append(" - 구간을 짧게 잡을경우 유사한 노래가 많이 발생할 수 있습니다. \n");
	}
}
