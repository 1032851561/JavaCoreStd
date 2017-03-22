package czm.java.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
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

public class DatagramChannelClientDemo {
	private String serverIp = "127.0.0.1";
	private int port = 9975;
	DatagramChannel channel;
	private Selector selector = null;

	public DatagramChannelClientDemo() throws IOException {
		try {
			selector = Selector.open();
			channel = DatagramChannel.open();
		} catch (Exception e) {
			selector = null;
			channel = null;
			System.out.println("超时");
		}
		System.out.println("--------------------------客户器启动--------------------------");
	}

	/* 服务器服务方法 */
	public void service() throws IOException {
		if (channel == null || selector == null)
			return;
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(serverIp, port));// 连接服务端
		channel.write(ByteBuffer.wrap(new String("HelloWorld").getBytes()));
		//channel.register(selector, SelectionKey.OP_READ);
		/** 外循环，已经发生了SelectionKey数目 */
		while (selector.select() > 0) {
			/* 得到已经被捕获了的SelectionKey的集合 */
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

	/* 接收 */
	synchronized public void reveice(SelectionKey key) throws IOException {
		if (key == null)
			return;
		try {
			// ：接收时需要考虑字节长度
			DatagramChannel sc = (DatagramChannel) key.channel();
			String content = "";
			// 第一次接；udp采用数据报模式，发送多少次，接收多少次
			ByteBuffer buf = ByteBuffer.allocate(65507);// java里一个(utf-8)中文3字节,gbk中文占2个字节
			buf.clear();
			sc.receive(buf); // read into buffer.
			buf.flip(); // make buffer ready for read
			content = new String(buf.array());
			System.out.println("客户端(接收)：" + content);

		} catch (PortUnreachableException ex) {
			ex.printStackTrace();
		}
	}

	public void send(SelectionKey key) {
		if (key == null)
			return;
		// ByteBuffer buff = (ByteBuffer) key.attachment();
		DatagramChannel sc = (DatagramChannel) key.channel();
		try {
			sc.write(ByteBuffer.wrap(new String("aaaa").getBytes()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				try {
					new DatagramChannelClientDemo().service();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}