package com.paust.Telnet;

import java.net.*;
import java.lang.reflect.*;

/**
 * This URLStreamHandler uses reflection to make it flexible enough to start
 * URLConnections that do not yet exist. The URLConnection handler must be named
 * normally and be in either java.net or thor.net.
 * 
 * <i>The <a href=http://www.gnu.org/copyleft/lgpl.html>LGPL</a> applies to this
 * software. Unless otherwise stated the software is Copyright 1996,2000 Daniel
 * Kristjansson</i>
 */

public class URLStreamHandler extends java.net.URLStreamHandler {
	/**
	 * Returns a <i>Protocol</i>URLConnection where protocol is the protocol in
	 * java.net or thor.net. This would include
	 * <ul>
	 * <li>JarURLConnection
	 * <li>HttpURLConnection
	 * <li>TelnetURLConnection
	 * </ul>
	 */
	public URLConnection openConnection(URL u) throws java.io.IOException {
		String str = u.getProtocol().toLowerCase();
		String first = str.substring(0, 1).toUpperCase();
		String middle = str.substring(1).toLowerCase();
		String n1 = "java.net." + first + middle + "URLConnection";
		Class c;
		try {
			c = Class.forName("java.net." + first + middle + "URLConnection");
		} catch (Exception e) {
			try {
				c = Class.forName("com.paust.Telnet." + first + middle
						+ "URLConnection");
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println(e2.toString());
				return null;
			}
		}
		try {
			Constructor con = c.getConstructor(new Class[] { URL.class });
			return (URLConnection) con.newInstance(new Object[] { u });
		} catch (Exception e3) {
			e3.printStackTrace();
			System.out.println(e3.toString());
			return null;
		}
	}
}
