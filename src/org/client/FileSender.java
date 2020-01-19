package org.client;


import java.net.Socket;
import java.net.InetAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Closeable;

import org.console.Menu;


public class FileSender implements Closeable {

	private Socket socket;

	private File file;

	private Menu console;


	public FileSender(InetAddress address, int port, File file, Menu console) throws IOException {
		this.socket = new Socket(address, port);

		this.file = file;

		this.console = console;
	}


	public void close() throws IOException {
		if(!this.socket.isClosed()) {
			this.socket.close();
		}
	}


	public void sendFile() {
		try(
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(this.file));
		) {
			this.console.printSystemMessage("Begin sending file.", null);

			BufferedOutputStream bos = new BufferedOutputStream(this.socket.getOutputStream());
			byte[] temp = new byte[64];
			for(int i = 0; i < file.getName().length(); i++){
				temp[i] = (byte) file.getName().charAt(i);
			}
			bos.write(temp, 0, 64);

			int bufferSize = 1024;
			byte[] data = new byte[bufferSize];
			int length;

			while((length = bis.read(data, 0, bufferSize)) != -1) {
				bos.write(data, 0, length);
			}

			bos.flush();

			this.console.printSystemMessage("End sending file.", null);
			this.console.printSystemMessage("Complete.", null);
		}
		catch(IOException exception) {
			if(!this.socket.isClosed()) {
				this.console.printSystemMessage("Error sending file.", exception);
			}
		}
	}

}