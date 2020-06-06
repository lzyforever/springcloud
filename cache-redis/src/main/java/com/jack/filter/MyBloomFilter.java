package com.jack.filter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;


/**
 * 布隆过滤器的使用
 * 通过BloomFilter.create创建一个布隆过滤器，初始化1,000,000条数据到过滤器中，然后在初始化数据
 * 的基础上加上10,000条，分别去过滤器中检查是否存在，按照正常的逻辑来说，匹配的数量肯定是1,000,000
 * 事实上输出的结果是：匹配数量 1000309
 * 大家肯定很好奇，明明多加的那10,000条数据是不存在的，为什么匹配出的数量多出来309条？
 * 那是因为布隆过滤器是存在一定错误率的，我们可以调节布隆过滤器的错误率，在create的时候可以指定
 * 第3个参数来指定错误率
 */
public class MyBloomFilter {
    public static void main(String[] args) {
        // 总数量
        int total = 1000000;

        BloomFilter<CharSequence> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total);

        // 错误率调节是一个double类型的参数，默认值是0.03，值超小错误率越小，同时存储空间会越大，这个可以
        // 根据需求去调整。那么错误率是怎么计算的喃？我们总共是1,010,000条数据去测试是否匹配，默认值是0.03，那
        // 么错误率就是10,100,000 * (0.03 / 100) = 303，刚刚测试的错误数量是309，可见处于这个范围内
        // 利用布隆过滤器我们可以预先将缓存的数据存储到过滤器中，比如用户ID，当根据ID来查询数据的时候，我们
        // 先从过滤器中判断是否存在，存在的话就继续下面的流程，不存在直接返回空即可，因为我们认为这是一个非法请求
        // BloomFilter<CharSequence> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total, 0.0003);


        // 缓存穿透不能完全解决，我们只能将其控制在一个可以容忍的范围内，如果是用Spring Cache来缓存的话我们可能
        // 用不了布隆过滤器，如果想要结合Spring Cache来使用的话我们必须对其扩展才行。

        // 初始化10000条数据到过滤器中
        for (int i = 0; i < total; i ++) {
            bf.put("" + i);
        }

        // 判断值是否存在过滤器中
        int count = 0 ;
        for (int i = 0; i < total + 10000; i ++) {
            if (bf.mightContain("" + i)) {
                count ++;
            }
        }
        System.out.println("匹配数量 " + count);
    }
}
