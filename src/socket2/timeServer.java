package socket2;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;


public class timeServer extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("Server's Running");
	ServerSocket serverSocket;
	Socket socket ;
	Scanner sc ;
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					timeServer frame = new timeServer();
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
	 */
	public timeServer() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 169, 103);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 135, 46);
		contentPane.add(lblNewLabel);
	}
	
	public void runServer() throws IOException
	{
		serverSocket = new ServerSocket(7777);
		socket = serverSocket.accept();
		sc = new Scanner(System.in);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		Thread thread = new Thread(() -> {
			try {
				
		
					while (true)
					{
						// doc du lien tu` client truyen len
						String str = dataInputStream.readUTF();
						System.out.println("clock's request :" + str);
						
						if (str.equals("q")) {
							break;
						}
						if (str.equals("time"))
							{
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
								String currentTime = dateFormat.format(new Date());
								dataOutputStream.writeUTF(currentTime);
							}
					}
				
				
				dataInputStream.close();
				dataOutputStream.close();
				serverSocket.close();
				socket.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		 
		thread.start();
		
	}
	
	public void ouputThread()
	{
		  Thread outputThread = new Thread(() -> {
	            try {
	                while (true) {
	                	//truyen du lieu ve client
//						String str2 = sc.nextLine();
//						dataOutputStream.writeUTF(str2);
						dataOutputStream.flush();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });outputThread.start();
	}

	@Override
	public void run() {
		try {
			runServer();
			ouputThread();
			this.setVisible(true);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
