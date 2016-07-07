package org.zunpeng.mapper;

import org.zunpeng.domain.AccountInfo;

/**
 * Created by dapeng on 6/30/16.
 */
public interface AccountInfoMapper {

	AccountInfo getById(Long id);

	AccountInfo getBySlug(String slug);

	void save(AccountInfo accountInfo);

	void update(AccountInfo accountInfo);

}
