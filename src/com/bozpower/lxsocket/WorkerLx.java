package com.bozpower.lxsocket;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bozpower.entity.Weathers;
import com.bozpower.jdbc.Jdbc;
import com.bozpower.util.Utils;

public class WorkerLx {
	
	private String closeDeviceName ;
	private static int state = 0;
	private Jdbc jdbc = new Jdbc();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//处理上传数据
	public void data(String dataStr, OutputStreamWriter os, OutputStream outputStream) {
		try {
			Weathers weathers = null;
			
			
			String time = format.format(new Date());
			closeDeviceName = dataStr.split(",")[0];
			if (state == 0) {
				jdbc.updateDeviceState(closeDeviceName, 1);
				state = 1;
				System.out.println("【【设备上线】】：" + closeDeviceName + "\n");
			}
			System.out.println("【采集信息为】:" + dataStr + ",time:" + time +"\n");
			
			if (dataStr.startsWith("0R0") && dataStr.length() > 20) {
				weathers = Utils.formatStr(dataStr);
				jdbc.insertData(weathers);
			} else if ((dataStr.startsWith("$A0XDR") || dataStr.startsWith("$A0MWD")) && dataStr.length() > 20) {// 德国
				weathers = Utils.formatWeathersStr(dataStr);
				jdbc.insertData(weathers);
			} else {
//				String serverSpeak = "xintiao";
				byte [] b = {1, 4, 0, 31, 0, 1, 0, 12};
//				os.write(serverSpeak);
				outputStream.write(b);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//获取设备名称
	public String getDeviceName() {
		
		return closeDeviceName;
	}

}
