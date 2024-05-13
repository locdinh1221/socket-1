package socket2;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

		Thread Server = new Thread(new timeServer());
		Server.start();
		clock c = new clock();
		c.setVisible(true);
		
	}
}
