package org.zunpeng.mapper;

import org.apache.ibatis.annotations.Param;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.SlugInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by dapeng on 7/4/16.
 */
public interface SlugInfoMapper {

	SlugInfo getById(Long id);

	void batchInsert(@Param("slugSet") Set<String> slugSet);

	void insert(SlugInfo slug);

	long count(Criteria criteria);

	List<SlugInfo> getAllLimit(Criteria criteria);

}
