package czm.java.core.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import czm.java.core.util.Constants;

/**
 * 文件nio测试类
 * 
 * @author chenzhiming
 *
 */
@SuppressWarnings("resource")
public class FileChannelTest {

	public static void main(String[] args) throws Exception {
		// readFile1();
		writeFile();
	}

	static public void readFile1() throws IOException {
		RandomAccessFile file = new RandomAccessFile(Constants.INFILE, "rw");
		ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_1); // 分配缓冲区
		FileChannel channel = file.getChannel(); //
		while (channel.read(buf) != -1) { // 循环读到文件末尾为止
			System.out.println(new String(buf.array()));
			buf.clear(); // 需要清空才能读取后面的内容,因为缓冲区设置只有1个字节
			buf.compact();
		}
	}

	static public void readFile2() throws IOException {
		RandomAccessFile file = new RandomAccessFile(Constants.INFILE, "rw");
		ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_100); // 分配缓冲区
		FileChannel channel = file.getChannel(); //
		while (channel.read(buf) != -1) { // 循环读到文件末尾为止
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			buf.clear();

		}
	}

	/**
	 * 写数据到文件
	 * 
	 * @throws IOException
	 */
	static public void writeFile() throws IOException {
		File file = new File(Constants.OUTFILE);
		file.createNewFile();
		FileOutputStream stream = new FileOutputStream(file);
		FileChannel channel = stream.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_100);
		byte[] bs = { 'a', 'b', 'c', '\n' };
		buf.put(bs);
		buf.put(new Date().toLocaleString().getBytes());
		buf.flip(); // 切换到读取模式，通道从缓冲读取数据写到文件
		channel.write(buf);
		channel.close();
	}
}
