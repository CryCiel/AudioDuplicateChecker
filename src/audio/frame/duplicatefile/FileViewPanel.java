/*
 * 중복된 파일 표시해주는 패널
 * 중복된 파일 정보도 가지고 있음
 * */
package audio.frame.duplicatefile;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import audio.frame.progress.module.ReadFile;
import audio.main.UsedData;

public class FileViewPanel extends JPanel {
	UsedData usedData;
	JLabel imgLabel;
	File file;
	Image mp3Img;
	JCheckBox selected;
	int key;
	int index;

	public FileViewPanel(UsedData usedData, File file, int key, int index) {
		this.usedData = usedData;
		this.file = file;
		this.key = key;
		this.index = index;

		this.setLayout(new BorderLayout());
		imgLabel = new JLabel(new ImageIcon(getImage(file)));

		selected = new JCheckBox();
		// 패널에 이미지 추가
		this.add(imgLabel, BorderLayout.NORTH);
		this.add(selected);

		imgLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (e.getClickCount()) {
				case 1:
					mouse1Click();
					break;
				case 2:
					mouse2Click();
					break;
				}
			}

			public void mouseEntered(MouseEvent e) {
				usedData.audioCheckerFrame.setFrmaeInfo(file.getAbsolutePath());
			}
		});

	}

	// 이미지 마우스 한번 클릭했을때 이벤트
	private void mouse1Click() {
		if (isSelected()) {
			setSelected(false);
		} else {
			setSelected(true);
		}
	}

	// 이미지 마우스 두번 클릭했을때 이벤트
	private void mouse2Click() {
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// mp3 파일에서 이미지 반환해주는 메서드
	private Image getImage(File file) {
		String ext = ReadFile.getFileExtension(file);
		BufferedImage img = null;
		if (ext.equals("mp3")) { // mp3 파일일 경우 동작
			try {
				Mp3File mp3 = new Mp3File(file);
				if (mp3.hasId3v2Tag()) {
					ID3v2 id3v2tag = mp3.getId3v2Tag();
					byte[] imageData = id3v2tag.getAlbumImage();
					try {
						img = ImageIO.read(new ByteArrayInputStream(imageData));
					} catch (NullPointerException e) { // mp3파일에 이미지 없을때 수행
						img = ImageIO.read(new File("res/empty_music_icon.png"));
					}
				}
			} catch (Exception e) { // 에러 발생시 기본 이미지 출력
				try {
					img = ImageIO.read(new File("res/empty_music_icon.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else { // wav파일은 기본 이미지 출력
			try {
				img = ImageIO.read(new File("res/empty_music_icon.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 이미지 크기 변경해서 리턴
		return img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	}

	public boolean isSelected() {
		boolean flag = true;
		if (selected.getSelectedObjects() == null) {
			flag = false;
		}
		return flag;
	}

	public void setSelected(boolean b) {
		selected.setSelected(b);
	}

	public int getKey() {
		return this.key;
	}

	public File getFile() {
		return this.file;
	}

	public void deleteFile() {
		UsedData.deleteFile(file);
	}

	public int getIndex() {
		return this.index;
	}
}
