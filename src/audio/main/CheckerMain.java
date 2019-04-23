/*
 * 실행되는 메인 클래스
 * */
package audio.main;

import audio.frame.AudioCheckerFrame;

public class CheckerMain {
	public static void main(String[] args) {
		UsedData usedData = new UsedData();
		new AudioCheckerFrame(usedData);
	}

}
