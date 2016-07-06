package org.zunpeng.mapper;

import org.springframework.data.domain.Page;
import org.zunpeng.domain.AccountInfo;

/**
 * Created by dapeng on 6/30/16.
 */
public interface AccountInfoMapper {

	Page<AccountInfo> pageByCondition();

	AccountInfo getById(Long id);
}
