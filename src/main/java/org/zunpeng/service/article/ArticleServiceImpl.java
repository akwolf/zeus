package org.zunpeng.service.article;

import com.google.common.collect.Lists;
import com.oldpeng.core.utils.BeanCopyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zunpeng.core.mybatis.Criteria;
import org.zunpeng.core.mybatis.Criterion;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.core.utils.CleanContentUtils;
import org.zunpeng.domain.ArticleInfo;
import org.zunpeng.mapper.ArticleInfoMapper;
import org.zunpeng.mapper.SlugInfoMapper;
import org.zunpeng.service.upload.ImageService;
import org.zunpeng.service.upload.SimpleUploadFileInfo;
import org.zunpeng.web.controller.admin.article.ArticleFormBean;

import java.io.IOException;
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

	@Autowired
	private ImageService imageService;

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
	public SimpleArticleInfo add(ArticleFormBean formBean) throws IOException {
		ArticleInfo articleInfo = new ArticleInfo();
		articleInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		articleInfo.setContent(CleanContentUtils.cleanHtml(formBean.getContent()));
		articleInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		articleInfo.setDeleted(formBean.isDeleted());
		articleInfo.setPublished(formBean.isPublished());
		articleInfo.setCreateTime(new Date());
		articleInfo.setSequence(formBean.getSequence());
		articleInfo.setLastModifyTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			articleInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		articleInfoMapper.save(articleInfo);

		articleInfo.setSlug(slugInfoMapper.getById(articleInfo.getId()).getSlug());
		articleInfoMapper.update(articleInfo);

		return trans2SimpleArticle(articleInfo);
	}

	@Override
	public SimpleArticleInfo edit(ArticleFormBean formBean) throws IOException {
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
		articleInfo.setPublished(formBean.isPublished());
		articleInfo.setSequence(formBean.getSequence());
		articleInfo.setLastModifyTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			articleInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		articleInfoMapper.update(articleInfo);

		return trans2SimpleArticle(articleInfo);
	}

	@Override
	public List<SimpleArticleInfo> getAllRecommend() {
		PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.ASC, "id");
		List<Criterion> criterionList = Lists.newArrayList();

		criterionList.add(new Criterion("deleted", "eq", false));
		criterionList.add(new Criterion("published", "eq", true));

		Criteria criteria = new Criteria(pageRequest, criterionList);

		List<ArticleInfo> articleInfoList = articleInfoMapper.getAllLimit(criteria);
		return articleInfoList.stream().map(this::trans2SimpleArticle).collect(Collectors.toList());
	}

	@Override
	public PageWrapper<SimpleArticleInfo> pageByPublished(Pageable pageable) {
		List<Criterion> criterionList = Lists.newArrayList();
		criterionList.add(new Criterion("deleted", "eq", false));
		criterionList.add(new Criterion("published", "eq", true));

		Criteria criteria = new Criteria(pageable, criterionList);
		List<ArticleInfo> articleInfoList = articleInfoMapper.getAllLimit(criteria);
		long count = articleInfoMapper.count(criteria);

		List<SimpleArticleInfo> simpleArticleInfoList = articleInfoList.stream().map(this::trans2SimpleArticle).collect(Collectors.toList());

		return new PageWrapper<>(pageable, simpleArticleInfoList, count);
	}

	private SimpleArticleInfo trans2SimpleArticle(ArticleInfo articleInfo){
		if(articleInfo == null){
			return null;
		}

		SimpleArticleInfo simpleArticleInfo = BeanCopyUtils.copy(articleInfo, SimpleArticleInfo.class);
		if(articleInfo.getCoverImg() != null){
			simpleArticleInfo.setCoverUrl(imageService.buildUrl(articleInfo.getCoverImg()));
		}
		return simpleArticleInfo;
	}
}
