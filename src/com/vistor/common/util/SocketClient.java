package com.vistor.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.vistor.common.AppUtil;

public class SocketClient{

	public static final String SERVER_IP = "121.40.179.150";
	
	public static final int SERVER_PORT_PEOPLE = 10001;
	
	private Socket socket; 
	private InputStream mInputStream;                
	private PrintWriter mPrintWriter; 
	private OutputStream mOutputStream;
	
	private String mIpAddress;
	private int mPort;
	

	public SocketClient(String ipAddress,int port){
		mIpAddress = ipAddress;
		mPort = port;
	}
	
	int num = 0;
	public boolean connection(){ 
		boolean bRet = false; 
		try{ 
			Thread.sleep(100);
			InetAddress ia = InetAddress.getByName(mIpAddress); 
			socket = new Socket(ia, mPort);
			mOutputStream = socket.getOutputStream();
			mPrintWriter = new PrintWriter(mOutputStream, true); 
			socket.setSoTimeout(3000);
			mInputStream = socket.getInputStream();        	 
			bRet = true; 
		} catch (Exception e){ 
			e.printStackTrace();
			num ++;
			if(num < 10){
				connection();
			}else{
				num = 0;
				bRet = false;
			}
		} 
		return bRet; 
	} 

	public boolean checkSocketConnection(){
		boolean bRet = false;
		try {
			AppUtil.sleep(100);
			InetAddress ia = InetAddress.getByName(mIpAddress); 
			socket = new Socket(ia, mPort);
		} catch (IOException e) {
			e.printStackTrace();
			num ++;
			if(num < 30){
				checkSocketConnection();
			}else{
				num = 0;
				bRet = false;
			}
		}
		try {
			mPrintWriter = new PrintWriter(socket.getOutputStream(), true); 
			socket.setSoTimeout(3000);
			mInputStream = socket.getInputStream();        	 
			bRet = true; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bRet;
	}

	public void sendMsg(String msg){ 
		if(mPrintWriter != null){
			if(AppUtil.isNotEmpty(msg)){
				mPrintWriter.print(msg); 
			}
			mPrintWriter.flush(); 
		}
	} 
	
	public void sendMsg(byte[] bytes){
		if(mOutputStream != null){
			try {
				mOutputStream.write(bytes);
				mOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public MsgContent readMsg(){ 
		MsgContent msgContent = new MsgContent();
		try{ 
			int reCount = mInputStream.read(msgContent.msg); 
			msgContent.len = reCount; 
		} catch (Exception e){ 
			return null;
		} 
		return msgContent;
	} 


	public void close(){ 
		try{ 
			socket.close(); 
		} catch (Exception e){ 
		} 
	} 
} 