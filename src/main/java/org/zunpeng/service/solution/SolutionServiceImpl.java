package org.zunpeng.service.solution;

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
import org.zunpeng.domain.SlugInfo;
import org.zunpeng.domain.SolutionInfo;
import org.zunpeng.mapper.SlugInfoMapper;
import org.zunpeng.mapper.SolutionInfoMapper;
import org.zunpeng.service.upload.ImageService;
import org.zunpeng.service.upload.SimpleUploadFileInfo;
import org.zunpeng.web.controller.admin.solution.SolutionFormBean;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dapeng on 2016/10/14.
 */
@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {

	@Autowired
	private SolutionInfoMapper solutionInfoMapper;

	@Autowired
	private SlugInfoMapper slugInfoMapper;

	@Autowired
	private ImageService imageService;

	@Override
	public PageWrapper<SimpleSolutionInfo> page(Pageable pageable) {
		Criteria criteria = new Criteria(pageable);
		List<SolutionInfo> solutionInfoList = solutionInfoMapper.getAllLimit(criteria);
		long count = solutionInfoMapper.count(criteria);

		List<SimpleSolutionInfo> simpleSolutionInfoList = solutionInfoList.stream().map(this::trans2SimpleSolutionInfo).collect(Collectors.toList());

		return new PageWrapper<>(pageable, simpleSolutionInfoList, count);
	}

	@Override
	public SimpleSolutionInfo getBySlug(String slug) {
		return trans2SimpleSolutionInfo(solutionInfoMapper.getBySlug(slug));
	}

	@Override
	public SimpleSolutionInfo add(SolutionFormBean formBean) throws IOException {
		SolutionInfo solutionInfo = new SolutionInfo();
		solutionInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		solutionInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		solutionInfo.setContent(CleanContentUtils.cleanHtml(formBean.getContent()));
		solutionInfo.setTechnology(CleanContentUtils.cleanHtml(formBean.getTechnology()));
		solutionInfo.setCreateTime(new Date());
		solutionInfo.setLastModifyTime(new Date());
		solutionInfo.setDeleted(formBean.isDeleted());
		solutionInfo.setPublished(formBean.isPublished());
		solutionInfo.setSequence(formBean.getSequence());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			solutionInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		solutionInfoMapper.save(solutionInfo);

		SlugInfo slugInfo = slugInfoMapper.getById(solutionInfo.getId());
		solutionInfo.setSlug(slugInfo.getSlug());
		solutionInfoMapper.update(solutionInfo);

		return trans2SimpleSolutionInfo(solutionInfo);
	}

	@Override
	public SimpleSolutionInfo edit(SolutionFormBean formBean) throws IOException {
		String slug = formBean.getSlug();
		if(StringUtils.isBlank(slug)){
			throw new RuntimeException("slug is blank");
		}

		SolutionInfo solutionInfo = solutionInfoMapper.getBySlug(slug);
		if(solutionInfo == null){
			throw new RuntimeException("solution doesn't exist");
		}

		solutionInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		solutionInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		solutionInfo.setContent(CleanContentUtils.cleanHtml(formBean.getContent()));
		solutionInfo.setTechnology(CleanContentUtils.cleanHtml(formBean.getTechnology()));
		solutionInfo.setSequence(formBean.getSequence());
		solutionInfo.setPublished(formBean.isPublished());
		solutionInfo.setLastModifyTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			solutionInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		solutionInfoMapper.update(solutionInfo);

		return trans2SimpleSolutionInfo(solutionInfo);
	}

	@Override
	public List<SimpleSolutionInfo> getAllRecommend() {
		PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.ASC, "id");
		List<Criterion> criterionList = Lists.newArrayList();
		criterionList.add(new Criterion("deleted", "eq", false));
		criterionList.add(new Criterion("published", "eq", true));

		Criteria criteria = new Criteria(pageRequest, criterionList);

		List<SolutionInfo> productInfoList = solutionInfoMapper.getAllLimit(criteria);
		return productInfoList.stream().map(this::trans2SimpleSolutionInfo).collect(Collectors.toList());
	}

	private SimpleSolutionInfo trans2SimpleSolutionInfo(SolutionInfo solutionInfo) {
		if(solutionInfo == null){
			return null;
		}

		SimpleSolutionInfo simpleSolutionInfo = BeanCopyUtils.copy(solutionInfo, SimpleSolutionInfo.class);
		simpleSolutionInfo.setCoverUrl(imageService.buildUrl(solutionInfo.getCoverImg()));
		return simpleSolutionInfo;
	}
}
