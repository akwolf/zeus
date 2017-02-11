package org.zunpeng.mapper;

import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.result.AccountPermissionBean;
import org.zunpeng.mapper.result.AccountRoleBean;

import java.util.List;

/**
 * Created by dapeng on 2016/10/16.
 */
public interface ShiroAccountInfoMapper {

	AccountInfo getById(Long accountId);

	AccountInfo getByUsername(String username);

	AccountInfo getBySlug(String slug);

	List<AccountRoleBean> getAllRoleByAccountId(Long accountId);

	List<AccountPermissionBean> getAllPermissionByAccountId(Long accountId);

	List<AccountPermissionBean> getAllRolePermissionByAccountId(Long accountId);
}
