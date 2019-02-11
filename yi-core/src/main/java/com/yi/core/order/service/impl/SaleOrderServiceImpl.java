/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponDao;
import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.dao.NationalGroupDao;
import com.yi.core.activity.dao.NationalGroupMemberDao;
import com.yi.core.activity.dao.NationalGroupRecordDao;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICommunityGroupService;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.activity.service.INationalGroupRecordService;
import com.yi.core.activity.service.INationalGroupService;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.cart.service.ICartService;
import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.gift.domain.entity.Gift;
import com.yi.core.gift.domain.entity.GiftBag;
import com.yi.core.member.dao.ConsumeRecordDao;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.Member_;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.vo.ShippingAddressVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.member.service.IMemberService;
import com.yi.core.member.service.IShippingAddressService;
import com.yi.core.order.OrderEnum;
import com.yi.core.order.dao.SaleOrderDao;
import com.yi.core.order.domain.bo.SaleOrderBo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.domain.entity.SaleOrderItem_;
import com.yi.core.order.domain.entity.SaleOrder_;
import com.yi.core.order.domain.simple.SaleOrderSimple;
import com.yi.core.order.domain.vo.SaleOrderListVo;
import com.yi.core.order.domain.vo.SaleOrderVo;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yi.core.order.service.IOrderLogService;
import com.yi.core.order.service.IOrderSettingService;
import com.yi.core.order.service.ISaleOrderItemService;
import com.yi.core.order.service.ISaleOrderService;
import com.yi.core.payment.weChat.WeChatNotifyService;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yi.core.supplier.service.ISupplierAccountService;
import com.yi.core.supplier.service.ISupplierService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.ValueUtils;
import com.yihz.common.utils.date.DateUtils;

/**
 * 销售订单
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SaleOrderServiceImpl implements ISaleOrderService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private SaleOrderDao orderDao;

	@Resource
	private CouponDao couponDao;

	@Resource
	private CommodityDao commodityDao;

	@Resource
	private ConsumeRecordDao consumeRecordDao;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private NationalGroupDao nationalGroupDao;

	@Resource
	private ICommunityGroupService communityGroupService;

	@Resource
	private NationalGroupRecordDao nationalGroupRecordDao;

	@Resource
	private INationalGroupRecordService nationalGroupRecordService;

	@Resource
	private ICartService cartService;

	@Resource
	private IStockService stockService;

	@Resource
	private IProductService productService;

	@Resource
	private IFreightTemplateConfigService freightTemplateService;

	@Resource
	private NationalGroupMemberDao nationalGroupMemberDao;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IShippingAddressService shippingAddressService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private ISupplierService supplierService;

	@Resource
	private BaseOrderService baseOrderService;

	@Resource
	private IOrderLogService orderLogService;

	@Resource
	private ISupplierAccountService supplierAccountService;

	@Resource
	private IOrderSettingService orderSettingService;

	@Resource
	private ICommentService commentService;

	@Resource
	private INationalGroupService nationalGroupService;

	@Resource
	private ISaleOrderItemService saleOrderItemService;

	@Resource
	private WeChatNotifyService weChatNotifyService;

	private EntityListVoBoSimpleConvert<SaleOrder, SaleOrderBo, SaleOrderVo, SaleOrderSimple, SaleOrderListVo> orderConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrder> query(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			}
		}));
		Page<SaleOrder> page = orderDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVo(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			}
		}));
		Page<SaleOrder> pages = orderDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCoupons(null);
			tmp.setVouchers(null);
			tmp.getSaleOrderItems().forEach(tmp2 -> {
				tmp2.setSaleOrder(null);
				tmp2.setMember(null);
				tmp2.setSupplier(null);
				tmp2.getCommodity().setCommodityLevelDiscounts(null);
				tmp2.getCommodity().setCategory(null);
				tmp2.getCommodity().setFreightTemplate(null);
				tmp2.getCommodity().setSupplier(null);
				tmp2.getCommodity().setProducts(null);
				tmp2.getCommodity().setComments(null);
				tmp2.getCommodity().setCouponGrantConfig(null);
				tmp2.getProduct().setAttributes(null);
				tmp2.getProduct().setSupplier(null);
			});
		});
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SaleOrderListVo> queryListVoForApp(Pagination<SaleOrder> query) {
		query.setEntityClazz(SaleOrder.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(SaleOrder_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(SaleOrder_.orderTime)));
			Object memberId = query.getParams().get("member.id");
			if (memberId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.member).get(Member_.id), memberId));
			}
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.equal(root.get(SaleOrder_.supplier).get(Supplier_.id), supplierId));
			}
		}));
		Page<SaleOrder> pages = orderDao.findAll(query, query.getPageRequest());
		List<SaleOrderListVo> vos = orderConvert.toListVos(pages.getContent());
		return new PageImpl<SaleOrderListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public SaleOrder addOrder(SaleOrder order) {
		if (order == null || order.getMember() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return orderDao.save(order);
	}

	@Override
	public SaleOrderVo addOrder(SaleOrderBo order) {
		return orderConvert.toVo(this.addOrder(orderConvert.toEntity(order)));
	}

	@Override
	public SaleOrder updateOrder(SaleOrder order) {
		SaleOrder dbOrder = orderDao.getOne(order.getId());
		AttributeReplication.copying(order, dbOrder, SaleOrder_.payTradeNo, SaleOrder_.tradeType, SaleOrder_.payMode, SaleOrder_.orderType, SaleOrder_.orderState,
				SaleOrder_.commentState, SaleOrder_.afterSaleState, SaleOrder_.refundAmount, SaleOrder_.buyerMessage, SaleOrder_.consignee, SaleOrder_.consigneePhone,
				SaleOrder_.consigneeAddr, SaleOrder_.deliveryMode, SaleOrder_.expressCompany, SaleOrder_.expressNo, SaleOrder_.payInvalidTime, SaleOrder_.returnInvalidTime,
				SaleOrder_.exchangeInvalidTime, SaleOrder_.paymentTime, SaleOrder_.deliveryTime, SaleOrder_.receiptTime, SaleOrder_.finishTime, SaleOrder_.closeTime,
				SaleOrder_.readType, SaleOrder_.orderSource, SaleOrder_.paymentChannel, SaleOrder_.remark);
		return dbOrder;
	}

	@Override
	public SaleOrderVo updateOrder(SaleOrderBo orderBo) {
		return orderConvert.toVo(this.updateOrder(orderConvert.toEntity(orderBo)));
	}

	@Override
	public void removeOrderById(Integer orderId) {
		SaleOrder dbSaleOrder = orderDao.getOne(orderId);
		if (dbSaleOrder != null) {
			dbSaleOrder.setDeleted(Deleted.DEL_TRUE);
			dbSaleOrder.setDelTime(new Date());
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrder getOrderById(Integer orderId) {
		if (orderDao.existsById(orderId)) {
			return orderDao.getOne(orderId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrderVo getOrderVoById(Integer orderId) {
		SaleOrderVo saleOrderVo = orderConvert.toVo(orderDao.getOne(orderId));
		// if
		// (!OrderEnum.ORDER_TYPE_ORDINARY.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// if
		// (OrderEnum.ORDER_TYPE_GROUP_BUY.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// saleOrderVo.setNationalGroupRecord(nationalGroupRecordService.getNationalGroupRecordVoById(saleOrderVo.getNationalGroupRecordId()));
		// } else if
		// (OrderEnum.ORDER_TYPE_GROUP_GINSENG.getCode().equals(saleOrderVo.getOrderType()))
		// {
		// NationalGroupMember nationalGroupMember =
		// nationalGroupMemberDao.getOne(saleOrderVo.getNationalGroupRecordId());
		// nationalGroupMember.getNationalGroupRecord().getNationalGroupMembers().stream().filter(e
		// -> OrderEnum.ALREADY_PAID.getCode().equals(e.getPay()));
		// saleOrderVo.setNationalGroupRecord(nationalGroupRecordService.entityTurnVo(nationalGroupMember.getNationalGroupRecord()));
		// }
		// }
		// saleOrderVo.setCloseTime(orderSettingService.getCloseTimeByOrder(saleOrderVo.getOrderTime(),
		// saleOrderVo.getOrderType()));
		return saleOrderVo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrderVo getOrderVoByGiftId(Integer giftId) {
		SaleOrderVo saleOrderVo = orderConvert.toVo(orderDao.findByGift_id(giftId));
		saleOrderVo.setCloseTime(orderSettingService.getInvalidTimeBySetType(saleOrderVo.getOrderTime(), OrderEnum.ORDER_SET_TYPE_NORMAL));
		return saleOrderVo;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrderListVo getOrderListVoById(Integer orderId) {
		return orderConvert.toListVo(this.orderDao.getOne(orderId));
	}

	protected void initConvert() {
		this.orderConvert = new EntityListVoBoSimpleConvert<SaleOrder, SaleOrderBo, SaleOrderVo, SaleOrderSimple, SaleOrderListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<SaleOrder, SaleOrderVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderVo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderVo OrderVo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderListVo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderListVo OrderListVo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderBo>(beanConvertManager) {
					@Override
					protected void postConvert(SaleOrderBo OrderBo, SaleOrder Order) {

					}
				};
			}

			@Override
			protected BeanConvert<SaleOrderBo, SaleOrder> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrderBo, SaleOrder>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SaleOrder, SaleOrderSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrder, SaleOrderSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<SaleOrderSimple, SaleOrder> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<SaleOrderSimple, SaleOrder>(beanConvertManager) {
					@Override
					public SaleOrder convert(SaleOrderSimple OrderSimple) throws BeansException {
						return orderDao.getOne(OrderSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 关闭、恢复订单等操作
	 */
	public void updateOrderState(Integer orderId, Integer orderState) {
		SaleOrder dbSaleOrder = this.getOrderById(orderId);
		if (dbSaleOrder != null && orderState != null) {
			// 关闭的订单恢复
			if (OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode().equals(dbSaleOrder.getOrderState()) && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(orderState)) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
				dbSaleOrder.setCloseTime(null);
			}
			// 待付款的订单关闭
			if (OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(dbSaleOrder.getOrderState()) && OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode().equals(orderState)) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode());
				dbSaleOrder.setCloseTime(new Date());
			}
		}
	}

	/**
	 * 取消订单
	 */
	@Override
	public void cancelOrder(Integer orderId) {
		SaleOrder dbSaleOrder = orderDao.getOne(orderId);
		if (dbSaleOrder != null) {
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode());
			dbSaleOrder.setCloseTime(new Date());
		}
	}

	/**
	 * 修改订单地址
	 */
	@Override
	public SaleOrderVo updateProvince(SaleOrderBo Order) {
		SaleOrder saleOrder = orderDao.getOne(Order.getId());
		saleOrder.setConsignee(Order.getConsignee());
		saleOrder.setConsigneeAddr(Order.getConsigneeAddr());
		saleOrder.setConsigneePhone(Order.getConsigneePhone());
		return orderConvert.toVo(saleOrder);
	}

	/**
	 * 修改订单金额
	 */
	@Override
	public SaleOrderVo updatePrice(SaleOrderBo saleOrderBo) {
		SaleOrder saleOrder = orderDao.getOne(saleOrderBo.getId());
		if (CollectionUtils.isNotEmpty(saleOrder.getSaleOrderItems())) {
			Set<SaleOrderItem> items = new HashSet<>();
			for (SaleOrderItem tmp : orderConvert.toEntity(saleOrderBo).getSaleOrderItems()) {
				if (tmp != null) {
					SaleOrderItem dbSaleOrderItem = saleOrderItemService.getSaleOrderItemById(tmp.getId());
					AttributeReplication.copying(tmp, dbSaleOrderItem, SaleOrderItem_.price);
				}
			}
		}
		saleOrder.setFreight(saleOrderBo.getFreight());
		return orderConvert.toVo(saleOrder);
	}

	/**
	 * 发货
	 */
	@Override
	public SaleOrderVo delivery(Integer id, String trackingNo, String logisticsCompany) {
		SaleOrder dbSaleOrder = orderDao.getOne(id);
		// 已付款待发货的状态 改为已发货待收货
		if (dbSaleOrder != null && OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode().equals(dbSaleOrder.getOrderState())) {
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode());
			dbSaleOrder.setExpressCompany(logisticsCompany);
			dbSaleOrder.setExpressNo(trackingNo);
			dbSaleOrder.setDeliveryTime(new Date());
			// 发货
			orderLogService.addLogByOrder(dbSaleOrder, OrderEnum.ORDER_LOG_STATE_DISTRIBUTION, "开始配送");
			return orderConvert.toVo(dbSaleOrder);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrderNumByDate(Date date) {
		if (date == null) {
			return 0;
		}
		return orderDao.countByDeletedAndCreateTime(Deleted.DEL_FALSE, date);
	}

	/**
	 * 根据时间 统计供应商订单数
	 *
	 * @param date
	 * @return
	 */

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrderNumByDateAndSupplier(Date date, Integer supplierId) {
		if (date == null) {
			return 0;
		}
		return orderDao.countByDeletedAndCreateTimeAndSupplier(Deleted.DEL_FALSE, date, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWaitDeliveryNum() {
		return orderDao.countByDeletedAndOrderState(Deleted.DEL_FALSE, OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
	}

	/**
	 * 获取供应商待发货订单数
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWaitDeliveryNumSupplier(Integer supplierId) {
		return orderDao.countByDeletedAndOrderStateAndSupplier_id(Deleted.DEL_FALSE, OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getTradeAmountByDate(Date date, Integer[] state) {
		if (date == null) {
			return BigDecimal.ZERO;
		}
		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		return orderDao.sumTradeAmountByDeletedAndStateAndCreateTime(Deleted.DEL_FALSE, state, date);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal getTradeAmountByDateAndSupplier(Date date, Integer[] state, Integer supplierId) {
		if (date == null) {
			return BigDecimal.ZERO;
		}
		// 订单状态 2-已付款待发货 3-已发货待收货 4-已收货已完成
		return orderDao.sumTradeAmountByDeletedAndStateAndCreateTimeAndSupplier(Deleted.DEL_FALSE, state, date, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAfterSaleOrderNum() {
		return orderDao.countByDeletedAndAfterSaleState(Deleted.DEL_FALSE, OrderEnum.AFTER_SALE_STATE_APPLY.getCode());
	}

	/**
	 * 统计 供应商售后订单数量
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAfterSaleOrderNumBySupplier(Integer supplierId) {
		return orderDao.countByDeletedAndAfterSaleStateAndSupplier_id(Deleted.DEL_FALSE, OrderEnum.AFTER_SALE_STATE_APPLY.getCode(), supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getMemberConsumesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return orderDao.findMemberConsumesByDate(pageable, startDate, endDate, state);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getCommoditySalesByDate(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return orderDao.findCommoditySalesByDate(pageable, startDate, endDate, state);
	}

	/**
	 * 供应商获取会员消费排行
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getMemberConsumesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId) {
		return orderDao.findMemberConsumesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
	}

	/**
	 * 供应商获取商品销量排行
	 *
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getCommoditySalesByDateAndSupplier(Pageable pageable, Date startDate, Date endDate, Integer[] state, Integer supplierId) {
		return orderDao.findCommoditySalesByDateAndSupplier(pageable, startDate, endDate, state, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDate(Date startDate, Date endDate) {
		return orderDao.findDailyAddNumByDate(Deleted.DEL_FALSE, startDate, endDate);
	}

	/**
	 * 统计供应商每天新增数量
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getDailyAddNumByDateAndSupplierId(Date startDate, Date endDate, Integer supplierId) {
		return orderDao.findDailyAddNumByDateAndSupplierId(Deleted.DEL_FALSE, startDate, endDate, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByDeletedAndState(Integer deleted, Integer state) {
		return orderDao.countByDeletedAndOrderState(deleted, state);
	}

	/**
	 * 供应商业绩排名
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Object[]> getSupplierAchievements(Pageable pageable, Date startDate, Date endDate, Integer[] state) {
		return orderDao.findSupplierAchievements(pageable, startDate, endDate, state);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getPayOrderByIds(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split(",");
			List<Integer> idList = new ArrayList<>();
			if (ArrayUtils.isNotEmpty(idArray)) {
				for (String id : idArray) {
					if (StringUtils.isNotBlank(id)) {
						idList.add(Integer.valueOf(id));
					}
				}
			}
			return orderDao.findAllById(idList);
		}
		return null;
	}

	/**
	 * 支付成功以后处理方法
	 */
	@Override
	public void afterPayByWeChat(SortedMap<String, String> resultMap) {
		if (MapUtils.isNotEmpty(resultMap)) {
			List<SaleOrder> dbSaleOrders = this.getPayOrderByIds(resultMap.get("attach"));
			if (CollectionUtils.isNotEmpty(dbSaleOrders)) {
				for (SaleOrder tmp : dbSaleOrders) {
					if (tmp != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmp.getOrderState())) {
						tmp.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
					}
				}
			}
		}

	}

	/**
	 * TODO 待修正优化
	 * <p>
	 * 用户订单状态数量
	 *
	 * @param memberId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int[] getOrderState(Integer memberId) {
		int[] listState = new int[4];
		listState[0] = orderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), memberId, Deleted.DEL_FALSE);
		listState[1] = orderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode(), memberId, Deleted.DEL_FALSE);
		listState[2] = orderDao.countByOrderStateAndMemberIdAndDeleted(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(), memberId, Deleted.DEL_FALSE);
		listState[3] = orderDao.countByCommentStateAndMemberIdAndDeleted(OrderEnum.COMMENT_STATE_WAIT.getCode(), memberId, Deleted.DEL_FALSE);
		// Object[] obj = orderDao.countByStateAndMemberId(memberId, Deleted.DEL_FALSE);
		return listState;
	}

	/**
	 * 获取待支付的订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getWaitPayOrdersForWeChat() {
		return orderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 评价更新状态
	 */
	@Override
	public void updateSaleOrderByComment(Integer orderId) {
		SaleOrder dbSaleOrder = this.getOrderById(orderId);
		if (dbSaleOrder != null) {
			dbSaleOrder.setCommentState(OrderEnum.COMMENT_STATE_ALREADY.getCode());
		}
	}

	/**
	 * 校验活动库存
	 *
	 * @param groupType
	 * @param groupId
	 * @param activityStock
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean groupCheckStock(Integer groupType, Integer groupId, Integer activityStock) {
		boolean adequate = false;
		if (groupType == 1) {
			adequate = nationalGroupService.groupBuyCheckStock(groupId, activityStock);
		}
		if (groupType == 2) {
			adequate = nationalGroupService.groupBuyCheckStock(groupId, activityStock);
		}
		if (groupType == 3) {
			adequate = nationalGroupService.groupBuyCheckStock(groupId, activityStock);
		}
		if (groupType == 4) {
			adequate = nationalGroupService.groupBuyCheckStock(groupId, activityStock);
		}

		return adequate;
	}

	/**
	 * 活动订单状态
	 *
	 * @param id
	 * @return
	 */
	@Override
	public void groupState(Integer id) {
		// try {
		// SaleOrder saleOrder = orderDao.getOne(id);
		// if
		// (!OrderEnum.ORDER_TYPE_ORDINARY.getCode().equals(saleOrder.getOrderType())) {
		// if (OrderEnum.GROUP_TYPE_NATIONAL.getCode().equals(saleOrder.getGroupType()))
		// {
		// if
		// (OrderEnum.ORDER_TYPE_GROUP_BUY.getCode().equals(saleOrder.getOrderType()))
		// {// 开团
		// NationalGroupRecord nationalGroupRecord =
		// nationalGroupRecordService.getOne(saleOrder.getNationalGroupRecordId());
		// nationalGroupRecord.setPay(OrderEnum.ALREADY_PAID.getCode());
		// nationalGroupRecord.setState(OrderEnum.WAIT_FOR_GROUP.getCode());
		// } else if
		// (OrderEnum.ORDER_TYPE_GROUP_GINSENG.getCode().equals(saleOrder.getOrderType()))
		// {// 参团
		// NationalGroupMember nationalGroupMember =
		// nationalGroupMemberDao.getOne(saleOrder.getNationalGroupRecordId());
		// nationalGroupMember.setPay(OrderEnum.ALREADY_PAID.getCode());
		// nationalGroupMember.setState(OrderEnum.WAIT_FOR_GROUP.getCode());
		// nationalGroupMember.getNationalGroupRecord().setJoinPeople(nationalGroupMember.getNationalGroupRecord().getJoinPeople()
		// + 1);
		// if (nationalGroupMember.getNationalGroupRecord().getGroupPeople() ==
		// nationalGroupMember.getNationalGroupRecord().getJoinPeople()) {
		// nationalGroupMember.getNationalGroupRecord().setState(OrderEnum.ALREADY_GROUP.getCode());
		// nationalGroupMember.setState(OrderEnum.ALREADY_GROUP.getCode());
		// }
		// }
		// }
		// }
		// } catch (Exception e) {
		// LOG.error("活动订单" + e.getMessage());
		// }
	}

	/**
	 * 确认订单 </br>
	 * 根据供应商 进行拆单 不会保存到数据库
	 */
	@Override
	public SaleOrderVo confirmOrder(SaleOrderBo orderBo) {
		if (orderBo == null) {
			LOG.error("confirmOrder，提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
		// 获取 会员信息
		Member dbMember = memberService.getMemberById(orderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("confirmOrder，会员数据为空");
			throw new BusinessException("会员数据不能为空");
		}
		// +++1 根据供应商进行拆单
		Map<Integer, SaleOrder> orderMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(orderBo.getCartVos())) {
			for (CartVo cartVo : orderBo.getCartVos()) {
				if (cartVo != null) {
					// 校验货品
					Product dbProduct = productService.checkProductForOrder(cartVo.getProduct().getId());
					if (dbProduct == null) {
						LOG.error("confirmOrder，货品{}不存在", cartVo.getProduct().getId());
						throw new BusinessException("购物车数据不能为空");
					}
					// 生成订单 校验库存
					orderMap = baseOrderService.createOrderByCart(orderMap, dbMember, dbProduct, dbProduct.getSupplier(), cartVo);
				}
			}
		}
		// +++2 计算订单的运费、优惠券、储值券等 并封装返回前端的数据
		SaleOrder pageOrder = new SaleOrder();
		// 如果前台传入的收货地址不为空
		ShippingAddressVo shippingAddressVo = null;
		if (orderBo.getShippingAddress() != null && orderBo.getShippingAddress().getId() > 0) {
			shippingAddressVo = shippingAddressService.getShippingAddressVoById(orderBo.getShippingAddress().getId());
		} else {
			// 查询会员的默认收货地址 如果没有查询最近添加的收货地址
			shippingAddressVo = shippingAddressService.getDefaultAddressVoByMember(dbMember.getId());
		}
		if (shippingAddressVo != null) {
			pageOrder.setConsignee(shippingAddressVo.getFullName());
			pageOrder.setConsigneePhone(shippingAddressVo.getPhone());
			pageOrder.setConsigneeAddr(shippingAddressVo.getProvince() + shippingAddressVo.getCity() + shippingAddressVo.getDistrict() + shippingAddressVo.getAddress());
		}
		if (MapUtils.isNotEmpty(orderMap)) {
			// 查询可用的优惠券
			List<CouponReceive> usableCoupons = couponReceiveService.getMemberCoupons(dbMember.getId());
			// 查询可用的代金券
			List<CouponReceive> usableVoucherCoupons = couponReceiveService.getMemberVouchers(dbMember.getId());
			// 锁定过的优惠券
			Set<CouponReceive> lockCoupons = new HashSet<>(0);
			// 锁定过的代金券
			Set<CouponReceive> lockVoucherCoupons = new HashSet<>(0);
			// 遍历订单
			for (SaleOrder tmpOrder : orderMap.values()) {
				if (tmpOrder != null) {
					// 运费不参与优惠券计算
					// +++ 2-1-----计算代金券
					// 计算适合的代金券
					CouponReceive tmpVoucher = baseOrderService.calculateCoupon(tmpOrder, lockVoucherCoupons, usableVoucherCoupons);
					if (tmpVoucher != null) {
						// 添加到锁定储值券
						lockVoucherCoupons.add(tmpVoucher);
						// 从可用的储值券中去除
						usableVoucherCoupons.remove(tmpVoucher);
						// 该笔订单使用的储值券
						if (tmpOrder.getVouchers() == null) {
							List<CouponReceive> useVoucherCoupons = new ArrayList<>();
							useVoucherCoupons.add(tmpVoucher);
							tmpOrder.setVouchers(useVoucherCoupons);
						} else {
							tmpOrder.getVouchers().add(tmpVoucher);
						}
						// 支付金额=当前支付金额-储值券金额
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpVoucher.getParValue()));
					}
					// ++2-2-----计算适合的优惠券
					CouponReceive tmpCoupon = baseOrderService.calculateCoupon(tmpOrder, lockCoupons, usableCoupons);
					if (tmpCoupon != null) {
						// 添加到锁定优惠券
						lockCoupons.add(tmpCoupon);
						// 从可用的优惠券中去除
						usableCoupons.remove(tmpCoupon);
						// 该笔订单使用的优惠券
						if (tmpOrder.getCoupons() == null) {
							List<CouponReceive> useCoupons = new ArrayList<>();
							useCoupons.add(tmpCoupon);
							tmpOrder.setCoupons(useCoupons);
						} else {
							tmpOrder.getCoupons().add(tmpCoupon);
						}
						// 支付金额=当前支付金额-优惠券金额
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpCoupon.getParValue()));
					}

					// +++2-3收货地址不为空 计算该笔订单的运费
					BigDecimal tmpFreightAmount = BigDecimal.ZERO;
					if (shippingAddressVo != null) {
						tmpFreightAmount = baseOrderService.calculateFreightByOrder(tmpOrder, shippingAddressVo.getProvince(), shippingAddressVo.getCity());
					}
					// 订单金额=当前订单金额+运费
					tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(tmpFreightAmount));
					// 支付金额=当前支付金额+运费
					tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(tmpFreightAmount));
					// 折扣金额
					// tmp.setDiscountAmount(tmpCoupon.getParValue());
					// 优惠券金额
					tmpOrder.setCouponAmount(Optional.ofNullable(tmpCoupon).map(e -> e.getParValue()).orElse(BigDecimal.ZERO));
					// 储值券金额
					tmpOrder.setVoucherAmount(Optional.ofNullable(tmpVoucher).map(e -> e.getParValue()).orElse(BigDecimal.ZERO));
					// 运费
					tmpOrder.setFreight(tmpFreightAmount);
					tmpOrder.setDeliveryMode("快递配送");
					// 计算总单相关金额
					pageOrder.setOrderAmount(Optional.ofNullable(pageOrder.getOrderAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
					pageOrder.setPayAmount(Optional.ofNullable(pageOrder.getPayAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
					pageOrder.setFreight(Optional.ofNullable(pageOrder.getFreight()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getFreight()));
					pageOrder.setCouponAmount(Optional.ofNullable(pageOrder.getCouponAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getCouponAmount()));
					pageOrder.setVoucherAmount(Optional.ofNullable(pageOrder.getVoucherAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getVoucherAmount()));
				}
			}
			// 返回数据
			pageOrder.setMember(dbMember);
			pageOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		}
		SaleOrderVo saleOrderVo = orderConvert.toVo(pageOrder);
		saleOrderVo.setCartVos(orderBo.getCartVos());
		saleOrderVo.setSplitOrders(orderConvert.toVos(orderMap.values()));
		saleOrderVo.setShippingAddress(shippingAddressVo);
		return saleOrderVo;
	}

	/**
	 * 提交订单
	 */
	@Override
	public SaleOrderVo submitOrder(SaleOrderBo orderBo) {
		if (orderBo == null || CollectionUtils.isEmpty(orderBo.getSplitOrders()) ) {
			LOG.error("submitOrder，提交数据为空");
			throw new BusinessException("提交数据不能为空");
		}
//		// 收货地址
//		ShippingAddress dbShippingAddress = shippingAddressService.getShippingAddressById(orderBo.getShippingAddress().getId());
//		if (dbShippingAddress == null) {
//			LOG.error("submitOrder，收货地址为空");
//			throw new BusinessException("请选择收货地址");
//		}
		// 获取会员信息
		Member dbMember = memberService.getMemberById(orderBo.getMember().getId());
		if (dbMember == null) {
			LOG.error("submitOrder，会员数据为空");
			throw new BusinessException("会员不能为空");
		}
		// +++1 计算需要支付的金额 并保存订单
		SaleOrderVo pageOrder = new SaleOrderVo();
		// 总单号
		pageOrder.setPayOrderNo(ValueUtils.generateGUID());
		// 需要保存的订单
		List<SaleOrder> saveOrders = orderConvert.toEntities(orderBo.getSplitOrders());
		// +++2 遍历订单 -整理 订单数据和订单项数据
		for (SaleOrder tmpOrder : saveOrders) {
			if (tmpOrder != null && CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
				// 清空前台传入金额 从新计算订单金额
				tmpOrder.setOrderAmount(BigDecimal.ZERO);
				tmpOrder.setPayAmount(BigDecimal.ZERO);
				tmpOrder.setFreight(BigDecimal.ZERO);
				tmpOrder.setCouponAmount(BigDecimal.ZERO);
				tmpOrder.setVoucherAmount(BigDecimal.ZERO);
				// +++2-1遍历订单项
				for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
					if (tmpItem != null) {
						if (tmpItem.getProduct() == null) {
							LOG.error("submitOrder，货品为空");
							throw new BusinessException("商品数据不能为空");
						}
						// 核验货品
						Product dbProduct = productService.checkProductForOrder(tmpItem.getProduct().getId());
						if (dbProduct == null) {
							LOG.error("submitOrder，货品为空");
							throw new BusinessException("商品数据不能为空");
						}
//						// 检查是否在销售区域内
//						boolean flag = baseOrderService.checkSaleRegion(dbProduct.getCommodity(), dbShippingAddress);
//						if (!flag) {
//							LOG.error("submitOrder，不在销售地区内");
//							throw new BusinessException(dbProduct.getProductShortName() + " 不在销售范围内，请重新选择");
//						}
						// 核验库存
						boolean stockFlag = stockService.checkStock(dbProduct.getId(), tmpItem.getQuantity());
						if (!stockFlag) {
							LOG.error("productId={}，库存不足", dbProduct.getId());
							throw new BusinessException(dbProduct.getProductShortName() + " 库存不足");
						}
						// 订单项 数据
						tmpItem.setSaleOrder(tmpOrder);
						tmpItem.setMember(dbMember);
						tmpItem.setSupplier(dbProduct.getCommodity().getSupplier());
						tmpItem.setCommodity(dbProduct.getCommodity());
						tmpItem.setProduct(dbProduct);
						// 计算会员价格 四舍五入
						BigDecimal tmpPrice = baseOrderService.calculatePriceByLevel(dbProduct, dbMember).setScale(2, BigDecimal.ROUND_UP);
						// 小计金额= 价格*数量
						BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(tmpItem.getQuantity()));
						tmpItem.setPrice(tmpPrice);// 修改为计算后的价格
						tmpItem.setSubtotal(subTotal);// 小计
						// +++订单 供应商
						tmpOrder.setSupplier(dbProduct.getCommodity().getSupplier());
						// 订单金额
						tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
						tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
					}
				}
				// +++2-2 订单数据
				tmpOrder.setMember(dbMember);
				tmpOrder.setCommunity(dbMember.getCommunity());
				tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
				tmpOrder.setPayOrderNo(pageOrder.getPayOrderNo());
				tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
				tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
//				// 收货地址
//				tmpOrder.setConsignee(dbShippingAddress.getFullName());
//				tmpOrder.setConsigneePhone(dbShippingAddress.getPhone());
//				tmpOrder.setConsigneeAddr(dbShippingAddress.getProvince() + dbShippingAddress.getCity() + dbShippingAddress.getDistrict() + dbShippingAddress.getAddress());
//				tmpOrder.setDeliveryMode("快递配送");
				// 运费不参与优惠券计算
				// +++2-3--核算代金券
				CouponReceive tmpVoucher = baseOrderService.checkCoupon(tmpOrder, tmpOrder.getVouchers());
				if (tmpVoucher != null) {
					// 支付金额=当前支付金额-储值券金额
					tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpVoucher.getParValue()));
					tmpOrder.getVouchers().add(tmpVoucher);
				}
				// +++2-4--核算优惠券
				CouponReceive tmpCoupon = baseOrderService.checkCoupon(tmpOrder, tmpOrder.getCoupons());
				if (tmpCoupon != null) {
					// 支付金额=当前支付金额-优惠券金额
					tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpCoupon.getParValue()));
					tmpOrder.getCoupons().add(tmpCoupon);
				}
				// 优惠券金额
				tmpOrder.setCouponAmount(Optional.ofNullable(tmpCoupon).map(e -> e.getParValue()).orElse(BigDecimal.ZERO));
				// 代金券金额
				tmpOrder.setVoucherAmount(Optional.ofNullable(tmpVoucher).map(e -> e.getParValue()).orElse(BigDecimal.ZERO));
				// +++2-5--核算运费
//				BigDecimal tmpFreightAmount = baseOrderService.calculateFreightByOrder(tmpOrder, dbShippingAddress.getProvince(), dbShippingAddress.getCity());
				// 运费
				tmpOrder.setFreight(BigDecimal.ZERO);
				// 订单金额=当前订单金额+运费
				tmpOrder.setOrderAmount(tmpOrder.getOrderAmount());
				// 支付金额=当前支付金额+运费
				tmpOrder.setPayAmount(tmpOrder.getPayAmount());
				tmpOrder.setOrderTime(new Date());
				tmpOrder.setPayInvalidTime(orderSettingService.getInvalidTimeBySetType(tmpOrder.getOrderTime(), OrderEnum.ORDER_SET_TYPE_NORMAL));
				// +++2.6--计算总单金额数据
				pageOrder.setOrderAmount(Optional.ofNullable(pageOrder.getOrderAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
				pageOrder.setPayAmount(Optional.ofNullable(pageOrder.getPayAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
				pageOrder.setFreight(Optional.ofNullable(pageOrder.getFreight()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getFreight()));
				pageOrder.setCouponAmount(Optional.ofNullable(pageOrder.getCouponAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getCouponAmount()));
				pageOrder.setVoucherAmount(Optional.ofNullable(pageOrder.getVoucherAmount()).map(e -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getVoucherAmount()));
			}
		}
		// 保存订单
		List<SaleOrder> dbSaleOrders = orderDao.saveAll(saveOrders);
		// 保存订单项
		// saleOrderItemDao.saveAll(entities);
		// 保存 订单日志
		orderLogService.batchAddByOrders(dbSaleOrders, OrderEnum.ORDER_LOG_STATE_CREATE_ORDER.getCode());
		// 使用优惠券
		couponReceiveService.useCouponsByOrder(dbSaleOrders);
		// 删除购物车
		cartService.batchDeleteByOrder(orderBo.getCartVos());
		// TODO使用库存
		stockService.useStockBySubmitOrder(dbSaleOrders);

		pageOrder.setSplitOrders(orderConvert.toVos(dbSaleOrders));
		return pageOrder;
	}

	/**
	 * 确认收货 计算积分和佣金
	 */
	@Override
	public void confirmReceipt(Integer orderId) {
		SaleOrder dbSaleOrder = orderDao.getOne(orderId);
		// 待收货的 确认收货
		if (dbSaleOrder != null && OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(dbSaleOrder.getOrderState())) {
			dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode());
			dbSaleOrder.setCommentState(OrderEnum.COMMENT_STATE_WAIT.getCode());
			dbSaleOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
			dbSaleOrder.setReceiptTime(new Date());
			dbSaleOrder.setReturnInvalidTime(DateUtils.addDays(dbSaleOrder.getReceiptTime(), 7));
			dbSaleOrder.setExchangeInvalidTime(DateUtils.addDays(dbSaleOrder.getReceiptTime(), 15));
			// 增加订单日志
			orderLogService.addLogByOrder(dbSaleOrder, OrderEnum.ORDER_LOG_STATE_CONFIRM_RECEIPT, "确认收货");
			//todo 计算上级佣金
//			// 计算上级佣金 和 小区管理员提成
			memberService.calculateCommissionForDistribution(dbSaleOrder, dbSaleOrder.getMember());
//			// 计算会员积分
//			memberService.calculateOrderIntegral(dbSaleOrder, dbSaleOrder.getMember());
//			// 计算供应商账户数据
//			supplierAccountService.updateSupplierAccountByConfirmReceipt(dbSaleOrder);
//			// 分步发放优惠券
//			couponReceiveService.grantVoucherByStep(dbSaleOrder.getMember(), dbSaleOrder, ActivityEnum.GRANT_NODE_RECEIPT);
		}
	}

	/**
	 * 自动关闭未支付的订单
	 */
	@Override
	public void closeOrderByTask() throws Exception {
		List<SaleOrder> saleOrders = orderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(saleOrders)) {
			LOG.info("没有超时未付款的订单");
			return;
		}
		LOG.info("超时未付款的订单个数为{}个", saleOrders.size());
		// 根据订单设置 校验是否超时
		for (SaleOrder tmpOrder : saleOrders) {
			if (tmpOrder != null && OrderEnum.ORDER_STATE_WAIT_PAY.getCode().equals(tmpOrder.getOrderState())) {
				// 超时状态
				boolean flag = false;
				// 普通订单
				if (OrderEnum.ORDER_TYPE_ORDINARY.getCode().equals(tmpOrder.getOrderType())) {
					// 根据订单设置 校验是否超时
					boolean flag1 = orderSettingService.checkTimeout(tmpOrder.getOrderTime(), new Date(), OrderEnum.ORDER_SET_TYPE_NORMAL);
					if (flag1) {
						flag = true;
					}
					// 拼团订单
				} else {
					// 根据订单设置 校验是否超时
					boolean flag2 = orderSettingService.checkTimeout(tmpOrder.getOrderTime(), new Date(), OrderEnum.ORDER_SET_TYPE_SECKILL);
					if (flag2) {
						flag = true;
					}
				}
				if (flag) {
					// 向微信或支付宝查询订单 查看是否支付过
					boolean payFlag = weChatNotifyService.orderQueryForWeChat(tmpOrder);
					if (!payFlag) {
						tmpOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode());
						tmpOrder.setCloseTime(new Date());
						// 增加订单日志
						orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_CLOSE_ORDER, "超时关闭订单");
					}
				}
			}
		}
	}

	/**
	 * 自动收货
	 */
	@Override
	public void autoReceiptByTask() throws Exception {
		List<SaleOrder> dbSaleOrders = orderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有超时未收货的订单");
			return;
		}
		LOG.info("超时未收货的订单数为{}个", dbSaleOrders.size());
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getDeliveryTime(), new Date(), OrderEnum.ORDER_SET_TYPE_RECEIPT);
			// 超时且未收货的
			if (flag && OrderEnum.ORDER_STATE_WAIT_RECEIPT.getCode().equals(tmpOrder.getOrderState())) {
				tmpOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode());
				tmpOrder.setCommentState(OrderEnum.COMMENT_STATE_WAIT.getCode());
				if (tmpOrder.getAfterSaleState() == null) {
					tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode());
				}
				tmpOrder.setReceiptTime(new Date());
				tmpOrder.setReturnInvalidTime(DateUtils.addDays(tmpOrder.getReceiptTime(), 7));
				tmpOrder.setExchangeInvalidTime(DateUtils.addDays(tmpOrder.getReceiptTime(), 15));
				// 增加订单日志
				orderLogService.addLogByOrder(tmpOrder, OrderEnum.ORDER_LOG_STATE_CONFIRM_RECEIPT, "超时确认收货");
				//todo 计算上级佣金
//				// 计算上级佣金 和 小区管理员提成
				memberService.calculateCommissionForDistribution(tmpOrder, tmpOrder.getMember());
//				// 计算会员积分
//				memberService.calculateOrderIntegral(tmpOrder, tmpOrder.getMember());
//				// 计算供应商账户数据
//				supplierAccountService.updateSupplierAccountByConfirmReceipt(tmpOrder);
//				// 分步发放优惠券
//				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_RECEIPT);
			}
		}
	}

	/**
	 * 自动好评订单
	 */
	@Override
	public void autoCommentByTask() throws Exception {
		List<SaleOrder> dbSaleOrders = orderDao.findByCommentStateAndDeleted(OrderEnum.COMMENT_STATE_WAIT.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有超时未评价的订单");
			return;
		}
		LOG.info("未评价的订单数为{}", dbSaleOrders.size());
		// 需要评价的订单
		Set<SaleOrder> needCommentOrders = new HashSet<>();
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getReceiptTime(), new Date(), OrderEnum.ORDER_SET_TYPE_COMMENT);
			if (flag && OrderEnum.COMMENT_STATE_WAIT.getCode().equals(tmpOrder.getCommentState())) {
				tmpOrder.setCommentState(OrderEnum.COMMENT_STATE_ALREADY.getCode());
				needCommentOrders.add(tmpOrder);
				// 分步发放优惠券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_COMMENT);
			}
		}
		LOG.info("超时未评价的订单数为{}", needCommentOrders.size());
		// 批量评价订单
		commentService.autoCommentByOrders(needCommentOrders);
	}

	/**
	 * 收货超过15天 自动完成交易且不能申请售后
	 */
	@Override
	public void autoTradeByTask() throws Exception {
		// 查询已经收货的订单
		List<SaleOrder> dbSaleOrders = orderDao.findByOrderStateAndDeleted(OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode(), Deleted.DEL_FALSE);
		if (CollectionUtils.isEmpty(dbSaleOrders)) {
			LOG.info("没有需要自动完成交易的订单");
			return;
		}
		for (SaleOrder tmpOrder : dbSaleOrders) {
			// 根据订单设置 校验是否超时
			boolean flag = orderSettingService.checkTimeout(tmpOrder.getReceiptTime(), new Date(), OrderEnum.ORDER_SET_TYPE_TRADE);
			if (flag && OrderEnum.AFTER_SALE_STATE_CAN_APPLY.getCode().equals(tmpOrder.getAfterSaleState())) {
				tmpOrder.setAfterSaleState(OrderEnum.AFTER_SALE_STATE_EXPIRE.getCode());
				tmpOrder.setFinishTime(new Date());
				// 分步发放优惠券
				couponReceiveService.grantVoucherByStep(tmpOrder.getMember(), tmpOrder, ActivityEnum.GRANT_NODE_COMMENT);
			}
		}
	}

	/**
	 * 获取支付宝待支付订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SaleOrder> getWaitPayOrdersForAlipay(String parentOrderNo) {
		if (StringUtils.isBlank(parentOrderNo)) {
			return null;
		}
		return orderDao.findByPayOrderNoAndOrderStateAndDeleted(parentOrderNo, OrderEnum.ORDER_STATE_WAIT_PAY.getCode(), Deleted.DEL_FALSE);
	}

	/**
	 * 通过礼包 创建礼包订单
	 */
	@Override
	public SaleOrder addGiftOrderByGiftBag(GiftBag giftBag) {
		if (giftBag != null) {
			// 生成订单
			SaleOrder tmpOrder = new SaleOrder();
			tmpOrder.setMember(giftBag.getMember());
			tmpOrder.setSupplier(giftBag.getCommodity().getSupplier());
			tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
			tmpOrder.setPayOrderNo(ValueUtils.generateGUID());
			tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GIFT.getCode());
			tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_PAY.getCode());
			tmpOrder.setGiftOrderType(OrderEnum.GIFT_ORDER_TYPE_SEND.getCode());
			tmpOrder.setGiftBag(giftBag);
			tmpOrder.setCommodity(giftBag.getCommodity());
			tmpOrder.setProduct(giftBag.getProduct());
			tmpOrder.setPrice(giftBag.getPrice());
			tmpOrder.setQuantity(giftBag.getQuantity());
			tmpOrder.setOrderAmount(giftBag.getTotal());
			tmpOrder.setFreight(BigDecimal.ZERO);
			tmpOrder.setPayAmount(giftBag.getTotal());
			tmpOrder.setOrderTime(new Date());
			tmpOrder.setPayInvalidTime(orderSettingService.getInvalidTimeBySetType(tmpOrder.getOrderTime(), OrderEnum.ORDER_SET_TYPE_NORMAL));
			tmpOrder.setReadType(OrderEnum.READ_TYPE_NO.getCode());
			tmpOrder.setRemark("送礼订单");
			// 生成订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setMember(giftBag.getMember());
			tmpItem.setSupplier(tmpOrder.getSupplier());
			tmpItem.setCommodity(giftBag.getCommodity());
			tmpItem.setProduct(giftBag.getProduct());
			tmpItem.setPrice(giftBag.getPrice());
			tmpItem.setQuantity(giftBag.getQuantity());
			tmpItem.setSubtotal(BigDecimal.ZERO);
			// 添加订单项
			tmpOrder.getSaleOrderItems().add(tmpItem);
			// 保存订单数据
			return orderDao.save(tmpOrder);
		}
		return null;
	}

	/**
	 * 通过礼物 创建礼物订单
	 */
	@Override
	public SaleOrder addGiftOrderByGift(Gift gift) {
		if (gift != null) {
			SaleOrder tmpOrder = new SaleOrder();
			tmpOrder.setMember(gift.getMember());
			tmpOrder.setSupplier(gift.getCommodity().getSupplier());
			tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
			tmpOrder.setPayOrderNo(ValueUtils.generateGUID());
			tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GIFT.getCode());
			tmpOrder.setOrderState(OrderEnum.ORDER_STATE_WAIT_DELIVERY.getCode());
			tmpOrder.setGiftOrderType(OrderEnum.GIFT_ORDER_TYPE_RECEIPT.getCode());
			tmpOrder.setGiftBag(gift.getGiftBag());
			tmpOrder.setGift(gift);
			tmpOrder.setCommodity(gift.getCommodity());
			tmpOrder.setProduct(gift.getProduct());
			tmpOrder.setPrice(gift.getPrice());
			tmpOrder.setQuantity(gift.getQuantity());
			tmpOrder.setOrderAmount(gift.getTotal());
			tmpOrder.setFreight(BigDecimal.ZERO);
			tmpOrder.setPayAmount(BigDecimal.ZERO);
			tmpOrder.setConsignee(gift.getConsignee());
			tmpOrder.setConsigneePhone(gift.getConsigneePhone());
			tmpOrder.setConsigneeAddr(gift.getConsigneeAddr());
			tmpOrder.setOrderTime(new Date());
			tmpOrder.setPaymentTime(new Date());
			tmpOrder.setReadType(OrderEnum.READ_TYPE_NO.getCode());
			tmpOrder.setRemark("收礼订单");
			// 生成订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setMember(gift.getMember());
			tmpItem.setSupplier(tmpOrder.getSupplier());
			tmpItem.setCommodity(gift.getCommodity());
			tmpItem.setProduct(gift.getProduct());
			tmpItem.setPrice(gift.getPrice());
			tmpItem.setQuantity(gift.getQuantity());
			tmpItem.setSubtotal(BigDecimal.ZERO);
			// 添加订单项
			tmpOrder.getSaleOrderItems().add(tmpItem);
			// 保存订单数据
			return orderDao.save(tmpOrder);
		}
		return null;
	}

	/**
	 * 获取送礼订单
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SaleOrder getByGiftBag(Integer giftBagId) {
		return orderDao.findByOrderTypeAndGiftOrderTypeAndGiftBag_id(OrderEnum.ORDER_TYPE_GIFT.getCode(), OrderEnum.GIFT_ORDER_TYPE_SEND.getCode(), giftBagId);
	}

	/**
	 * 退款时关闭订单
	 */
	@Override
	public void closeOrderByRefund(SaleOrder saleOrder) {
		if (saleOrder != null && saleOrder.getId() > 0) {
			LOG.info("退款时关闭订单");
			SaleOrder dbSaleOrder = this.getOrderById(saleOrder.getId());
			// 非已完成、已关闭的订单 关闭
			if (dbSaleOrder != null && !OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode().equals(dbSaleOrder.getOrderState())
					&& !OrderEnum.ORDER_STATE_ALREADY_FINISH.getCode().equals(dbSaleOrder.getOrderState())) {
				dbSaleOrder.setOrderState(OrderEnum.ORDER_STATE_ALREADY_CLOSE.getCode());
			}
		}
	}

	@Override
	public SaleOrderVo confirmGroupOrder(SaleOrderBo orderBo) {
		// if (orderBo == null) {
		// LOG.error("confirmOrder，提交数据为空");
		// throw new BusinessException("提交数据不能为空");
		// }
		// // 获取 会员信息
		// Member dbMember = memberService.getMemberById(orderBo.getMember().getId());
		// if (dbMember == null) {
		// LOG.error("confirmOrder，会员数据为空");
		// throw new BusinessException("会员数据不能为空");
		// }
		//
		// NationalGroupVo nationalGroup =
		// nationalGroupService.getListVoById(orderBo.getGroupId());
		//
		// if (CollectionUtils.isNotEmpty(orderBo.getCartVos())) {
		// for (CartVo cartVo : orderBo.getCartVos()) {
		// if (cartVo != null) {
		// // 校验货品
		// Product dbProduct =
		// productService.checkProductForOrder(cartVo.getProduct().getId());
		// if (dbProduct == null) {
		// LOG.error("confirmOrder，货品{}不存在", cartVo.getProduct().getId());
		// throw new BusinessException("购物车数据不能为空");
		// }
		// }
		// }
		// }
		// // 2 进一步 计算订单的运费 优惠券，储值券等 并封装返回前端的数据
		// SaleOrder pageOrder = new SaleOrder();
		// // 整单的订单金额
		// BigDecimal orderAmount = BigDecimal.ZERO;
		// // 整单的支付金额
		// BigDecimal payAmount = BigDecimal.ZERO;
		// // 整单的运费
		// BigDecimal freightAmount = BigDecimal.ZERO;
		//
		// // 查询会员收货地址 如果没有 查询 最新添加的收货地址
		// ShippingAddressVo shippingAddressVo =
		// shippingAddressService.getDefaultAddressVoByMember(dbMember.getId());
		// if (shippingAddressVo != null) {
		// pageOrder.setConsignee(shippingAddressVo.getFullName());
		// pageOrder.setConsigneePhone(shippingAddressVo.getPhone());
		// pageOrder.setConsigneeAddr(shippingAddressVo.getProvince() +
		// shippingAddressVo.getCity() + shippingAddressVo.getDistrict() +
		// shippingAddressVo.getAddress());
		// }
		// List<SaleOrder> saleOrders = new ArrayList<>();
		// SaleOrder tmp = new SaleOrder();
		// tmp.setOrderAmount(nationalGroup.getGroupPrice().multiply(new
		// BigDecimal(orderBo.getCartVos().iterator().next().getQuantity())).add(nationalGroup.getFreight()));
		// tmp.setPayAmount(nationalGroup.getGroupPrice().multiply(new
		// BigDecimal(orderBo.getCartVos().iterator().next().getQuantity())).add(nationalGroup.getFreight()));
		// tmp.setFreight(nationalGroup.getFreight());
		//
		// if (orderBo.getCartVos().iterator().next().getQuantity() >
		// nationalGroup.getLimitQuantity()) {
		// LOG.error("商品限购" + nationalGroup.getLimitQuantity() + "件");
		// throw new BusinessException("商品限购" + nationalGroup.getLimitQuantity() + "件");
		// }
		// Boolean stockVerification = groupCheckStock(orderBo.getGroupType(),
		// orderBo.getGroupId(), orderBo.getCartVos().iterator().next().getQuantity());
		// if (!stockVerification) {
		// throw new BusinessException("活动库存不足");
		// }
		// if (tmp != null) {
		// tmp.setDeliveryMode("快递配送");
		// // 收货地址不为空 计算该笔订单的运费
		// BigDecimal tmpFreightAmount = BigDecimal.ZERO;
		// // 订单金额和支付金额四舍五入
		// tmp.getOrderAmount().setScale(2, BigDecimal.ROUND_UP);
		// tmp.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
		// // 总单-订单金额
		// orderAmount = orderAmount.add(tmp.getOrderAmount());
		// // // 总单-支付金额
		// payAmount = payAmount.add(tmp.getPayAmount());
		// // 总单-运费金额
		// freightAmount = freightAmount.add(tmp.getFreight());
		//
		// SaleOrder saleOrder = orderConvert.toEntity(orderBo);
		// // 订单项
		// SaleOrderItem tmpItem = new SaleOrderItem();
		//
		// Product product =
		// productService.getById(orderBo.getCartVos().iterator().next().getProduct().getId());
		// // 计算会员价格 四舍五入
		// // BigDecimal tmpPrice = calculatePriceByLevel(product, member).setScale(2,
		// // BigDecimal.ROUND_UP);
		// // 小计金额= 价格*数量+运费
		// BigDecimal subTotal = nationalGroup.getGroupPrice().multiply(new
		// BigDecimal(orderBo.getCartVos().iterator().next().getQuantity())).add(nationalGroup.getFreight());
		// tmpItem.setSaleOrder(tmp);
		// tmpItem.setProduct(product);
		// tmpItem.setMember(saleOrder.getMember());
		// tmpItem.setSupplier(product.getSupplier());
		// tmpItem.setCommodityImg(product.getCommodity().getImgPath());
		// tmpItem.setCommodityName(product.getCommodity().getCommodityName());
		// tmpItem.setCommodityShortName(product.getCommodity().getCommodityShortName());
		// tmpItem.setPrice(nationalGroup.getGroupPrice());
		// tmpItem.setQuantity(new
		// BigDecimal(orderBo.getCartVos().iterator().next().getQuantity()));
		// // tmpItem.setDiscount(cartVo.getDiscount());
		// tmpItem.setTotal(subTotal);
		// List<SaleOrderItem> saleOrderItems = new ArrayList<>();
		// saleOrderItems.add(tmpItem);
		// tmp.setSaleOrderItems(saleOrderItems);
		//
		// }
		// // 返回数据
		// pageOrder.setMember(dbMember);
		// pageOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		// pageOrder.setOrderAmount(orderAmount);
		// pageOrder.setPayAmount(payAmount);
		// pageOrder.setFreight(freightAmount);
		// pageOrder.setCouponAmount(new BigDecimal(0));
		// pageOrder.setGroupType(orderBo.getGroupType());
		// pageOrder.setGroupId(orderBo.getGroupId());
		// pageOrder.setVoucherAmount(new BigDecimal(0));
		// /**
		// * 0默认订单 1开团，参团
		// */
		// pageOrder.setOrderType(orderBo.getOrderType());
		// /**
		// * 0普通订单 OrderTypewei开团为0 参团时为开团id
		// */
		// if (orderBo.getNationalGroupRecordId() != null &&
		// !OrderEnum.ORDER_GENERAL.getCode().equals(orderBo.getNationalGroupRecordId()))
		// {
		// pageOrder.setNationalGroupRecordId(orderBo.getNationalGroupRecordId());
		// } else {
		// pageOrder.setNationalGroupRecordId(OrderEnum.ORDER_GENERAL.getCode());
		// }
		// SaleOrderVo saleOrderVo = orderConvert.toVo(pageOrder);
		// saleOrderVo.setCartVos(orderBo.getCartVos());
		// saleOrders.add(tmp);
		// saleOrderVo.setSplitOrders(orderConvert.toVos(saleOrders));
		// saleOrderVo.setShippingAddress(shippingAddressVo);
		// return saleOrderVo;
		return null;
	}

	/**
	 * 提交拼团订单订单
	 *
	 * @param orderBo
	 * @return
	 */
	@Override
	public SaleOrderVo submitGroupOrder(SaleOrderBo orderBo) {
		// if (orderBo == null || CollectionUtils.isEmpty(orderBo.getSplitOrders()) ||
		// orderBo.getShippingAddress() == null) {
		// LOG.error("submitGroupOrder，提交数据为空");
		// throw new BusinessException("提交数据不能为空");
		// }
		// // 收货地址
		// ShippingAddress dbShippingAddress =
		// shippingAddressService.getShippingAddressById(orderBo.getShippingAddress().getId());
		// if (dbShippingAddress == null) {
		// LOG.error("submitGroupOrder，收货地址为空");
		// throw new BusinessException("收货地址不能为空");
		// }
		// // 获取 会员信息
		// Member dbMember = memberService.getMemberById(orderBo.getMember().getId());
		// if (dbMember == null) {
		// LOG.error("submitGroupOrder，会员数据为空");
		// throw new BusinessException("会员不能为空");
		// }
		// if (orderBo.getOrderType() == null) {
		// orderBo.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		// }
		//
		// /* if(orderBo.getGroupId()) */
		// NationalGroup nationalGroup = nationalGroupDao.getOne(orderBo.getGroupId());
		// // 1计算需要支付的金额 并保存订单
		// SaleOrderVo pageOrder = new SaleOrderVo();
		//
		// pageOrder.setGroupType(orderBo.getGroupType());
		// pageOrder.setOrderType(orderBo.getOrderType());
		// pageOrder.setGroupId(orderBo.getGroupId());
		// // 总单号
		// pageOrder.setParentOrderNo(ValueUtils.generateGUID());
		// // 需要保存的订单
		// List<SaleOrder> saveOrders =
		// orderConvert.toEntities(orderBo.getSplitOrders());
		// SaleOrder tmpOrder = saveOrders.get(0);
		//
		// if (tmpOrder != null &&
		// CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
		// // 清空前台传入金额 从新计算订单金额
		// tmpOrder.setOrderAmount(BigDecimal.ZERO);
		// tmpOrder.setPayAmount(BigDecimal.ZERO);
		// tmpOrder.setFreight(BigDecimal.ZERO);
		// tmpOrder.setCouponAmount(BigDecimal.ZERO);
		// tmpOrder.setVoucherAmount(BigDecimal.ZERO);
		//
		// SaleOrderItem tmpItem = tmpOrder.getSaleOrderItems().iterator().next();
		// if (tmpItem != null) {
		// if (tmpItem.getProduct() == null) {
		// LOG.error("submitGroupOrder，货品为空");
		// throw new BusinessException("商品数据不能为空");
		// }
		// // 核验货品
		// Product dbProduct =
		// productService.checkProductForOrder(tmpItem.getProduct().getId());
		// if (dbProduct == null) {
		// LOG.error("submitGroupOrder，货品为空");
		// throw new BusinessException("商品数据不能为空");
		// }
		// // 检查是否在销售区域内
		// boolean flag = baseOrderService.checkSaleRegion(dbProduct.getCommodity(),
		// dbShippingAddress);
		//
		// if (!flag) {
		// LOG.error("submitGroupOrder，不在销售地区内");
		// throw new BusinessException(dbProduct.getProductShortName() + "
		// 不在销售范围内，请重新选择");
		// }
		//
		// if (orderBo.getCartVos().iterator().next().getQuantity() >
		// nationalGroup.getLimitQuantity()) {
		// LOG.error("商品限购" + nationalGroup.getLimitQuantity() + "件");
		// throw new BusinessException("商品限购" + nationalGroup.getLimitQuantity() + "件");
		// }
		// Boolean stockVerification = groupCheckStock(orderBo.getGroupType(),
		// orderBo.getGroupId(), orderBo.getCartVos().iterator().next().getQuantity());
		// if (!stockVerification) {
		// throw new BusinessException("活动库存不足");
		// }
		//
		// if (nationalGroup.getStockSet() == OrderEnum.ORDER_STOCK_SET.getCode()) {
		// nationalGroup.setStockSet(nationalGroup.getStockSet() -
		// orderBo.getCartVos().iterator().next().getQuantity());
		// }
		//
		// // 订单项 数据
		//
		// tmpItem.setSaleOrder(tmpOrder);
		// tmpItem.setMember(dbMember);
		// tmpItem.setCommodity(dbProduct.getCommodity());
		// tmpItem.setProduct(dbProduct);
		// tmpItem.setSupplier(dbProduct.getCommodity().getSupplier());
		// // 计算会员价格
		// // BigDecimal tmpPrice = baseOrderService.calculatePriceByLevel(dbProduct,
		// // dbMember);
		// // //小计金额= 价格*数量
		// BigDecimal subTotal =
		// nationalGroup.getGroupPrice().multiply(tmpItem.getQuantity());
		// tmpItem.setPrice(nationalGroup.getGroupPrice());// 修改为计算后的价格
		// tmpItem.setTotal(subTotal);// 小计
		// // +++订单 供应商
		// tmpOrder.setSupplier(dbProduct.getCommodity().getSupplier());
		// // 订单金额
		// tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
		// }
		// }
		// // 订单数据
		// tmpOrder.setMember(dbMember);
		// tmpOrder.setParentOrderNo(pageOrder.getParentOrderNo());
		// tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
		// tmpOrder.setState(OrderEnum.STATE_WAIT_PAY.getCode());
		// tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		// tmpOrder.setGroupId(orderBo.getGroupId());
		// tmpOrder.setGroupType(orderBo.getGroupType());
		// tmpOrder.setFreight(nationalGroup.getFreight());
		// tmpOrder.setNationalGroupRecordId(orderBo.getNationalGroupRecordId());
		// // 收货地址
		// tmpOrder.setConsignee(dbShippingAddress.getFullName());
		// tmpOrder.setConsigneePhone(dbShippingAddress.getPhone());
		// tmpOrder.setConsigneeAddr(dbShippingAddress.getProvince() +
		// dbShippingAddress.getCity() + dbShippingAddress.getDistrict() +
		// dbShippingAddress.getAddress());
		// tmpOrder.setDeliveryMode("快递配送");
		// // 订单金额=当前订单金额+运费
		// tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(tmpOrder.getFreight()));
		// // 支付金额=当前支付金额+运费
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(tmpOrder.getFreight()));
		// // +++++计算总单金额数据
		// pageOrder.setOrderAmount(Optional.ofNullable(pageOrder.getOrderAmount()).map(e
		// -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
		// pageOrder.setPayAmount(Optional.ofNullable(pageOrder.getPayAmount()).map(e ->
		// e).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
		// pageOrder.setFreight(Optional.ofNullable(pageOrder.getFreight()).map(e ->
		// e).orElse(BigDecimal.ZERO).add(tmpOrder.getFreight()));
		// orderDao.save(tmpOrder);
		// // 保存订单
		// // List<SaleOrder> dbSaleOrders = orderDao.saveAll(saveOrders);
		// if (orderBo.getOrderType() == OrderEnum.ORDER_TYPE_GROUP_BUY.getCode()) {
		// NationalGroupRecord nationalGroupRecord = new NationalGroupRecord();
		// NationalGroup nationalGroup1 =
		// nationalGroupDao.findByIdAndDeleted(orderBo.getGroupId(), Deleted.DEL_FALSE);
		// Member member = memberDao.getOne(orderBo.getMember().getId());
		// nationalGroupRecord.setNationalGroup(nationalGroup1);
		// nationalGroupRecord.setGroupCode(DateUtils.getSeriNo());
		// nationalGroupRecord.setMember(member);
		// nationalGroupRecord.setState(OrderEnum.NATIONAL_GROUP_RECORD_WAIT_FOR.getCode());
		// nationalGroupRecord.setGroupPeople(nationalGroup1.getGroupPeople());
		// nationalGroupRecord.setJoinPeople(1);
		// nationalGroupRecord.setOpenTime(new Date());
		// nationalGroupRecord.setConsignee(dbShippingAddress.getFullName());
		// nationalGroupRecord.setConsigneePhone(dbShippingAddress.getPhone());
		// nationalGroupRecord.setConsigneeAddr(dbShippingAddress.getProvince() +
		// dbShippingAddress.getCity() + dbShippingAddress.getDistrict() +
		// dbShippingAddress.getAddress());
		// nationalGroupRecord.setCreateTime(new Date());
		// NationalGroupRecord nationalGroupRecord1 =
		// nationalGroupRecordService.save(nationalGroupRecord);
		// pageOrder.setNationalGroupRecordId(nationalGroupRecord1.getId());
		// tmpOrder.setNationalGroupRecordId(nationalGroupRecord1.getId());
		// tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GROUP_BUY.getCode());
		// //
		// dbSaleOrders.iterator().next().setOrderType(OrderEnum.ORDER_TYPE_GROUP_BUY.getCode());
		// //
		// dbSaleOrders.iterator().next().setNationalGroupRecordId(nationalGroupRecord.getId());
		//
		// } else if (orderBo.getOrderType() ==
		// OrderEnum.ORDER_TYPE_GROUP_GINSENG.getCode()) {
		// NationalGroupMember nationalGroupMember = new NationalGroupMember();
		// NationalGroupRecord nationalGroupRecord =
		// nationalGroupRecordService.getOne(orderBo.getNationalGroupRecordId());
		// nationalGroupMember.setNationalGroupRecord(nationalGroupRecord);
		// Member member = memberDao.getOne(orderBo.getMember().getId());
		// nationalGroupMember.setMember(member);
		// nationalGroupMember.setConsignee(dbShippingAddress.getFullName());
		// nationalGroupMember.setState(OrderEnum.NATIONAL_GROUP_RECORD_WAIT_FOR.getCode());
		// nationalGroupMember.setConsigneePhone(dbShippingAddress.getPhone());
		// nationalGroupMember.setConsigneeAddr(dbShippingAddress.getProvince() +
		// dbShippingAddress.getCity() + dbShippingAddress.getDistrict() +
		// dbShippingAddress.getAddress());
		// nationalGroupMember.setCreateTime(new Date());
		// nationalGroupMemberDao.save(nationalGroupMember);
		// tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_GROUP_GINSENG.getCode());
		// tmpOrder.setNationalGroupRecordId(nationalGroupMember.getId());
		// pageOrder.setNationalGroupRecordId(nationalGroupMember.getId());
		// }
		// List<SaleOrder> dbSaleOrders = new ArrayList<>();
		// dbSaleOrders.add(tmpOrder);
		// orderLogService.batchAddByOrders(dbSaleOrders,
		// OrderEnum.ORDER_LOG_STATE_CREATE_ORDER.getCode());
		// stockService.useStockBySubmitOrder(dbSaleOrders);
		// pageOrder.setSplitOrders(orderConvert.toVos(dbSaleOrders));
		// return pageOrder;
		return null;
	}

	/**
	 * 全国拼团提交订单
	 */
	@Override
	public SaleOrderVo groupOrder(SaleOrderBo orderBo) {
		// if (orderBo == null || CollectionUtils.isEmpty(orderBo.getSplitOrders()) ||
		// orderBo.getShippingAddress() == null) {
		// LOG.error("submitOrder，提交数据为空");
		// throw new BusinessException("提交数据不能为空");
		// }
		// // 收货地址
		// ShippingAddress dbShippingAddress =
		// shippingAddressService.getShippingAddressById(orderBo.getShippingAddress().getId());
		// if (dbShippingAddress == null) {
		// LOG.error("submitOrder，收货地址为空");
		// throw new BusinessException("收货地址不能为空");
		// }
		// // 获取 会员信息
		// Member dbMember = memberService.getMemberById(orderBo.getMember().getId());
		// if (dbMember == null) {
		// LOG.error("submitOrder，会员数据为空");
		// throw new BusinessException("会员不能为空");
		// }
		// // 1计算需要支付的金额 并保存订单
		// SaleOrderVo pageOrder = new SaleOrderVo();
		// // 总单号
		// pageOrder.setParentOrderNo(ValueUtils.generateGUID());
		// // 需要保存的订单
		// List<SaleOrder> saveOrders =
		// orderConvert.toEntities(orderBo.getSplitOrders());
		// // 遍历订单 -整理 订单数据和订单项数据
		// if (orderBo.getOrderType() == OrderEnum.ORDER_TYPE_GROUP_BUY.getCode()) {
		// if (orderBo.getNationalGroupRecordId() == OrderEnum.ORDER_GENERAL.getCode())
		// {
		// NationalGroupRecord nationalGroupRecord = new NationalGroupRecord();
		// NationalGroup nationalGroup =
		// nationalGroupDao.findByProductIdAndDeleted(orderBo.getSaleOrderItems().iterator().next().getProduct().getId(),
		// Deleted.DEL_FALSE);
		// Member member = memberDao.getOne(orderBo.getMember().getId());
		// nationalGroupRecord.setNationalGroup(nationalGroup);
		// nationalGroupRecord.setGroupCode(DateUtils.getSeriNo());
		// nationalGroupRecord.setMember(member);
		// nationalGroupRecord.setGroupPeople(nationalGroup.getGroupPeople());
		// nationalGroupRecord.setJoinPeople(1);
		// nationalGroupRecord.setOpenTime(new Date());
		// nationalGroupRecord.setConsignee(orderBo.getConsignee());
		// nationalGroupRecord.setConsigneeAddr(orderBo.getConsigneePhone());
		// nationalGroupRecord.setConsigneePhone(orderBo.getConsigneeAddr());
		// nationalGroupRecord.setCreateTime(new Date());
		// nationalGroupRecordService.save(nationalGroupRecord);
		// } else {
		// NationalGroupMember nationalGroupMember = new NationalGroupMember();
		// NationalGroupRecord nationalGroupRecord =
		// nationalGroupRecordService.getOne(orderBo.getNationalGroupRecordId());
		// nationalGroupRecord.setJoinPeople(nationalGroupRecord.getJoinPeople() + 1);
		// if (nationalGroupRecord.getGroupPeople() ==
		// nationalGroupRecord.getJoinPeople()) {
		// nationalGroupRecord.setState(OrderEnum.NATIONAL_GROUP_RECORD_STATE.getCode());
		// }
		// nationalGroupMember.setNationalGroupRecord(nationalGroupRecord);
		// Member member = memberDao.getOne(orderBo.getMember().getId());
		// nationalGroupMember.setMember(member);
		// nationalGroupMember.setConsignee(orderBo.getConsignee());
		// nationalGroupMember.setConsigneeAddr(orderBo.getConsigneePhone());
		// nationalGroupMember.setConsigneePhone(orderBo.getConsigneeAddr());
		// nationalGroupMember.setCreateTime(new Date());
		// nationalGroupMemberDao.save(nationalGroupMember);
		// }
		// }
		// for (SaleOrder tmpOrder : saveOrders) {
		// if (tmpOrder != null &&
		// CollectionUtils.isNotEmpty(tmpOrder.getSaleOrderItems())) {
		// // 清空前台传入金额 从新计算订单金额
		// tmpOrder.setOrderAmount(BigDecimal.ZERO);
		// tmpOrder.setPayAmount(BigDecimal.ZERO);
		// tmpOrder.setFreight(BigDecimal.ZERO);
		// tmpOrder.setCouponAmount(BigDecimal.ZERO);
		// tmpOrder.setVoucherAmount(BigDecimal.ZERO);
		//
		// for (SaleOrderItem tmpItem : tmpOrder.getSaleOrderItems()) {
		// if (tmpItem != null) {
		// if (tmpItem.getProduct() == null) {
		// LOG.error("submitOrder，货品为空");
		// throw new BusinessException("商品数据不能为空");
		// }
		// // 核验货品
		// Product dbProduct =
		// productService.checkProductForOrder(tmpItem.getProduct().getId());
		// if (dbProduct == null) {
		// LOG.error("submitOrder，货品为空");
		// throw new BusinessException("商品数据不能为空");
		// }
		// // 检查是否在销售区域内
		// boolean flag = baseOrderService.checkSaleRegion(dbProduct.getCommodity(),
		// dbShippingAddress);
		// if (!flag) {
		// LOG.error("submitOrder，不在销售地区内");
		// throw new BusinessException(dbProduct.getProductShortName() + "
		// 不在销售范围内，请重新选择");
		// }
		// // 核验库存
		// boolean stockFlag = stockService.checkStock(dbProduct.getId(),
		// tmpItem.getQuantity());
		// if (!stockFlag) {
		// LOG.error("productId={}，库存不足", dbProduct.getId());
		// throw new BusinessException(dbProduct.getProductShortName() + " 库存不足");
		// }
		// // 订单项 数据
		// tmpItem.setSaleOrder(tmpOrder);
		// tmpItem.setMember(dbMember);
		// tmpItem.setCommodity(dbProduct.getCommodity());
		// tmpItem.setProduct(dbProduct);
		// tmpItem.setSupplier(dbProduct.getCommodity().getSupplier());
		// // 计算会员价格
		// BigDecimal tmpPrice = baseOrderService.calculatePriceByLevel(dbProduct,
		// dbMember);
		// // 小计金额= 价格*数量
		// BigDecimal subTotal = tmpPrice.multiply(tmpItem.getQuantity());
		// tmpItem.setPrice(tmpPrice);// 修改为计算后的价格
		// tmpItem.setTotal(subTotal);// 小计
		// // +++订单 供应商
		// tmpOrder.setSupplier(dbProduct.getCommodity().getSupplier());
		// // 订单金额
		// tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
		// }
		// }
		// // 订单数据
		// tmpOrder.setMember(dbMember);
		// tmpOrder.setParentOrderNo(pageOrder.getParentOrderNo());
		// tmpOrder.setOrderNo(NumberGenerateUtils.generateOrderNo());
		// tmpOrder.setState(OrderEnum.STATE_WAIT_PAY.getCode());
		// tmpOrder.setOrderType(OrderEnum.ORDER_TYPE_ORDINARY.getCode());
		// // 收货地址
		// tmpOrder.setConsignee(dbShippingAddress.getFullName());
		// tmpOrder.setConsigneePhone(dbShippingAddress.getPhone());
		// tmpOrder.setConsigneeAddr(dbShippingAddress.getProvince() +
		// dbShippingAddress.getCity() + dbShippingAddress.getDistrict() +
		// dbShippingAddress.getAddress());
		// tmpOrder.setDeliveryMode("快递配送");
		// // 1.--核算运费
		// BigDecimal tmpFreightAmount =
		// baseOrderService.calculateFreightByOrder(tmpOrder,
		// dbShippingAddress.getProvince(), dbShippingAddress.getCity());
		// // 运费
		// tmpOrder.setFreight(tmpFreightAmount);
		// // 订单金额=当前订单金额+运费
		// tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(tmpFreightAmount));
		// // 支付金额=当前支付金额+运费
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(tmpFreightAmount));
		// // 2.--核算 优惠券
		// CouponReceive tmpCoupon = baseOrderService.checkCoupon(tmpOrder,
		// tmpOrder.getCoupons());
		// if (tmpCoupon != null) {
		// // 支付金额=当前支付金额-优惠券金额
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpCoupon.getParValue()));
		// }
		// // 3.--核算储值券
		// CouponReceive tmpStorageCoupon = baseOrderService.checkCoupon(tmpOrder,
		// tmpOrder.getStorageCoupons());
		// if (tmpStorageCoupon != null) {
		// // 支付金额=当前支付金额-储值券金额
		// tmpOrder.setPayAmount(tmpOrder.getPayAmount().subtract(tmpStorageCoupon.getParValue()));
		// }
		// // 优惠券金额
		// tmpOrder.setCouponAmount(Optional.ofNullable(tmpCoupon).map(e ->
		// e.getParValue()).orElse(BigDecimal.ZERO));
		// // 储值券金额
		// tmpOrder.setVoucherAmount(Optional.ofNullable(tmpStorageCoupon).map(e ->
		// e.getParValue()).orElse(BigDecimal.ZERO));
		//
		// // +++++计算总单金额数据
		// pageOrder.setOrderAmount(Optional.ofNullable(pageOrder.getOrderAmount()).map(e
		// -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getOrderAmount()));
		// pageOrder.setPayAmount(Optional.ofNullable(pageOrder.getPayAmount()).map(e ->
		// e).orElse(BigDecimal.ZERO).add(tmpOrder.getPayAmount()));
		// pageOrder.setFreight(Optional.ofNullable(pageOrder.getFreight()).map(e ->
		// e).orElse(BigDecimal.ZERO).add(tmpOrder.getFreight()));
		// pageOrder.setCouponAmount(Optional.ofNullable(pageOrder.getCouponAmount()).map(e
		// -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getCouponAmount()));
		// pageOrder.setVoucherAmount(Optional.ofNullable(pageOrder.getVoucherAmount()).map(e
		// -> e).orElse(BigDecimal.ZERO).add(tmpOrder.getVoucherAmount()));
		// }
		// }
		// // 保存订单
		// List<SaleOrder> dbSaleOrders = orderDao.saveAll(saveOrders);
		// //
		// // List<SaleOrderVo> splitOrders = orderConvert.toVos(dbSaleOrders);
		// // 保存 订单日志
		// orderLogService.batchAddByOrders(dbSaleOrders,
		// OrderEnum.ORDER_LOG_STATE_CREATE_ORDER.getCode());
		// // 使用优惠券
		// couponReceiveService.useCouponsByOrder(dbSaleOrders);
		// // 删除购物车
		// cartService.batchDeleteByOrder(orderBo.getCartVos());
		// // TODO使用库存
		// stockService.useStockBySubmitOrder(dbSaleOrders);
		// pageOrder.setSplitOrders(orderConvert.toVos(dbSaleOrders));
		// return pageOrder;
		return null;
	}

}