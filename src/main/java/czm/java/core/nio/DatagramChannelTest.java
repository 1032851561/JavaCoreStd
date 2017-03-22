package czm.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import czm.java.core.util.Constants;

/**
 * DatagramChannel测试类
 * 
 * @author chenzhiming
 *
 */
public class DatagramChannelTest {
	public static void main(String[] args) throws Exception {
		//receive();
		send();
	}

	/**
	 * 单独线程中接收数据
	 * 
	 * @throws IOException
	 */
	static public void receive() throws IOException {
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramChannel channel = DatagramChannel.open();
					channel.socket().bind(new InetSocketAddress(9999));
					ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_1024);
					while (channel.receive(buf) == null) { // 等待接收到数据
						Thread.sleep(1000);
					}
					System.out.println("接收数据：" + new String(buf.array()));
					channel.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		}).start();

	}

	/**
	 * 单独线程中发送数据
	 * 
	 * @throws IOException
	 */
	static public void send() throws IOException {
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramChannel channel = DatagramChannel.open();
					ByteBuffer buf = ByteBuffer.wrap("HelloWorld".getBytes());
					int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));
					System.out.println("发送数据：" + bytesSent);
					channel.close();
				} catch (Exception e) {
				}

			}
		}).start();
	}
}
