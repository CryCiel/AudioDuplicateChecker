/*
 * 컴퓨터내의 파일 리스트를 보여주는 트리
 * 바로 붙여서 사용함
 * */
package audio.frame.filetree;

import java.io.File;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileTreeView extends JPanel {
	private FileSystemView fileSystemView;

	private JTree tree;
	private DefaultTreeModel treeModel;

	private File selFile;

	public File getSelFile() {
		return selFile;
	}

	public JTree getGui() {

		fileSystemView = FileSystemView.getFileSystemView();

		// the File tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(root);

		// 트리 클릭했을때 이벤트 출력해주는데
		TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
				showChildren(node);
				selFile = (File) node.getUserObject();
				// System.out.println(selFile.getAbsolutePath()); // 선택한 파일 경로
			}
		};

		// show the file system roots.
		File[] roots = fileSystemView.getRoots();
		for (File fileSystemRoot : roots) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
			root.add(node);
			File[] files = fileSystemView.getFiles(fileSystemRoot, true);
			for (File file : files) {
				if (file.isDirectory()) {
					node.add(new DefaultMutableTreeNode(file));
				}
			}
		}

		tree = new JTree(treeModel);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(treeSelectionListener);
		tree.setCellRenderer(new FileTreeCellRenderer());
		tree.expandRow(0);
		// tree.setVisibleRowCount(35);

		return tree;
	}

	private void showChildren(final DefaultMutableTreeNode node) {
		tree.setEnabled(false);

		SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() {
				File file = (File) node.getUserObject();
				if (file.isDirectory()) {
					File[] files = fileSystemView.getFiles(file, true);
					if (node.isLeaf()) {
						for (File child : files) {
							if (child.isDirectory()) {
								publish(child);
							}
						}
					}
				}
				return null;
			}

			protected void process(List<File> chunks) {
				for (File child : chunks) {
					node.add(new DefaultMutableTreeNode(child));
				}
			}

			protected void done() {
				tree.setEnabled(true);
			}
		};
		worker.execute();
	}

}
