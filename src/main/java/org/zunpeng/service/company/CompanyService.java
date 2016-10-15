package org.zunpeng.service.company;

import org.zunpeng.web.controller.admin.company.CompanyFormBean;

import java.io.IOException;

/**
 * Created by dapeng on 2016/10/14.
 */
public interface CompanyService {

	SimpleCompanyInfo getInfo();

	SimpleCompanyInfo updateInfo(CompanyFormBean formBean) throws IOException;
}
