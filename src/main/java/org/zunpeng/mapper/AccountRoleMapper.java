package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.AccountRole;
import org.zunpeng.mapper.result.AccountRoleBean;

import java.util.List;

/**
 * Created by dapeng on 16/8/8.
 */
public interface AccountRoleMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	AccountRole getById(Long id);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(AccountRole accountRole);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(AccountRole accountRole);

	List<AccountRole> getAllLimit(Criteria criteria);

	long count(Criteria criteria);

	@Caching(deps = {RoleInfoMapper.class})
	List<AccountRoleBean> getAllByAccountId(Long accountId);
}
