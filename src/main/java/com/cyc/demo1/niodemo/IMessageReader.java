package com.cyc.demo1.niodemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author chenyuchuan
 */
public interface IMessageReader {

    public void init(MessageBuffer readMessageBuffer);

    public void read(Socket socket, ByteBuffer byteBuffer) throws IOException;

    public List<Message> getMessages();

}
