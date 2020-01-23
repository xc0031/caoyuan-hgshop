package com.caoyuan.hgshop.mapper;

import com.caoyuan.hgshop.domain.Spec;
import com.caoyuan.hgshop.domain.SpecOption;

import java.util.List;


public interface SpecMapper {

	List<Spec> selectSpecList(Spec spec);

	Integer insertSpec(Spec spec);

	void insertSpecOption(SpecOption specOption);

	Spec selectSpecById(Integer id);

	Integer updateSpec(Spec spec);

	void deleteSpecOptionBySpecId(Integer specId);

	Integer deleteSpecByIds(Integer[] ids);

	void deleteSpecOptionBySpecIds(Integer[] specIds);

	List<Spec> selectSpecByIds(List<Integer> specIds);

	List<Spec> selectSpecs();

}
