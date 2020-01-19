package org.server;
import java.net.DatagramSocket;
import org.client.Client;

public class Server {
	private Client client;
	public Server(String name, DatagramSocket sourceSocket) {
		this.client = new Client(name, sourceSocket, null);
	}

	public void messaging() { //обмен сообщениями
		this.client.messaging();
	}

}