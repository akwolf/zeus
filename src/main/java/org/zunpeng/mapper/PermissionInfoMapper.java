package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.PermissionInfo;

import java.util.List;

/**
 * Created by dapeng on 16/8/8.
 */
public interface PermissionInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	PermissionInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(PermissionInfo permissionInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(PermissionInfo permissionInfo);

	List<PermissionInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
