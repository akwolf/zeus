package org.zunpeng.service.account;

import org.springframework.data.domain.Pageable;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.domain.SlugInfo;

/**
 * Created by dapeng on 7/4/16.
 */
public interface AccountService {

	Object test();

	void testAdd();

	void testAddOne();

	PageWrapper<SlugInfo> page(Pageable pageable);

	void testFind(Long id);

	void testFindBySlug(String slug);

	void updateSlug();
}
