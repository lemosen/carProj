/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.member.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.member.domain.entity.ConsumeRecord;
import com.yi.core.member.domain.vo.ConsumeRecordListVo;
import com.yi.core.member.domain.vo.ConsumeRecordVo;
import com.yi.core.member.service.IConsumeRecordService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/consumeRecord")
public class ConsumeRecordController {

	private final Logger LOG = LoggerFactory.getLogger(ConsumeRecordController.class);

	@Resource
	private IConsumeRecordService consumeRecordService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ConsumeRecordListVo> queryConsumeRecord(@RequestBody Pagination<ConsumeRecord> query) {
		Page<ConsumeRecordListVo> page = consumeRecordService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewConsumeRecord(@RequestParam("id") int consumeRecordId) {
		try {
			ConsumeRecordVo consumeRecord = consumeRecordService.getConsumeRecordVoById(consumeRecordId);
			return RestUtils.successWhenNotNull(consumeRecord);
		} catch (BusinessException ex) {
			LOG.error("get ConsumeRecord failure : id=consumeRecordId", ex);
			return RestUtils.error("get ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addConsumeRecord(@RequestBody ConsumeRecord consumeRecord) {
		try {
			ConsumeRecordVo dbConsumeRecord = consumeRecordService.addConsumeRecord(consumeRecord);
			return RestUtils.successWhenNotNull(dbConsumeRecord);
		} catch (BusinessException ex) {
			LOG.error("add ConsumeRecord failure : consumeRecord", consumeRecord, ex);
			return RestUtils.error("add ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateConsumeRecord(@RequestBody ConsumeRecord consumeRecord) {
		try {
			ConsumeRecordVo dbConsumeRecord = consumeRecordService.updateConsumeRecord(consumeRecord);
			return RestUtils.successWhenNotNull(dbConsumeRecord);
		} catch (BusinessException ex) {
			LOG.error("update ConsumeRecord failure : consumeRecord", consumeRecord, ex);
			return RestUtils.error("update ConsumeRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeConsumeRecordById(@RequestParam("id") int consumeRecordId) {
		try {
			consumeRecordService.removeConsumeRecordById(consumeRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ConsumeRecord failure : id=consumeRecordId", ex);
			return RestUtils.error("remove ConsumeRecord failure : " + ex.getMessage());
		}
	}
}