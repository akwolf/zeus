package org.zunpeng.service.article;

import org.springframework.data.domain.Pageable;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.web.controller.admin.article.ArticleFormBean;

/**
 * Created by dapeng on 2016/10/12.
 */
public interface ArticleService {

	PageWrapper<SimpleArticleInfo> page(Pageable pageable);

	SimpleArticleInfo getBySlug(String slug);

	SimpleArticleInfo add(ArticleFormBean formBean);

	SimpleArticleInfo edit(ArticleFormBean formBean);
}
