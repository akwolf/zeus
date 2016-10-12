package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.AccountPermission;
import org.zunpeng.mapper.result.AccountPermissionBean;

import java.util.List;

/**
 * Created by dapeng on 16/8/8.
 */
public interface AccountPermissionMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	AccountPermission getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(AccountPermission accountPermission);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(AccountPermission accountPermission);

	List<AccountPermission> getAllLimit(Criteria criteria);

	long count(Criteria criteria);

	@Caching(deps = {PermissionInfoMapper.class})
	List<AccountPermissionBean> getAllByAccountId(Long accountId);
}
