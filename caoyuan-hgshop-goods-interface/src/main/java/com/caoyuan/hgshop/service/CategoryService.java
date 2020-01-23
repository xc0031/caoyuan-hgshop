package com.caoyuan.hgshop.service;

import com.caoyuan.hgshop.domain.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CategoryService {

	PageInfo<Category> list(Category category, Integer pageNum, Integer pageSize);
	
	List<Category> getAllCategories();

	Integer addCategory(Category category);

	Category getCategoryById(Integer id);
	
	Map<String, String> deleteCategory(Integer id);


}
