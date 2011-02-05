package org.koffeinfrei.zueribad.utils;

import java.io.*;


public class StringSerializer {
	public static Serializable deserialize(String s) throws IOException,
			ClassNotFoundException {
		
		if(s == null) {
			return null;
		}
		
		byte[] data = Base64Coder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
				data));
		Object o = ois.readObject();
		ois.close();
		return (Serializable)o;
	}

	/** Write the object to a Base64 string. */
	public static String serialize(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return new String(Base64Coder.encode(baos.toByteArray()));

	}

}
