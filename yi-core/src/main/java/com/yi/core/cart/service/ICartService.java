/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cart.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.cart.domain.bo.CartBo;
import com.yi.core.cart.domain.entity.Cart;
import com.yi.core.cart.domain.vo.CartListVo;
import com.yi.core.cart.domain.vo.CartVo;
import com.yihz.common.persistence.Pagination;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICartService {

	/**
	 * 分页查询: Cart
	 *
	 * @param query
	 * @return
	 */
	Page<Cart> query(Pagination<Cart> query);

	/**
	 * 分页查询: CartListVo
	 *
	 * @param query
	 * @return
	 */
	Page<CartListVo> queryListVo(Pagination<Cart> query);

	/**
	 * 根据Entity创建Cart
	 *
	 * @param cart
	 * @return
	 */
	Cart addCart(Cart cart);

	/**
	 * 根据BO创建Cart
	 *
	 * @param cartBo
	 * @return
	 */
	CartVo addCart(CartBo cartBo);

	/**
	 * 根据Entity更新Cart
	 *
	 * @param cart
	 * @return
	 */
	Cart updateCart(Cart cart);

	/**
	 * 根据BO更新Cart
	 *
	 * @param cartBo
	 * @return
	 */
	CartVo updateCart(CartBo cartBo);

	/**
	 * 删除Cart
	 *
	 * @param cartId
	 */
	void removeCartById(int cartId);

	/**
	 * 根据ID得到Cart
	 *
	 * @param cartId
	 * @return
	 */
	Cart getCartById(int cartId);

	/**
	 * 根据ID得到CartVo
	 *
	 * @param cartId
	 * @return
	 */
	CartVo getCartVoById(int cartId);

	/**
	 * 根据ID得到CartListVo
	 *
	 * @param cartId
	 * @return
	 */
	CartListVo getCartListVoById(int cartId);

	/**
	 * 根据用户Id获取购物车
	 *
	 * @param memberId
	 * @return
	 */
	List<CartListVo> getShopCarts(int memberId);

	/**
	 * 修改购物车 货品数量
	 *
	 * @param cartId
	 * @param num
	 * @return
	 */
	CartVo changeShopCartNum(int cartId, int num);

	/**
	 * 添加货品到购物车
	 *
	 * @param memberId
	 * @param productId
	 * @param num
	 * @return
	 */
	CartVo addProductToCart(int memberId, int productId, int num);

//	/**
//	 * 提交优惠券订单分类
//	 * 
//	 * @param saleOrderBo
//	 * @return
//	 */
//	Set<CouponReceiveVo> judgeCouponReceive(SaleOrderBo saleOrderBo);

	/**
	 * 批量删除购物车数据
	 *
	 * @param cartVos
	 */
	void batchDeleteByOrder(List<CartVo> cartVos);
}
