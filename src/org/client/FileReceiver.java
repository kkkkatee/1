package org.client;


import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Closeable;

import java.lang.Thread;

import org.console.Menu;


public class FileReceiver extends Thread implements Closeable {

	private ServerSocket server;

	private Menu console;

	public FileReceiver(int port, Menu console) throws IOException {
		this.server = new ServerSocket(port);
		this.console = console;
	}


	public void close() throws IOException {
		if(!this.server.isClosed()) {
			this.server.close();
		}
	}


	@Override
	public void interrupt() {
		super.interrupt();

		try {
			close();
		}
		catch(IOException ignored) {}
	}


	@Override
	public void run() {
		try {
			this.console.printSystemMessage("Begin receiving file.", null);

			Socket socket = this.server.accept();
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			byte[] filenameData = new byte[64];
			bis.read(filenameData, 0, 64);
			String filename = new String(filenameData).trim();
			File file = new File("C:\\Users\\kate\\Desktop" + "\\" + filename);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			int bufferSize = 1024;
			byte[] data = new byte[bufferSize];
			int length;

			while((length = bis.read(data, 0, bufferSize)) != -1) {
				bos.write(data, 0, length);
			}
			bos.flush();

			this.console.printSystemMessage("End receiving file.", null);
			this.console.printSystemMessage("Complete.", null);
		}
		catch(IOException exception) {
			if(!this.server.isClosed()) {
				this.console.printSystemMessage("Error receiving file." ,exception);
			}
		}

		try {
			close();
		}
		catch(IOException ignored) {}
	}

}