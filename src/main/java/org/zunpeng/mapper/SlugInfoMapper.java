package org.zunpeng.mapper;

import org.apache.ibatis.annotations.Param;
import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.SlugInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by dapeng on 7/4/16.
 */
public interface SlugInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	SlugInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.CLEAR)
	void batchInsert(@Param("slugSet") Set<String> slugSet);

	@Caching(operator = Caching.OperatorType.SAVE)
	void insert(SlugInfo slug);

	long count(Criteria criteria);

	List<SlugInfo> getAllLimit(Criteria criteria);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	SlugInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(SlugInfo slugInfo);
}
