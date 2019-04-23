package audio.frame.progress.threads;

import javax.swing.JLabel;

public class TimerThread extends Thread {
	JLabel timerLabel;
	int sec;
	boolean flag = true;
	boolean addTime = true;
	boolean startFlag = false;

	public TimerThread(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	public void run() {
		startFlag = true;
		while (flag) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (addTime) {
				sec++;
			}
			setTimer(timerLabel, sec);

		}
	}

	public void timerStop() {
		flag = false;
	}

	public void timerPause() {
		addTime = false;
	}

	public void timerStart() {
		if (startFlag) {
			addTime = true;
		} else {
			this.start();
		}
	}

	private void setTimer(JLabel label, int time) {
		int hour = time / 3600;
		int minute = (time % 3600) / 60;
		int sec = time % 60;
		label.setText(setChar(hour) + " : " + setChar(minute) + " : " + setChar(sec));
	}

	private String setChar(int num) {
		String tmpStr = num + "";
		if (tmpStr.length() == 1) {
			tmpStr = "0" + tmpStr;
		}
		return tmpStr;
	}

}
