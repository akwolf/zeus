package org.zunpeng.service.account;

import org.zunpeng.domain.AccountInfo;

import java.util.Collection;

/**
 * Created by dapeng on 2016/10/16.
 */
public interface ShiroAccountService {

	AccountInfo getById(Long accountId);

	AccountInfo getByUsername(String username);

	Collection<String> getAllRolesByAccountId(Long accountId);

	Collection<String> getAllPermissionsByAccountId(Long accountId);
}
