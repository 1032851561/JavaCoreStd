package czm.java.core.nio;

import java.nio.IntBuffer;

/**
 * 缓冲区测试类
 * 
 * @author chenzhiming
 *
 */
public class BufferTest {
	public static void main(String[] args) {
		testLimit();
	}

	/**
	 * 文件的读写使用ByteBuffer，理解IntBuffer的使用
	 */
	public static void testIntBuffer() {

	}

	/**
	 * 理解limit的值
	 */
	public static void testLimit() {
		IntBuffer buffer = IntBuffer.allocate(10); // 分配capacity=10的缓冲区
		int[] arr = { 0, 1, 2, 3, 4 };
		buffer.put(arr, 0, 5);
		System.out.println(buffer.limit()); // 这个时候limit=capacity=10
		buffer.flip();
		System.out.println(buffer.limit());// 切换到读取模式后，limit=5
	}
}
