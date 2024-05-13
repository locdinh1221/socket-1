package socket1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Client extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt;
	private JTextArea messages;
	private JButton send;
	
	Socket socket;
	DataInputStream dataInputStream ;
	DataOutputStream dataOutputStream;
	Scanner sc;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Client() throws UnknownHostException, IOException {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		messages = new JTextArea();
		messages.setBounds(10, 10, 416, 199);
		contentPane.add(messages);
		
		txt = new JTextField();
		txt.setBounds(10, 219, 337, 34);
		contentPane.add(txt);
		txt.setColumns(10);
		
		send = new JButton("SEND");
		send.setBounds(352, 219, 74, 34);
		contentPane.add(send);
		send.addActionListener(this);
		
		socket = new Socket("localhost",8888);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		sc = new Scanner(System.in);
		getServerMessages();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("SEND"))
		{
			try 
			{
				String str = txt.getText();
				dataOutputStream.writeUTF(str);
				messages.setText("\n client: "+str);
				txt.setText("");
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			if(txt.getText().equals("!exit"))
			{
				
				try {
					dataInputStream.close();
					dataOutputStream.close();
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}
	
	
	
	 public void getServerMessages() {
	        Thread thread = new Thread(() -> {
	            try {
	                while (true) {
	                    String str2 = dataInputStream.readUTF();
	                    messages.append("\n server : " + str2);
	                    messages.updateUI();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	        thread.start();
	    }
}
