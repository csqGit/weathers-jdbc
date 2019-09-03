package com.bozpower.task;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.TimerTask;


public class WeatherTask extends TimerTask {
	private static int index = 0;
	private Socket socket;
//	private String deviceId;
//	private static int state = 0;

	public WeatherTask() {
	}

	public WeatherTask(Socket socket) {
		this.socket = socket;
	}
//
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}

	@Override
	public void run() {
		OutputStream outputStream = null;
		OutputStreamWriter os = null;
		InputStream inputStream = null;
		InputStreamReader reader = null;
		try {
//			System.out.println("线程名称：" + Thread.currentThread().getName());
//			System.out.println("socket是否关闭：" + socket.isClosed());

			if(!socket.isClosed()) {
				inputStream = socket.getInputStream();
				reader = new InputStreamReader(inputStream, "GBK");
				char[] cbuf = new char[1024];
				index++;
				System.out.println("index:" + index);
				
				outputStream = socket.getOutputStream();
				os = new OutputStreamWriter(outputStream, "GBK");
				String sendStr = index + " : hello world \n";
				os.write(sendStr);
				os.flush();
				
				int len = reader.read(cbuf);
				String clientStr = new String(cbuf, 0, len);
				System.out.println("【心跳】：" + clientStr);
				index = 1;
			}else {
//				Thread.currentThread().stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void closeConnection(OutputStreamWriter os, OutputStream outputStream, InputStream inputStream,
//			InputStreamReader reader) {
//		try {
//			os.close();
//			outputStream.close();
//			reader.close();
//			inputStream.close();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
//	}
}
