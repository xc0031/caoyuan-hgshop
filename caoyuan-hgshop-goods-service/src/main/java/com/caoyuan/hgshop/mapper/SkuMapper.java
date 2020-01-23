/**
 * 
 */
package com.caoyuan.hgshop.mapper;

import com.caoyuan.hgshop.domain.Sku;
import com.caoyuan.hgshop.domain.SkuSpec;

import java.util.List;


/**
 * @author coolface
 *
 */
public interface SkuMapper {

	List<Sku> selectSkuList(Sku sku);

	Integer insertSku(Sku sku);

	Integer updateSku(Sku sku);

	void insertSkuSpec(SkuSpec skuSpec);

	void updateSkuSpec(SkuSpec skuSpec);

	Sku selectSkuById(Integer id);

	Integer deleteSkuByIds(Integer[] ids);

	void deleteSkuSpecBySkuIds(Integer[] ids);
	
}
