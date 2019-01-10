/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.bo.SupplierSaleStatsBo;
import com.yi.core.finance.domain.entity.SupplierSaleStats;
import com.yi.core.finance.domain.vo.SupplierSaleStatsListVo;
import com.yi.core.finance.domain.vo.SupplierSaleStatsVo;
import com.yihz.common.persistence.Pagination;

/**
 * 供应商销售统计
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierSaleStatsService {

	/**
	 * 根据ID得到SupplierSaleStat
	 * 
	 * @param supplierSaleStatId
	 * @return
	 */
	SupplierSaleStats getById(int supplierSaleStatId);

	/**
	 * 根据ID得到SupplierSaleStatVo
	 * 
	 * @param supplierSaleStatId
	 * @return
	 */
	SupplierSaleStatsVo getVoById(int supplierSaleStatId);

	/**
	 * 根据ID得到SupplierSaleStatListVo
	 * 
	 * @param supplierSaleStatId
	 * @return
	 */
	SupplierSaleStatsListVo getListVoById(int supplierSaleStatId);

	/**
	 * 根据Entity创建SupplierSaleStat
	 * 
	 * @param supplierSaleStats
	 * @return
	 */
	SupplierSaleStats addSupplierSaleStat(SupplierSaleStats supplierSaleStats);

	/**
	 * 根据BO创建SupplierSaleStat
	 * 
	 * @param supplierSaleStatsBo
	 * @return
	 */
	SupplierSaleStatsListVo addSupplierSaleStat(SupplierSaleStatsBo supplierSaleStatsBo);

	/**
	 * 根据Entity更新SupplierSaleStat
	 * 
	 * @param supplierSaleStats
	 * @return
	 */
	SupplierSaleStats updateSupplierSaleStat(SupplierSaleStats supplierSaleStats);

	/**
	 * 根据BO更新SupplierSaleStat
	 * 
	 * @param supplierSaleStatsBo
	 * @return
	 */
	SupplierSaleStatsListVo updateSupplierSaleStat(SupplierSaleStatsBo supplierSaleStatsBo);

	/**
	 * 删除SupplierSaleStat
	 * 
	 * @param supplierSaleStatId
	 */
	void removeSupplierSaleStatById(int supplierSaleStatId);

	/**
	 * 分页查询: SupplierSaleStat
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierSaleStats> query(Pagination<SupplierSaleStats> query);

	/**
	 * 分页查询: SupplierSaleStatListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierSaleStatsListVo> queryListVo(Pagination<SupplierSaleStats> query);

	/**
	 * 供应商销售合计
	 * 
	 * @return
	 */
	SupplierSaleStatsVo getTotalSaleStats();

	/**
	 * 供应商销售集合
	 * 
	 * @param query
	 * @return
	 */
	List<SupplierSaleStatsListVo> getSaleStatsList(Pagination<SupplierSaleStatsBo> query) throws Exception;

	List<Object[]> getSupplierGrouping(String startTime, String endTime);


}
