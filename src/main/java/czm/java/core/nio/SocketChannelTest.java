package czm.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import czm.java.core.util.Constants;

/**
 * SocketChannel测试类
 * 
 * @author chenzhiming
 *
 */
public class SocketChannelTest {
	public static void main(String[] args) throws IOException {
		SocketChannel channel = SocketChannel.open();
		boolean connect = channel.connect(new InetSocketAddress("localhost", 9999)); // 连接本机9999端口
		Scanner scan = new Scanner(System.in);
		if (connect) {
			try {
				String nextLine = null;
				ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_1024);
				while (true) { // 不断从控制台输入数据，并发送到服务端
					nextLine = scan.nextLine();
					buf.clear();
					buf.put(nextLine.getBytes());
					buf.flip();
					channel.write(buf);
				}
			} catch (Exception e) {
			} finally {
				scan.close();
				channel.close();
			}
		}

	}
}
