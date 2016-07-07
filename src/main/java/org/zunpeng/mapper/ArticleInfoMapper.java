package org.zunpeng.mapper;

import org.zunpeng.domain.ArticleInfo;

/**
 * Created by dapeng on 7/7/16.
 */
public interface ArticleInfoMapper {

	ArticleInfo getById(Long id);

	ArticleInfo getBySlug(String slug);

	void save(ArticleInfo articleInfo);

	void update(ArticleInfo articleInfo);
}
