package com.cyc.demo1.niodemo.http;

import com.cyc.demo1.niodemo.IMessageReader;
import com.cyc.demo1.niodemo.IMessageReaderFactory;

/**
 * @author chenyuchuan
 */
public class HttpMessageReaderFactory implements IMessageReaderFactory {

    public HttpMessageReaderFactory() {}

    @Override
    public IMessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
