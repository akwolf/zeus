package org.zunpeng.service.company;

import org.zunpeng.web.controller.admin.company.CompanyFormBean;

import java.io.IOException;

/**
 * Created by dapeng on 2016/10/14.
 */
public interface CompanyService {

	SimpleCompanyInfo getInfo(Long id);

	SimpleCompanyInfo add(CompanyFormBean formBean) throws IOException;

	SimpleCompanyInfo updateInfo(CompanyFormBean formBean) throws IOException;
}
