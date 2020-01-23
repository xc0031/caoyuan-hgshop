package com.caoyuan.hgshop.service;

import java.util.List;

import com.caoyuan.hgshop.domain.Spu;
import com.github.pagehelper.PageInfo;

public interface SpuService {
	PageInfo<Spu> list(Spu spu, Integer pageNum, Integer pageSize);

	Integer saveOrUpdateSpu(Spu spu);

	Spu getSpuById(Integer id);

	Integer deleteSpuByIds(Integer[] ids);

	List<Spu> spus();
}
