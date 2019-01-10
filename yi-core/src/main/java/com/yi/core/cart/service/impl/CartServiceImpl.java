/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.dao.CouponReceiveDao;
import com.yi.core.activity.service.ICouponReceiveService;
import com.yi.core.cart.dao.CartDao;
import com.yi.core.cart.domain.bo.CartBo;
import com.yi.core.cart.domain.entity.Cart;
import com.yi.core.cart.domain.entity.Cart_;
import com.yi.core.cart.domain.simple.CartSimple;
import com.yi.core.cart.domain.vo.CartListVo;
import com.yi.core.cart.domain.vo.CartVo;
import com.yi.core.cart.service.ICartService;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.member.service.IMemberService;
import com.yi.core.order.dao.SaleOrderDao;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 购物车
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CartServiceImpl implements ICartService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CartDao cartDao;

	@Resource
	private CouponReceiveDao couponReceiveDao;

	@Resource
	private SaleOrderDao saleOrderDao;

	@Resource
	private ICouponReceiveService couponReceiveService;

	@Resource
	private IStockService stockService;

	@Resource
	private IMemberService memberService;

	@Resource
	private IProductService productService;

	private EntityListVoBoSimpleConvert<Cart, CartBo, CartVo, CartSimple, CartListVo> cartConvert;

	@Override
	public Page<Cart> query(Pagination<Cart> query) {
		query.setEntityClazz(Cart.class);
		Page<Cart> page = cartDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CartListVo> queryListVo(Pagination<Cart> query) {
		Page<Cart> pages = this.query(query);
		List<CartListVo> vos = cartConvert.toListVos(pages.getContent());
		return new PageImpl<CartListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Cart getCartById(int cartId) {
		return cartDao.getOne(cartId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CartVo getCartVoById(int cartId) {
		return cartConvert.toVo(this.cartDao.getOne(cartId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CartListVo getCartListVoById(int cartId) {
		return cartConvert.toListVo(this.cartDao.getOne(cartId));
	}

	@Override
	public Cart addCart(Cart cart) {
		if (cart == null || cart.getMember() == null || cart.getProduct() == null) {
			throw new BusinessException("提交数据不能为空");
		}
		return cartDao.save(cart);
	}

	@Override
	public CartVo addCart(CartBo cartBo) {
		return cartConvert.toVo(this.addCart(cartConvert.toEntity(cartBo)));
	}

	@Override
	public Cart updateCart(Cart cart) {
		if (cart == null || cart.getId() < 1) {
			throw new BusinessException("提交数据不能为空");
		}
		Cart dbCart = cartDao.getOne(cart.getId());
		if (dbCart != null) {
			AttributeReplication.copying(cart, dbCart, Cart_.quantity, Cart_.price, Cart_.discount, Cart_.discountInfo);
		}
		return dbCart;
	}

	@Override
	public CartVo updateCart(CartBo cartBo) {
		return cartConvert.toVo(this.updateCart(cartConvert.toEntity(cartBo)));
	}

	@Override
	public void removeCartById(int cartId) {
		cartDao.deleteById(cartId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CartListVo> getShopCarts(int memberId) {
		return cartConvert.toListVos(cartDao.findByMember_idOrderByCreateTimeDesc(memberId));
	}

	/**
	 * 更新购物车数据
	 */
	@Override
	public CartVo changeShopCartNum(int cartId, int num) {
		if (num < 1) {
			throw new BusinessException("请选择正确的购买数量");
		}
		// 获取购物车信息
		Cart dbCart = cartDao.getOne(cartId);
		if (dbCart == null) {
			throw new BusinessException("数据异常，请联系客服处理");
		}
		// 核验库存
		boolean stockFlag = stockService.checkStock(dbCart.getProduct().getId(), num - dbCart.getQuantity());
		if (!stockFlag) {
			LOG.error("productId={}，库存不足", dbCart.getProduct().getId());
			throw new BusinessException(dbCart.getProduct().getProductShortName() + " 库存不足");
		}
		dbCart.setQuantity(num);
		return cartConvert.toVo(dbCart);
	}

	/**
	 * 添加数据到购物车
	 */
	@Override
	public CartVo addProductToCart(int memberId, int productId, int num) {
		if (num < 1) {
			throw new BusinessException("请选择正确的购买数量");
		}
		// 校验 会员添加购物车最大数量
		int count = cartDao.countByMember_Id(memberId);
		if (count > 99) {
			throw new BusinessException("最多添加99件商品");
		}
		// 核验库存
		boolean stockFlag = stockService.checkStock(productId, num);
		if (!stockFlag) {
			LOG.error("productId={}，库存不足", productId);
			throw new BusinessException(" 库存不足");
		}
		// 校验购物车是否有该商品 有就更新物车数量 没有就新建
		Cart dbCart = cartDao.findByMember_idAndProduct_id(memberId, productId);
		if (dbCart != null) {
			dbCart.setQuantity(dbCart.getQuantity() + num);
		} else {
			Product dbProduct = productService.getById(productId);
			if (dbProduct == null) {
				throw new BusinessException("商品不能为空");
			}
			Member dbMember = memberService.getMemberById(memberId);
			if (dbMember == null) {
				throw new BusinessException("会员不能为空");
			}
			Cart cart = new Cart();
			cart.setMember(dbMember);
			cart.setProduct(dbProduct);
			cart.setQuantity(num);
			dbCart = cartDao.save(cart);
		}
		return cartConvert.toVo(dbCart);
	}

	/**
	 * 下单时批量删除购物车
	 */
	@Override
	public void batchDeleteByOrder(List<CartVo> cartVos) {
		if (CollectionUtils.isNotEmpty(cartVos)) {
			cartVos.forEach(cartVo -> {
				this.removeCartById(cartVo.getId());
			});
		}
	}

//	@Override
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public Set<CouponReceiveVo> judgeCouponReceive(SaleOrderBo saleOrderBo) {
//		Set<CouponReceiveVo> canUse = new HashSet<>();
//		List<CouponReceive> couponReceives = couponReceiveDao.findByMember_id(saleOrderBo.getMember().getId());
//		r: for (CouponReceive o : couponReceives) {
//			if (o.getCoupon().getCouponType() == CartEnum.COUPON_COUPON_TYPE.getCode()) {
//				o.setState(CartEnum.COUPONRECEIVES_STATE_NO.getCode());
//
//				if (o.getCoupon().getUseConditionType() == CartEnum.COUPON_USE_CONDITION_TYPE_PIECE.getCode()) {
//					for (CartVo e : saleOrderBo.getCartVos()) {
//						for (Commodity e1 : o.getCoupon().getCommodities()) {
//							if (e.getProduct().getCommodity().getId() == e1.getId()) {
//								if (o.getCoupon().getFullQuantity() <= e.getQuantity()) {
//									o.setState(CartEnum.COUPONRECEIVES_STATE_YES.getCode());
//									continue r;
//								}
//							}
//						}
//						;
//					}
//					;
//				}
//				if (o.getCoupon().getUseConditionType() == CartEnum.COUPON_USE_CONDITION_TYPE_MONEY.getCode()) {
//					BigDecimal jia = new BigDecimal(0);
//					for (CartVo o1 : saleOrderBo.getCartVos()) {
//						for (Commodity m : o.getCoupon().getCommodities()) {
//							if (o1.getProduct().getCommodity().getId() == m.getId()) {
//								jia.add(o1.getProduct().getCurrentPrice());// CURRENT_PRICE
//							}
//						}
//						;
//					}
//					;
//					if (jia.intValue() >= o.getCoupon().getFullMoney().intValue()) {
//						o.setState(CartEnum.COUPONRECEIVES_STATE_YES.getCode());
//					}
//					jia.multiply(new BigDecimal(0));
//					continue r;
//				}
//				if (o.getCoupon().getUseConditionType() != CartEnum.COUPON_USE_CONDITION_TYPE_MONEY.getCode()
//						&& o.getCoupon().getCouponType() != CartEnum.COUPON_COUPON_TYPE.getCode()) {
//					o.setState(CartEnum.COUPONRECEIVES_STATE_YES.getCode());
//				}
//			}
//		}
//		;
//
//		for (CouponReceive couponReceive : couponReceives) {
//			if (couponReceive.getCoupon().getCouponType() == CartEnum.COUPON_COUPON_TYPE.getCode()) {
//				canUse.add(couponReceiveService.getCouponReceiveConvert().toVo(couponReceive));
//			}
//		}
//		/**
//		 * 筛选过期的优惠券
//		 */
//		Date current = new Date();
//		canUse.forEach(d -> {
//			if (!d.getStartTime().before(current) || !current.before(d.getEndTime())) {
//				d.setState(CartEnum.COUPONRECEIVES_STATE_NO.getCode());
//			}
//		});
//		return canUse;
//	}

	protected void initConvert() {
		this.cartConvert = new EntityListVoBoSimpleConvert<Cart, CartBo, CartVo, CartSimple, CartListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Cart, CartVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartVo>(beanConvertManager) {
					@Override
					protected void postConvert(CartVo CartVo, Cart Cart) {

					}
				};
			}

			@Override
			protected BeanConvert<Cart, CartListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CartListVo CartListVo, Cart Cart) {

					}
				};
			}

			@Override
			protected BeanConvert<Cart, CartBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartBo>(beanConvertManager) {
					@Override
					protected void postConvert(CartBo CartBo, Cart Cart) {

					}
				};
			}

			@Override
			protected BeanConvert<CartBo, Cart> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CartBo, Cart>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Cart, CartSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Cart, CartSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<CartSimple, Cart> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CartSimple, Cart>(beanConvertManager) {
					@Override
					public Cart convert(CartSimple CartSimple) throws BeansException {
						return cartDao.getOne(CartSimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

}
