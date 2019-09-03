package com.bozpower;

import com.bozpower.lxsocket.WeatherServerSocketLx;
//import com.bozpower.jdbc.Jdbc;测试
//import com.bozpower.socket.WeatherServerSocket;
 
public class SocketMain {
	// 存放设备的名称
	public static String [] deviceName = { "0R0", "$A0XDR" };
	// 存储n个设备的当前温度
	public static double [] taArray = new double[deviceName.length];
	//设置服务器连接端口号
	private static Integer port = 8003;
	
	
	public static void main(String[] args) {
		for (int i = 0; i < taArray.length; i++) {
			taArray[i] = 1000.0;
		}

		new WeatherServerSocketLx(port);
	}

}
