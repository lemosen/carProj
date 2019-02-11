package com.yi.core.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.entity.CouponReceive;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.basic.domain.entity.Area;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.CommodityLevelDiscount;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.domain.entity.ShippingAddress;
import com.yi.core.member.domain.vo.MemberLevelListVo;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.domain.entity.SaleOrderItem;
import com.yi.core.order.service.IFreightTemplateConfigService;
import com.yi.core.supplier.domain.entity.Supplier;
import com.yihz.common.exception.BusinessException;

/**
 * 订单基础服务类
 * 
 * @author xuyh
 *
 */
@Service
public class BaseOrderService implements InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(BaseOrderService.class);

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private IFreightTemplateConfigService freightTemplateService;

	@Resource
	private IStockService stockService;

	@Resource
	private IProductService productService;

	@Resource
	private ICouponReceiveService couponReceiveService;

	/**
	 * 通过购物车的数据创建订单
	 * 
	 * @param orderMap
	 * @param member
	 * @param cartVo
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<Integer, SaleOrder> createOrderByCart(Map<Integer, SaleOrder> orderMap, Member member, Product product, Supplier supplier, CartVo cartVo) {
		// 校验购物车
		if (member == null || product == null || supplier == null || cartVo == null) {
			LOG.error("createOrderByCart，cartVo参数为空");
			throw new BusinessException("购物车数据不能为空");
		}
		// 校验库存
		boolean flag = stockService.checkStock(product.getId(), cartVo.getQuantity());
		if (!flag) {
			LOG.error("createOrderByCart，{}库存不足", product.getId());
			throw new BusinessException(product.getProductShortName() + " 库存不足");
		}
		// 拆单
		if (orderMap.containsKey(supplier.getId())) {
			SaleOrder tmpOrder = orderMap.get(supplier.getId());
			// 订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			// 计算会员价格 四舍五入
			BigDecimal tmpPrice = calculatePriceByLevel(product, member).setScale(2, BigDecimal.ROUND_UP);
			// 小计金额= 价格*数量
			BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(cartVo.getQuantity()));
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setProduct(product);
			tmpItem.setMember(member);
			tmpItem.setSupplier(supplier);
			tmpItem.setPrice(tmpPrice);
			tmpItem.setQuantity(cartVo.getQuantity());
			tmpItem.setSubtotal(subTotal);
			tmpOrder.getSaleOrderItems().add(tmpItem);
			tmpOrder.setOrderAmount(tmpOrder.getOrderAmount().add(subTotal));
			tmpOrder.setPayAmount(tmpOrder.getPayAmount().add(subTotal));
		} else {
			SaleOrder tmpOrder = new SaleOrder();
			// 订单项
			SaleOrderItem tmpItem = new SaleOrderItem();
			// 计算会员价格 四舍五入
			BigDecimal tmpPrice = calculatePriceByLevel(product, member).setScale(2, BigDecimal.ROUND_UP);
			// 小计金额= 价格*数量
			BigDecimal subTotal = tmpPrice.multiply(BigDecimal.valueOf(cartVo.getQuantity()));
			tmpItem.setSaleOrder(tmpOrder);
			tmpItem.setProduct(product);
			tmpItem.setMember(member);
			tmpItem.setSupplier(supplier);
			tmpItem.setPrice(tmpPrice);
			tmpItem.setQuantity(cartVo.getQuantity());
			tmpItem.setSubtotal(subTotal);
			if (tmpOrder.getSaleOrderItems() == null) {
				List<SaleOrderItem> saleOrderItems = new ArrayList<>();
				saleOrderItems.add(tmpItem);
				tmpOrder.setSaleOrderItems(saleOrderItems);
			} else {
				tmpOrder.getSaleOrderItems().add(tmpItem);
			}
			tmpOrder.setOrderAmount(subTotal);// 订单金额
			tmpOrder.setPayAmount(subTotal);// 支付金额
			orderMap.put(supplier.getId(), tmpOrder);
		}
		return orderMap;
	}

	/**
	 * 计算运费-- 根据整单计算运费
	 * 
	 * @param saleOrder
	 * @param address
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal calculateFreightByOrder(SaleOrder saleOrder, String province, String city) {
		if (saleOrder == null || CollectionUtils.isEmpty(saleOrder.getSaleOrderItems()) || StringUtils.isAnyBlank(province, city)) {
			LOG.error("calculateFreightByOrder， 计算运费参数为空");
			return BigDecimal.ZERO;
		}
		// 运费 = 统一运费 + 模板运费
		BigDecimal freight = BigDecimal.ZERO;
		for (SaleOrderItem tmpItem : saleOrder.getSaleOrderItems()) {
			if (tmpItem != null) {
				// 计算 统一运费
				if (CommodityEnum.FREIGHT_SET_UNIFIED.getCode().equals(tmpItem.getProduct().getCommodity().getFreightSet())) {
					freight = freight.add(tmpItem.getProduct().getCommodity().getUnifiedFreight());
					// 计算模板运费
				} else {
					BigDecimal tplFreight = freightTemplateService.calculateFreight(tmpItem.getProduct().getCommodity().getFreightTemplate(), tmpItem, province, city);
					freight = freight.add(tplFreight);
				}
			}
		}
		return freight;
	}

	/**
	 * 根据等级 计算会员价格
	 * 
	 * @param product
	 * @param member
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigDecimal calculatePriceByLevel(Product product, Member member) {
		if (product == null || member == null) {
			return BigDecimal.ZERO;
		}
		// 如果该商品设置自定义的会员等级折扣 采用商品的
		if (CollectionUtils.isNotEmpty(product.getCommodity().getCommodityLevelDiscounts())) {
			for (CommodityLevelDiscount tmp : product.getCommodity().getCommodityLevelDiscounts()) {
				if (tmp != null && tmp.getDiscount() != null && member.getMemberLevel() != null && tmp.getMemberLevel().getId() == member.getMemberLevel().getId()) {
					return product.getCurrentPrice().multiply(tmp.getDiscount()).divide(BigDecimal.valueOf(100));
				}
			}
		}
		// 如果商品没有设置自定义的会员等级折扣 则采用平台设置的会员折扣计算价格
		List<MemberLevelListVo> memberLevels = memberLevelService.queryAll();
		if (CollectionUtils.isNotEmpty(memberLevels)) {
			for (MemberLevelListVo tmp : memberLevels) {
				if (tmp != null && tmp.getDiscount() != null && member.getMemberLevel() != null && tmp.getId() == member.getMemberLevel().getId()) {
					return product.getCurrentPrice().multiply(tmp.getDiscount()).divide(BigDecimal.valueOf(100));
				}
			}
		}
		// 如果都没有 返回商品 现价
		return product.getCurrentPrice();
	}

	/**
	 * 检查优惠券
	 * 
	 * @param saleOrder
	 * @param usableCoupons
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceive checkCoupon(SaleOrder saleOrder, List<CouponReceive> useCoupons) {
		if (saleOrder != null && CollectionUtils.isNotEmpty(useCoupons)) {
			// 检查优惠券 是否符合使用要求
			for (CouponReceive tmpCouponReceive : useCoupons) {
				if (tmpCouponReceive != null && tmpCouponReceive.getId() > 0) {
					CouponReceive tmpCoupon = couponReceiveService.getById(tmpCouponReceive.getId());
					if (tmpCoupon != null) {
						// 使用条件-无限制 面值小于支付金额
						if (ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode().equals(tmpCoupon.getCoupon().getUseConditionType())
								&& tmpCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
							return tmpCoupon;
							// 使用条件-满XX元可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_MONEY.getCode().equals(tmpCoupon.getCoupon().getUseConditionType())) {
							// 满足条件且面值小于支付金额
							if (checkBuyAmount(saleOrder.getSaleOrderItems(), tmpCoupon.getCoupon()) && tmpCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmpCoupon;
							}
							// 使用条件-满XX件可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_PIECE.getCode().equals(tmpCoupon.getCoupon().getUseConditionType())) {
							// 满足条件且面值小于支付金额
							if (checkBuyQuantity(saleOrder.getSaleOrderItems(), tmpCoupon.getCoupon()) && tmpCoupon.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmpCoupon;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 计算优惠券
	 * 
	 * @param saleOrder
	 *            订单
	 * @param usedCoupons
	 *            锁定的优惠券
	 * @param usableCoupons
	 *            可用优惠券
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CouponReceive calculateCoupon(SaleOrder saleOrder, Set<CouponReceive> lockCoupons, List<CouponReceive> usableCoupons) {
		if (saleOrder != null && CollectionUtils.isNotEmpty(usableCoupons)) {
			// 遍历可用的优惠券 查询最优的优惠券
			for (CouponReceive tmp : usableCoupons) {
				if (tmp != null && !lockCoupons.contains(tmp)) {
					// 满减卷
					if (ActivityEnum.COUPON_TYPE_COUPON.getCode().equals(tmp.getCoupon().getCouponType())) {
						// 使用条件-无限制 面值小于支付金额
						if (ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode().equals(tmp.getCoupon().getUseConditionType())
								&& tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
							return tmp;
							// 使用条件-满XX元可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_MONEY.getCode().equals(tmp.getCoupon().getUseConditionType())) {
							// 满足优惠券条件且面值小于支付金额
							if (checkBuyAmount(saleOrder.getSaleOrderItems(), tmp.getCoupon()) && tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmp;
							}
							// 使用条件-满XX件可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_PIECE.getCode().equals(tmp.getCoupon().getUseConditionType())) {
							if (checkBuyQuantity(saleOrder.getSaleOrderItems(), tmp.getCoupon()) && tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmp;
							}
						}
						// 代金券
					} else if (ActivityEnum.COUPON_TYPE_VOUCHER.getCode().equals(tmp.getCoupon().getCouponType())) {
						// 使用条件-无限制 且面值小于支付金额
						if (ActivityEnum.USE_CONDITION_TYPE_UNLIMITED.getCode().equals(tmp.getCoupon().getUseConditionType())
								&& tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
							return tmp;
							// 使用条件-满XX元可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_MONEY.getCode().equals(tmp.getCoupon().getUseConditionType())) {
							// 满足优惠券条件且面值小于支付金额
							if (checkBuyAmount(saleOrder.getSaleOrderItems(), tmp.getCoupon()) && tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmp;
							}
							// 使用条件-满XX件可用
						} else if (ActivityEnum.USE_CONDITION_TYPE_FULL_PIECE.getCode().equals(tmp.getCoupon().getUseConditionType())) {
							if (checkBuyQuantity(saleOrder.getSaleOrderItems(), tmp.getCoupon()) && tmp.getParValue().compareTo(saleOrder.getPayAmount()) <= 0) {
								return tmp;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 遍历订单项 查看购买数量是否符合优惠券要求
	 *
	 * @param saleOrderItems
	 * @param coupon
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkBuyQuantity(List<SaleOrderItem> saleOrderItems, Coupon coupon) {
		boolean flag = false;
		if (CollectionUtils.isEmpty(saleOrderItems) || coupon == null) {
			return flag;
		}
		// 统计订单项中商品购买数量
		Map<Integer, BigDecimal> buyQuantity = new HashMap<>();
		saleOrderItems.forEach(tmp -> {
			if (tmp != null) {
				if (buyQuantity.containsKey(tmp.getProduct().getCommodity().getId())) {
					buyQuantity.put(tmp.getProduct().getCommodity().getId(), buyQuantity.get(tmp.getProduct().getCommodity().getId()).add(BigDecimal.valueOf(tmp.getQuantity())));
				} else {
					buyQuantity.put(tmp.getProduct().getCommodity().getId(), BigDecimal.valueOf(tmp.getQuantity()));
				}
			}
		});
		// 检验是否符合优惠券的使用条件
		for (Commodity tmp : coupon.getCommodities()) {
			if (tmp != null) {
				if (buyQuantity.containsKey(tmp.getId()) && buyQuantity.get(tmp.getId()).intValue() >= coupon.getFullQuantity()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 遍历订单项 查看购买金额是否符合优惠券要求
	 *
	 * @param saleOrderItems
	 * @param coupon
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkBuyAmount(List<SaleOrderItem> saleOrderItems, Coupon coupon) {
		boolean flag = false;
		if (CollectionUtils.isEmpty(saleOrderItems) || coupon == null) {
			return flag;
		}
		// 统计订单项中商品购买金额
		Map<Integer, BigDecimal> buyAmount = new HashMap<>();
		saleOrderItems.forEach(tmp -> {
			if (tmp != null) {
				if (buyAmount.containsKey(tmp.getProduct().getCommodity().getId())) {
					buyAmount.put(tmp.getProduct().getCommodity().getId(), buyAmount.get(tmp.getProduct().getCommodity().getId()).add(tmp.getSubtotal()));
				} else {
					buyAmount.put(tmp.getProduct().getCommodity().getId(), tmp.getSubtotal());
				}
			}
		});
		// 检验是否符合优惠券的使用条件
		for (Commodity tmp : coupon.getCommodities()) {
			if (tmp != null) {
				if (buyAmount.containsKey(tmp.getId()) && buyAmount.get(tmp.getId()).compareTo(coupon.getFullMoney()) >= 0) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 检查是否在销售地区内
	 * 
	 * @param commodity
	 * @param address
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean checkSaleRegion(Commodity commodity, ShippingAddress address) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodity.getRegions()) && address != null) {
			for (Region region : commodity.getRegions()) {
				if (Optional.ofNullable(region.getArea()).map(Area::getName).orElse("").equals(address.getProvince())
						|| Optional.ofNullable(region.getArea()).map(Area::getAreaCode).orElse("").equals(address.getProvince())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

}
