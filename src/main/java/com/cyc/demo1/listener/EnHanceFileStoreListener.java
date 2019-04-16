package com.cyc.demo1.listener;

/**
 * @author chenyuchuan
 */
public interface EnHanceFileStoreListener {

    void beforeFileStore();

    void afterFileStoreError();

    void afterFileStoreSuccess();

}
