/*
 * 진행사항 보여주는 패널
 * */
package audio.frame.progress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import audio.frame.progress.threads.TimerThread;
import audio.main.UsedData;

public class ProgressView extends JPanel {
	Compare compare;
	TimerThread timer;
	UsedData usedData;

	JLabel la_total, la_progress, la_repect, la_total_cnt, la_progress_cnt, la_repect_cnt;
	JLabel la_time;
	JProgressBar progressBar;
	JButton bt_stop, bt_start;
	Dimension d;

	public ProgressView(UsedData usedData) {
		this.usedData = usedData;
		d = new Dimension(75, 20);

		this.setLayout(new BorderLayout());

		this.add(getNorth(), BorderLayout.NORTH);
		this.add(getCenter());
		this.add(getSouth(), BorderLayout.SOUTH);

	}

	// 패널의 north 항목 설정
	private JPanel getNorth() {
		JPanel p_north = new JPanel();
		JPanel p_north_west = new JPanel();
		JPanel p_north_center = new JPanel();

		la_total = new JLabel("전체 파일");
		la_progress = new JLabel("진행한 파일");
		la_repect = new JLabel("비슷한 노래");
		la_total_cnt = new JLabel("0");
		la_progress_cnt = new JLabel("0");
		la_repect_cnt = new JLabel("0");

		la_total.setPreferredSize(d);
		la_progress.setPreferredSize(d);
		la_repect.setPreferredSize(d);
		la_total_cnt.setPreferredSize(d);
		la_progress_cnt.setPreferredSize(d);
		la_repect_cnt.setPreferredSize(d);

		p_north_west.add(la_total);
		p_north_west.add(la_progress);
		p_north_west.add(la_repect);
		p_north_center.add(la_total_cnt);
		p_north_center.add(la_progress_cnt);
		p_north_center.add(la_repect_cnt);

		p_north_west.setLayout(new BoxLayout(p_north_west, BoxLayout.Y_AXIS));
		p_north_center.setLayout(new BoxLayout(p_north_center, BoxLayout.Y_AXIS));
		p_north.add(p_north_west, BorderLayout.WEST);
		p_north.add(p_north_center);

		return p_north;

	}

	private JPanel getCenter() {
		JPanel p_center = new JPanel();
		p_center.setLayout(new BorderLayout());
		la_time = new JLabel("00 : 00 : 00");
		la_time.setHorizontalAlignment(SwingConstants.CENTER);
		progressBar = new JProgressBar();

		progressBar.setStringPainted(true);
		p_center.add(la_time, BorderLayout.NORTH);
		p_center.add(progressBar);
		Dimension d = new Dimension(80, 30);
		la_time.setPreferredSize(d);
		progressBar.setPreferredSize(d);

		return p_center;
	}

	private JPanel getSouth() {
		JPanel p_south = new JPanel();

		// 버튼 아이콘 설정
		Image img_start = Toolkit.getDefaultToolkit().getImage("res/bt_start.png");
		Image img_stop = Toolkit.getDefaultToolkit().getImage("res/bt_stop.png");

		bt_start = new JButton(new ImageIcon(btIconResize(img_start)));
		bt_stop = new JButton(new ImageIcon(btIconResize(img_stop)));

		Dimension d_bt = new Dimension(40, 40);
		bt_start.setPreferredSize(d_bt);
		bt_stop.setPreferredSize(d_bt);

		p_south.add(bt_start);
		p_south.add(bt_stop);

		bt_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compareStop();
			}
		});

		bt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compareStart();
			}
		});

		return p_south;

	}

	private Image btIconResize(Image image) {
		return image.getScaledInstance(17, 17, Image.SCALE_SMOOTH);
	}

	public void compareStart() {
		compare = new Compare(this.usedData);
		compare.compareStart();
		timer = new TimerThread(la_time);
		timer.timerStart();
		usedData.duplicateFileView.removePanel();
		bt_start.setEnabled(false);
	}

	public void compareStop() {
		compare.compareStop();
		timer.timerStop();
		bt_start.setEnabled(true);
		compare = null;
		timer = null;
		System.gc();
	}

	public void setProgressBar() {
		int max = compare.getDataLength();
		int now = compare.getNowDataLength();
		int simi = compare.getSimiDataCnt();
		if (max != 0) {
			int per = now * 100 / max;
			progressBar.setValue(per);
			progressBar.setString(per + " %");
		}
		setProcessLabel(now, simi);

	}

	public void setProcessLabel(int progress, int simi) {
		la_progress_cnt.setText(progress + "");
		la_repect_cnt.setText(simi + "");
	}

	public void setProcessMaxLabel(int max) {
		la_total_cnt.setText(max + "");
	}

}
