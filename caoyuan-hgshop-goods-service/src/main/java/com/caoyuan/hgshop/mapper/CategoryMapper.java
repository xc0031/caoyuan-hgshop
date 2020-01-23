package com.caoyuan.hgshop.mapper;

import com.caoyuan.hgshop.domain.Category;

import java.util.List;


public interface CategoryMapper {

	List<Category> selectCategoryList(Category category);
	
	List<Category> selectAllCategories();

	Integer insertCategory(Category category);

	Category selectCategoryById(Integer id);

	Integer updateCategory(Category category);

	Integer getChildCategoryCount(Integer id);

	Integer deleteCategoryById(Integer id);

	String selectCategoryNamesByThreeCategoryId(Integer categoryId);

	List<Category> selectCategoryByIds(List<Long> categoryIds);


}
