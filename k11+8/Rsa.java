/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k11;

import java.math.BigInteger;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cirben
 */
public class Rsa {

	static String encrypt(String text, PublicKey key, int method) {
		long startTime = System.nanoTime();
		BigInteger bytes = new BigInteger(text.getBytes());
		BigInteger a = key.getA();
		BigInteger b = key.getB();
		if (method == 1) { // potegowanie szybkie logn
			BigInteger ret = BigInteger.ONE;
			while (a.compareTo(BigInteger.ZERO) == 1) {

				BigInteger tmp = a.mod(new BigInteger("2"));
				if (tmp.equals(BigInteger.ONE)) {
					ret = ret.multiply(bytes).mod(b);
				}
				bytes = bytes.pow(2).mod(b);
				a = a.shiftRight(1);

			}
			long elapsedTime = System.nanoTime() - startTime;
			//System.out.println(elapsedTime);
			long cap = 1234567898;
			long toWait = cap - elapsedTime;
			if (toWait < 0) {
				toWait = cap - (toWait * (-1)) % cap;
			}
			//System.out.println(toWait);
			try {
				Thread.sleep(toWait / 1000000);
			} catch (InterruptedException ex) {
				Logger.getLogger(KeyPair.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("### encrypted in: " + (System.nanoTime() - startTime));
			return ret.toString();
		} else { //tw o resztach n^2
			Stack<BigInteger> stack = new Stack<>();
			stack.push(bytes.mod(b));
			BigInteger l = bytes.mod(b);
			BigInteger z = new BigInteger("2");
			while (z.compareTo(a) <= 0) {
				l = (l.pow(2)).mod(b);
				z = z.shiftLeft(1);
				stack.push(l);
			}
			BigInteger tmp = z;
			z = z.shiftRight(2);
			tmp = tmp.shiftRight(1);
			a = a.subtract(tmp);
			BigInteger ret = stack.pop();
			while (a.compareTo(BigInteger.ZERO) == 1) {
				while (z.compareTo(a) == 1) {
					stack.pop();
					z = z.shiftRight(1);
				}
				a = a.subtract(z);
				z = z.shiftRight(1);
				BigInteger last = stack.pop();
				BigInteger tmp2 = ret.multiply(last);
				ret = tmp2.mod(b);
			}
			long elapsedTime = System.nanoTime() - startTime;
			//System.out.println(elapsedTime);
			long cap = 1234567898;
			long toWait = cap - elapsedTime;
			if (toWait < 0) {
				toWait = cap - (toWait * (-1)) % cap;
			}
			//System.out.println(toWait);
			try {
				Thread.sleep(toWait / 1000000);
			} catch (InterruptedException ex) {
				Logger.getLogger(KeyPair.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("### encrypted in: " + (System.nanoTime() - startTime));
			return ret.toString();
		}
	}

	static String decrypt(String text, PrivateKey key, int method) {
		long startTime = System.nanoTime();
		BigInteger bytes = new BigInteger(text);
		BigInteger a = key.getA();
		BigInteger b = key.getB();

		if (method == 1) { // potegowanie szybkie
			BigInteger ret = BigInteger.ONE;
			while (a.compareTo(BigInteger.ZERO) == 1) {

				BigInteger tmp = a.mod(new BigInteger("2"));
				if (tmp.equals(BigInteger.ONE)) {
					ret = ret.multiply(bytes).mod(b);
				}
				bytes = bytes.pow(2).mod(b);
				a = a.shiftRight(1);

			}
			long elapsedTime = System.nanoTime() - startTime;
			//System.out.println(elapsedTime);
			long cap = 1234567898;
			long toWait = cap - elapsedTime;
			if (toWait < 0) {
				toWait = cap - (toWait * (-1)) % cap;
			}
			//System.out.println(toWait);
			try {
				Thread.sleep(toWait / 1000000);
			} catch (InterruptedException ex) {
				Logger.getLogger(KeyPair.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("### decrypted in: " + (System.nanoTime() - startTime));
			return new String(ret.toByteArray());
		} else {
			Stack<BigInteger> stack = new Stack<>();
			stack.push(bytes.mod(b));
			BigInteger l = bytes.mod(b);
			BigInteger z = new BigInteger("2");
			while (z.compareTo(a) <= 0) {
				l = (l.pow(2)).mod(b);
				z = z.shiftLeft(1);
				stack.push(l);
			}
			BigInteger tmp = z;
			z = z.shiftRight(2);
			tmp = tmp.shiftRight(1);
			a = a.subtract(tmp);
			BigInteger ret = stack.pop();
			while (a.compareTo(BigInteger.ZERO) == 1) {
				while (z.compareTo(a) == 1) {
					stack.pop();
					z = z.shiftRight(1);
				}
				a = a.subtract(z);
				z = z.shiftRight(1);
				BigInteger last = stack.pop();
				BigInteger tmp2 = ret.multiply(last);
				ret = tmp2.mod(b);
			}
			long elapsedTime = System.nanoTime() - startTime;
			//System.out.println(elapsedTime);
			long cap = 1234567898;
			long toWait = cap - elapsedTime;
			if (toWait < 0) {
				toWait = cap - (toWait * (-1)) % cap;
			}
			//System.out.println(toWait);
			try {
				Thread.sleep(toWait / 1000000);
			} catch (InterruptedException ex) {
				Logger.getLogger(KeyPair.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println("### decrypted in: " + (System.nanoTime() - startTime));
			return new String(ret.toByteArray());
		}
	}
}
