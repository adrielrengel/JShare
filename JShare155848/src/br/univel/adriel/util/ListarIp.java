package br.univel.adriel.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListarIp {
	public List<String> buscarIp(){
		List<String> addrList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();

			while (ifaces.hasMoreElements()) {
				NetworkInterface ifc = ifaces.nextElement();
				if (ifc.isUp()) {
					Enumeration<InetAddress> addresses = ifc.getInetAddresses();
					while (addresses.hasMoreElements()) {

						InetAddress addr = addresses.nextElement();

						String ip = addr.getHostAddress();
//validações para o IP
						if (ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
							addrList.add(ip);
						}

					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		return addrList;
	}
		
	}

}
