package org.zunpeng.service.account;

import org.zunpeng.domain.AccountInfo;

/**
 * Created by dapeng on 2016/10/16.
 */
public interface ShiroAccountService {

	AccountInfo getById(Long accountId);

	AccountInfo getByUsername(String username);
}
