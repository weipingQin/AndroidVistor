package com.vistor.api;

import com.vistor.common.util.SocketClient;

public class PeopleRequest {
	
	private static PeopleRequest request;
	
	private PeopleRequest(){
		
	}
	
	public PeopleRequest getPeopleInstance(){
		if(request == null){
			request = new PeopleRequest();
		}
		return request;
	}
	
	
	private SocketClient getSocketClient(){
		return new SocketClient(SocketClient.SERVER_IP,SocketClient.SERVER_PORT_PEOPLE);
	}
}
