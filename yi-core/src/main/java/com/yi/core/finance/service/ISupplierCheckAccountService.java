/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.finance.service;

import org.springframework.data.domain.Page;

import com.yi.core.finance.domain.bo.SupplierCheckAccountBo;
import com.yi.core.finance.domain.entity.SupplierCheckAccount;
import com.yi.core.finance.domain.vo.SupplierCheckAccountListVo;
import com.yi.core.finance.domain.vo.SupplierCheckAccountVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ISupplierCheckAccountService {

	/**
	 * 根据ID得到SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccount getSupplierCheckAccountById(int supplierCheckAccountId);

	/**
	 * 根据ID得到SupplierCheckAccountVo
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccountVo getSupplierCheckAccountVoById(int supplierCheckAccountId);

	/**
	 * 根据ID得到SupplierCheckAccountListVo
	 * 
	 * @param supplierCheckAccountId
	 * @return
	 */
	SupplierCheckAccountListVo getSupplierCheckAccountListVoById(int supplierCheckAccountId);

	/**
	 * 根据Entity创建SupplierCheckAccount
	 * 
	 * @param supplierCheckAccount
	 * @return
	 */
	SupplierCheckAccountVo addSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount);

	/**
	 * 根据BO创建SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountBo
	 * @return
	 */
	SupplierCheckAccountListVo addSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo);

	/**
	 * 根据Entity更新SupplierCheckAccount
	 * 
	 * @param supplierCheckAccount
	 * @return
	 */
	SupplierCheckAccount updateSupplierCheckAccount(SupplierCheckAccount supplierCheckAccount);

	/**
	 * 根据BO更新SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountBo
	 * @return
	 */
	SupplierCheckAccountListVo updateSupplierCheckAccount(SupplierCheckAccountBo supplierCheckAccountBo);

	/**
	 * 删除SupplierCheckAccount
	 * 
	 * @param supplierCheckAccountId
	 */
	void removeSupplierCheckAccountById(int supplierCheckAccountId);

	/**
	 * 分页查询: SupplierCheckAccount
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierCheckAccount> query(Pagination<SupplierCheckAccount> query);

	/**
	 * 分页查询: SupplierCheckAccountListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<SupplierCheckAccountListVo> queryListVo(Pagination<SupplierCheckAccount> query);

}
