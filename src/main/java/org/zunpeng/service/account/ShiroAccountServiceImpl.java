package org.zunpeng.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.mapper.ShiroAccountInfoMapper;

/**
 * Created by dapeng on 2016/10/16.
 */
@Service
@Transactional
public class ShiroAccountServiceImpl implements ShiroAccountService {

	@Autowired
	private ShiroAccountInfoMapper shiroAccountInfoMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public AccountInfo getById(Long accountId) {
		return shiroAccountInfoMapper.getById(accountId);
	}

	@Override
	public AccountInfo getByUsername(String username) {
		return shiroAccountInfoMapper.getByUsername(username);
	}
}
