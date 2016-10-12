package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.ProductInfo;

import java.util.List;

/**
 * Created by dapeng on 7/7/16.
 */
public interface ProductInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	ProductInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	ProductInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(ProductInfo productInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(ProductInfo productInfo);

	List<ProductInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
