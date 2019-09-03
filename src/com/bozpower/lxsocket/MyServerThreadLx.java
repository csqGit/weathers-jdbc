package com.bozpower.lxsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bozpower.entity.Device;
import com.bozpower.entity.Weathers;
import com.bozpower.jdbc.Jdbc;
import com.bozpower.util.Crc16;

/**
 * 
 * @author bozpower
 *
 */
public class MyServerThreadLx extends Thread {

	private Socket socket;
	private String closeDeviceName;// 存储设备关闭的设备名称
//	private int state = 0;// 0离线
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Jdbc jdbc = new Jdbc();

	InputStream inputStream = null;
	InputStreamReader reader = null;
	OutputStream outputStream = null;
	OutputStreamWriter os = null;
	Timer timer = null;

	// 下发数据
	byte[] b = { 1, 4, 0, 31, 0, 1, 0, 12 };

	private int index = 0;

	public MyServerThreadLx() {

	}

	public MyServerThreadLx(Socket socket) {
		this.socket = socket;
		
		timer = new Timer();
		timer.schedule(new MyTask(), 0, 60000);

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
		try {
			// 读取客户端发送的数据
			inputStream = socket.getInputStream();
			reader = new InputStreamReader(inputStream, "GBK");
			// 写，向客户端发送数据
			outputStream = socket.getOutputStream();
			os = new OutputStreamWriter(outputStream, "GBK");

			int len = 0;
			while (true) {
//				socket.sendUrgentData(0xff);
					byte[] readByte = new byte[1024];
					if (!socket.isClosed()) {
						socket.setSoTimeout(35000);
						len = inputStream.read(readByte);
						if(len == -1) {
							socket.close();
							timer.cancel();
							System.out.println("设备离线\t\ttime:" + format.format(new Date()));
							break;
						}
						// 数据处理
						for (int i = 0; i < len; i++) {
							System.out.print(Integer.valueOf(readByte[i] & 0xff) + "*");
						}
						processData(readByte);
						
					} else {
						break;
					}
			}
				
		} catch (Exception e) {
			System.out.println("设备离线\t\ttime:" + format.format(new Date()));
			if(!socket.isClosed())
				try {
					socket.close();
					timer.cancel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		}

	}
	
	


	/**
	 * 处理数据 01 04 02 02 02 39 91
	 * 
	 * @param readByte
	 * @return
	 */
	private int processData(byte[] readByte) {
		int index = 0;
		String time = format.format(new Date());

		// 数据处理
		byte[] newByte = new byte[7];
		for (int j = 0; j < readByte.length - 1; j++) {
			if (readByte[j] == 4 && readByte[j + 1] == 2) {

				for (int k = 0; k < newByte.length; k++) {
					newByte[k] = readByte[j - 1];
					j++;

					if (j > readByte.length) {
						return 0;
					}
				}
				break;
			}
			if ((readByte[j] == 120) && (readByte[j + 1] == 105) && (readByte[j + 2] == 110)) {
				System.out.println("心跳包,time：" + time + "\t");
			}
		}

		// 数据校验
		boolean flag = Crc16.getCrc16(newByte);
		if (flag) {

			int tmp = 0;
			tmp = (newByte[3] & 0xff) << 8;
			tmp += newByte[4] & 0xff;
			double ta = tmp / 10.0;

			Weathers w = new Weathers();
			Device device = new Device();
			w.setTime(time);
			w.setTa(ta);

			device.setDeviceName("$A0XDR");
			w.setDeviceId(device);
			System.out.println("\n温度：" + ta + "，time：" + time + "\n");
			jdbc.insertDataTa(w);
			return 2;
		} else {
			System.out.println("数据校验不通过！\ttime：" + time + "\n");
		}
		return index;
	}

	private class MyTask extends TimerTask {

		@Override
		public void run() {
			// 读取客户端发送的数据
			try {
				if (!socket.isClosed()) {
					// 写，向客户端发送数据
					outputStream = socket.getOutputStream();
					// 要数据
					outputStream.write(b);
					System.out.println("定时：" + index + "\t线程号：" + Thread.currentThread().getName() + "\ttime:"
							+ format.format(new Date()));
					index++;

				} else {
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

}
