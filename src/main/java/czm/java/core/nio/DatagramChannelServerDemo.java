package czm.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
/**
 * @author 
 * @date 2015-8-7 上午11:36:25
 */
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class DatagramChannelServerDemo {
	// UDP协议服务端
	private int port = 9975;
	private String serverIp = "127.0.0.1";
	DatagramChannel channel;
	private Selector selector = null;

	public DatagramChannelServerDemo() throws IOException {
		try {
			selector = Selector.open();
			channel = DatagramChannel.open();
		} catch (Exception e) {
			selector = null;
			channel = null;
			System.out.println("超时");
		}
		System.out.println("---------------------服务器启动--------------------------");
	}

	/* 服务器服务方法 */
	public void start() throws IOException {
		if (channel == null || selector == null)
			return;
		channel.socket().bind(new InetSocketAddress(port));
		//channel.connect(new InetSocketAddress(serverIp, port));
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ);
		/** 外循环，已经发生了SelectionKey数目 */
		while (selector.select() > 0) {
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = null;
				try {
					key = (SelectionKey) iterator.next();
					iterator.remove();
					if (key.isReadable()) {
						reveice(key);
					}
					if (key.isWritable()) {
						// send(key);
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						if (key != null) {
							key.cancel();
							key.channel().close();
						}
					} catch (ClosedChannelException cex) {
						e.printStackTrace();
					}
				}
			}
			/* 内循环完 */
		}
		/* 外循环完 */
	}

	/*
	 * 接收 用receive()读IO 作为服务端一般不需要调用connect()，如果未调用connect()时调用read()\write()读写，
	 * 会报java.nio.channels.NotYetConnectedException
	 * 只有调用connect()之后,才能使用read和write.
	 */
	synchronized public void reveice(SelectionKey key) throws IOException {
		if (key == null)
			return;
		// ***用channel.receive()获取客户端消息***//
		// ：接收时需要考虑字节长度
		DatagramChannel channel = (DatagramChannel) key.channel();
		String content = "";
		ByteBuffer buf = ByteBuffer.allocate(1024);// java里一个(utf-8)中文3字节,gbk中文占2个字节
		buf.clear();
		channel.receive(buf); // read into buffer. 返回客户端的地址信息
		content = new String(buf.array());
		System.out.println("服务端(接收)：" + content.trim());
	}

	public static void main(String[] args) throws IOException {
		new DatagramChannelServerDemo().start();
	}
}