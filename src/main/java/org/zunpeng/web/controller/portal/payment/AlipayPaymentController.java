package org.zunpeng.web.controller.portal.payment;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zunpeng.service.payment.PaymentService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dapeng on 2017/3/3.
 */
@Controller
public class AlipayPaymentController {

	private static Logger logger = LoggerFactory.getLogger(AlipayPaymentController.class);

	@Autowired
	private PaymentService paymentService;

	@RequestMapping({"/shop/alipay/payment/callback", "/alipay/payment/callback"})
	@ResponseBody
	public String registerNew(HttpServletRequest request){
		logger.info("alipay callback: " + JSONObject.toJSONString(request.getParameterMap()));
		String outTradeNo = request.getParameter("out_trade_no");
		String tradeStatus = request.getParameter("trade_status");
		String totalFee = request.getParameter("total_fee");
		paymentService.updatePayment(outTradeNo, tradeStatus, totalFee);
		return "success";
	}
}
