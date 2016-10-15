package org.zunpeng.service.solution;

import org.springframework.data.domain.Pageable;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.web.controller.admin.solution.SolutionFormBean;

import java.io.IOException;
import java.util.List;

/**
 * Created by dapeng on 2016/10/14.
 */
public interface SolutionService {

	PageWrapper<SimpleSolutionInfo> page(Pageable pageable);

	SimpleSolutionInfo getBySlug(String slug);

	SimpleSolutionInfo add(SolutionFormBean formBean) throws IOException;

	SimpleSolutionInfo edit(SolutionFormBean formBean) throws IOException;

	List<SimpleSolutionInfo> getAllRecommend();
}
