package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.AccountInfo;

import java.util.List;

/**
 * Created by dapeng on 6/30/16.
 */
public interface AccountInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	AccountInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	AccountInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(AccountInfo accountInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(AccountInfo accountInfo);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	AccountInfo getByUsername(String username);

	List<AccountInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
