package com.bozpower.task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class WeatherTaskLx extends TimerTask {

	private Socket socket;

	private OutputStream outputStream = null;

	private int index = 0;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// 下发数据
	byte[] b = { 1, 4, 0, 31, 0, 1, 0, 12 };

	public WeatherTaskLx() {

	}

	public WeatherTaskLx(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		// 读取客户端发送的数据
				try {
					if(!socket.isClosed()) {
						// 写，向客户端发送数据
						outputStream = socket.getOutputStream();
						// 要数据
						outputStream.write(b);
						System.out.println("定时：" + index + "\t线程号：" + Thread.currentThread().getName() 
								+ "\ttime:" + format.format(new Date()));
						index++;
						
					}else {
						index = 0;
						System.out.println("socket已经关闭");
						this.cancel();
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						outputStream.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

		

	}

}
