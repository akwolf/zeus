package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.domain.AccountInfo;

/**
 * Created by dapeng on 6/30/16.
 */
public interface AccountInfoMapper {

	AccountInfo getById(Long id);

	AccountInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(AccountInfo accountInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(AccountInfo accountInfo);

	AccountInfo getByUsername(String username);
}
