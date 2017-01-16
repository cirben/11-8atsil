/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;

/**
 *
 * @author Cirben
 */
public class K11 {

	K11() {
	}

	static void sendMessage(String msg, ObjectOutputStream out) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	static void sendMessage(PublicKey msg, ObjectOutputStream out) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	static void sendMessage(PrivateKey msg, ObjectOutputStream out) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		server s = new server();
		s.start();

		Socket requestSocket = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		String message = "";
		try {
			requestSocket = new Socket("localhost", 6736);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		KeyPair kp = null;
		message = (String) in.readObject();
		if (message.equals("hi")) {
			for (int h = 1; h < 3; h++) {
				System.out.println("Podaj ciąg znaków do zakodowania");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String text = br.readLine();
				System.out.print("Wczytany: " + text + "\n");
				sendMessage("init",out);
				kp = (KeyPair) in.readObject();
				sendMessage("encrypt" + h,out);
				sendMessage(text,out);
				sendMessage(kp.getA(),out);
				String encrypted = (String) in.readObject();
				System.out.print("Zakodowany: " + encrypted + "\n");
				sendMessage("decrypt" + h,out);
				sendMessage(encrypted,out);
				sendMessage(kp.getB(),out);
				String decrypted = (String) in.readObject();
				System.out.print("odkowany: " + decrypted + "\n");
				System.out.print("__________________________________________________________\n");
			}
		}
		sendMessage("bye",out);
		try {
			in.close();
			out.close();
			requestSocket.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
