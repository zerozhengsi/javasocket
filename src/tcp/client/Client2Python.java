package tcp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2Python {
	
	private byte start = 0x68;
	
	private int length;
	
	private String code;
	
	private String message;
	
	private byte end = 0x18;
	
	public static void main(String args[]){
		
		
		try {
			Client2Python client2Python = new Client2Python();
			client2Python.setCode("CS0001");
			client2Python.setMessage("测试消息0001");
			client2Python.setLength(client2Python.getMessage().getBytes("UTF-8").length);
			
			byte[] b = new byte[11];
			byte[] br = new byte[64];
			byte[] temp;
			Socket clientSocket = new Socket("127.0.0.1", 9999);
			InputStream is = clientSocket.getInputStream();
			OutputStream os = clientSocket.getOutputStream();
			b[0] = client2Python.getStart();
			temp = toLH(client2Python.getLength());
			System.out.println(client2Python.getLength());
			System.arraycopy(temp, 0, b, 1, temp.length);
			byte[] codes = client2Python.getCode().getBytes("UTF-8");
			System.arraycopy(codes, 0, b, 1+temp.length, codes.length);
//			byte[] messages = client2Python.getMessage().getBytes("UTF-8");
//			System.arraycopy(messages, 0, b,1+temp.length+codes.length, messages.length);
//			b[temp.length+codes.length+messages.length+1] = client2Python.getEnd();
			os.write(b);
			int len=0;
			while((len=is.read(br))!=-1){
				System.out.println("receive length:"+len);
				System.out.println(new String(br,"UTF-8"));
			}
			is.close();
			os.close();
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* 将int转为低字节在前，高字节在后的byte数组
	*/
	public static byte[] toLH(int n) 
	{
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public byte getStart() {
		return start;
	}

	public void setStart(byte start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte getEnd() {
		return end;
	}

	public void setEnd(byte end) {
		this.end = end;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
