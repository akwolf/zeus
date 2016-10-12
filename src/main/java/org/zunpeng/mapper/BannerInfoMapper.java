package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.BannerInfo;

import java.util.List;

/**
 * Created by dapeng on 7/7/16.
 */
public interface BannerInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	BannerInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void save(BannerInfo bannerInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(BannerInfo bannerInfo);

	List<BannerInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
