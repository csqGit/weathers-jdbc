package com.bozpower.socket;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bozpower.entity.Weathers;
import com.bozpower.jdbc.Jdbc;
import com.bozpower.util.Utils;

/**
 * 定时器采集数据
 * @author bozpower
 *
 */
public class Worker {
	
	private String closeDeviceName ;
	private static int state = 0;
	private Jdbc jdbc = new Jdbc();
	
	
	//处理上传数据
	public void data(String dataStr, OutputStreamWriter os) {
		try {
			Weathers weathers = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			System.out.println("【采集信息为】:" + dataStr + ",time:" + time +"\n");
			closeDeviceName = dataStr.split(",")[0];
			if (state == 0 && closeDeviceName != null && !"".equals(closeDeviceName)) {
				System.out.println("【【设备上线】】：" + closeDeviceName + "\n");
				jdbc.updateDeviceState(closeDeviceName, 1);
				state = 1;
			}
			
			
			if (dataStr.startsWith("0R0") && dataStr.length() > 20) {
				weathers = Utils.formatStr(dataStr);
				jdbc.insertData(weathers);
			} else if ((dataStr.startsWith("$A0XDR") || dataStr.startsWith("$A0MWD")) && dataStr.length() > 20) {// 德国
				weathers = Utils.formatWeathersStr(dataStr);
				jdbc.insertData(weathers);
			} else {
				String serverSpeak = "xintiao";
				os.write(serverSpeak);
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
