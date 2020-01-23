package com.caoyuan.hgshop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.caoyuan.hgshop.domain.Sku;
import com.caoyuan.hgshop.domain.SkuSpec;
import com.caoyuan.hgshop.domain.Spec;
import com.caoyuan.hgshop.mapper.SkuMapper;
import com.caoyuan.hgshop.mapper.SpecMapper;
import com.caoyuan.hgshop.service.SkuService;
import com.github.pagehelper.Page;
import org.apache.dubbo.config.annotation.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class SkuServiceImpl implements SkuService {

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SpecMapper specMapper;

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private RedisTemplate<String, Sku> redisTemplate;

    /**
     * 查询sku列表(分页)
     */
    @Override
    public PageInfo<Sku> list(Sku sku, Integer pageNum, Integer pageSize) {
        ListOperations<String, Sku> skuListOperations = redisTemplate.opsForList();
        if (!redisTemplate.hasKey("skuList")) {
            // 如果没有对应的键
            // 从mysql中获取所有sku的数据
            List<Sku> list = skuMapper.selectSkuList(sku);
            // 获取全部数据以后，存入redis中
            skuListOperations.rightPushAll("skuList", list);
        }
        //从redis中取出来,分页后数据,包含头,包含尾
        List<Sku> skuList = skuListOperations.range("skuList", (pageNum - 1) * pageSize, pageNum * pageSize - 1);
        //获取list长度
        Long totals = skuListOperations.size("skuList");
        //创建page对象,用于分页数据储存
        Page<Sku> pages = new Page<>(pageNum,pageSize);
        //放入总长度
        pages.setTotal(totals);
        //放入list
        pages.addAll(skuList);
        //准备传到前台
        PageInfo<Sku> pageInfo = new PageInfo<Sku>(pages);
        return pageInfo;
    }

    /**
     * 添加或修改sku
     */
    @Override
    public Integer saveOrUpdateSku(Sku sku) {
        Integer count = 0;
        //①新增
        if (sku.getId() == null) {
            sku.setStatus("0");
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
            //转换为json字符串,用于kafka转发
            String jsonString = JSON.toJSONString(sku);
            kafkaTemplate.sendDefault("addSku", jsonString);
            count = 1;
        } else {
            //Ⅱ修改
            sku.setUpdateTime(new Date());
            //转换为json字符串,用于kafka转发
            String jsonString = JSON.toJSONString(sku);
            kafkaTemplate.sendDefault("updateSku", jsonString);
            count = 2;
        }
        return count;
    }

    /**
     * 修改回显或查看详情
     */
    @Override
    public Map<String, Object> getSkuById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        //1.获取sku详情
        Sku sku = skuMapper.selectSkuById(id);
        //2.将中间表对象列表映射成规格参数id列表
        List<Integer> specIds = sku.getSkuSpec().stream().map(ks -> ks.getSpecId()).collect(Collectors.toList());
        //3.根据规格参数id列表获取规格参数及规格参数选项信息
        List<Spec> specList = specMapper.selectSpecByIds(specIds);
        map.put("sku", sku);
        map.put("specs", specList);
        return map;
    }

    @Override
    public Integer deleteSkuByIds(Integer[] ids) {
        String jsonString = JSON.toJSONString(ids);
        kafkaTemplate.sendDefault("deleteSku", jsonString);
        return 1;
    }
}
