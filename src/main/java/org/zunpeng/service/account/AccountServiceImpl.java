package org.zunpeng.service.account;

import com.oldpeng.core.utils.BeanCopyUtils;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.AccountInfoMapper;
import org.zunpeng.web.controller.portal.account.RegisterFormBean;

import java.util.Date;

/**
 * Created by dapeng on 7/4/16.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountInfoMapper accountInfoMapper;

	@Override
	public SimpleAccountInfo getById(Long id) {
		return trans2SimpleAccountInfo(accountInfoMapper.getById(id));
	}

	@Override
	public SimpleAccountInfo getByUsername(String username) {
		return trans2SimpleAccountInfo(accountInfoMapper.getByUsername(username));
	}

	@Override
	public SimpleAccountInfo getBySlug(String slug) {
		return trans2SimpleAccountInfo(accountInfoMapper.getBySlug(slug));
	}

	@Override
	public void add(RegisterFormBean formBean) {
		String mobile = formBean.getMobile().trim();
		String password = formBean.getPassword();
		String rePassword = formBean.getRePassword();

		if(StringUtils.isBlank(password)
				|| StringUtils.isBlank(rePassword)
				|| !password.equals(rePassword)){
			throw new RuntimeException("两次输入的密码不同");
		}

		AccountInfo accountInfo = accountInfoMapper.getByUsername(mobile);
		if(accountInfo != null){
			throw new RuntimeException("该手机号已存在，请更换手机号或直接登录");
		}

		accountInfo = new AccountInfo();
		accountInfo.setMobile(mobile);
		accountInfo.setUsername(mobile);
		accountInfo.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		accountInfo.setCreateTime(new Date());
		accountInfo.setLastModifyTime(new Date());

		accountInfoMapper.save(accountInfo);
	}

	private SimpleAccountInfo trans2SimpleAccountInfo(AccountInfo accountInfo){
		if(accountInfo == null){
			return null;
		}

		return BeanCopyUtils.copy(accountInfo, SimpleAccountInfo.class);
	}
}
