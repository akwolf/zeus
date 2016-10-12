package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.LessonVideoInfo;

import java.util.List;

/**
 * Created by dapeng on 16/8/6.
 */
public interface LessonVideoInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	LessonVideoInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	LessonVideoInfo getByFkey(String fkey);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(LessonVideoInfo lessonVideoInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(LessonVideoInfo lessonVideoInfo);

	List<LessonVideoInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
