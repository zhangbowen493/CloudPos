package cn.wonders.pos_qdg.tool8583;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.LogUtil;

/**
 *
 *
 * 类名称：TcpClient 类描述： 创建人：mzw 创建时间：2016年4月20日 上午11:47:36 修改人：mzw 修改时间：2016年4月20日
 * 上午11:47:36 Modification History:
 * =============================================================================
 * Author Date Description
 * ---------------------------------------------------------------------------
 * =============================================================================
 * 
 * @version 1.0.0 Socket通讯的客户端
 */
public class TcpClient {
	private String host;// 地址
	private String port;// 端口

	private Process8583Packet process;// 具体的处理

	private int connetTimeout;// 连接超时设置
	private int readTimeout;// 读超时设置

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public ProcessPacket getProcess() {
		return process;
	}

	public void setProcess(Process8583Packet process) {
		this.process = process;
	}

	public int getConnetTimeout() {
		return connetTimeout;
	}

	public void setConnetTimeout(int connetTimeout) {
		this.connetTimeout = connetTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public ResponseDataPacket sendData(RequestDataPacket request,byte[]header)
			throws ProcessException {
		ResponseDataPacket packet = null;
		Socket socket = null;
		OutputStream output = null;
		InputStream reader = null;
		try {
			socket = connectServer();
			socket.setSoTimeout(readTimeout);// 设置读超时
			//打包报文
			process = new Process8583Packet();
			byte[] sendByte = process.pack(request,header);

			LogUtil.d("发送报文" + CodeUtils.getHexStr(sendByte));
			output = socket.getOutputStream();
			output.write(sendByte);

			LogUtil.e("接收报文");
			reader = socket.getInputStream();
			
			packet = process.unpack(reader);

			LogUtil.e("处理完成关闭连接返回");
			CloudposApplication.getInstance().setIsSignIn(false);
		} catch (SocketException e) {
			throw new ProcessException(ConstentVar.UNKNOWN_ERR, "Socket异常");
		} catch (IOException e) {
			throw new ProcessException(ConstentVar.TCP_IO, "IO异常");
		} catch (Exception e) {
			
			LogUtil.e("未知异常123[" + e.getMessage() + "]");
			
			e.printStackTrace();
			
			throw new ProcessException(ConstentVar.UNKNOWN_ERR, "未知异常["
					+ e.getMessage() + "]");
			
			
		} finally {
				try {
					if (output != null)
						output.close();
					if (reader != null)
						reader.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				LogUtil.e("socket关闭");
			
		}
		
		return packet;

	}

	/**
	 *
	 * connectSocket socket连接
	 * 
	 * @return Socket
	 * @exception
	 * @since 1.0.0
	 */
	private Socket connectServer() throws Exception {
		Socket socket = new Socket();
		int strport=Integer.parseInt(port);
		try {
			LogUtil.d("host:"+host);
			LogUtil.d("port:"+strport);
			socket.connect(new InetSocketAddress(host, strport), connetTimeout);
		} catch (SocketTimeoutException e) {
			throw new ProcessException(ConstentVar.TCP_CONNET_TIMEOUT, "连接超时");
		}
		return socket;
	}

}
