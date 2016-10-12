package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.RoleInfo;

import java.util.List;

/**
 * Created by dapeng on 16/8/8.
 */
public interface RoleInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	RoleInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(RoleInfo roleInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(RoleInfo roleInfo);

	List<RoleInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
