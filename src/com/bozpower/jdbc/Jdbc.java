package com.bozpower.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bozpower.entity.Weathers;

public class Jdbc {
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/db_weather?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT";
	private static String USER = "root";
	private static String PASSWORD = "Bozpower123#";
	private static Connection connection;

	public Jdbc() {
//		getConnection();
	}

	/**
	 * 获取数据库连接
	 */
	public  Connection getConnection() {
		try {
			if (connection != null) {
				return connection;
			} else {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
//			System.out.println("数据库连接成功");
		} catch (Exception e) {
			System.err.println("数据库连接失败getConnection()");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 新增数据
	 */
	public  void insertData(Weathers w) {
		String sql = "insert into weathers_tb ( dm,   sm,  ta, ua, pa, time, company_id, device_id)"//
				+ " values ( ?, ?, ?, ?, ?, ?,?,? )";//
		try {
			Connection conn = this.getConnection();
			if (conn != null) {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				// 新增传感器 信息
				pstmt.setDouble(1, w.getDm());
				pstmt.setDouble(2, w.getSm());//
				pstmt.setDouble(3, w.getTa());
				pstmt.setDouble(4, w.getUa());
				pstmt.setDouble(5, w.getPa());
				pstmt.setString(6, w.getTime());// 新增时间信息
				pstmt.setInt(7, w.getCompanyId().getId());
				pstmt.setString(8, w.getDeviceId().getDeviceId());
				int result = pstmt.executeUpdate();
				if (result == 1)
					System.out.println("【采集成功】\n");
				else
					System.out.println("【采集失败】\n");
			}
		} catch (SQLException e) {
			System.err.println("新增数据错误insertData()");
			e.printStackTrace();
		}
	}
	
	public  void insertDataTa(Weathers weathers) {
		String sql = "insert into weathers_tb (ta,time, company_id, device_id)"//
				+ " values ( ?, ?, ?, ? )";//
		try {
			Connection conn = this.getConnection();
			if (conn != null) {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				// 新增传感器 信息
				
				pstmt.setDouble(1, weathers.getTa());
				pstmt.setString(2, weathers.getTime());// 新增时间信息
				pstmt.setInt(3, 1);
//				pstmt.setString(4, "$A0XDR");
				pstmt.setString(4, weathers.getDeviceId().getDeviceName());
				int result = pstmt.executeUpdate();
				if (result == 1)
					System.out.println("【采集成功】\n");
				else
					System.out.println("【采集失败】\n");
			}
		} catch (SQLException e) {
			System.err.println("新增数据错误insertData()");
			e.printStackTrace();
		}
	}

	/*
	 * 更新设备的在线状态
	 */
	public void updateDeviceState(String deviceId, int state) {
		String sql = "update device_tb set device_state = ? where device_id = ?";
		try {
			Connection conn = this.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setString(2, deviceId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("更新设备在线状态异常：updateDeviceState()");
			e.printStackTrace();
		}
	}

}
