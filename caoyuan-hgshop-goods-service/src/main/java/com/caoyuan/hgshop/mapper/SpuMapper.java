package com.caoyuan.hgshop.mapper;

import com.caoyuan.hgshop.domain.Spu;

import java.util.List;


public interface SpuMapper {

	List<Spu> selectSpuList(Spu spu);

	Integer insertSpu(Spu spu);

	Spu selectSpuById(Integer id);

	Integer deleteSpuByIds(Integer[] ids);

	Integer updateSpu(Spu spu);

	List<Spu> selectSpus();

}
