package audio.frame.progress.threads;

import audio.frame.progress.Compare;
import audio.main.UsedData;

public class ThreadSuper extends Thread {
	Compare compare;
	UsedData usedData;
	boolean endFlag = false;

	public ThreadSuper(Compare compare, UsedData usedData) {
		this.compare = compare;
		this.usedData = usedData;
	}

	public boolean isEndFlag() {
		return endFlag;
	}

}
