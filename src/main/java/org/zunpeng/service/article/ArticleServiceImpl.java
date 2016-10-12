package org.zunpeng.service.article;

import com.oldpeng.core.utils.BeanCopyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.core.utils.CleanContentUtils;
import org.zunpeng.domain.ArticleInfo;
import org.zunpeng.mapper.ArticleInfoMapper;
import org.zunpeng.mapper.SlugInfoMapper;
import org.zunpeng.web.controller.admin.article.ArticleFormBean;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dapeng on 2016/10/12.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleInfoMapper articleInfoMapper;

	@Autowired
	private SlugInfoMapper slugInfoMapper;

	@Override
	public PageWrapper<SimpleArticleInfo> page(Pageable pageable) {
		Criteria criteria = new Criteria(pageable);
		List<ArticleInfo> articleInfoList = articleInfoMapper.getAllLimit(criteria);
		long count = articleInfoMapper.count(criteria);

		List<SimpleArticleInfo> simpleArticleInfoList = articleInfoList.stream().map(this::trans2SimpleArticle).collect(Collectors.toList());

		return new PageWrapper<>(pageable, simpleArticleInfoList, count);
	}

	@Override
	public SimpleArticleInfo getBySlug(String slug) {
		ArticleInfo articleInfo = articleInfoMapper.getBySlug(slug);
		return trans2SimpleArticle(articleInfo);
	}

	@Override
	public SimpleArticleInfo add(ArticleFormBean formBean) {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		articleInfo.setContent(CleanContentUtils.cleanHtml(formBean.getContent()));
		articleInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		articleInfo.setDeleted(formBean.isDeleted());
		articleInfo.setDisabled(formBean.isDisabled());
		articleInfo.setCreateTime(new Date());
		articleInfo.setLastModifyTime(new Date());
		articleInfoMapper.save(articleInfo);

		articleInfo.setSlug(slugInfoMapper.getById(articleInfo.getId()).getSlug());
		articleInfoMapper.update(articleInfo);

		return trans2SimpleArticle(articleInfo);
	}

	@Override
	public SimpleArticleInfo edit(ArticleFormBean formBean) {
		if(StringUtils.isBlank(formBean.getSlug())){
			throw new RuntimeException("slug is blank");
		}

		ArticleInfo articleInfo = articleInfoMapper.getBySlug(formBean.getSlug());
		if(articleInfo == null){
			throw new RuntimeException("article doesn't exist");
		}

		articleInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		articleInfo.setContent(CleanContentUtils.cleanHtml(formBean.getContent()));
		articleInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		articleInfo.setDeleted(formBean.isDeleted());
		articleInfo.setDisabled(formBean.isDisabled());
		articleInfo.setLastModifyTime(new Date());
		articleInfoMapper.update(articleInfo);

		return trans2SimpleArticle(articleInfo);
	}

	private SimpleArticleInfo trans2SimpleArticle(ArticleInfo articleInfo){
		return BeanCopyUtils.copy(articleInfo, SimpleArticleInfo.class);
	}
}
