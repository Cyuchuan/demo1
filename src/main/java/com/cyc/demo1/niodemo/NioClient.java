package com.cyc.demo1.niodemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
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

    InetSocketAddress clientAddress;

    InetSocketAddress serverAddress;

    public NioClient(String serverHost, int serverPort) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
            socketChannel.connect(serverAddress);

            Selector selector = Selector.open();
            this.selector = selector;

            this.clientAddress = (InetSocketAddress)socketChannel.getLocalAddress();
            this.serverAddress = serverAddress;

            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            log.error("{}", e);
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

                    if (next.isConnectable()) {
                        log.info("正在与服务器建立连接");

                    }

                    iterator.remove();
                }
            } catch (IOException e) {
                log.error("{}", e);
            }

        }
    }
}
