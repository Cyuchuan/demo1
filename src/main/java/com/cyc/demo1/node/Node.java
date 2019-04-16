package com.cyc.demo1.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;
import lombok.ToString;

/**
 * 节点
 * 
 * @author chenyuchuan
 */
@Getter
public class Node {
    /**
     * 节点名称
     */
    private String name;

    /**
     * 相关联节点,(K，V)key为节点名，value为节点
     */
    private Map<String, Node> relativeNodes = new HashMap<>();

    private BlockingQueue<Packet> nodeBlockingQueue = new LinkedBlockingQueue<>();

    private int maxBlockSize;

    public Node(String name) {
        this.name = name;
    }

    public void link(Node... nodes) {
        for (Node node : nodes) {
            this.relativeNodes.put(node.name, node);
            node.getRelativeNodes().put(this.name, this);
        }
    }

    public Map<String, Node> getRelativeNodes() {
        return relativeNodes;
    }

    public void process(Packet packet) throws InterruptedException {
        // 放入阻塞队列
        nodeBlockingQueue.put(packet);
        maxBlockSize++;

        Packet packetInQueue = nodeBlockingQueue.take();

        if (!this.getName().equals(packetInQueue.getSourceNode().getName())) {
            packetInQueue.skip();
        }

        // 记录当前节点
        packetInQueue.getRoute().add(this.name);

        if (packetInQueue.getSkipTime() > packetInQueue.getMaxSkipTime()) {
            // 已到达包的最大TTL
            lifeTodeal(packetInQueue);

        } else if (this.getName().equals(packetInQueue.getTargetNode().getName())) {
            // 已经是终点节点,进行业务处理
            successTranslate(packetInQueue);

        } else {
            // 正常转发
            normalTranslate(packetInQueue);
        }
    }

    private void normalTranslate(Packet packet) {
        boolean isAvailableNodeExist = false;

        Set<Map.Entry<String, Node>> entries = relativeNodes.entrySet();
        for (Map.Entry<String, Node> nextNode : entries) {
            if (!packet.getRoute().contains(nextNode.getKey())) {
                isAvailableNodeExist = true;

                // 可以异步进行
                new Thread(() -> {
                    // 这里属于另一个节点的转发处理
                    try {
                        nextNode.getValue().process(packet.clone());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }

        if (!isAvailableNodeExist) {
            System.out.println("传输失败，无法找到下一结点: " + packet);
        }
    }

    private void lifeTodeal(Packet packet) {
        System.out.println("传输失败，已超出生命周期: " + packet);
    }

    private void successTranslate(Packet packet) {
        System.out.println("传输成功: " + packet + "  当前节点为：" + name);
    }
}
