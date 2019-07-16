package com.cyc.demo1.niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 这是一个nio服务器
 * 
 * @author chenyuchuan
 */
@Slf4j
@ToString
public class NioServer implements Runnable {

    ServerSocketChannel serverSocketChannel;

    Selector selector;

    InetSocketAddress serverAddress;

    public NioServer(String host, int port) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);

            serverSocketChannel.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            this.serverSocketChannel = serverSocketChannel;
            this.serverAddress = inetSocketAddress;
            this.selector = selector;

        } catch (IOException e) {
            log.error("{}", e);
            throw new RuntimeException("开启nio服务器异常");
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();

                    if (next.isAcceptable()) {
                        accept(next);

                    }

                    if (next.isReadable()) {
                        read(next);

                    }

                    iterator.remove();
                }
            } catch (IOException e) {
                log.error("{}", e);
            }
        }
    }

    private void accept(SelectionKey selectionKey) throws IOException {
        log.info("这是一个可以接受的请求");
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();

        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        SocketAddress localAddress = socketChannel.getLocalAddress();
        log.error("socketChannel:{} SocketAddress:{} ", socketChannel, localAddress);

        socketChannel.write(ByteBuffer.wrap((serverAddress + "已建立连接").getBytes()));
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

    }

    private void read(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel)selectionKey.channel();

        ByteBuffer allocate = ByteBuffer.allocate(1024);

        int read = channel.read(allocate);

        while (read > 0) {
            log.error("读取了：{} 个字节", read);
            allocate.flip();
            byte[] bytes = new byte[allocate.limit()];
            allocate.get(bytes);
            String s = new String(bytes, StandardCharsets.UTF_8);

            log.error("读取的字符串为：{}", s);

            allocate.clear();

            String writeString = "HTTP/1.1 200 OK\r\n" + "Content-Length: 38\r\n" + "Content-Type: text/html\r\n"
                + "\r\n" + "<html><body>Hello World!</body></html>";
            // 写回channel
            while (allocate.hasRemaining()) {
                channel.write(ByteBuffer.wrap(writeString.getBytes(StandardCharsets.UTF_8)));

            }

            read = channel.read(allocate);
        }

        if (read == -1) {
            channel.close();
        }

    }
}
