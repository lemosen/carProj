/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import org.springframework.data.domain.Page;

import com.yi.core.basic.domain.bo.LoginRecordBo;
import com.yi.core.basic.domain.entity.LoginRecord;
import com.yi.core.basic.domain.vo.LoginRecordListVo;
import com.yi.core.basic.domain.vo.LoginRecordVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface ILoginRecordService {

	/**
	 * 根据ID得到LoginRecord
	 * 
	 * @param loginRecordId
	 * @return
	 */
	LoginRecord getLoginRecordById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecordVo
	 * 
	 * @param loginRecordId
	 * @return
	 */
	LoginRecordVo getLoginRecordVoById(int loginRecordId);

	/**
	 * 根据ID得到LoginRecordListVo
	 * 
	 * @param loginRecordId
	 * @return
	 */
	LoginRecordListVo getLoginRecordListVoById(int loginRecordId);

	/**
	 * 根据Entity创建LoginRecord
	 * 
	 * @param loginRecord
	 * @return
	 */
	LoginRecord addLoginRecord(LoginRecord loginRecord);

	/**
	 * 根据BO创建LoginRecord
	 * 
	 * @param loginRecordBo
	 * @return
	 */
	LoginRecordVo addLoginRecord(LoginRecordBo loginRecordBo);

	/**
	 * 根据Entity更新LoginRecord
	 * 
	 * @param loginRecord
	 * @return
	 */
	LoginRecord updateLoginRecord(LoginRecord loginRecord);

	/**
	 * 根据BO更新LoginRecord
	 * 
	 * @param loginRecordBo
	 * @return
	 */
	LoginRecordVo updateLoginRecord(LoginRecordBo loginRecordBo);

	/**
	 * 删除LoginRecord
	 * 
	 * @param loginRecordId
	 */
	void removeLoginRecordById(int loginRecordId);

	/**
	 * 分页查询: LoginRecord
	 * 
	 * @param query
	 * @return
	 */
	Page<LoginRecord> query(Pagination<LoginRecord> query);

	/**
	 * 分页查询: LoginRecordListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<LoginRecordListVo> queryListVo(Pagination<LoginRecord> query);

}
