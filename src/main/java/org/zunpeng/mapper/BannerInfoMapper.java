package org.zunpeng.mapper;

import org.zunpeng.domain.BannerInfo;

/**
 * Created by dapeng on 7/7/16.
 */
public interface BannerInfoMapper {

	BannerInfo getById(Long id);

	void save(BannerInfo bannerInfo);

	void update(BannerInfo bannerInfo);
}
