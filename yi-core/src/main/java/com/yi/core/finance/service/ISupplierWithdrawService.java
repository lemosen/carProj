/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.bo.SupplierWithdrawBo;
import com.yi.core.finance.domain.entity.SupplierWithdraw;
import com.yi.core.finance.domain.vo.SupplierWithdrawListVo;
import com.yi.core.finance.domain.vo.SupplierWithdrawVo;
import com.yihz.common.persistence.Pagination;

import java.math.BigDecimal;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierWithdrawService {

	/**
	 * 根据ID得到SupplierWithdraw
	 * 
	 * @param supplierWithdrawId
	 * @return
	 */
	SupplierWithdraw getSupplierWithdrawById(int supplierWithdrawId);

	/**
	 * 根据ID得到SupplierWithdrawVo
	 * 
	 * @param supplierWithdrawId
	 * @return
	 */
	SupplierWithdrawVo getSupplierWithdrawVoById(int supplierWithdrawId);

	/**
	 * 根据ID得到SupplierWithdrawListVo
	 * 
	 * @param supplierWithdrawId
	 * @return
	 */
	SupplierWithdrawListVo getSupplierWithdrawListVoById(int supplierWithdrawId);

	/**
	 * 根据Entity创建SupplierWithdraw
	 * 
	 * @param supplierWithdraw
	 * @return
	 */
	SupplierWithdrawVo addSupplierWithdraw(SupplierWithdraw supplierWithdraw);

	/**
	 * 根据BO创建SupplierWithdraw
	 * 
	 * @param supplierWithdrawBo
	 * @return
	 */
	SupplierWithdrawListVo addSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo);

	/**
	 * 根据Entity更新SupplierWithdraw
	 * 
	 * @param supplierWithdraw
	 * @return
	 */
	SupplierWithdrawVo updateSupplierWithdraw(SupplierWithdraw supplierWithdraw);

	/**
	 * 根据BO更新SupplierWithdraw
	 * 
	 * @param supplierWithdrawBo
	 * @return
	 */
	SupplierWithdrawListVo updateSupplierWithdraw(SupplierWithdrawBo supplierWithdrawBo);

	/**
	 * 删除SupplierWithdraw
	 * 
	 * @param supplierWithdrawId
	 */
	void removeSupplierWithdrawById(int supplierWithdrawId);

	/**
	 * 分页查询: SupplierWithdraw
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierWithdraw> query(Pagination<SupplierWithdraw> query);

	/**
	 * 分页查询: SupplierWithdrawListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierWithdrawListVo> queryListVo(Pagination<SupplierWithdraw> query);


	/*
	* 发放
	* */
	void grant(int supplierWithdrawId);


	/*
	 * 驳回
	 * */
	void reject(int supplierWithdrawId);

	/**
	 * 查询申请状态供应商
	 * @param state
	 * @return
	 */
	int countByState(Integer state);

	/**
	 * 查询供应商待结算资金
	 * @param state
	 * @return
	 */
	BigDecimal waitSettlement(Integer state);

	/**
	 * 查询供应商后台待提现数量
	 * @param supplierId
	 * @return
	 */
	int withdrawNum(int supplierId);

}
