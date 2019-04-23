/*
 * 테이블 모델 정의한 클래스
 * */
package audio.frame.selfilelist;

import java.io.File;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class SelFileListViewTableModel extends AbstractTableModel {
	ArrayList<File> selFolderList;
	ArrayList<Boolean> chkList;
	String[] tableTitle = { "선택", "경로" };

	public SelFileListViewTableModel(ArrayList<File> selFolderList, ArrayList<Boolean> chkList) {
		this.selFolderList = selFolderList;
		this.chkList = chkList;

	}

	public int getRowCount() {
		return selFolderList.size();
	}

	public int getColumnCount() {
		return tableTitle.length;
	}

	// 컬럼 별 클래스 설정
	public Class getColumnClass(int col) {
		switch (col) {
		case 0:
			return Boolean.class;
		case 1:
			return String.class;
		default:
			return null;
		}
	}

	// 체크박스 부분만 수정 가능
	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 0:
			return true;
		default:
			return false;
		}

	}

	// 컬럼 명 출력
	public String getColumnName(int column) {
		return tableTitle[column];
	}

	// 값 가져오는거
	public Object getValueAt(int row, int col) {
		Object obj = null;
		if (col == 0) { // 체크리스트에서 true/false 출력
			obj = chkList.get(row);
		} else if (col == 1) { // 파일 리스트의 경로 출력
			obj = selFolderList.get(row).getAbsolutePath();
		}

		return obj;
	}

	public void setValueAt(Object aValue, int row, int col) {
		switch (col) {
		case 0:
			chkList.set(row, (Boolean) aValue);
		}
	}
}
