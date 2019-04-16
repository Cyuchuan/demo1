package com.cyc.demo1;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyuchuan
 */
@Slf4j
public class NioTest {

    @Test
    public void nioTest() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("nio-test.txt");

        RandomAccessFile rw = new RandomAccessFile(resource.getFile(), "rw");
        FileChannel channel = rw.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int read = channel.read(buf);

        while (read != -1) {
            log.error("read:{}", read);
            buf.flip();

            while (buf.hasRemaining()) {
                log.error("{}", (char)buf.get());
            }

            buf.clear();

            read = channel.read(buf);
        }

        rw.close();
    }

    @Test
    public void fileTest() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("nio-test.txt");

        File file = new File("nio-test.txt");

    }
}
