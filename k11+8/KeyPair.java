/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k11;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cirben
 */
public class KeyPair implements Serializable {

	PublicKey a;
	PrivateKey b;

	public PublicKey getA() {
		return a;
	}

	public PrivateKey getB() {
		return b;
	}

	public KeyPair(PublicKey A, PrivateKey B) {
		a = A;
		b = B;
	}

	public KeyPair() {
		long startTime = System.nanoTime();
		Random r = new Random(System.currentTimeMillis());
		BigInteger first = new BigInteger(512, 100, r);
		BigInteger second = new BigInteger(512, 100, r);
		//String as = a.toString();
		//String [] halfs = as.split("(?<=\\G.{512})");
		//BigInteger first = BigInteger(halfs[0]);
		//BigInteger second = BigInteger(halfs[1]);
		BigInteger mulmin = (first.subtract(BigInteger.ONE)).multiply(second.subtract(BigInteger.ONE));
		BigInteger mul = first.multiply(second); // to public
		BigInteger ret;
		BigInteger ret2 = BigInteger.ONE;  //to public
		BigInteger copy = mulmin;
		while (true) { //n
			copy = mulmin;
			ret = new BigInteger(mulmin.bitLength(), r);
			ret2 = ret;
			while (!ret.equals(BigInteger.ZERO)) {
				BigInteger tmp = ret;
				ret = copy.mod(ret);
				copy = tmp;
			}
			if (copy.equals(BigInteger.ONE)) {
				break;
			}
		}
		this.a = new PublicKey(ret2, mul);
		BigInteger a = mulmin;
		BigInteger b = BigInteger.ONE;
		BigInteger c = ret2;
		BigInteger d = BigInteger.ZERO; // to private 
		BigInteger e = mulmin;
		while (!c.equals(BigInteger.ZERO)) { //
			if (c.compareTo(e) == -1) {
				BigInteger tmp = b;
				b = d;
				d = tmp;
				tmp = c;
				c = e;
				e = tmp;
			}
			BigInteger q = c.divide(e);
			b = b.subtract(q.multiply(d));
			c = c.subtract(q.multiply(e));
		}
		if (d.compareTo(BigInteger.ZERO) == -1) {
			d = d.add(a);
		}
		this.b = new PrivateKey(d, mul);
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
		System.out.println("### Keys generated in: " + (System.nanoTime() - startTime));
	}
}
