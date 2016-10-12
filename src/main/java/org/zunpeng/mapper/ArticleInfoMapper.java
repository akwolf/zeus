package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.domain.ArticleInfo;

import java.util.List;

/**
 * Created by dapeng on 7/7/16.
 */
public interface ArticleInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	ArticleInfo getById(Long id);

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	ArticleInfo getBySlug(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(ArticleInfo articleInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(ArticleInfo articleInfo);

	List<ArticleInfo> getAllLimit(Criteria criteria);

	long count(Criteria criteria);
}
