/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yihz.common.persistence.Pagination;

/**
 * 商品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ICommodityService {

	/**
	 * 分页查询: Commodity
	 * 
	 * @param query
	 * @return
	 */
	Page<Commodity> query(Pagination<Commodity> query);

	/**
	 * 分页查询: CommodityListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<CommodityListVo> queryListVo(Pagination<Commodity> query);

	/**
	 * 根据Entity创建Commodity
	 * 
	 * @param commodity
	 * @return
	 */
	Commodity addCommodity(Commodity commodity);

	/**
	 * 根据BO创建Commodity
	 * 
	 * @param commodityBo
	 * @return
	 */
	CommodityVo addCommodity(CommodityBo commodityBo);

	/**
	 * 根据Entity更新Commodity
	 * 
	 * @param commodity
	 * @return
	 */
	Commodity updateCommodity(Commodity commodity);

	/**
	 * 修改商品销售数量
	 * 
	 * @param saleQuantity
	 * @param commodityId
	 * @return
	 */
	int updateCommodityBySaleQuantity(int saleQuantity, int commodityId);

	/**
	 * 修改商品评论数量
	 * 
	 * @param commodityId
	 * @return
	 */
	int updateCommodityByCommentQuantity(int commodityId);

	/**
	 * 根据BO更新Commodity
	 * 
	 * @param commodityBo
	 * @return
	 */
	CommodityVo updateCommodity(CommodityBo commodityBo);

	/**
	 * 删除Commodity
	 * 
	 * @param commodityId
	 */
	void removeCommodityById(int commodityId);

	/**
	 * 根据ID得到Commodity
	 * 
	 * @param commodityId
	 * @return
	 */
	Commodity getById(int commodityId);

	/**
	 * app商品详情根据ID得到CommodityVo
	 * 
	 * @param commodityId
	 * @return
	 */
	CommodityVo getVoById(int commodityId);
	
	/**
	 * app商品详情根据ID得到CommodityVo
	 * 
	 * @param commodityId
	 * @return
	 */
	CommodityVo getVoByIdForApp(int commodityId);

	/**
	 * 根据ID得到CommodityListVo
	 *
	 * @param commodityId
	 * @return
	 */
	CommodityListVo getListVoById(int commodityId);

	/**
	 * 供应商分页查询: CommodityListVo
	 *
	 * @param query
	 * @return
	 */
	Page<CommodityListVo> queryListVoForSupplier(Pagination<Commodity> query);

	/**
	 * 修改商品上下架状态
	 * 
	 * @param commodityId
	 * @return
	 */
	Commodity upAndDown(int commodityId);

	/**
	 * 商品重新申请上架
	 * 
	 * @param commodityId
	 * @return
	 */
	Commodity applyOnTheShelf(int commodityId);

	/**
	 * app商品展示
	 * 
	 * @param query
	 * @return
	 */
	Page<CommodityListVo> queryListVoForApp(Pagination<Commodity> query);

	/**
	 * 确认审核
	 */
	Commodity updateEnable(int id);

	/**
	 * 拒绝审核
	 */
	Commodity updateDisable(int id);

	/**
	 * 统计商品数量
	 * 
	 * @return
	 */
	int getCommodityNum();

	/**
	 * 统计供应商商品数量
	 *
	 * @return
	 */
	int getCommodityNumBySupplier(int supplierId);

	List<Commodity> getAllNotInId();

	/**
	 * 统计商品数量
	 * 
	 * @param deleted
	 * @param state
	 * @return
	 */
	int countByDeletedAndStateAndShelf(Integer deleted, Integer state, Integer shelf);

	/**
	 * 批量上架
	 *
	 * @param ids
	 * @return
	 */
	void batchUpperShelf(List<Integer> ids);

	/**
	 * 批量下架
	 * 
	 * @param ids
	 */
	void batchLowerShelf(List<Integer> ids);

	/**
	 * 添加代金券发放方案时 更新关联的商品
	 * 
	 * @param commodities
	 * @param couponGrantConfig
	 */
	void batchAddForVoucherGrant(Collection<Commodity> commodities, CouponGrantConfig couponGrantConfig);

	/**
	 * 修改代金券发放方案时 更新关联的商品
	 * 
	 * @param commodities
	 * @param couponGrantConfig
	 */
	void batchUpdateForVoucherGrant(Collection<Commodity> commodities, CouponGrantConfig couponGrantConfig);

}
