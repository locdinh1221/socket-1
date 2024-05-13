package socket1;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

		Thread Server = new Thread(new Server());
		Server.start();
		Client b = new Client();
		b.setVisible(true);
		
	}
}
