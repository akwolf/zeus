package org.zunpeng.mapper;

import org.zunpeng.domain.ProductInfo;

/**
 * Created by dapeng on 7/7/16.
 */
public interface ProductInfoMapper {

	ProductInfo getById(Long id);

	ProductInfo getBySlug(String slug);

	void save(ProductInfo productInfo);

	void update(ProductInfo productInfo);
}
