package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.domain.CompanyInfo;

/**
 * Created by dapeng on 7/7/16.
 */
public interface CompanyInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	CompanyInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(CompanyInfo companyInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(CompanyInfo companyInfo);
}
