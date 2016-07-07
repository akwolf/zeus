package org.zunpeng.mapper;

import org.zunpeng.domain.CompanyInfo;

/**
 * Created by dapeng on 7/7/16.
 */
public interface CompanyInfoMapper {

	CompanyInfo getById(Long id);

	void save(CompanyInfo companyInfo);

	void update(CompanyInfo companyInfo);
}
