package com.bozpower.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bozpower.SocketMain;
import com.bozpower.entity.Company;
import com.bozpower.entity.Device;
import com.bozpower.entity.Weathers;

public class Utils {
	public static String[] deviceName = SocketMain.deviceName;// 存放设备的名称
	public static double[] taArray = SocketMain.taArray;

	/**
	 * 将传感器采集的数据格式化
	 * 0R0,Dn=000D,Dm=333D,Dx=293D,Sn=000.0M,Sm=000.1M,Sx=000.5M,Ta=022.0C,Ua=086.6P,Pa=000956.6H
	 * Dn:最小风向，Dm:平均风向，Dx:最大风向 Sn:最小风速，Sm:平均风速，Sx:最大风速 Ta:温度 Ua:湿度 Pa:气压
	 * @param str
	 * @return
	 */
	public static Weathers formatStr(String str) {
		Weathers w = new Weathers();
		try {
			Map<String, String> map = new HashMap<String, String>();
			Company c = new Company();
			Device d = new Device();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			String str1[] = str.split(",");
			for (int i = 1; i < str1.length; i++) {
				String[] str2 = str1[i].split("=");
				map.put(str2[0], str2[1].substring(0, str2[1].length() - 1));
			}
			double thisTa = Double.parseDouble(map.get("Ta"));
//			double ta = getTa(str1[0], thisTa);
			// 传感器数据
			w.setDm(Double.parseDouble(map.get("Dm")));
			w.setSm(Double.parseDouble(map.get("Sm")));
			w.setUa(Double.parseDouble(map.get("Ua")));
			double pa = Double.parseDouble(map.get("Pa").substring(0, 8));
			w.setPa(pa);
			w.setTa(thisTa);
			d.setDeviceId(str1[0]);// 设置设备编号
			c.setId(1);// 设置单位名称
			w.setDeviceId(d);
			w.setTime(time);
			w.setCompanyId(c);
		} catch (Exception e) {
			System.err.println("formatStr()异常");
			e.printStackTrace();
		}
		return w;
	}

	/**
	 * $A0XDR,P,0.9581,B,0,C,25.1,C,0,H,64.9,P,0$A0MWD,40.9,T,40.9,M,0.0,N,0.0,M
	 * 
	 * @param str
	 * @return
	 */
	public static Weathers formatWeathersStr(String str) {
		Weathers weathers = new Weathers();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			String[] strArr = str.split(",");
			// 压强
			DecimalFormat f = new DecimalFormat(".##");// 将压强格式化为.00的格式，去掉后面的特别长的部分
			String paStr = f.format(Double.parseDouble(strArr[2]) * 1000);
			double pa = Double.parseDouble(paStr);
			double thisTa = Double.parseDouble(strArr[6]);
//			double ta = getTa(strArr[0], thisTa);
			weathers.setPa(pa);// 压强
			weathers.setTa(thisTa);// 温度
			weathers.setUa(Double.parseDouble(strArr[10]));// 湿度
			weathers.setDm(Double.parseDouble(strArr[15]));// 风向
			weathers.setSm(Double.parseDouble(strArr[19]));// 风速
			Device device = new Device();
			String deviceid = strArr[0];
			device.setDeviceId(deviceid);// 设置设备编号
			weathers.setDeviceId(device);//
			Company c = new Company();
			c.setId(1);
			weathers.setCompanyId(c);// 设置单位名称
			weathers.setTime(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weathers;
	}

	/*
	 * 1、先找出设备， 2、再找出设备id 3、找出设备的上一条数据的温度
	 */
//	public static double getTa(String deviceId, double thisTa) {
//		int index = 0;
//		for (int i = 0; i < deviceName.length; i++) {// 获取上一条温度的记录的索引
//			if (deviceName[i].equals(deviceId)) {
//				index = i;
//				break;
//			}
//		}
//		if (taArray[index] == 1000.0) {// 表示初始温度为0，将第一条数据保存
//			taArray[index] = thisTa;
//		}
//		if (Math.abs((thisTa - taArray[index])) < 10) {// 如果小于10，没有突变，
//			taArray[index] = thisTa;// 将当前的温度作为最后一条数据的温度
//		}
//		return taArray[index];
//	}
}
