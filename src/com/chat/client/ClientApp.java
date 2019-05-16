package com.chat.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField msgText;
	private static JTextArea msgArea;
	private JButton btnSend;
	
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
					ClientApp frame = new ClientApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		try  
        {  
            socket = new Socket("127.0.0.1",1201);  
            dataInputStream = new DataInputStream(socket.getInputStream());  
            dataOutputStream = new DataOutputStream(socket.getOutputStream());  
            String msgin = "";  
            while(!msgin.equals("Exit"))  
            {  
                msgin=dataInputStream.readUTF();  
                msgArea.setText(msgArea.getText().trim()+"\nServer: "+msgin);  
            }  
        }  
        catch(Exception e)  
        {  
  
        }  
	}

	/**
	 * Create the frame.
	 */
	public ClientApp() {
		setResizable(false);
		setTitle("Client App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 493);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setEditable(false);
		msgArea.setBounds(15, 16, 674, 322);
		contentPane.add(msgArea);
		
		msgText = new JTextField();
		msgText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
					btnSend.doClick();
				}
			}
		});
		msgText.setColumns(10);
		msgText.setBounds(53, 376, 418, 49);
		contentPane.add(msgText);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
		        	String msgout = "";  
			        msgout = msgText.getText().trim(); 
			        msgArea.setText(msgArea.getText().trim()+"\nme: " + msgout);  
					dataOutputStream.writeUTF(msgout);
					msgText.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}  
			}
		});
		btnSend.setBounds(486, 376, 147, 49);
		contentPane.add(btnSend);
	}

}
