package com.caoyuan.hgshop.listener;

import com.alibaba.fastjson.JSON;
import com.caoyuan.hgshop.domain.Sku;
import com.caoyuan.hgshop.domain.SkuSpec;
import com.caoyuan.hgshop.mapper.SkuMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/*********************************************************
 @author 曹原
 @date 2020/1/23 13:13 
 *********************************************************/
//@Component这里是因为xml里已经注册了bean
public class KafkaConsumerListener implements MessageListener<String, String> {

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(ConsumerRecord<String, String> result) {
        String key = result.key();
        if (key == null) {
            return;
        }
        String value = result.value();
        //修改或者添加
        if (key.equals("addSku")) {
            Sku sku = JSON.parseObject(value, Sku.class);
            //存入数据库
            skuMapper.insertSku(sku);
            //全部添加到中间表
            List<SkuSpec> skuSpecs = sku.getSkuSpec();
            if (skuSpecs != null) {
                for (SkuSpec skuSpec : skuSpecs) {
                    skuSpec.setSkuId(sku.getId());
                    skuMapper.insertSkuSpec(skuSpec);
                }
            }
        }
        if (key.equals("updateSku")) {
            Sku sku = JSON.parseObject(value, Sku.class);
            skuMapper.updateSku(sku);
            //删除修改后之前的中间表数据(全部删除,一会儿全部添加)
            skuMapper.deleteSkuSpecBySkuIds(new Integer[]{sku.getId()});
            //全部添加到中间表
            List<SkuSpec> skuSpecs = sku.getSkuSpec();
            if (skuSpecs != null) {
                for (SkuSpec skuSpec : skuSpecs) {
                    skuSpec.setSkuId(sku.getId());
                    skuMapper.insertSkuSpec(skuSpec);
                }
            }
        }
        if (key.equals("deleteSku")) {
            List<Integer> deleteSku = JSON.parseArray("deleteSku", Integer.class);
            Integer[] ids = deleteSku.toArray(new Integer[deleteSku.size()]);
            //1.删除sku表
            Integer count = skuMapper.deleteSkuByIds(ids);
            if (count > 0) {
                //2.删除sku_spec表(中间表)
                skuMapper.deleteSkuSpecBySkuIds(ids);
            }
        }
        //添加,修改,删除都把整个列表删除了,
        redisTemplate.delete("skuList");
    }
}
