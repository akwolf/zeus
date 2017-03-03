package org.zunpeng.service.company;

import com.oldpeng.core.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zunpeng.core.utils.CleanContentUtils;
import org.zunpeng.domain.CompanyInfo;
import org.zunpeng.mapper.CompanyInfoMapper;
import org.zunpeng.service.upload.ImageService;
import org.zunpeng.service.upload.SimpleUploadFileInfo;
import org.zunpeng.web.controller.admin.company.CompanyFormBean;

import java.io.IOException;
import java.util.Date;

/**
 * Created by dapeng on 2016/10/14.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyInfoMapper companyInfoMapper;

	@Autowired
	private ImageService imageService;

	@Override
	public SimpleCompanyInfo getInfo(Long id) {
		CompanyInfo companyInfo = companyInfoMapper.getById(id);
		return trans2SimpleCompanyInfo(companyInfo);
	}

	@Override
	public SimpleCompanyInfo add(CompanyFormBean formBean) throws IOException {
		CompanyInfo companyInfo = new CompanyInfo();

		companyInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		companyInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		companyInfo.setAddress(CleanContentUtils.clean(formBean.getAddress()));
		companyInfo.setBrief(CleanContentUtils.clean(formBean.getBrief()));
		companyInfo.setContact(CleanContentUtils.clean(formBean.getContact()));
		companyInfo.setTelphone(CleanContentUtils.clean(formBean.getTelphone()));
		companyInfo.setMobile(CleanContentUtils.clean(formBean.getMobile()));
		companyInfo.setLastModifyTime(new Date());
		companyInfo.setCreateTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(uploadFile);
			companyInfo.setLogoImg(simpleUploadFileInfo.getFileName());
		}

		companyInfoMapper.save(companyInfo);

		return trans2SimpleCompanyInfo(companyInfo);
	}

	@Override
	public SimpleCompanyInfo updateInfo(CompanyFormBean formBean) throws IOException {
		CompanyInfo companyInfo = companyInfoMapper.getById(formBean.getId());
		companyInfo.setTitle(CleanContentUtils.clean(formBean.getTitle()));
		companyInfo.setDescription(CleanContentUtils.cleanHtml(formBean.getDescription()));
		companyInfo.setAddress(CleanContentUtils.clean(formBean.getAddress()));
		companyInfo.setBrief(CleanContentUtils.clean(formBean.getBrief()));
		companyInfo.setContact(CleanContentUtils.clean(formBean.getContact()));
		companyInfo.setTelphone(CleanContentUtils.clean(formBean.getTelphone()));
		companyInfo.setMobile(CleanContentUtils.clean(formBean.getMobile()));
		companyInfo.setLastModifyTime(new Date());

		MultipartFile uploadFile = formBean.getUpload();
		if(uploadFile != null && !uploadFile.isEmpty()){
			SimpleUploadFileInfo simpleUploadFileInfo = imageService.upload(uploadFile);
			companyInfo.setLogoImg(simpleUploadFileInfo.getFileName());
		}

		companyInfoMapper.update(companyInfo);

		return trans2SimpleCompanyInfo(companyInfo);
	}

	private SimpleCompanyInfo trans2SimpleCompanyInfo(CompanyInfo companyInfo) {
		if(companyInfo == null){
			return null;
		}

		SimpleCompanyInfo simpleCompanyInfo = BeanCopyUtils.copy(companyInfo, SimpleCompanyInfo.class);
		if(companyInfo.getLogoImg() != null){
			simpleCompanyInfo.setLogoUrl(imageService.buildUrl(companyInfo.getLogoImg()));
		}

		return simpleCompanyInfo;
	}
}
