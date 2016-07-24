package org.zunpeng.service.account;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.core.mybatis.Criterion;
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
		List<Criterion> criterionList = Lists.newArrayList();
		criterionList.add(new Criterion("id", "gt", 100));
		criterionList.add(new Criterion("id", "le", 200));
		criterionList.add(new Criterion("slug", "like", "6"));
		criterionList.add(new Criterion("id", "bt", 1, 150));
		criterionList.add(new Criterion("id", "notnull"));

		List<Integer> list = Lists.newArrayList();
		for(int i = 100; i < 200; i ++){
			list.add(i);
		}
		criterionList.add(new Criterion("id", "in", list.toArray()));

		Criteria criteria = new Criteria(pageable, criterionList);

		List<SlugInfo> items = slugInfoMapper.getAllLimit(criteria);
		long totalCount = slugInfoMapper.count(criteria);
		PageWrapper<SlugInfo> page = new PageWrapper<>(pageable, items, totalCount);
		return page;
	}

}
