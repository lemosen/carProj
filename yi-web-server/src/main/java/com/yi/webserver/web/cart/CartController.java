/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.webserver.web.cart;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cart.service.ICartService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 购物车
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/cart")
public class CartController {

	private final Logger LOG = LoggerFactory.getLogger(CartController.class);

	@Resource
	private ICartService cartService;

	/**
	 * 获取购物车信息
	 *
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "getShopCarts", method = RequestMethod.GET)
	public RestResult getShopCarts(@RequestParam("memberId") int memberId) {
		try {
			return RestUtils.success(cartService.getShopCarts(memberId));
		} catch (Exception e) {
			LOG.error("getShopCarts error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 添加商品进购物车
	 *
	 * @param memberId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "addShopCart", method = RequestMethod.GET)
	public RestResult addShopCart(@RequestParam("memberId") int memberId, @RequestParam("productId") int productId, @RequestParam("num") int num) {
		try {
			return RestUtils.success(cartService.addProductToCart(memberId, productId, num));
		} catch (Exception e) {
			LOG.error("addShopCart error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 删除商品从购物车
	 *
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "removeShopCart", method = RequestMethod.GET)
	public RestResult removeShopCart(@RequestParam("shoppingCartProductId") int cartId) {
		try {
			cartService.removeCartById(cartId);
			return RestUtils.success(true);
		} catch (Exception e) {
			LOG.error("removeShopCart error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	/**
	 * 修改商品数量从购物车
	 *
	 * @param cartId
	 * @return
	 */
	@RequestMapping(value = "changeShopCartNum", method = RequestMethod.GET)
	public RestResult changeShopCartNum(@RequestParam("shoppingCartProductId") int cartId, @RequestParam("num") int num) {
		try {
			return RestUtils.success(cartService.changeShopCartNum(cartId, num));
		} catch (Exception e) {
			LOG.error("change ShopCart Number error:{}", e.getMessage(), e);
			return RestUtils.error(e.getMessage());
		}
	}

	// /**
	// * 分页查询
	// */
	// @RequestMapping(value = "query", method = RequestMethod.POST)
	// public Page<CartListVo> queryCart(@RequestBody Pagination<Cart> query) {
	// Page<CartListVo> page = cartService.queryListVo(query);
	// return page;
	// }

}