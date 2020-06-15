package spring.waregg.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalIPAddress {
	public String getLocalIpAddress() {
		InetAddress local;
		String ip = "";
		try {
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ip;
	}
}
