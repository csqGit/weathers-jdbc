package com.bozpower;

import java.io.UnsupportedEncodingException;

public class Test {
	
	public static void testByteToString() throws UnsupportedEncodingException {
		byte[] b = {-28, -72, -83, -27, -101, -67, -17, -68, -116, -28, -67, -96, -27, -91, -67};
		StringBuffer sb = new StringBuffer();
		sb.append(new String(b,0, b.length, "UTF-8"));
		System.out.println((new String(b,0, b.length, "UTF-8")));
		System.out.println(sb);
		byte [] bb = "23.5".getBytes();
		for(byte b2:bb) {
			System.out.print(b2  );
			System.out.print(", ");
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		testByteToString();
	}

}
