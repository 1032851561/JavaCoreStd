package czm.java.core.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import czm.java.core.util.Constants;

/**
 * DatagramChannel测试类
 * 
 * @author chenzhiming
 *
 */
public class DatagramChannelTest_1 {

	static DatagramChannel channel = null;

	static public DatagramChannel getChannel() throws Exception {
		if (channel == null) {
			channel = DatagramChannel.open();
			channel.connect(new InetSocketAddress("localhost", 9999));
			channel.configureBlocking(false);
		}
		return channel;
	}

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		DatagramChannel channel = DatagramChannelTest_1.getChannel();
		channel.register(selector, SelectionKey.OP_READ);
		while (true) {
			int readyChannels = selector.select();
			if (readyChannels == 0)
				continue;
			Set<?> selectedKeys = selector.selectedKeys();
			Iterator<?> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				SelectionKey key = (SelectionKey) keyIterator.next();
				if (key.isAcceptable()) {
				} else if (key.isConnectable()) {
				} else if (key.isReadable()) {
					key.channel();
				} else if (key.isWritable()) {
				}
				keyIterator.remove();
			}
		}
	}

	public static void read() throws Exception {
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramChannel channel = DatagramChannelTest_1.getChannel();
					ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_100);
					int read = channel.read(buf);
					System.out.println(read);
					System.out.println("接收数据：" + new String(buf.array()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public static void write() throws Exception {
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramChannel channel = DatagramChannelTest_1.getChannel();
					String msg = "HelloWorld";
					for (int i = 0; i < 10; i++) {
						int write = channel.write(ByteBuffer.wrap(msg.getBytes()));
						System.out.println("发送数据：" + write);
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
