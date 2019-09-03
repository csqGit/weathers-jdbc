package com.bozpower.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bozpower.jdbc.Jdbc;


/**
 * 定时器采集数据
 * @author bozpower
 *
 */
public class MyServerThread extends Thread {

	private Socket socket;
	private String closeDeviceName;// 存储设备关闭的设备名称
//	private int state = 0;// 0离线

	public MyServerThread() {

	}

	public MyServerThread(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 得到设备名称
	 * 
	 * @return
	 */
	public String getDeviceName() {
		return closeDeviceName;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		InputStreamReader reader = null;
		OutputStream outputStream = null;
		OutputStreamWriter os = null;
		char[] cbuf = new char[1024];
		Jdbc jdbc = new Jdbc();
		int len = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			// 读取客户端发送的数据
			inputStream = socket.getInputStream();
			reader = new InputStreamReader(inputStream, "GBK");
			// 写，向客户端发送数据
			outputStream = socket.getOutputStream();
			os = new OutputStreamWriter(outputStream, "GBK");
			socket.setKeepAlive(true);  
			socket.setSoTimeout(70000);
			System.out.println("time:" + socket.getSoTimeout());
			
			
			Worker worker = new Worker();
			while ((len = reader.read(cbuf)) != -1) {
				String str = new String(cbuf, 0, len);
				worker.data(str, os);
				closeDeviceName = worker.getDeviceName();
			}
			
			System.out.println("【【设备离线】】: " + closeDeviceName + "\n, time:" + format.format(new Date()));
			jdbc.updateDeviceState(closeDeviceName, 0);
			closeConnection(os, outputStream, inputStream, reader);
			
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(os, outputStream, inputStream, reader);
			System.err.println("【设备离线】:" + closeDeviceName + "\n, time:" + format.format(new Date())  );
		}
	}

	private void closeConnection(OutputStreamWriter os, OutputStream outputStream, InputStream inputStream,
			InputStreamReader reader) {
		try {
			if(os != null)
				os.close();
			if(outputStream != null)
			outputStream.close();
			if(reader != null)
				reader.close();
			if(inputStream != null)
				inputStream.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}
