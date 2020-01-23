package com.caoyuan.hgshop.service;

import java.util.List;

import com.caoyuan.hgshop.domain.Spec;
import com.github.pagehelper.PageInfo;

public interface SpecService {
	PageInfo<Spec> list(Spec spec, Integer pageNum, Integer pageSize);

	Integer saveOrUpdateSpec(Spec spec);

	Spec getSpecById(Integer id);

	Integer deleteSpecByIds(Integer[] ids);

	List<Spec> specs();
}
