/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.bo.PlatformSaleStatBo;
import com.yi.core.finance.domain.entity.PlatformSaleStat;
import com.yi.core.finance.domain.vo.PlatformSaleStatListVo;
import com.yi.core.finance.domain.vo.PlatformSaleStatVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IPlatformSaleStatService {

	/**
	 * 根据ID得到PlatformSaleStat
	 * 
	 * @param platformSaleStatId
	 * @return
	 */
	PlatformSaleStat getPlatformSaleStatById(int platformSaleStatId);

	/**
	 * 根据ID得到PlatformSaleStatVo
	 * 
	 * @param platformSaleStatId
	 * @return
	 */
	PlatformSaleStatVo getPlatformSaleStatVoById(int platformSaleStatId);

	/**
	 * 根据ID得到PlatformSaleStatListVo
	 * 
	 * @param platformSaleStatId
	 * @return
	 */
	PlatformSaleStatListVo getPlatformSaleStatListVoById(int platformSaleStatId);

	/**
	 * 根据Entity创建PlatformSaleStat
	 * 
	 * @param platformSaleStat
	 * @return
	 */
	PlatformSaleStatVo addPlatformSaleStat(PlatformSaleStat platformSaleStat);

	/**
	 * 根据BO创建PlatformSaleStat
	 * 
	 * @param platformSaleStatBo
	 * @return
	 */
	PlatformSaleStatListVo addPlatformSaleStat(PlatformSaleStatBo platformSaleStatBo);

	/**
	 * 根据Entity更新PlatformSaleStat
	 * 
	 * @param platformSaleStat
	 * @return
	 */
	PlatformSaleStatVo updatePlatformSaleStat(PlatformSaleStat platformSaleStat);

	/**
	 * 根据BO更新PlatformSaleStat
	 * 
	 * @param platformSaleStatBo
	 * @return
	 */
	PlatformSaleStatListVo updatePlatformSaleStat(PlatformSaleStatBo platformSaleStatBo);

	/**
	 * 删除PlatformSaleStat
	 * 
	 * @param platformSaleStatId
	 */
	void removePlatformSaleStatById(int platformSaleStatId);

	/**
	 * 分页查询: PlatformSaleStat
	 * 
	 * @param query
	 * @return
	 */
	Page<PlatformSaleStat> query(Pagination<PlatformSaleStat> query);

	/**
	 * 分页查询: PlatformSaleStatListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<PlatformSaleStatListVo> queryListVo(Pagination<PlatformSaleStat> query);

}
