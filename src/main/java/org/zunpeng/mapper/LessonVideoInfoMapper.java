package org.zunpeng.mapper;

import org.zunpeng.domain.LessonVideoInfo;

/**
 * Created by dapeng on 16/8/6.
 */
public interface LessonVideoInfoMapper {

	LessonVideoInfo getById(Long id);

	LessonVideoInfo getByFkey(String fkey);

	void save(LessonVideoInfo lessonVideoInfo);

	void update(LessonVideoInfo lessonVideoInfo);
}
