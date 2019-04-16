package com.cyc.demo1.node;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuchuan
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Packet implements Cloneable {
    /**
     * 最大的跳跃次数
     */
    private int maxSkipTime;

    /**
     * 当前跳跃次数
     */
    private int skipTime;

    /**
     * 目标节点
     */
    private Node targetNode;

    /**
     * 源节点
     */
    private Node sourceNode;

    /**
     * 传输路径
     */
    private ArrayList<String> route;

    /**
     * 每过一个路由节点加一
     */
    public void skip() {
        this.skipTime = this.skipTime + 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Packet clone() {
        Packet result = null;
        try {
            result = (Packet)super.clone();
            result.route = (ArrayList<String>)this.route.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("报文的传输路径为: %s", route);
    }
}
