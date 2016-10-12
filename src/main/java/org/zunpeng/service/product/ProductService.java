package org.zunpeng.service.product;

import org.springframework.data.domain.Pageable;
import org.zunpeng.core.page.PageWrapper;
import org.zunpeng.web.controller.admin.product.ProductFormBean;

import java.io.IOException;

/**
 * Created by dapeng on 2016/10/12.
 */
public interface ProductService {

	SimpleProductInfo getBySlug(String slug);

	PageWrapper<SimpleProductInfo> page(Pageable pageable);

	SimpleProductInfo add(ProductFormBean formBean) throws IOException;

	SimpleProductInfo edit(ProductFormBean formBean) throws IOException;
}
