package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.domain.RolePermission;
import org.zunpeng.mapper.result.AccountPermissionBean;

import java.util.List;

/**
 * Created by dapeng on 2016/10/16.
 */
public interface RolePermissionMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	RolePermission getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(RolePermission rolePermission);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(RolePermission rolePermission);

	@Caching(deps = {PermissionInfoMapper.class})
	List<AccountPermissionBean> getAllByAccountId(Long accountId);
}
