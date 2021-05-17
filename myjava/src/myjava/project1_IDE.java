package myjava;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class project1_IDE extends JFrame {
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private boolean debug = true;
	private String filename = "";
	private String javac; 
	private String java;
	private File pwd;

	
	final JFileChooser myFileChooser = new JFileChooser();
	
	String file_name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					project1_IDE frame = new project1_IDE();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public project1_IDE() {
		
		setTitle("new file");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 329);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project1_IDE frame = new project1_IDE();
				frame.setVisible(true); 
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intRet = myFileChooser.showOpenDialog(project1_IDE.this);
	            
	            if(intRet == JFileChooser.APPROVE_OPTION) {
	               try {
	                  String strLine;
	                  File myFile = myFileChooser.getSelectedFile();
	                  
	                  file_name=myFile.getName();
	                     // myFile(파일주소)에서 파일명 추출
	                  project1_IDE.this.setTitle(myFile.getName());
	                     // save 버튼 클릭시 불러온 파일명으로 타이틀 변경
	                  BufferedReader myReader = new BufferedReader(
	                        new FileReader(myFile.getAbsolutePath()));
	                  textArea.setText(myReader.readLine());
	                  while((strLine = myReader.readLine()) != null) {
	                     textArea.append("\n" + strLine);
	                  }
	                  myReader.close();
	               }catch(IOException ie) {
	                  System.out.println(ie+ "==> 입출력오류 발생");
	               }
	            }
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intRet = myFileChooser.showSaveDialog(project1_IDE.this);
				
				if(intRet == JFileChooser.APPROVE_OPTION) {
					try {
						File myFile = myFileChooser.getSelectedFile();
						
						file_name = myFile.getName();
						
						project1_IDE.this.setTitle(file_name);
						
						PrintWriter myWriter = new PrintWriter(new BufferedWriter(new FileWriter(myFile.getAbsolutePath())));
						myWriter.write(textArea.getText());
						myWriter.close();
						
					} catch(IOException ie) {
						System.out.println(ie+ "==>입출력 오류 발생");
					}
					
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Exit");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Edit");
		mnNewMenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Copy");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.copy();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Paste");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.paste();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Cut");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.cut();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenu mnNewMenu_2 = new JMenu("Copiler");
		mnNewMenu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuBar.add(mnNewMenu_2);
		
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Compile");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cmd = javac + " " + filename;
				textArea_1.setText(cmd + "\n");
				try {
				    Runtime rt = Runtime.getRuntime();
				    Process ps = rt.exec(cmd, null, pwd);
				    InputStream in = ps.getErrorStream(); 
				    byte buf[] = new byte[1024];
				    int n = 0;
				    boolean hasError = false;
				    while((n = in.read(buf)) != -1) {
					    textArea_1.append(new String(buf, 0, n));
				        hasError = true;
				    }
				    if ( !hasError ) textArea_1.append("No Error\n");
				    } catch( Exception ex ) {
				    if( debug ) ex.printStackTrace();
				    }
			    }
		});
		mnNewMenu_2.add(mntmNewMenuItem_7);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Run");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = filename.lastIndexOf(".");
				String className = filename.substring(0, index);
				String cmd = java + " " + className;
				textArea_1.setText(cmd + "\n");
				try {
				    Runtime rt = Runtime.getRuntime();
				    Process ps = rt.exec(cmd, null, pwd);
				    InputStream in = ps.getInputStream();
				    byte buf[] = new byte[1024];
				    int n = 0;
				    while((n = in.read(buf)) != -1) {
					    textArea_1.append(new String(buf, 0, n));
				    }
				    in.close();
				    in = ps.getErrorStream();
				    while((n = in.read(buf)) != -1) {
					    textArea_1.append(new String(buf, 0, n));
				    }
				    in.close();
				  } catch(Exception ex) { if (debug) ex.printStackTrace(); }
		   }
		});
		
		
		mnNewMenu_2.add(mntmNewMenuItem_8);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBounds(6, 6, 438, 273);
		contentPane.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		textArea = new JTextArea();
//		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		textArea_1 = new JTextArea();
//		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
	}
}
