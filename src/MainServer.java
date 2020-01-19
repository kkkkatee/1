import java.net.DatagramSocket;
import java.io.IOException;
import org.server.Server;

public class MainServer {
	public static void main(String[] args) {
		DatagramSocket socket;

		try {
			socket = new DatagramSocket(1000);
		}
		catch(IOException | RuntimeException exception) {
			System.out.println("SYSTEM: Incorrect argument\n" + exception + ".");
			return;
		}

		Server server = new Server("Server", socket);
		server.messaging();
	}
}
