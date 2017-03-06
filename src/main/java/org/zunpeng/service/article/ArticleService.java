package org.zunpeng.service.article;

import org.springframework.data.domain.Pageable;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.web.controller.admin.article.ArticleFormBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by dapeng on 2016/10/12.
 */
public interface ArticleService {

	PageWrapper<SimpleArticleInfo> page(Pageable pageable);

	SimpleArticleInfo getBySlug(String slug);

	SimpleArticleInfo add(ArticleFormBean formBean) throws IOException;

	SimpleArticleInfo edit(ArticleFormBean formBean) throws IOException;

	List<SimpleArticleInfo> getAllRecommend();

	PageWrapper<SimpleArticleInfo> pageByPublished(Pageable pageable);
}
