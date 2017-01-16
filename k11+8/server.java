/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k11;

import java.io.*;
import java.net.*;

/**
 *
 * @author Cirben
 */
public class server extends Thread {

	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;

	server() {
	}

	@Override
	public void run() {
		try {
			providerSocket = new ServerSocket(6736, 10);
			System.out.println("Waiting for connection");
			connection = providerSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());

			sendMessage("hi");
			do {
				try {
					message = (String) in.readObject();
					if (message.equals("bye")) {
						sendMessage("bye");
					} else if (message.equals("init")) {
						KeyPair kp = new KeyPair();
						sendMessage(kp);
					} else if (message.equals("encrypt1")) {
						message = (String) in.readObject();
						PublicKey A = (PublicKey) in.readObject();
						String encrypted = Rsa.encrypt(message, A, 1);
						sendMessage(encrypted);
					} else if (message.equals("encrypt2")) {
						message = (String) in.readObject();
						PublicKey A = (PublicKey) in.readObject();
						String encrypted = Rsa.encrypt(message, A, 2);
						sendMessage(encrypted);
					} else if (message.equals("decrypt1")) {
						message = (String) in.readObject();
						PrivateKey B = (PrivateKey) in.readObject();
						String decrypted = Rsa.decrypt(message, B, 1);
						sendMessage(decrypted);
					} else if (message.equals("decrypt2")) {
						message = (String) in.readObject();
						PrivateKey B = (PrivateKey) in.readObject();
						String decrypted = Rsa.decrypt(message, B, 2);
						sendMessage(decrypted);
					}
				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}
			} while (!message.equals("bye"));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendMessage(KeyPair kp) {
		try {
			out.writeObject(kp);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
