package com.bozpower.util;


public class Crc16 {
	static byte[] b = { 1,4,2,0,(byte) 0XDF,(byte) 0XF8,(byte) 0XA8 };
	
	public static void main(String[] args) {
//		byte [] bb = getCrc16(b);
		boolean flag = getCrc16(b);
		System.out.println(flag);
//		for(int i = 0; i < bb.length; i ++) {
//			System.out.print(bb[i] + ",");
//		}
//		byte[] readByte = {120,105,110,116,105,97,111,50,1,4,2,0,(byte) 0XDF,(byte) 0XF8,(byte) 0XA8,120,105,110,116,105,97,111,50,120,105,110,116,105,97,111,50};
//		byte [] newByte = new byte[7];
//		for(int j = 0; j < readByte.length; j ++) {
//			if(readByte[j] == 4 && readByte[j+1] == 2) {
//				for(int k = 0; k < newByte. length; k ++) {
//					newByte[k] = readByte[j + k - 1];
//				}
//				break;
//			}
//		}
//		
//		int tmp = 0;
//		tmp = (newByte[3] & 0xff) << 8;
//		tmp += newByte[4] & 0xff;
//		double ta = tmp / 10.0;
//		System.out.println(ta);
////		for(int i = 0; i < newByte.length; i ++) {
////			System.out.print((newByte[i] & 0XFF) + "-");
////		}
	}

//	public static byte[] appendCrc16(byte[] aa) {
//		byte[] bb = getCrc16(aa);
//		byte[] cc = new byte[aa.length + bb.length];
//		System.arraycopy(aa, 0, cc, 0, aa.length);
//		System.arraycopy(bb, 0, cc, aa.length, bb.length);
//		return cc;
//	}

	/**
	 * 获取验证码byte数组，基于Modbus CRC16的校验算法
	 */
	public static boolean   getCrc16(byte[] arr_buff) {
		int len = arr_buff.length;
		
		// 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
		int crc = 0xFFFF;
		int i, j;
		for (i = 0; i < len; i++) {
			// 把第一个 8 位二进制数据 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器
			crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (arr_buff[i] & 0xFF));
			for (j = 0; j < 8; j++) {
				// 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
				if ((crc & 0x0001) > 0) {
					// 如果移出位为 1, CRC寄存器与多项式A001进行异或
					crc = crc >> 1;
					crc = crc ^ 0xA001;
				} else
					// 如果移出位为 0,再次右移一位
					crc = crc >> 1;
			}
		}
		
		
		
		return intToBytes(crc, arr_buff);
	}

	/**
	 * 将int转换成byte数组，低位在前，高位在后 改变高低位顺序只需调换数组序号
	 */
	private  static boolean intToBytes(int value, byte [] b) {
		byte[] src = new byte[2];
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);

		boolean flag =  ((src[0] == 0) && (src[1] == 0));
		return flag;
	}

}
