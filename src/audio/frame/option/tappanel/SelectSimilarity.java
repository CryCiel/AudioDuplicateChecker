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

public class SelectSimilarity extends JPanel {
	public final static int SIMMIN = 60;
	public final static int SIMMAX = 100;
	public final static int SIMINIT = 80;
	OptionView optionView;
	JSlider similaritySlider;
	JTextArea textArea;
	Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();

	public SelectSimilarity(OptionView optionView) {
		this.optionView = optionView;
		textArea = new JTextArea();
		textArea.setEditable(false);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("작업시 설정할 정밀도를 설정", JLabel.CENTER), BorderLayout.NORTH);
		textArea.setBackground(new Color(238, 238, 238));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		setTextArea();
		this.add(textArea);

		// 슬라이더 설정
		similaritySlider = new JSlider(JSlider.VERTICAL, SIMMIN, SIMMAX, SIMINIT);
		similaritySlider.setMajorTickSpacing(10);
		similaritySlider.setMinorTickSpacing(2);
		similaritySlider.setPaintTicks(true);
		labelTable.put(new Integer(SIMMIN), new JLabel("낮음    "));
		labelTable.put(new Integer(SIMINIT), new JLabel("기본    "));
		labelTable.put(new Integer(SIMMAX), new JLabel("높음    "));
		similaritySlider.setLabelTable(labelTable);
		similaritySlider.setPaintLabels(true);

		similaritySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				optionView.setSimilarity(similaritySlider.getValue());
			}
		});

		add(similaritySlider, BorderLayout.WEST);
	}

	public void setTextArea() {
		textArea.append("유사도 검사 시 사용할 정밀도를 설정합니다. \n");
		textArea.append("너무 낮게 설정 시 비슷한 파일이 과도하게 생성될 수 있습니다. \n");

	}
}
