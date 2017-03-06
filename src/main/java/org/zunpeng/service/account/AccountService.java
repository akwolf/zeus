package org.zunpeng.service.account;

import org.zunpeng.web.controller.portal.account.RegisterFormBean;

/**
 * Created by dapeng on 7/4/16.
 */
public interface AccountService {

	SimpleAccountInfo getById(Long id);

	SimpleAccountInfo getByUsername(String username);

	SimpleAccountInfo getBySlug(String slug);

	void add(RegisterFormBean formBean);
}
