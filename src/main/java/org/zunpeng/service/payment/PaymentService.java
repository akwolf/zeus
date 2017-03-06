package org.zunpeng.service.payment;

import com.oldpeng.core.alipay.AlipayPaymentBean;

/**
 * Created by dapeng on 2017/3/6.
 */
public interface PaymentService {

	AlipayPaymentBean getPaymentUrl(Long accountId);

	void updatePayment(String outTradeNo, String tradeStatus, String totalFee);
}
