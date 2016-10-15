package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.SolutionInfo;

import java.util.List;

/**
 * Created by dapeng on 2016/10/14.
 */
public interface SolutionInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	SolutionInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	SolutionInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(SolutionInfo solutionInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(SolutionInfo solutionInfo);

	List<SolutionInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
