package com.bozpower.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.bozpower.lxsocket.MyServerThreadLx;

/**
 * 建立设备的连接
 * @author bozpower
 *
 */
public class WeatherServerSocket {
	
	public WeatherServerSocket() {
		
	}
	
	public WeatherServerSocket(Integer port) {
		this.myServer(port);
	}
	
	// 启动线程，接收多客户端连接
		public void myServer(Integer port) {
			ServerSocket serverSocket = null;
			Socket socket = null;
			try {
				serverSocket = new ServerSocket(port);
				System.out.println("服务器已启动。。。\n" );
				while (true) {
					socket = serverSocket.accept();
					
					new MyServerThreadLx(socket).start();
					System.out.println("服务器已建立连接\n");
				}

			} catch (Exception e) {
				System.err.println("服务器异常myServer()");
				e.printStackTrace();
				if(socket != null)
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}
}
