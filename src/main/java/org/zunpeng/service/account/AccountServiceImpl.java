package org.zunpeng.service.account;

import com.oldpeng.core.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.AccountInfoMapper;

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

	private SimpleAccountInfo trans2SimpleAccountInfo(AccountInfo accountInfo){
		if(accountInfo == null){
			return null;
		}

		return BeanCopyUtils.copy(accountInfo, SimpleAccountInfo.class);
	}
}
