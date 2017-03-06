package org.zunpeng.service.product;

import com.google.common.collect.Lists;
import com.oldpeng.core.utils.BeanCopyUtils;
import com.oldpeng.core.utils.MathUtils;
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
import org.zunpeng.domain.ProductInfo;
import org.zunpeng.domain.SlugInfo;
import org.zunpeng.mapper.ProductInfoMapper;
import org.zunpeng.mapper.SlugInfoMapper;
import org.zunpeng.service.upload.ImageService;
import org.zunpeng.service.upload.SimpleUploadFileInfo;
import org.zunpeng.web.controller.admin.product.ProductFormBean;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dapeng on 2016/10/12.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoMapper productInfoMapper;

	@Autowired
	private SlugInfoMapper slugInfoMapper;

	@Autowired
	private ImageService imageService;

	@Override
	public SimpleProductInfo getBySlug(String slug) {
		ProductInfo productInfo = productInfoMapper.getBySlug(slug);
		return trans2SimpleProductInfo(productInfo);
	}

	@Override
	public PageWrapper<SimpleProductInfo> page(Pageable pageable) {
		Criteria criteria = new Criteria(pageable);
		List<ProductInfo> productInfoList = productInfoMapper.getAllLimit(criteria);
		long count = productInfoMapper.count(criteria);

		List<SimpleProductInfo> simpleProductInfoList = Lists.newArrayList();
		simpleProductInfoList.addAll(productInfoList.stream().map(this::trans2SimpleProductInfo).collect(Collectors.toList()));

		return new PageWrapper<>(pageable, simpleProductInfoList, count);
	}

	@Override
	public SimpleProductInfo add(ProductFormBean formBean) throws IOException {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setAmount(MathUtils.convert2Cent(formBean.getPrice()));
		productInfo.setBrief(CleanContentUtils.clean(formBean.getBrief()));
		productInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		productInfo.setName(CleanContentUtils.clean(formBean.getName()));
		productInfo.setLastModifyTime(new Date());
		productInfo.setCreateTime(new Date());
		productInfo.setPublished(formBean.isPublished());
		productInfo.setDeleted(formBean.isDeleted());
		productInfo.setSequence(formBean.getSequence());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			productInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		productInfoMapper.save(productInfo);

		SlugInfo slugInfo = slugInfoMapper.getById(productInfo.getId());
		productInfo.setSlug(slugInfo.getSlug());
		productInfoMapper.update(productInfo);

		return trans2SimpleProductInfo(productInfo);
	}

	@Override
	public SimpleProductInfo edit(ProductFormBean formBean) throws IOException {
		if(StringUtils.isBlank(formBean.getSlug())){
			throw new RuntimeException("slug is blank");
		}

		ProductInfo productInfo = productInfoMapper.getBySlug(formBean.getSlug());
		if(productInfo == null){
			throw new RuntimeException("product doesn't exist");
		}

		productInfo.setAmount(MathUtils.convert2Cent(formBean.getPrice()));
		productInfo.setBrief(CleanContentUtils.clean(formBean.getBrief()));
		productInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		productInfo.setName(CleanContentUtils.clean(formBean.getName()));
		productInfo.setSequence(formBean.getSequence());
		productInfo.setPublished(formBean.isPublished());
		productInfo.setLastModifyTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(formBean.getUpload());
			productInfo.setCoverImg(simpleUploadFileInfo.getFileName());
		}

		productInfoMapper.update(productInfo);

		return trans2SimpleProductInfo(productInfo);
	}

	@Override
	public List<SimpleProductInfo> getAllRecommend() {
		PageRequest pageRequest = new PageRequest(0, 10, Sort.Direction.ASC, "id");
		List<Criterion> criterionList = Lists.newArrayList();

		criterionList.add(new Criterion("deleted", "eq", false));
		criterionList.add(new Criterion("published", "eq", true));

		Criteria criteria = new Criteria(pageRequest, criterionList);

		List<ProductInfo> productInfoList = productInfoMapper.getAllLimit(criteria);
		return productInfoList.stream().map(this::trans2SimpleProductInfo).collect(Collectors.toList());
	}

	@Override
	public PageWrapper<SimpleProductInfo> pageByPublished(Pageable pageable) {
		List<Criterion> criterionList = Lists.newArrayList();
		criterionList.add(new Criterion("deleted", "eq", false));
		criterionList.add(new Criterion("published", "eq", true));

		Criteria criteria = new Criteria(pageable, criterionList);
		List<ProductInfo> productInfoList = productInfoMapper.getAllLimit(criteria);

		long count = productInfoMapper.count(criteria);

		List<SimpleProductInfo> simpleProductInfoList = Lists.newArrayList();
		simpleProductInfoList.addAll(productInfoList.stream().map(this::trans2SimpleProductInfo).collect(Collectors.toList()));

		return new PageWrapper<>(pageable, simpleProductInfoList, count);
	}

	private SimpleProductInfo trans2SimpleProductInfo(ProductInfo productInfo) {
		if(productInfo == null){
			return null;
		}

		SimpleProductInfo simpleProductInfo = BeanCopyUtils.copy(productInfo, SimpleProductInfo.class);
		simpleProductInfo.setPrice(MathUtils.convert2Dollar(productInfo.getAmount()));
		if(productInfo.getCoverImg() != null){
			simpleProductInfo.setCoverUrl(imageService.buildUrl(productInfo.getCoverImg()));
		}
		return simpleProductInfo;
	}
}
