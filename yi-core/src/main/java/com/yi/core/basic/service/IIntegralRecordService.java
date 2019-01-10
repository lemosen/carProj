/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.basic.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;

import com.yi.core.basic.BasicEnum;
import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.member.domain.entity.Account;
import com.yi.core.member.domain.entity.Member;
import com.yihz.common.persistence.Pagination;

/**
 * *
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
 */
public interface IIntegralRecordService {

	Page<IntegralRecord> query(Pagination<IntegralRecord> query);

	/**
	 * 创建IntegralRecordBo
	 **/
	IntegralRecordVo addIntegralRecord(IntegralRecordBo integralRecord);

	/**
	 * 创建IntegralRecord
	 **/
	IntegralRecordVo addIntegralRecord(IntegralRecord integralRecord);

	/**
	 * 更新IntegralRecord
	 **/
	IntegralRecordVo updateIntegralRecord(IntegralRecordBo integralRecord);

	/**
	 * 删除IntegralRecord
	 **/
	void removeIntegralRecordById(int integralRecordId);

	/**
	 * 根据ID得到IntegralRecordVo
	 **/
	IntegralRecordVo getIntegralRecordVoById(int integralRecordId);

	/**
	 * 根据ID得到IntegralRecordListVo
	 **/
	IntegralRecordVo getListVoById(int integralRecordId);

	/**
	 * 分页查询: IntegralRecord
	 **/
	Page<IntegralRecordListVo> queryListVo(Pagination<IntegralRecord> query);

	/**
	 * 根据任务类型新增积分
	 * 
	 * @param memberId
	 * @param taskType
	 */
	void addIntegralRecordByTaskType(Integer memberId, BasicEnum taskType);
	
	/**
	 * 根据任务类型新增积分
	 * 
	 * @param member
	 * @param taskType
	 */
	void addIntegralRecordByTaskType(Member member, BasicEnum taskType);

	/**
	 * 订单相关 增加积分
	 * 
	 * @param member
	 * @param orderIntegral
	 * @param taskName
	 */
	Account addIntegralRecordByOrder(Member member, BigDecimal orderIntegral, BasicEnum taskType);

}
