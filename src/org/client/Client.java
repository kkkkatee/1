package org.client;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.io.File;
import java.io.Closeable;
import java.io.IOException;
import org.console.Menu;


public class Client implements Closeable {
	private String name;
	private DatagramSocket sourceSocket;
	private SocketAddress destinationSocket;
	private Sender sender = null;
	private Receiver receiver = null;
	private boolean inputPath = false;
	private File file = null;

	public Client(String name, DatagramSocket sourceSocket, SocketAddress destinationSocket) {
		this.name = name;
		this.sourceSocket = sourceSocket;
		this.destinationSocket = destinationSocket;
	}

	public void close() {
		this.sourceSocket.close();
	}

	public boolean isClosed() {
		return this.sourceSocket.isClosed();
	}

	public DatagramSocket getSourceSocket() {
		return this.sourceSocket;
	}

	public int getPort() {
		return this.sourceSocket.getLocalPort();
	}

	public SocketAddress getDestinationSocket() {
		return this.destinationSocket;
	}

	public void setDestinationSocket(SocketAddress destinationSocket) {
		this.destinationSocket = destinationSocket;
	}

	public InetAddress getDestinationAddress() {
		InetSocketAddress inetSocketAddress = (InetSocketAddress)this.destinationSocket;
		return inetSocketAddress.getAddress();
	}

	public int getDestinationPort() {
		InetSocketAddress inetSocketAddress = (InetSocketAddress)this.destinationSocket;
		return inetSocketAddress.getPort();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Receiver getReceiver() {
		return this.receiver;
	}

	public Sender getSender() {
		return this.sender;
	}

	public boolean isInputPath() {
		return this.inputPath;
	}

	public void setInputPath(boolean inputPath) {
		this.inputPath = inputPath;

	}



	public File getFile() {
		return this.file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public void messaging() {
		Menu console = new Menu();

		this.receiver = new Receiver(this, console);
		this.sender = new Sender(this, console);

		this.receiver.start();
		this.sender.start();
	}

}
