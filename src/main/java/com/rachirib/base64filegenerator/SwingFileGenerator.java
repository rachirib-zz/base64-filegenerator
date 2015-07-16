package com.rachirib.base64filegenerator;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SwingFileGenerator {

	private JFrame frmBase;
	private JTextField txtExportFolder;
	private JFileChooser chooser;
	private JLabel lblExport;
	private JButton btnSearch;
	private JLabel lblFileName;
	private JTextField txtFileName;
	private JLabel lblBase64;
	private JTextArea txtBase64;
	private JButton btnGenerate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingFileGenerator window = new SwingFileGenerator();
					window.frmBase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingFileGenerator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBase = new JFrame();
		frmBase.setTitle("Base64 to File Utility");
		frmBase.setBounds(100, 100, 600, 300);
		frmBase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBase.getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		lblExport = new JLabel("Export to");
		frmBase.getContentPane().add(lblExport, "4, 6");

		java.io.File currentFolder = new java.io.File("");

		txtExportFolder = new JTextField(40);
		txtExportFolder.setEditable(false);
		txtExportFolder.setText(currentFolder.getAbsolutePath());
		frmBase.getContentPane().add(txtExportFolder, "6, 6");

		btnSearch = new JButton("Search...");
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.setDialogTitle("Export File to..");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setCurrentDirectory(new File(txtExportFolder.getText()));
				// disable the "All files" option.
				chooser.setAcceptAllFileFilterUsed(false);

				if (chooser.showOpenDialog(frmBase) == JFileChooser.APPROVE_OPTION) {
					txtExportFolder.setText(chooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		});
		frmBase.getContentPane().add(btnSearch, "8, 6");

		lblFileName = new JLabel("File Name And Extension");
		frmBase.getContentPane().add(lblFileName, "4, 8");

		txtFileName = new JTextField();
		JTextFieldLimiter jTextFieldLimiter = new JTextFieldLimiter(30);
		jTextFieldLimiter
				.addDocumentListener(new HighlightListener(txtFileName));
		txtFileName.setDocument(jTextFieldLimiter);
		frmBase.getContentPane().add(txtFileName, "6, 8");

		lblBase64 = new JLabel("Base64 String");
		frmBase.getContentPane().add(lblBase64, "4, 10");

		txtBase64 = new JTextArea(5, 60);
		txtBase64.setLineWrap(true);
		txtBase64.getDocument().addDocumentListener(
				new HighlightListener(txtBase64));
		JScrollPane scrollPane = new JScrollPane(txtBase64);
		frmBase.getContentPane().add(scrollPane, "6, 10, center, center");

		btnGenerate = new JButton("Generate");
		btnGenerate.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtBase64.getText().trim().isEmpty()
						|| txtFileName.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(frmBase,
							"Missing Required Fields", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				byte[] data = Base64.getDecoder().decode(txtBase64.getText());
				String destination = txtExportFolder.getText() + "/"
						+ txtFileName.getText();
				try (OutputStream stream = new FileOutputStream(destination)) {

					stream.write(data);

				} catch (IOException e) {
					JOptionPane.showMessageDialog(frmBase, e.getMessage(),
							"Error: Invalid Parsing", JOptionPane.ERROR_MESSAGE);
				}

				JOptionPane.showMessageDialog(frmBase,
						"It's been created successfully the file : "
								+ destination, "Successful Transformation",
						JOptionPane.INFORMATION_MESSAGE);
				txtFileName.setText("");
				txtBase64.setText("");
			}
		});
		frmBase.getContentPane().add(btnGenerate, "6, 14");
	}

}
