package czm.java.core.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

import czm.java.core.util.Constants;

/**
 * 文件nio测试类
 * 
 * @author chenzhiming
 *
 */
public class FileChanelTest {

	public static void main(String[] args) throws Exception {
		readFile1();
	}

	static public void readFile1() throws IOException {
		RandomAccessFile file = new RandomAccessFile(Constants.INFILE, "rw");
		ByteBuffer buf = ByteBuffer.allocate(Constants.BUFFER_SIZE_100); // 分配缓冲区
		FileChannel channel = file.getChannel(); //
		while (channel.read(buf) != -1) { // 循环读到文件末尾为止
			System.out.println(new String(buf.array()));
			buf.clear(); // 需要清空才能读取后面的内容
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

}
