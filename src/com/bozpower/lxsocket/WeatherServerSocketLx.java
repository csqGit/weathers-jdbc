package com.bozpower.lxsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.bozpower.lxsocket.MyServerThreadLx;

/**
 * 建立设备的连接
 * @author bozpower
 *
 */
public class WeatherServerSocketLx {
	
	public WeatherServerSocketLx() {
		
	}
	
	public WeatherServerSocketLx(Integer port) {
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
					//客户端与服务器建立连接
					socket = serverSocket.accept();
					System.out.println("服务器已建立连接\n");
					//创建定时器
//					Timer timer = new Timer();
//					timer.schedule(new WeatherTaskLx(socket), 0 , 60000);
					//开启线程
					new MyServerThreadLx(socket).start();
					
				}

			} catch (Exception e) {
				e.printStackTrace();
				if(!socket.isClosed())
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}
}
