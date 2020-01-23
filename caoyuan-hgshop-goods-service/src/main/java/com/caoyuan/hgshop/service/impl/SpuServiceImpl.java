package com.caoyuan.hgshop.service.impl;

import java.util.List;

import com.caoyuan.hgshop.domain.Spu;
import com.caoyuan.hgshop.mapper.SpuMapper;
import com.caoyuan.hgshop.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SpuServiceImpl implements SpuService {

	@Autowired
	private SpuMapper spuMapper;
	
	@Override
	public PageInfo<Spu> list(Spu spu, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Spu> list = spuMapper.selectSpuList(spu);
		PageInfo<Spu> pageInfo = new PageInfo<Spu>(list);
		return pageInfo;
	}

	@Override
	public Integer saveOrUpdateSpu(Spu spu) {
		Integer count = null;
		if (spu.getId() == null) {
			//新增
			count = spuMapper.insertSpu(spu);
		} else {
			//修改
			count = spuMapper.updateSpu(spu);
		}
		return count;
	}

	@Override
	public Spu getSpuById(Integer id) {
		return spuMapper.selectSpuById(id);
	}

	@Override
	public Integer deleteSpuByIds(Integer[] ids) {
		return spuMapper.deleteSpuByIds(ids);
	}
	
	
	
	@Override
	public List<Spu> spus() {
		return spuMapper.selectSpus();
	}

}
