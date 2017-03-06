package org.zunpeng.mapper;

import org.zunpeng.core.annotation.Caching;
import org.zunpeng.domain.PaymentInfo;

/**
 * Created by dapeng on 2017/3/6.
 */
public interface PaymentInfoMapper {

	@Caching(operator = Caching.OperatorType.GET_SINGLE)
	PaymentInfo getByOutTradeNo(String slug);

	@Caching(operator = Caching.OperatorType.SAVE)
	void save(PaymentInfo paymentInfo);

	@Caching(operator = Caching.OperatorType.UPDATE)
	void update(PaymentInfo paymentInfo);
}
