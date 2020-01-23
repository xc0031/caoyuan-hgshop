package com.caoyuan.hgshop.service;

import java.util.Map;

import com.caoyuan.hgshop.domain.Sku;
import com.github.pagehelper.PageInfo;

public interface SkuService {

	PageInfo<Sku> list(Sku sku, Integer pageNum, Integer pageSize);

	Integer saveOrUpdateSku(Sku sku);

	Map<String, Object> getSkuById(Integer id);

	Integer deleteSkuByIds(Integer[] ids);

}
