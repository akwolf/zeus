package org.zunpeng.service.account;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.core.utils.SlugGenerateUtils;
import org.zunpeng.domain.SlugInfo;
import org.zunpeng.mapper.SlugInfoMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by dapeng on 7/4/16.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private SlugInfoMapper slugInfoMapper;

	@Override
	public Object test() {
		return slugInfoMapper.getById(2L);
	}

	@Override
	public void testAdd() {
		Set<String> set = Sets.newHashSet();
		for(int i = 0; i < 100; i ++){
			set.add(SlugGenerateUtils.generate(6));
		}
		slugInfoMapper.batchInsert(set);
	}

	@Override
	public void testAddOne() {
		SlugInfo slugInfo = new SlugInfo();
		slugInfo.setSlug(UUID.randomUUID().toString().replace("-", ""));
		slugInfoMapper.insert(slugInfo);
		logger.info("slugInfo: " + JSONObject.toJSONString(slugInfo));
	}

	@Override
	public PageWrapper<SlugInfo> page(Pageable pageable) {
		List<SlugInfo> items = slugInfoMapper.getAllLimit(pageable);
//		List<SlugInfo> items = slugInfoMapper.getAllLimit2(pageable.getOffset(), pageable.getPageSize());
		long totalCount = slugInfoMapper.count(pageable);
		PageWrapper<SlugInfo> page = new PageWrapper<>(items, pageable, totalCount);
		return page;
	}

}
