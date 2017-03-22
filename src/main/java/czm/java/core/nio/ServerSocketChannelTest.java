package czm.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import czm.java.core.util.Constants;

/**
 * ServerSocketChannel测试类
 * 
 * @author chenzhiming
 *
 */
public class ServerSocketChannelTest {
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 9999)); // 监听本地9999端口
		ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_1024);
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept(); // 接收连接
			if (socketChannel != null) {
				System.out.println("新连接进入!");
				try {
					while (true) { // 循环获取数据
						socketChannel.read(buf);
						buf.flip();
						System.out.println("服务端接收到数据：" + new String(buf.array()));
					}
				} catch (Exception e) {
				} finally {
					socketChannel.close();
				}
			}
		}

	}
}
