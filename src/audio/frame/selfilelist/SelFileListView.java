/*
 * 선택한 파일 리스트를 보여주는 패널
 * */
package audio.frame.selfilelist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import audio.main.UsedData;

public class SelFileListView extends JPanel {
	UsedData usedData;
	ArrayList<File> selFilesList = new ArrayList<File>();
	ArrayList<Boolean> chkList;
	JButton bt_add, bt_delete;
	JPanel p_north;
	JTable table;
	AbstractTableModel model;

	JScrollPane tableScroll;

	public SelFileListView(UsedData usedData) {
		this.usedData = usedData;

		this.setLayout(new BorderLayout());
		chkList = new ArrayList<Boolean>(); // 선택했는지 체크하는 리스트
		table = new JTable(new SelFileListViewTableModel(selFilesList, chkList));

		// 첫번째 컬럼 크기 설정
		TableColumn column = table.getColumnModel().getColumn(0);
		int columnSize = 50;
		column.setPreferredWidth(columnSize);
		column.setMinWidth(columnSize);
		column.setMaxWidth(columnSize);

		tableScroll = new JScrollPane(table);

		bt_add = new JButton("폴더 추가");
		bt_delete = new JButton("폴더 삭제");
		p_north = new JPanel();

		p_north.add(bt_add);
		p_north.add(bt_delete);

		this.add(p_north, BorderLayout.NORTH);
		this.add(tableScroll);

		setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(10, 10)); // 패널 크기설정

		bt_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selFilesList.size() != 0) {
					removeList();
				}
			}
		});

		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File getFile = usedData.fileTreeView.getSelFile();
				if (getFile != null) {
					addList(getFile);
				}
			}
		});
	}

	// 리스트에 추가
	public void addList(File addFile) {
		// 리스트에 추가
		selFilesList.add(addFile);
		chkList.add(false);

		table.updateUI();
	}

	// 리스트에서 제거
	public void removeList() {
		int i = chkList.size() - 1;

		while (i >= 0) { // 끝에서부터 확인해서 체크되어 있으면 제거
			boolean chk = chkList.get(i);
			if (chk) {
				selFilesList.remove(i);
				chkList.remove(i);
			}
			i--;
		}

		table.updateUI();
	}

	public ArrayList<File> getSelFiles() {
		return selFilesList;
	}

}
