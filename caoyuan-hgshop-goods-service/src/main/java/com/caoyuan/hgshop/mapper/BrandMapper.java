package com.caoyuan.hgshop.mapper;

import com.caoyuan.hgshop.domain.Brand;

import java.util.List;


public interface BrandMapper {

	List<Brand> selectBrandList(Brand brand);

	Integer insertBrand(Brand brand);

	Integer updateBrand(Brand brand);

	Brand selectBrandById(Integer id);

	Integer deleteBrandByIds(Integer[] ids);

	List<Brand> selectAllBrands();

	List<Brand> selectBrandByIds(List<Long> brandIds);

}
