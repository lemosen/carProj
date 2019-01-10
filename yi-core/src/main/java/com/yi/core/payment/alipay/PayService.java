package com.yi.core.payment.alipay;
///**
// * 
// */
//package com.yi.core.pay;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.JoinType;
//import javax.persistence.criteria.Order;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.NullArgumentException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.yihz.common.exception.CommonException;
//import com.yihz.common.utils.date.DateUtils;
//
//import io.micrometer.core.instrument.MeterRegistry.Config;
//
///**
// * @author Aidy
// * 
// */
//@Service
//@Transactional
//public class PayService implements IPayService {
//
//	private static Logger logger = Logger.getLogger(PayService.class);
//
//	@Resource
//	IOrderService orderService;
//
//	@Resource
//	IRankService rankService;
//
//	@Resource
//	private PayDao payDao;
//
//	@Resource
//	private OrderDao orderDao;
//
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public String pay(String orderCode, Customer customer, ChannelType channelType, String servicePath)
//			throws Exception {
//		if (null == customer) {
//			throw new NullPointerException("customer is null");
//		}
//		Order order = orderService.getOrderByCode(orderCode);
//		AlipayCore.logInfo("用户提交支付请求 订单号：" + order.getOrderCode() + " 用户登录账号：" + customer.getAccountName());
//		try {
//			Payment payment = new Payment();
//			String payFlowCode = "PAY" + DateUtils.getSeriNo();
//			payment.setChannelType(channelType);
//			payment.setOrder(order);
//			payment.setPayFlowCode(payFlowCode);
//			payment.setCustomer(customer);
//			payment.setPayAmount(order.getFeeTotal());
//			payment.setCreateTime(new Date());
//			payment.setPayType(PayType.PAYMENT);
//			payment.setPayState(PayState.PROCESSING);
//			payment.setPayNotifyState(PayNotifyState.NOT_NOTIFY);
//			String htmlText = null;
//			// 组织支付参数、加密，发送支付请求(根据支付通道选择相应的方法 方法计3个)。支付订单编号用payId
//			if (channelType == ChannelType.ALIPAY) {// 支付宝
//				htmlText = aliPay(payment, servicePath);
//			} else if (payment.getChannelType() == ChannelType.UNIONPAY) {
//				// htmlText = unionPay(payment, request);
//			}
//			return htmlText;
//		} catch (Exception e) {
//			AlipayCore.logInfo("支付请求异常订单：" + order.getOrderCode() + " 错误信息：" + e);
//			return null;
//		}
//
//	}
//
//	@Override
//	public Payment getById(int payId) {
//		return payDao.findByPayId(payId);
//	}
//
//	// 支付宝支付请求
//	protected String aliPay(Payment payment, String servicePath) {
//		// 支付类型 必填，不能修改
//		String payment_type = "1";
//		// 服务器异步通知页面路径 //需http://格式的完整路径，不能加?id=123这类自定义参数
//		String notify_url = servicePath + GlobalConfig.getProperty(Config.ALIPAY_NOTIFY_URL);
//
//		// 页面跳转同步通知页面路径
//		// 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
//		String return_url = servicePath + GlobalConfig.getProperty(Config.ALIPAY_RETURN_URL);
//
//		// 卖家支付宝帐户 必填
//		String seller_email = AlipayConfig.seller_email;
//
//		// 商户订单号 商户网站订单系统中唯一订单号，必填
//		String out_trade_no = payment.getOrder().getOrderCode();
//
//		// 订单名称 可以为空
//		String subject = "兜兜云厨商品";
//
//		// 付款 必填
//		String total_fee = String.valueOf(payment.getPayAmount());
//
//		// 订单描述 可以为空
//		String body = "";
//
//		// 商品展示地址 需以http://开头的完整路径
//		String show_url = "http://www.13906.com/member/orders.htm";
//
//		// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
//		String anti_phishing_key = "";
//		// 商户号
//		String alipaypartner = AlipayConfig.partner;
//
//		// 把请求参数打包成数组
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", "create_direct_pay_by_user");
//		sParaTemp.put("partner", alipaypartner);
//		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("payment_type", payment_type);
//		sParaTemp.put("notify_url", notify_url);
//		sParaTemp.put("return_url", return_url);
//		sParaTemp.put("seller_email", seller_email);
//		sParaTemp.put("out_trade_no", out_trade_no);
//		sParaTemp.put("subject", subject);
//		sParaTemp.put("total_fee", total_fee);
//		sParaTemp.put("body", body);
//		sParaTemp.put("show_url", show_url);
//		sParaTemp.put("anti_phishing_key", anti_phishing_key);
//		sParaTemp.put("exter_invoke_ip", "");
//		Payment dbpayment = payDao.getPaymentByPayStateAndOrderCode(PayState.PROCESSING, out_trade_no);
//		if (null == dbpayment) {
//			payDao.save(payment);
//		}
//		AlipayCore.logResult("建立支付请求 订单号：" + out_trade_no);
//		// 建立请求
//		return AlipaySubmit.buildRequest(sParaTemp, "post", "submit");
//	}
//
//	public boolean callBackAlipay(Map<String, String> params) {
//		AlipayCore.logInfo("支付回调验证开始。。。");
//		// 计算得出通知验证结果
//		boolean verify_result = AlipayNotify.verify(params);
//		AlipayCore.logInfo("支付回调验证结果：" + verify_result);
//		// 验证成功
//		if (verify_result) {
//			// 回调内容
//			String msg = params.get("body");
//			// 商户订单号
//			String out_trade_no = params.get("out_trade_no");
//			// 交易流水号
//			String trade_no = params.get("trade_no");
//			// 交易金额
//			String total_fee = params.get("total_fee");
//			// 买家付宝账号
//			String buyer_email = params.get("buyer_email");
//			// 交易状态
//			String trade_status = params.get("trade_status");
//			// 购买时间
//			String notify_time = params.get("notify_time");
//
//			String error_code = params.get("error_code");
//
//			Order order = orderService.getOrderByCode(out_trade_no);
//			Payment payment = payDao.getPaymentByPayStateAndOrderCode(PayState.PROCESSING, out_trade_no);
//			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
//				try {
//					/** 已有支付记录 **/
//					if (order.getOrderState() == OrderState.PAID_PAYMENT
//							&& payment.getChannelType() == ChannelType.ALIPAY) {
//						AlipayCore.logInfo("已有支付记录！支付宝前台同步回调已经执行，后台回调不再次做订单处理");
//					} else {
//						AlipayCore.logInfo("支付宝支付开始。。。");
//						Date curDate = new Date();
//						payment.setBuyer_account(buyer_email);
//						payment.setPayFlowCode(trade_no);
//						payment.setPayNotifyState(PayNotifyState.NOTIFY_SUCCESS);
//						payment.setNotifyTime(DateUtils.parseTimestamp(notify_time));
//						// 支付信息
//						if (payment.getPayState() == PayState.PROCESSING) {/** 0:处理中1:处理成功2:处理失败3:部分成功 */
//							if (payment.getErrorCode() != null) {
//								payment.setErrorCode(error_code);
//								payment.setPayState(!payment.getErrorCode().equals("00") ? PayState.PROCESSED_FAILURE
//										: PayState.PROCESSED_SUCCESS);// '0：处理中1：处理成功2：处理失败'
//							} else if (payment.getErrorCode() == null || payment.getErrorCode().equals("00")) {
//								payment.setPayState(PayState.PROCESSED_SUCCESS);
//								AlipayCore.logInfo("支付宝支付处理成功！");
//							}
//							payment.setCompleteTime(curDate);
//						}
//
//						order.setOrderState(OrderState.PAID_PAYMENT);
//						order.setUpdateTime(curDate);
//						order.setPaymentType(PaymentType.ALIPAY);
//						order.setPaymentSequence(trade_no);
//						order.setPaymentTime(curDate);
//						// 找到当前用户，更新用户积分，根据当前积分，更新用户等级 并在积分记录表插入数据
//						rankService.updateRankAndIntegral(payment.getCustomer().getCustomerId(),
//								Double.valueOf(total_fee));
//						AlipayCore.logInfo("支付宝支付成功！更新用户等级并在积分记录表插入数据");
//					}
//
//					AlipayCore.logInfo("支付回调验证结束。。。");
//					return true;
//				} catch (Exception e) {
//					AlipayCore.logInfo("支付回调验证异常。。。异常信息：" + e);
//					return false;
//				}
//			} else {
//				payment.setPayNotifyState(PayNotifyState.NOTIFY_FAILURE);
//				payment.setNotifyTime(DateUtils.parseTimestamp(notify_time));
//				payment.setPayState(PayState.PROCESSED_FAILURE);
//				payment.setErrorCode(error_code);
//				AlipayCore.logInfo("支付回调验证状态失败。。。失败信息，error_code=" + error_code);
//				return false;
//			}
//		} else {
//			logger.info("alipay verify fail");
//			AlipayCore.logInfo("支付回调验证失败。。。");
//			return false;
//		}
//	}
//
//	public String refund(String servicePath, String orderCode, ChannelType channelType) throws CommonException {
//		Payment payment = payDao.getPayByOrderCode(orderCode);
//		if (null == payment) {
//			throw new CommonException("该订单无付款记录");
//		}
//		AlipayCore.logInfo("用户提交退款请求 订单号：" + orderCode);
//		String htmlText = null;
//		// 组织支付参数、加密，发送支付请求(根据支付通道选择相应的方法 方法计3个)。支付订单编号用payId
//		if (channelType == ChannelType.ALIPAY) {// 支付宝
//			htmlText = refundAlipay(payment, servicePath);
//		}
//		return htmlText;
//	}
//
//	public String refundAlipay(Payment payment, String servicePath) {
//		// 批次号，必填，格式：当天日期[8位]+序列号[3至24位]，如：201603081000001
//		String batch_no = UtilDate.getOrderNum();
//
//		String orderCode = payment.getOrder().getOrderCode();
//		List<ProductReturn> productReturns = orderService.getProductReturnsByOrderCode(orderCode);
//		StringBuffer sb = new StringBuffer();
//		int i = 0;
//		for (ProductReturn productReturn : productReturns) {
//			if (productReturn.getReturnRefundState() == ReturnRefundState.RETURNED_WAIT_REFUND
//					|| productReturn.getReturnRefundState() == ReturnRefundState.WAIT_REFUND) {
//				sb.append(payment.getPayFlowCode() + "^");
//				sb.append(productReturn.getRefundPrice() + "^");
//				String reason = null;
//				if (productReturn.isRefundOnly()) {
//					reason = productReturn.getRefundReason().toString();
//				} else {
//					reason = productReturn.getReturnReason().toString();
//				}
//				sb.append(reason + "#");
//				i++;
//				Payment payment1 = new Payment();
//				payment1.setChannelType(ChannelType.ALIPAY);
//				payment1.setOrder(payment.getOrder());
//				payment1.setBatchNo(batch_no);
//				payment1.setPayFlowCode(payment.getPayFlowCode());
//				payment1.setReturnCode(productReturn.getReturnCode());
//				payment1.setCustomer(payment.getCustomer());
//				payment1.setCreateTime(new Date());
//				payment1.setPayType(PayType.REIMBURSE);
//				payment1.setPayState(PayState.PROCESSING);
//				payment1.setPayNotifyState(PayNotifyState.NOT_NOTIFY);
//				payment1.setPayAmount(productReturn.getRefundPrice());
//				payment1.setBatchNum(i);
//				payDao.save(payment1);
//			}
//		}
//
//		// 退款笔数，必填，参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
//		String batch_num = String.valueOf(i);
//
//		// 退款详细数据，必填，格式（支付宝交易号^退款金额^备注），多笔请用#隔开
//		String dData = sb.toString();
//		dData = dData.substring(0, dData.lastIndexOf("#"));
//		String detail_data = dData;
//
//		// 服务器异步通知页面路径 //需http://格式的完整路径，不能加?id=123这类自定义参数
//		String notify_url = servicePath + GlobalConfig.getProperty(Config.ALIPAY_REFUND_NOTIFY_URL);
//		System.out.println(notify_url);
//		//////////////////////////////////////////////////////////////////////////////////
//
//		// 把请求参数打包成数组
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", AlipayConfig.service);
//		sParaTemp.put("partner", AlipayConfig.partner);
//		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("notify_url", notify_url);
//		sParaTemp.put("seller_email", AlipayConfig.seller_email);
//		sParaTemp.put("refund_date", AlipayConfig.refund_date);
//		sParaTemp.put("batch_no", batch_no);
//		sParaTemp.put("batch_num", batch_num);
//		sParaTemp.put("detail_data", detail_data);
//
//		// 建立请求
//		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
//		AlipayCore.logInfo("建立退款请求 退款批次号：" + batch_no);
//		return sHtmlText;
//	}
//
//	public boolean refundAlipayCallBack(Map<String, String> params) throws CommonException {
//
//		AlipayCore.logInfo("支付宝退款回调开始。。。");
//		// 计算得出通知验证结果
//		boolean verify_result = AlipayNotify.verify(params);
//		AlipayCore.logInfo("支付宝退款回调验证结果：" + verify_result);
//		// 验证成功
//		if (verify_result) {
//			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//			// 批次号
//			String batch_no = params.get("batch_no");
//
//			// 批量退款数据中转账成功的笔数
//			String success_num = params.get("success_num");
//
//			// 批量退款数据中的详细信息
//			String result_details = params.get("result_details");
//			AlipayCore.logInfo("支付宝退款回调验证成功！退款批次号：" + batch_no + " 退款详情数据：" + result_details);
//			String notify_time = params.get("notify_time");
//			List<Payment> payments = payDao.getPaymentsByBatchNo(PayState.PROCESSING, batch_no);
//			if (!success_num.equalsIgnoreCase("0")) {
//				for (Payment payment : payments) {
//					String[] results = result_details.split("$");
//					if (LogicUtils.isNotNullAndEmpty(results)) {
//						for (String rl : results) {
//							String[] frl = rl.split("^");
//							if (frl[2].equalsIgnoreCase("SUCCESS")) {
//								if (payment.getPayFlowCode().equalsIgnoreCase(frl[0])) {
//									setPaymentSate(payment, notify_time, success_num);
//
//								}
//							} else {
//								throw new CommonException("支付宝退款回调状态错误,交易流水号:" + frl[0] + ",状态码：" + frl[2]);
//							}
//						}
//					} else {
//						String[] frl = result_details.split("^");
//						if (frl[2].equalsIgnoreCase("SUCCESS")) {
//							if (payment.getPayFlowCode().equalsIgnoreCase(frl[0])) {
//								setPaymentSate(payment, notify_time, success_num);
//							} else {
//								throw new CommonException("支付宝退款回调状态错误,交易流水号:" + frl[0] + ",状态码：" + frl[2]);
//							}
//						}
//					}
//				}
//
//			}
//			return true;
//		} else {
//			AlipayCore.logInfo("支付宝退款回调验证失败！");
//			return false;
//		}
//
//	}
//
//	private void setPaymentSate(Payment payment, String notify_time, String success_num) {
//		payment.setPayState(PayState.PROCESSED_SUCCESS);
//		payment.setPayNotifyState(PayNotifyState.NOTIFY_SUCCESS);
//		payment.setNotifyTime(DateUtils.parseDate(notify_time));
//		payment.setBatchNum(Integer.valueOf(success_num));
//		payDao.save(payment);
//
//		ProductReturn productReturn = orderService.getProductReturnByReturnCode(payment.getReturnCode());
//		if (productReturn.isRefundOnly()) {
//			productReturn.setReturnRefundState(ReturnRefundState.REFUNDED);
//		} else {
//			productReturn.setReturnRefundState(ReturnRefundState.RETURNED_REFUND);
//		}
//		AlipayCore.logInfo("支付宝退款成功！更新退款号：" + productReturn.getReturnCode() + " 状态为成功！通知时间：" + notify_time);
//	}
//
//	protected String unionPay(Payment payment, HttpServletRequest request) {
//		if (StringUtils.isEmpty(payment.getUserIPAddress())) {
//			throw new NullArgumentException("UserIPAddress is null");
//		}
//		String reqPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//				+ request.getContextPath(); // URL路径，用于拼接回调函数
//
//		String str = getClass().getResource("/").getFile().toString();
//		int num = str.indexOf("WEB-INF");
//		String privateKeyPath = str.substring(0, num + "WEB-INF".length())
//				+ "/MerPrK_808080201307375_20141118112327.key"; // 私钥
//		String publicKeyPath = str.substring(0, num + "WEB-INF".length()) + "/PgPubk.key"; // 公钥
//
//		File file = new File(privateKeyPath);
//		if (file.exists()) {
//			System.out.print("123141234134123");
//		}
//
//		String MerId = UnionPayConf.PAY_UNIONPAY_MERCODE; // 商户号，长度为15个字节的数字串，由ChinaPay分配
//		String OrdId = payment.getOrder().getOrderCode().substring(1, 17); // 订单号，长度为16个字节的数字串，由商户系统生成，失败的订单号允许重复支付(我们的订单号为17位)
//		String TransAmt = String.format("%012d",
//				new BigDecimal(payment.getPayAmount()).multiply(new BigDecimal(100)).intValue()); // 交易金额,长度为12个字节的数字串，例如：数字串"000000001234"表示12.34元。
//		String CuryId = "156"; // 货币代码, 长度为3个字节的数字串，目前只支持人民币，取值为"156"
//		String TransDate = new SimpleDateFormat("yyyyMMdd").format(new Date()); // 交易日期，长度为8个字节的数字串，表示格式为：YYYYMMDD
//		String TransType = "0001"; // 交易类型，长度为4个字节的数字串，取值范围为："0001"和"0002"， 其中"0001"表示消费交易，"0002"表示退货交易
//		String Version = "20070129"; // 支付接入版本号，必填
//		String BgRetUrl = reqPath + "/serverUnion.htm"; // 后台交易接收URL，长度不要超过80个字节，必填
//		String PageRetUrl = reqPath + "/callbackUnionpay.htm"; // 页面交易接收URL，长度不要超过80个字节，必填
//		String GateId = ""; // 支付网关号，可选
//		String Priv1 = "dongzuo"; // 商户私有域，长度不要超过60个字节
//		String ChkValue; // 256字节长的ASCII码,为此次交易提交关键数据的数字签名，必填
//
//		// 说明：
//		// PageRetUrl为页面接受应答地址，用于引导使用者返回支付后的商户网站页面。
//		// BgRetUrl
//		// 为后台接受应答地址，用于商户记录交易信息和处理，对于使用者是不可见的。另外ChinaPay会根据后台发送的http的返回码来判定是否重新发送后台应答流水，以确保后台应答的接收。
//		// Priv1表示
//		// “商户私有域”，在支付版本20070129中会参与数字签名，商户通过此字段向Chinapay发送的信息，Chinapay依原样填充返回给商户。
//		// GateId为可选项，表示 “支付网关号”，如填写GateId（支付网关号），则消费者将直接进入支付页面，否则进入网关选择页面。
//		// OrdId，表示 “订单号”， 该域段的内容可以完全由用户自己定义。
//
//		String signData = MerId + OrdId + TransAmt + CuryId + TransDate + TransType + Priv1; // 拼接待签名的字符串
//
//		/** 生成数字签名 **/
//		// chinapay.PrivateKey key=new chinapay.PrivateKey();
//		// chinapay.SecureLink t;
//		// boolean flag;
//		// /**
//		// * public boolean buildKey (String MerId, int KeyUsage, String KeyFile)
//		// * 用于创建私/公钥的对象，用于签名或者验证签名，
//		// * @param String MerId 商户号
//		// * @param int KeyUsage 使用私/公钥的方式，固定为 0
//		// * @param String KeyFile 私/公钥的文件路径（包含文件名称）。例如："d:\\MerPrk.key”
//		// * @return
//		// true 表示找到正确的私/公钥文件,并且可以调用签名方法签名或者签名验证方法验证签名， false
//		// 表示创建私/公钥对象失败，不可以使用签名方法和签名验证方法。
//		// */
//		// flag=key.buildKey(MerId,0,privateKeyPath);
//		// if (flag==false)
//		// {
//		// System.out.println("build key error!");
//		// return "";
//		// }
//		// t=new chinapay.SecureLink (key);
//		// ChkValue=t.Sign(signData);
//
//		Map<String, String> map = new HashMap<>();
//		map.put("MerId", MerId);
//		map.put("OrdId", OrdId);
//		map.put("TransAmt", TransAmt);
//		map.put("CuryId", CuryId);
//		map.put("TransDate", TransDate);
//		map.put("TransType", TransType);
//		map.put("Version", Version);
//		map.put("BgRetUrl", BgRetUrl);
//		map.put("PageRetUrl", PageRetUrl);
//		map.put("Priv1", Priv1);
//		// map.put("ChkValue",ChkValue);
//
//		return new UnionPayUtils().generateAutoSubmitForm(UnionPayConf.gateWay, map);// 跳转到银联页面支付
//
//		//
//		//
//		//
//		// // 商户需要组装如下对象的数据
//		// String[] valueVo = new String[] {
//		// UnionPayConf.version,// 协议版本
//		// UnionPayConf.charset,// 字符编码
//		// "01",// 交易类型
//		// payment.getPayFlowCode(),// 原始交易流水号
//		// UnionPayConf.PAY_UNIONPAY_MERCODE,// 商户代码
//		// UnionPayConf.PAY_UNIONPAY_MERNAME,// 商户简称
//		// "",// 收单机构代码（仅收单机构接入需要填写）
//		// "",// 商户类别（收单机构接入需要填写）
//		// payment.getCommodityUrl(),// 商品URL
//		// payment.getOrderForm().getOrderCode(),// 商品名称
//		// String.valueOf(payment.getPayAmount()*100),// 商品单价 单位：分
//		// "1",// 商品数量
//		// "0",// 折扣 单位：分
//		// String.valueOf(payment.getOrderForm().getFreightPrice()*100),// 运费 单位：分
//		// payment.getOrderForm().getOrderCode(),// 订单号（需要商户自己生成）
//		// String.valueOf(new BigDecimal(payment.getPayAmount()).multiply(
//		// new BigDecimal(100)).intValue()),//
//		// "27960",//String.valueOf(payVo.getPayAmount()*100),//"3100",//交易金额
//		// // 单位：分String.valueOf(a*100)
//		// "156",// 交易币种
//		// new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),// 交易时间
//		// payment.getUserIPAddress(),// 用户IP
//		// payment.getCustomer().getRealName(),// 用户真实姓名
//		// "",// 默认支付方式
//		// "",// 默认银行编号
//		// "300000",// 交易超时时间
//		// // UnionPayConf.merFrontEndUrl,// 前台回调商户URL
//		// UnionPayConf.PAY_UNIONPAY_MERFRONTENDURL,
//		// // UnionPayConf.merBackEndUrl,// 后台回调商户URL
//		// UnionPayConf.PAY_UNIONPAY_MERBACKENDURL, ""// 商户保留域
//		// };
//		// String signType = UnionPayConf.signType;
//		// return new UnionPayUtils().createPayHtml(valueVo, signType);// 跳转到银联页面支付
//	}
//
//	@Override
//	public Payment addPay(Payment payment) {
//		payment.setCreateTime(new Date());
//		return payDao.save(payment);
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public Page<Payment> pagePayment(final PayQuery payQuery) {
//		createPayment(payQuery);
//		Page<Payment> pages = payDao.findAll(payQuery, payQuery.getPageRequest());
//		return pages;
//	}
//
//	@Override
//	public long countPayment(PayQuery query) {
//		createPayment(query);
//		return payDao.count(query);
//	}
//
//	private void createPayment(final PayQuery payQuery) {
//		payQuery.setCustomSpecification(new CustomSpecification<Payment>() {
//
//			// public void buildFetch(Root<Payment> root) {
//			// root.fetch(Pay_.orderForm, JoinType.LEFT);
//			// root.fetch(Pay_.member, JoinType.LEFT);
//			// }
//			//
//			// @Override
//			// public void addConditions(Root<Payment> root, CriteriaQuery<?> query,
//			// CriteriaBuilder builder, List<Predicate> predicates) {
//			//
//			// //如果当前登录用户 展馆ids不为空，则筛选出此展馆下的订单
//			// Path<Integer> exhibitionHallIds =
//			// root.get(Pay_.orderForm).get(OrderForm_.hall).get(ExhibitionHall_.hallId);
//			// CriteriaBuilder.In<Integer> in = builder.in(exhibitionHallIds);
//			// if (LogicUtils.isNotNullAndEmpty(payQuery.getHallIds())) {
//			// for (Integer i : payQuery.getHallIds()) {
//			// in.value(i);
//			// }
//			// predicates.add(in);
//			// }
//			// if (LogicUtils.isNotNullAndEmpty(payQuery.getHallIds())) {
//			// predicates.add(builder.in(exhibitionHallIds).value(root.get(Pay_.orderForm).get(OrderForm_.hall).get(ExhibitionHall_.hallId)));
//			// }
//			//
//			// if (LogicUtils.isNotNullAndEmpty(payQuery.getSearch1())) {
//			// String str = Verification.removalSpace(payQuery.getSearch1());
//			// String str2 = Verification.caseConversion(payQuery.getSearch1());
//			// predicates.add(builder.like(root.get(Pay_.payFlowCode), "%" + str + "%"));
//			// predicates.add(builder.like(root.get(Pay_.payFlowCode), "%" + str2 + "%"));
//			// }
//			// if (LogicUtils.isNotNullAndEmpty(payQuery.getSearch2())) {
//			// String str = Verification.removalSpace(payQuery.getSearch2());
//			// String str2 = Verification.caseConversion(payQuery.getSearch2());
//			// predicates.add(builder.like(root.get(Pay_.member).get(Customer_.accountName),
//			// "%" + str + "%"));
//			// predicates.add(builder.like(root.get(Pay_.member).get(Customer_.accountName),
//			// "%" + str2 + "%"));
//			// }
//			//
//			// if (LogicUtils.isNotNull(payQuery.getPayStyle())) {
//			// predicates.add((builder.equal(root.get(Pay_.payStyle),
//			// payQuery.getPayStyle())));
//			// }
//			// query.orderBy(builder.desc(root.get(Pay_.createTime)));
//			// }
//
//			@Override
//			public void addConditions(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder builder,
//					List<Predicate> predicates) {
//
//				// 如果当前登录用户 展馆ids不为空，则筛选出此展馆下的订单
//				// Path<Integer> exhibitionHallIds =
//				// root.get(Pay_.orderForm).get(OrderForm_.hall).get(ExhibitionHall_.hallId);
//				// CriteriaBuilder.In<Integer> in = builder.in(exhibitionHallIds);
//				// if (LogicUtils.isNotNullAndEmpty(payQuery.getHallIds())) {
//				// for (Integer i : payQuery.getHallIds()) {
//				// in.value(i);
//				// }
//				// predicates.add(in);
//				// }
//				// if (LogicUtils.isNotNullAndEmpty(payQuery.getHallIds())) {
//				// predicates.add(builder.in(exhibitionHallIds).value(root.get(Pay_.orderForm).get(OrderForm_.hall).get(ExhibitionHall_.hallId)));
//				// }
//
//				// if (LogicUtils.isNotNullAndEmpty(payQuery.getSearch1())) {
//				// String str = Verification.removalSpace(payQuery.getSearch1());
//				// String str2 = Verification.caseConversion(payQuery.getSearch1());
//				// predicates.add(builder.like(root.get(Pay_.payFlowCode), "%" + str + "%"));
//				// predicates.add(builder.like(root.get(Pay_.payFlowCode), "%" + str2 + "%"));
//				// }
//				// if (LogicUtils.isNotNullAndEmpty(payQuery.getSearch2())) {
//				// String str = Verification.removalSpace(payQuery.getSearch2());
//				// String str2 = Verification.caseConversion(payQuery.getSearch2());
//				// predicates.add(builder.like(root.get(Pay_.member).get(Customer_.accountName),
//				// "%" + str + "%"));
//				// predicates.add(builder.like(root.get(Pay_.member).get(Customer_.accountName),
//				// "%" + str2 + "%"));
//				// }
//
//				// if (LogicUtils.isNotNull(payQuery.getPayStyle())) {
//				// predicates.add((builder.equal(root.get(Pay_.payStyle),
//				// payQuery.getPayStyle())));
//				// }
//				query.orderBy(builder.desc(root.get(Payment_.createTime)));
//			}
//
//		});
//	}
//
//	@Override
//	public boolean checkPayFlowCode(String payFlowCode) {
//		Payment payment = payDao.findByCode(payFlowCode);
//		if (payment != null) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * 退款历史记录
//	 * 
//	 * @param payQuery
//	 * @return
//	 */
//	@Override
//	public Page<Payment> pagePaymentforGoodsReutrn(PayQuery payQuery) {
//		createPaymentForGoodsReutrn(payQuery);
//		Page<Payment> pages = payDao.findAll(payQuery, payQuery.getPageRequest());
//		// for(Payment payment: pages.getContent()) {
//		// Goods goods = goodsDao.findOne(payment.getOperatorId());
//		// payment.setRfid(goods.getRfid());
//		// }
//		return pages;
//	}
//
//	/**
//	 * 统计退款历史记录
//	 * 
//	 * @param query
//	 * @return
//	 */
//	@Override
//	public long pagePaymentforGoodsReutrnCount(PayQuery query) {
//		createPaymentForGoodsReutrn(query);
//		return payDao.count(query);
//	}
//
//	@Override
//	public Payment fullPaid(int orderId) {
//		Order order = orderService.getOrderById(orderId);
//		Payment payment = payDao.getPayByOrderId(order.getOrderId());
//		// double balance= order.getTotalPrice()-payment.getPayAmount();
//		// order.setOrderState(OrderState.PAID);
//
//		Payment payment1 = new Payment();
//
//		/** 新建一条付款记录 **/
//		payment1.setPayFlowCode("P" + UUIDGeneratorUtil.getUUCode());
//		// payment1.setPayStyle(payment.getPayStyle());
//		payment1.setUserIPAddress(payment.getUserIPAddress());
//		payment1.setCommodityUrl(payment.getCommodityUrl());
//		// payment1.setRemark("补交差额："+balance+"元");
//		payment1.setBuyer_account(payment.getBuyer_account());
//		payment1.setChannelType(payment.getChannelType());
//		payment1.setCompleteTime(new Date());
//		payment1.setCreateTime(new Date());
//		payment1.setCustomer(payment.getCustomer());
//		payment1.setErrorCode(payment.getErrorCode());
//		payment1.setErrorMsg(payment.getErrorMsg());
//		payment1.setNotifyTime(payment.getNotifyTime());
//		payment1.setOperatorId(payment.getOperatorId());
//		// payment1.setPayMethod(payment.getPayMethod());
//		payment1.setPayState(PayState.PROCESSED_SUCCESS);
//		payment1.setPayType(payment.getPayType());
//		payment1.setPayNotifyState(payment.getPayNotifyState());
//		payment1.setOrder(payment.getOrder());
//		// payment1.setPayAmount(balance);
//
//		payDao.save(payment1);
//		return payment1;
//	}
//
//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public Double getPayByOrderId(int orderId) {
//		// List<Payment> pays = payDao.findByOrderId(orderId);
//		Double payMoney = 0.0;
//		// if(LogicUtils.isNotNullAndEmpty(pays)){
//		// for(Payment payment : pays){
//		// payMoney+=payment.getPayAmount();
//		// }
//		// }
//		return payMoney;
//
//	}
//
//	/**
//	 * 退款历史记录
//	 * 
//	 * @param payQuery
//	 */
//	private void createPaymentForGoodsReutrn(final PayQuery payQuery) {
//		payQuery.setCustomSpecification(new CustomSpecification<Payment>() {
//
//			public void buildFetch(Root<Payment> root) {
//				root.fetch(Payment_.order, JoinType.LEFT);
//				root.fetch(Payment_.customer, JoinType.LEFT);
//			}
//
//			@Override
//			public void addConditions(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder builder,
//					List<Predicate> predicates) {
//
//				// 如果当前登录用户 展馆ids不为空，则筛选出此展馆下的订单
//				// if(LogicUtils.isNotNullAndEmpty(payQuery.getSearch2())){
//				// String str = Verification.removalSpace(payQuery.getSearch2());
//				// String str2 = Verification.caseConversion(payQuery.getSearch2());
//				// predicates.add(builder.like(root.get(Pay_.payFlowCode),"%"+str+"%"));
//				// predicates.add(builder.like(root.get(Pay_.payFlowCode),"%"+str2+"%"));
//				// }
//				// if(LogicUtils.isNotNullAndEmpty(payQuery.getSearch1())){
//				// String str = Verification.removalSpace(payQuery.getSearch1());
//				// String str2 = Verification.caseConversion(payQuery.getSearch1());
//				// predicates.add(builder.like(root.get(Pay_.orderForm).get(OrderForm_.orderCode),"%"+str+"%"));
//				// predicates.add(builder.like(root.get(Pay_.orderForm).get(OrderForm_.orderCode),"%"+str2+"%"));
//				// }
//				// if(LogicUtils.isNotNull(payQuery.getPayType())){
//				// predicates.add((builder.equal(root.get(Pay_.payType),payQuery.getPayType())));
//				// }
//				query.orderBy(builder.desc(root.get(Payment_.createTime)));
//			}
//		});
//	}
//
//}
