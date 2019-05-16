package com.chat.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ServerApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField msgText;
	private static JTextArea msgArea;
	private JButton btnSend;
	
	static ServerSocket serverSocket;  
    static Socket socket;  
    static DataInputStream dataInputStream;  
    static DataOutputStream dataOutputStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerApp frame = new ServerApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String msgin = "";  
        try  
        {  
            serverSocket = new ServerSocket(1201);  
            socket = serverSocket.accept();  
            dataInputStream = new DataInputStream(socket.getInputStream());  
            dataOutputStream = new DataOutputStream(socket.getOutputStream());  
            while(!msgin.equals("exit"))  
            {  
                msgin =dataInputStream.readUTF();  
                msgArea.setText(msgArea.getText().trim()+"\nClient: "+msgin);  
            }  
        }  
        catch(Exception e)  
        {  
        } 
	}

	/**
	 * Create the frame.
	 */
	public ServerApp() {
		setResizable(false);
		setTitle("Server App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 706, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msgText = new JTextField();
		msgText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnSend.doClick();
				}
			}
		});
		msgText.setBounds(53, 387, 418, 49);
		contentPane.add(msgText);
		msgText.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        try {
		        	String msgout = "";  
			        msgout = msgText.getText().trim(); 
			        msgArea.setText(msgArea.getText().trim()+"\nme: "+msgout); 
					dataOutputStream.writeUTF(msgout);
					msgText.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		});
		btnSend.setBounds(486, 387, 147, 49);
		contentPane.add(btnSend);
		
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		msgArea.setBounds(15, 16, 670, 333);
		contentPane.add(msgArea);
	}
}
