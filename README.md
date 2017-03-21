# JavaCoreStd
学习java核心知识点的demo：nio，并发编程，泛型，jvm等

## nio:
> 新IO或非阻塞IO

主要Channel

- FileChannel：从文件中读写数据
- DatagramChannel：通过UDP读写网络中的数据
- SocketChannel：通过TCP读写网络中的数据
- ServerSocketChannel：可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。

主要Buffer：

- ByteBuffer
- CharBuffer
- DoubleBuffer
- FloatBuffer
- IntBuffer
- LongBuffer
- ShortBuffer

### FileChannel

用来读、写、映射和操纵文件的channel.
可从现有的 `FileInputStream`、`FileOutputStream` 或 `RandomAccessFile` 对象获得文件通道

### 缓冲Buffer

#### 使用步骤

1. 首先分配缓冲区大小:`ByteBuffer.allocate(1024)`
2. 从通道中写入数据到Buffer：`channel.read(buffer)`
3. 调用flip()方法：将Buffer从写模式切换到读模式，其实就是调整position,limit值
4. 从Buffer中读取数据
5. 调用clear()方法或者compact()方法：清空整个缓冲区&&清除已经读过的数据，然后再加到步骤2

#### Buffer实现原理

#### Buffer常用操作