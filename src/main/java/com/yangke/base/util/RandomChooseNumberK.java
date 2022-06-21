package com.yangke.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 不等概率不放回的抽样类 使用方法：传入你的概率rates，以及需要抽取的样本个数k。假如传入的概率是：[1,2,3,4,5]，
 * k为2，如果最后选择到的概率是1,3；那么返回的index为0(概率1的index),2(概率3的index)
 *
 * @author xutaoyang
 *
 */
public class RandomChooseNumberK {
    static Random rand = new Random();

    /**

     * 对外接口方法
     * @param nums 概率
     * @param k 目标样本的个数
     * @return 命中的样本的在概率list中的index
     */
    public static List<Long> randKWithoutReplacement(List<Long> nums, int k) {
        if (null == nums || nums.isEmpty()) {
            throw new RuntimeException("<> the rates list is null or empty");
        }
        if (k > nums.size()) {
            throw new RuntimeException("<> k is bigger than rates' size");
        }
        List<Node> nodes = new ArrayList(nums.size());
        for (int index = 0; index < nums.size(); index++) {
            nodes.add(new Node(nums.get(index), index));
        }
        List<Long> result = new ArrayList(k);
        List<Node> heap = buildHeap(nodes);
        for (int index = 0; index < k; index++) {
            result.add(heapPop(heap));
        }
        return result;

    }
    private static List buildHeap(List<Node> nodes) {
        List<Node> heap = new ArrayList(nodes.size() + 1);
        heap.add(null);
        for (int index = 0; index < nodes.size(); index++) {
            heap.add(nodes.get(index));
        }
        for (int index = heap.size() - 1; index > 1; index--) {
            double curTW = heap.get(index >> 1).totalWeight;
            heap.get(index >> 1).totalWeight = curTW + heap.get(index).totalWeight;
        }
        return heap;
    }

    /** 关于double的计算都用+-x/了，那点误差就让它去吧，性能高很多啊 */
    private static long heapPop(List<Node> heap) {
        double gas = heap.get(1).totalWeight * rand.nextDouble();
        int i = 1;
        while (gas > heap.get(i).weight) {
            gas = gas - heap.get(i).weight;
            i <<= 1;
            if (gas > heap.get(i).totalWeight) {
                gas = gas - heap.get(i).totalWeight;
                i++;
            }
        }
        double weight = heap.get(i).weight;
        double myWeight = heap.get(i).weight;
//        int value = heap.get(i).value;
        heap.get(i).weight = 0;
        while (i > 0) {
            heap.get(i).totalWeight = heap.get(i).totalWeight - weight;
            i >>= 1;
        }
        return new Double(myWeight).longValue();
    }

    private static class Node {
        double weight;
        int value;
        double totalWeight;
        public Node(double weight, int value) {
            this.weight = weight;
            this.value = value;
            this.totalWeight = weight;
        }
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(10L);
        list.add(19L);
        list.add(8L);
        list.add(6L);
        List<Long> list1 = RandomChooseNumberK.randKWithoutReplacement(list, 4);
        for (Long dd:list1) {
            System.out.println(dd);
        }
    }

}
