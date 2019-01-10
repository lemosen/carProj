/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yi.core.commodity.domain.entity.Commodity;

/**
 * 商品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface CommodityDao extends JpaRepository<Commodity, Integer>, JpaSpecificationExecutor<Commodity> {

	// 获取商品数量
	int countByShelf(int Shelf);

	/**
	 * 统计商品数量
	 * 
	 * @param deleted
	 * @param state
	 * @return
	 */
	int countByDeletedAndStateAndShelf(Integer deleted, Integer state, Integer Shelf);

	/**
	 * 统计商品数量
	 *
	 * @param deleted
	 * @param state
	 * @return
	 */
	int countByDeletedAndStateAndShelfAndSupplierId(Integer deleted, Integer state, Integer Shelf, Integer supplierId);

	/**
	 * 查询没有添加买送券的商品
	 * 
	 * @param commodityId
	 * @return
	 */
	@Query(value = "select * from Commodity c where c.id not in(?1)", nativeQuery = true)
	List<Commodity> findNoGive(int[] commodityId);

	Collection<Commodity> findByIdNotIn(int[] commodityId);

	List<Commodity> findByShelfAndDeleted(int shelf, int deleted);

	/**
	 * 校验编码或名称
	 * 
	 * @param code
	 * @param name
	 * @param deleted
	 * @return
	 */
	@Query("select count(*) from Commodity t1 where (t1.commodityNo=?1 or t1.commodityName=?2) and t1.deleted=?3")
	int countByCommodityNoOrCommodityNameAndDeleted(String code, String name, Integer deleted);

	/**
	 * 校验编码或名称
	 * 
	 * @param code
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	@Query("select count(*) from Commodity t1 where (t1.commodityNo=?1 or t1.commodityName=?2) and t1.deleted=?3 and " + "t1.id<>?4")
	int countByCommodityNoOrCommodityNameAndDeletedAndIdNot(String code, String name, Integer deleted, Integer id);

	/**
	 * 校验编码或名称
	 * 
	 * @param code
	 * @param name
	 * @param deleted
	 * @return
	 */
	@Query("select count(*) from Commodity t1 left join t1.supplier t2 where t2.id=?1 and (t1.commodityNo=?2 or t1.commodityName=?3) and t1.deleted=?4")
	int countBySupplierIdAndCommodityNoOrCommodityNameAndDeleted(Integer supplierId, String code, String name, Integer deleted);

	/**
	 * 校验编码或名称
	 * 
	 * @param code
	 * @param name
	 * @param deleted
	 * @param id
	 * @return
	 */
	@Query("select count(*) from Commodity t1 left join t1.supplier t2 where t2.id=?1 and (t1.commodityNo=?2 or t1.commodityName=?3) and t1.deleted=?4 and t1.id<>?5")
	int countBySupplierIdAndCommodityNoOrCommodityNameAndDeletedAndIdNot(Integer supplierId, String code, String name, Integer deleted, Integer id);

	/**
	 * 修改商品销售数量
	 * 
	 * @param saleQuantity
	 * @param commodityId
	 * @return
	 */
	@Modifying
	@Query("update Commodity set saleQuantity=saleQuantity+?1 where id=?2")
	int updateCommodityBySaleQuantity(int saleQuantity, int commodityId);

	/**
	 * 修改商品评价数量
	 * 
	 * @param commodityId
	 * @return
	 */
	@Modifying
	@Query("update Commodity set commentQuantity=commentQuantity+1 where id=?1")
	int updateCommodityByCommentQuantity(int commodityId);

	/**
	 * 查询该发放方案的商品
	 * 
	 * @param grantConfigId
	 * @return
	 */
	List<Commodity> findByCouponGrantConfig_id(Integer grantConfigId);

}