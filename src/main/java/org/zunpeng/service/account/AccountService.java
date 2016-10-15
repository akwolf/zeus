package org.zunpeng.service.account;

/**
 * Created by dapeng on 7/4/16.
 */
public interface AccountService {

	SimpleAccountInfo getById(Long id);

	SimpleAccountInfo getByUsername(String username);

	SimpleAccountInfo getBySlug(String slug);
}
