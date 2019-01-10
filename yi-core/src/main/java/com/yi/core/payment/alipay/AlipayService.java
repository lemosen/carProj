package com.yi.core.payment.alipay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.yi.core.commodity.service.IStockRecordService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.MemberEnum;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IAccountRecordService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IPayRecordService;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.utils.date.DateUtils;

/**
 * 支付宝 支付服务
 * 
 * @author xuyh
 *
 */
@Component
public class AlipayService {

	private static final Logger LOG = LoggerFactory.getLogger(AlipayService.class);

	@Resource
	private IMemberService memberService;

	@Resource
	private ISaleOrderService saleOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private IPayRecordService payRecordService;

	@Resource
	private IStockService stockService;

	@Resource
	private IStockRecordService stockRecordService;

	@Resource
	private IAccountRecordService accountRecordService;

	/** 初始化 支付中心 */
	private static AlipayClient ALIPAY_CLIENT;

	static {
		// 实例化客户端
		ALIPAY_CLIENT = new DefaultAlipayClient(AlipayConfig.SERVER_URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET_UTF8,
				AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
	}

	/**
	 * 创建支付宝支付订单
	 * 
	 * @param alipayVo
	 * @return
	 */
	public String createPayOrder(AlipayVo alipayVo) throws Exception {
		// 1-校验基本参数
		if (alipayVo == null || StringUtils.isAnyBlank(alipayVo.getMemberId(), alipayVo.getOrderIds())) {
			LOG.error("创建支付宝支付订单失败，请求参数为空。memberId={}，orderIds={}", alipayVo.getMemberId(), alipayVo.getOrderIds());
			throw new Exception("请求参数不能为空");
		}
		// 2-检查会员
		Member dbMember = memberService.getMemberById(Integer.valueOf(alipayVo.getMemberId()));
		if (dbMember == null) {
			LOG.error("会员{}不存在", alipayVo.getMemberId());
			throw new Exception("支付失败，请稍后重试");
		}
		// 3-获取需要支付的订单
		List<SaleOrder> dbSaleOrders = saleOrderService.getPayOrderByIds(alipayVo.getOrderIds());
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.error("订单{}不存在", alipayVo.getOrderIds());
			throw new Exception("支付失败，请稍后重试");
		}
		// 4-计算支付金额
		BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
		if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(alipayVo.getPayAmount()).orElse("0"))) != 0) {
			LOG.error("前台传入支付金额{}和后台计算支付金额{}不匹配，请核实", alipayVo.getPayAmount(), payAmount);
		}
		// 5-进一步封装参数
		alipayVo.setOutTradeNo(dbSaleOrders.get(0).getPayOrderNo());
		if (AlipayConfig.PROD_ENV) {
			alipayVo.setPayAmount(payAmount.toString());
		} else {
			alipayVo.setPayAmount("0.01");
		}
		// 6-实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(AlipayConfig.BODY);// 支付信息
		model.setSubject(AlipayConfig.SUBJECT);// 商品名称
		model.setOutTradeNo(alipayVo.getOutTradeNo());// 商户订单号
		// model.setTimeoutExpress("30m");
		if (AlipayConfig.PROD_ENV) {
			model.setTotalAmount(alipayVo.getPayAmount());
		} else {
			model.setTotalAmount("0.01");
		}
		model.setProductCode(AlipayConfig.PRODUCT_CODE);// 销售产品码
		// 参数
		request.setBizModel(model);
		if (AlipayConfig.PROD_ENV) {
			request.setNotifyUrl(AlipayConfig.APP_PROD_NOTIFY_URL);
		} else {
			request.setNotifyUrl(AlipayConfig.APP_TEST_NOTIFY_URL);
		}
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = ALIPAY_CLIENT.sdkExecute(request);
			if (response.isSuccess()) {
				// orderString 可以直接给客户端请求，无需再做处理。
				return response.getBody();
			} else {
				LOG.error("创建支付订单失败，返回数据为{}", response.getBody());
				return null;
			}
		} catch (AlipayApiException e) {
			LOG.error("创建支付订单失败", e);
			return null;
		}
	}

	/**
	 * 支付宝回调接口 </br>
	 * 通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h
	 * 
	 * @param params
	 */
	public String alipayNotify(Map<String, String> resultMap) throws Exception {
		LOG.info("支付宝回调开始，返回参数为：{}", resultMap);
		// 1-校验基本参数
		if (MapUtils.isEmpty(resultMap)) {
			LOG.error("支付宝回调，参数为空");
			return AlipayConfig.NOTIFY_FAIL;
		}
		// 2-校验签名
		boolean signVerfied = AlipaySignature.rsaCheckV1(resultMap, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET_UTF8, AlipayConfig.SIGN_TYPE);
		// 3-验签成功
		if (signVerfied) {
			// 3-1交易成功
			if (AlipayConfig.TRADE_STATUS_SUCCESS.equals(resultMap.get("trade_status"))) {
				/**
				 * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号</br>
				 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）</br>
				 * 3、校验通知中的seller_id（或者seller_email)，是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），</br>
				 * 4、验证app_id是否为该商户本身。
				 */
				String out_trade_no = resultMap.get("out_trade_no");
				String total_amount = resultMap.get("total_amount");
				String seller_email = resultMap.get("seller_email");
				String app_id = resultMap.get("app_id");
				// 3-1-1校验 out_trade_no
				List<SaleOrder> dbSaleOrders = saleOrderService.getWaitPayOrdersForAlipay(out_trade_no);
				if (CollectionUtils.isEmpty(dbSaleOrders)) {
					LOG.error("支付宝回调失败，根据out_trade_no查询数据为空");
					return AlipayConfig.NOTIFY_FAIL;
				}
				// 3-1-2校验 total_amount
				BigDecimal payAmount = this.calculatePayAmount(dbSaleOrders);
				if (payAmount.compareTo(new BigDecimal(Optional.ofNullable(total_amount).orElse("0"))) != 0) {
					LOG.error("支付宝回调失败，支付金额{}和后台订单金额不一致", total_amount, payAmount);
					// return AlipayConfig.NOTIFY_SUCCESS;
				}
				// 3-1-3验证 seller_email
				if (!AlipayConfig.SELLER_EMAIL.equals(seller_email)) {
					LOG.error("支付宝回调失败，卖家支付宝账号不一致，原{}，现{}", AlipayConfig.SELLER_EMAIL, seller_email);
					// return AlipayConfig.NOTIFY_SUCCESS;
				}
				// 3-1-4 验证 app_id
				if (!AlipayConfig.APP_ID.equals(app_id)) {
					LOG.error("支付宝回调失败，商户app_id不一致，原{}，现{}", AlipayConfig.APP_ID, app_id);
					return AlipayConfig.NOTIFY_FAIL;
				}
				// 3-1-5修改订单数据并记录
				for (SaleOrder tmpOrder : dbSaleOrders) {
					if (tmpOrder != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
						// 更新订单状态
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());// 订单状态 待发货
						tmpOrder.setPaymentTime(DateUtils.parseDate(resultMap.get("gmt_payment"), "yyyy-MM-dd HH:mm:ss"));
						tmpOrder.setPayTradeNo(resultMap.get("trade_no"));// 交易号
						tmpOrder.setPayMode(OrderEnum.PAY_MODE_ALIPAY.getCode());
						tmpOrder.setRemark("支付完成");
						// 库存修正+库存记录
						stockService.useStockByPayOrder(tmpOrder);
						// 支付记录
						payRecordService.addPayRecordByOrderForAlipay(tmpOrder, resultMap);
						// 订单记录
						orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_PAY_SUCCESS, "支付成功");
						// 会员资金账户记录
						if (OrderEnum.ORDER_TYPE_GIFT.getCode().equals(tmpOrder.getOrderType())) {
							accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_GIFT_PAYMENT,
									tmpOrder.getMember());
						} else {
							accountRecordService.addAccountRecordByTradeType(tmpOrder, tmpOrder.getMember(), tmpOrder.getPayAmount(), MemberEnum.TRADE_TYPE_ONLINE_PAYMENT,
									tmpOrder.getMember());
						}
					}
				}
				return AlipayConfig.NOTIFY_SUCCESS;
			} else {
				LOG.error("支付宝回调失败，返回数据为", resultMap);
				return AlipayConfig.NOTIFY_SUCCESS;
			}
		} else {
			// TODO 验签失败则记录异常日志，并在response中返回failure.
			LOG.error("支付宝回调，签名校验失败");
			return AlipayConfig.NOTIFY_FAIL;
		}
	}

	/**
	 * 交易查询接口alipay.trade.query
	 * 
	 * @param out_trade_no
	 *            支付时传入的商户订单号，与trade_no必填一个
	 * @param trade_no
	 *            支付时返回的支付宝交易号，与out_trade_no必填一个
	 */
	public void tradeQuery(String out_trade_no, String trade_no) {
		// 创建API对应的request类
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		// 设置业务参数
		Map<String, Object> params = new HashMap<>();
		params.put("out_trade_no", out_trade_no);
		params.put("trade_no", trade_no);
		request.setBizContent(JSONObject.toJSONString(params));
		try {
			// 通过alipayClient调用API，获得对应的response类
			AlipayTradeQueryResponse response = ALIPAY_CLIENT.execute(request);
			// 根据response中的结果继续业务逻辑处理
			JSONObject jsonObject = JSONObject.parseObject(response.getBody());
			// 支付宝28位交易号
			String r_trade_no = jsonObject.getString("trade_no");
			// 支付时传入的商户订单号
			String r_out_trade_no = jsonObject.getString("out_trade_no");
			// 交易当前状态
			String trade_status = jsonObject.getString("trade_status");
			System.out.println(trade_status);
		} catch (AlipayApiException e) {
			LOG.error("交易查询失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 交易退款接口alipay.trade.refund
	 * 
	 * @param out_trade_no
	 *            支付时传入的商户订单号，与trade_no必填一个
	 * @param trade_no
	 *            支付时返回的支付宝交易号，与out_trade_no必填一个
	 * @param out_request_no
	 *            本次退款请求流水号，部分退款时必传
	 * @param refund_amount
	 *            本次退款金额
	 */
	public void tradeRefund(String out_trade_no, String trade_no, String out_request_no, String refund_amount) {
		// 创建API对应的request类
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		// 设置业务参数
		Map<String, Object> params = new HashMap<>();
		params.put("out_trade_no", out_trade_no);
		params.put("trade_no", trade_no);
		params.put("out_request_no", out_request_no);
		params.put("refund_amount", refund_amount);
		request.setBizContent(JSONObject.toJSONString(params));
		try {
			// 通过alipayClient调用API，获得对应的response类
			AlipayTradeRefundResponse response = ALIPAY_CLIENT.execute(request);
			// 根据response中的结果继续业务逻辑处理
			JSONObject jsonObject = JSONObject.parseObject(response.getBody());
			// 该笔交易已退款的总金额
			String refund_fee = jsonObject.getString("refund_fee");
		} catch (AlipayApiException e) {
			LOG.error("交易退款失败", e);
			e.printStackTrace();
		}
	}

	/**
	 * 查询对账单下载地址接口alipay.data.dataservice.bill.downloadurl.query
	 * 
	 * @param bill_type
	 *            固定传入trade
	 * @param bill_date
	 *            需要下载的账单日期，最晚是当期日期的前一天
	 */
	public String billDownloadUrlQuery(String bill_type, String bill_date) {
		// 创建API对应的request类
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		// 设置业务参数
		Map<String, Object> params = new HashMap<>();
		params.put("bill_type", bill_type);
		params.put("bill_date", bill_date);
		request.setBizContent(JSONObject.toJSONString(params));
		// 通过alipayClient调用API，获得对应的response类
		try {
			AlipayDataDataserviceBillDownloadurlQueryResponse response = ALIPAY_CLIENT.execute(request);
			JSONObject jsonObject = JSONObject.parseObject(response.getBody());
			// 账单文件下载地址，30秒有效
			String bill_download_url = jsonObject.getString("bill_download_url");
			return bill_download_url;
		} catch (AlipayApiException e) {
			LOG.error("查询对账单下载地址失败", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 下载账单文件
	 */
	public void downloadBillFile() {
		// 将接口返回的对账单下载地址传入urlStr
		String urlStr = "http://dwbillcenter.alipay.com/downloadBillFile.resource?bizType=X&userId=X&fileType=X&bizDates=X&downloadFileName=X&fileId=X";
		// 指定希望保存的文件路径
		String filePath = "/Users/fund_bill_20160405.csv";
		URL url = null;
		HttpURLConnection httpUrlConnection = null;
		InputStream fis = null;
		FileOutputStream fos = null;
		try {
			url = new URL(urlStr);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5 * 1000);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
			httpUrlConnection.connect();
			fis = httpUrlConnection.getInputStream();
			byte[] temp = new byte[1024];
			int b;
			fos = new FileOutputStream(new File(filePath));
			while ((b = fis.read(temp)) != -1) {
				fos.write(temp, 0, b);
				fos.flush();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
				httpUrlConnection.disconnect();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据订单 计算支付金额
	 * 
	 * @param saleOrders
	 * @return
	 */
	private BigDecimal calculatePayAmount(List<SaleOrder> saleOrders) {
		if (CollectionUtils.isEmpty(saleOrders)) {
			return BigDecimal.ZERO;
		}
		try {
			return saleOrders.stream().map(SaleOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
		} catch (Exception e) {
			LOG.error("计算总单金额异常" + e.getMessage());
			return BigDecimal.ZERO;
		}
	}

}
