/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import com.yi.core.basic.domain.bo.IntegralRecordBo;
import com.yi.core.basic.domain.entity.IntegralRecord;
import com.yi.core.basic.domain.vo.IntegralRecordListVo;
import com.yi.core.basic.domain.vo.IntegralRecordVo;
import com.yi.core.basic.service.IIntegralRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/integralRecord")
public class IntegralRecordController {

	private final Logger LOG = LoggerFactory.getLogger(IntegralRecordController.class);

	@Resource
	private IIntegralRecordService integralRecordService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<IntegralRecordListVo> queryIntegralRecord(@RequestBody Pagination<IntegralRecord> query) {
		Page<IntegralRecordListVo> page = integralRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewIntegralRecord(@RequestParam("id") int integralRecordId) {
		try {
			IntegralRecordVo integralRecord = integralRecordService.getIntegralRecordVoById(integralRecordId);
			return RestUtils.successWhenNotNull(integralRecord);
		} catch (BusinessException ex) {
			LOG.error("get IntegralRecord failure : id=integralRecordId", ex);
			return RestUtils.error("get IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addIntegralRecord(@RequestBody IntegralRecordBo integralRecord) {
		try {
			IntegralRecordVo dbIntegralRecord = integralRecordService.addIntegralRecord(integralRecord);
			return RestUtils.successWhenNotNull(dbIntegralRecord);
		} catch (BusinessException ex) {
			LOG.error("add IntegralRecord failure : integralRecord", integralRecord, ex);
			return RestUtils.error("add IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateIntegralRecord(@RequestBody IntegralRecordBo integralRecord) {
		try {
			IntegralRecordVo dbIntegralRecord = integralRecordService.updateIntegralRecord(integralRecord);
			return RestUtils.successWhenNotNull(dbIntegralRecord);
		} catch (BusinessException ex) {
			LOG.error("update IntegralRecord failure : integralRecord", integralRecord, ex);
			return RestUtils.error("update IntegralRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeIntegralRecordById(@RequestParam("id") int integralRecordId) {
		try {
			integralRecordService.removeIntegralRecordById(integralRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove IntegralRecord failure : id=integralRecordId", ex);
			return RestUtils.error("remove IntegralRecord failure : " + ex.getMessage());
		}
	}
}