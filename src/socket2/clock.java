package socket2;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class clock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel time;
	
	Socket socket;
	DataInputStream dataInputStream ;
	DataOutputStream dataOutputStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clock frame = new clock();
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
	 * @throws InterruptedException 
	 */
	public clock() throws UnknownHostException, IOException, InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 220, 114);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		time = new JLabel("New label");
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setBounds(10, 10, 186, 57);
		contentPane.add(time);
		socket = new Socket("localhost",7777);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		sendRequestToServer();
	}
	
	
	 public void sendRequestToServer() throws InterruptedException {
	        Thread thread = new Thread(() -> {
	            try {
	                while (true) {
	                    String request = "time";
	                    dataOutputStream.writeUTF(request);
	                    String serverTimeRespone = dataInputStream.readUTF();
	                    System.out.println("Server's respone : "+serverTimeRespone);
	                    this.time.setText(serverTimeRespone);
	                    Thread.sleep(1000);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	        thread.start();
	     
	        
	    }
}
