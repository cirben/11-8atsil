/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k11;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Cirben
 */
class PublicKey implements Serializable{
	BigInteger a;	
	BigInteger b;

	public PublicKey(BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
	}

	public PublicKey() {
	}

	public BigInteger getA() {
		return a;
	}

	public void setA(BigInteger a) {
		this.a = a;
	}

	public BigInteger getB() {
		return b;
	}

	public void setB(BigInteger b) {
		this.b = b;
	}
	
}
