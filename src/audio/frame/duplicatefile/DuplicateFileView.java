/*
 * 중복 검사 시 중복된 파일 보여주는 패널
 * */
package audio.frame.duplicatefile;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import audio.main.UsedData;

public class DuplicateFileView extends JPanel {
	JScrollPane jScrollPane;
	UsedData usedData;
	HashMap<Integer, ArrayList<File>> dupliFileData;
	ArrayList<FileViewPanel> panelList;
	JButton bt_autoSelect;
	JButton bt_selFileDelete;
	JPanel p_north, p_center;
	GridBagConstraints gbc;
	int lineNum;
	int indexMaxCnt;

	public DuplicateFileView(UsedData usedData) {
		this.usedData = usedData;
		this.setLayout(new BorderLayout());
		panelList = new ArrayList<FileViewPanel>();
		gbc = new GridBagConstraints();

		p_north = new JPanel();
		p_center = new JPanel();
		jScrollPane = new JScrollPane(p_center);
		p_center.setLayout(new GridBagLayout());
		bt_autoSelect = new JButton("자동선택");
		bt_selFileDelete = new JButton("선택삭제");
		p_north.add(bt_autoSelect);
		p_north.add(bt_selFileDelete);

		this.add(p_north, BorderLayout.NORTH);
		this.add(jScrollPane);

		bt_selFileDelete.addActionListener((e) -> {
			selFileDelete();
		});

		bt_autoSelect.addActionListener((e) -> {
			autoSelect();
		});

	}

	// 패널에 중복된 파일 보여주는 메서드
	public void setDupliPanel() {
		// 담는 공간 초기화
		p_center.removeAll();
		System.gc();
		lineNum = 0;
		int index = 0;

		while (panelList.size() > 0) {
			panelList.remove(0);
		}

		for (Integer key : dupliFileData.keySet()) {
			if (dupliFileData.get(key).size() > 1) {// 중복파일이 2개이상 있을때만 동작
				addPanel(key, index);// 2개 이상 있으면 패널에 추가
			}
			index++;
		}
		indexMaxCnt = index - 1;
		
		
		// 아래 빈 공간 채우기
		JPanel tmpPanel = new JPanel();
		tmpPanel.add(new JLabel(" "));
		gbc.gridx = 0;
		gbc.gridy = lineNum;
		gbc.weightx = 1;
		gbc.weighty = 20;
		gbc.fill = GridBagConstraints.VERTICAL;
		p_center.add(tmpPanel, gbc);
		

		updateUI();
	}

	// 현재 패널에 중복된 음악 파일 패널을 추가함
	public void addPanel(int key, int index) {
		ArrayList<File> fileList = dupliFileData.get(key);
		for (int i = 0; i < fileList.size(); i++) {
			FileViewPanel panel = new FileViewPanel(usedData, fileList.get(i), key, index);
			panelList.add(panel);
			gbc.gridx = i;
			gbc.gridy = lineNum;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.NONE;
			p_center.add(panel, gbc);
		}
		lineNum++;

	}

	public void selFileDelete() {
		for (int i = panelList.size() - 1; i >= 0; i--) {
			FileViewPanel panel = panelList.get(i);
			if (panel.isSelected()) {// 선택되었을때만 동작
				// 맵에서 제거
				dupliFileData.get(panel.getKey()).remove(panel.getFile());
				// 파일 삭제
				panel.deleteFile();
				panelList.remove(i);

			}
		}

		// 패널 다시 불러옴
		setDupliPanel();
	}

	// 같은 인덱스에 있는 패널 중 고름
	public void autoSelect() {
		for (int i = 0; i <= indexMaxCnt; i++) {
			File maxFile = null;
			for (int j = 0; j < panelList.size(); j++) {
				if (panelList.get(j).getIndex() == i) { // 같은 인덱스인지 확인
					File nowFile = panelList.get(j).getFile();
					if (maxFile == null) {
						maxFile = nowFile;
					} else if (maxFile.length() < nowFile.length()) {
						maxFile = nowFile;
					}

				}
			}
			// 파일 크기 제일 큰 파일 제외하고 선택
			for (int j = 0; j < panelList.size(); j++) {
				if (panelList.get(j).getIndex() == i) {
					if (panelList.get(j).getFile() != maxFile) {
						panelList.get(j).setSelected(true);
					} else {
						panelList.get(j).setSelected(false);
					}
				}
			}
		}

	}

	public void setDupliFileData(HashMap<Integer, ArrayList<File>> map) {
		this.dupliFileData = map;
		setDupliPanel();
	}
	
	public void removePanel() {
		p_center.removeAll();
		dupliFileData = null;
		updateUI();
	}

}
