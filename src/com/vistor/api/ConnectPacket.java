package com.vistor.api;

import com.vistor.common.util.SocketClient;

public class ConnectPacket {

	private static ConnectPacket instance;

	private  ConnectPacket(){
		
	}

	public static ConnectPacket getConnectInstance(){
		if(instance == null){
			instance  = new ConnectPacket();
		}
		return instance;
	}

	public static SocketClient ConnectServer(){
		return new SocketClient(SocketClient.SERVER_IP,SocketClient.SERVER_PORT_PEOPLE);
	}
	
	public String getPeopleNumber(){
		
		
		return null;
	}
}
