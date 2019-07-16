package com.cyc.demo1.niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 一个nio客户端
 * 
 * @author chenyuchuan
 */
@Slf4j
@ToString
public class NioClient implements Runnable {

    Selector selector;

    SocketChannel socketChannel;

    InetSocketAddress clientAddress;

    InetSocketAddress serverAddress;

    public NioClient(String serverHost, int serverPort) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
            socketChannel.connect(serverAddress);

            this.clientAddress = (InetSocketAddress)socketChannel.getLocalAddress();
            this.serverAddress = serverAddress;

            if (socketChannel.finishConnect()) {
                log.info("与服务器：{} 建立连接", serverAddress);
            }

            this.socketChannel = socketChannel;
        } catch (IOException e) {
            log.error("{}", e);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);

                Selector selector = Selector.open();
                this.selector = selector;

                socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                while (true) {
                    selector.select();

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();

                        if (selectionKey.isReadable()) {
                            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                            ByteBuffer buffer = ByteBuffer.allocate(1024);

                            socketChannel.read(buffer);

                            buffer.flip();

                            byte[] bytes = new byte[buffer.limit()];
                            buffer.get(bytes);
                            String s = new String(bytes, StandardCharsets.UTF_8);
                            log.error("收到服务器消息：{}", s);

                            socketChannel.write(ByteBuffer.wrap("你好，我叫jane".getBytes(StandardCharsets.UTF_8)));
                        }

                        iterator.remove();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
