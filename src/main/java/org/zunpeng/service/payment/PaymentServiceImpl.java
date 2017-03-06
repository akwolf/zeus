package org.zunpeng.service.payment;

import com.alibaba.fastjson.JSONObject;
import com.oldpeng.core.alipay.AlipayPaymentBean;
import com.oldpeng.core.utils.ApiUtils;
import com.oldpeng.core.utils.Md5Utils;
import com.oldpeng.core.utils.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zunpeng.domain.AccountInfo;
import org.zunpeng.domain.PaymentInfo;
import org.zunpeng.mapper.AccountInfoMapper;
import org.zunpeng.mapper.PaymentInfoMapper;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by dapeng on 2017/3/6.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Value("${alipay.partner}")
	private String partner;

	@Value("${alipay.key}")
	private String key;

	@Value("${alipay.seller.email}")
	private String sellerMail;

	@Value("${alipay.notify_url}")
	private String notifyUrl;

	@Value("${alipay.return_url}")
	private String returnUrl;

	@Autowired
	private PaymentInfoMapper paymentInfoMapper;

	@Autowired
	private AccountInfoMapper accountInfoMapper;

	@Override
	public AlipayPaymentBean getPaymentUrl(Long accountId) throws UnsupportedEncodingException {
		AlipayPaymentBean alipayPaymentBean = new AlipayPaymentBean();

		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setCreateTime(new Date());
		paymentInfo.setAccountId(accountId);
		paymentInfo.setOutTradeNo(UuidUtils.generate());
		paymentInfo.setStatus(0);
		paymentInfo.setTotalFee(0.01);
		paymentInfo.setLastModifyTime(new Date());
		paymentInfoMapper.save(paymentInfo);

		alipayPaymentBean.setPartner(partner);
		alipayPaymentBean.setSellerEmail(sellerMail);
		alipayPaymentBean.setOutTradeNo(paymentInfo.getOutTradeNo());
		alipayPaymentBean.setTotalFee(paymentInfo.getTotalFee());
		alipayPaymentBean.setSubject("成为付费会员");
		alipayPaymentBean.setBody("成为付费会员");
		alipayPaymentBean.setNotifyUrl(notifyUrl);
		alipayPaymentBean.setReturnUrl(returnUrl);
		alipayPaymentBean.setItBPay("30m");
		alipayPaymentBean.setPaymentType(1);

		logger.info("payment bean: " + JSONObject.toJSONString(alipayPaymentBean));

		String preSign = ApiUtils.buildParamStr(alipayPaymentBean.retrieveStringProp(), false, true) + key;
		logger.info("pre sign: " + preSign);

		alipayPaymentBean.setSign(Md5Utils.md5(preSign));

		return alipayPaymentBean;
	}

	@Override
	public void updatePayment(String outTradeNo, String tradeStatus, String totalFee) {
		PaymentInfo paymentInfo = paymentInfoMapper.getByOutTradeNo(outTradeNo);

		if(paymentInfo != null){

			if("TRADE_SUCCESS".equalsIgnoreCase(tradeStatus)
					|| "TRADE_PENDING".equalsIgnoreCase(tradeStatus)
					|| "TRADE_FINISHED".equalsIgnoreCase(tradeStatus)){
				paymentInfo.setStatus(1);
				paymentInfo.setTotalFee(Double.valueOf(totalFee));
				paymentInfo.setLastModifyTime(new Date());
				paymentInfoMapper.update(paymentInfo);

				AccountInfo accountInfo = accountInfoMapper.getById(paymentInfo.getAccountId());
				accountInfo.setBrief("hello");
				accountInfoMapper.update(accountInfo);
			}
		}
	}
}
