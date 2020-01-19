import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import org.client.Client;

public class MainClient {
	public static void sendConnect(DatagramSocket sourceSocket, SocketAddress destinationSocket) {
		try {
			byte[] data = "@connect".getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);

			sourceSocket.send(packet);
		}
		catch(IOException exception) {
			System.out.println("SYSTEM: Error connection.");
		}
	}

	public static void main(String[] args) {
		DatagramSocket sourceSocket;
		InetSocketAddress destinationSocket;

		try {
			sourceSocket = new DatagramSocket();
			destinationSocket = new InetSocketAddress("localhost", 1000);
		}
		catch(IOException | RuntimeException exception) {
			System.out.println("SYSTEM: Incorrect argument\n" + exception + ".");
			return;
		}

		Client client = new Client("Client", sourceSocket, destinationSocket);
		sendConnect(sourceSocket, destinationSocket);
		client.messaging();
	}
}
