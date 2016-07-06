package org.zunpeng.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
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

	long count(Pageable pageable);

	List<SlugInfo> getAllLimit(Pageable pageable);

	List<SlugInfo> getAllLimit2(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
