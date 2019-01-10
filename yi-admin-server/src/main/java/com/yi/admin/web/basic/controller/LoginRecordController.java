/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.basic.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.basic.domain.bo.LoginRecordBo;
import com.yi.core.basic.domain.entity.LoginRecord;
import com.yi.core.basic.domain.vo.LoginRecordListVo;
import com.yi.core.basic.domain.vo.LoginRecordVo;
import com.yi.core.basic.service.ILoginRecordService;
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
@RequestMapping(value = "/loginRecord")
public class LoginRecordController {

	private final Logger LOG = LoggerFactory.getLogger(LoginRecordController.class);

	@Resource
	private ILoginRecordService loginRecordService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<LoginRecord> queryLoginRecord(@RequestBody Pagination<LoginRecord> query) {
		Page<LoginRecord> page = loginRecordService.query(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewLoginRecord(@RequestParam("id") int loginRecordId) {
		try {
			LoginRecordVo loginRecord = loginRecordService.getLoginRecordVoById(loginRecordId);
			return RestUtils.successWhenNotNull(loginRecord);
		} catch (BusinessException ex) {
			LOG.error("get LoginRecord failure : id=loginRecordId", ex);
			return RestUtils.error("get LoginRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addLoginRecord(@RequestBody LoginRecordBo loginRecord) {
		try {
			LoginRecordVo dbLoginRecord = loginRecordService.addLoginRecord(loginRecord);
			return RestUtils.successWhenNotNull(dbLoginRecord);
		} catch (BusinessException ex) {
			LOG.error("add LoginRecord failure : loginRecord", loginRecord, ex);
			return RestUtils.error("add LoginRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateLoginRecord(@RequestBody LoginRecordBo loginRecord) {
		try {
			LoginRecordVo dbLoginRecord = loginRecordService.updateLoginRecord(loginRecord);
			return RestUtils.successWhenNotNull(dbLoginRecord);
		} catch (BusinessException ex) {
			LOG.error("update LoginRecord failure : loginRecord", loginRecord, ex);
			return RestUtils.error("update LoginRecord failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeLoginRecordById(@RequestParam("id") int loginRecordId) {
		try {
			loginRecordService.removeLoginRecordById(loginRecordId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove LoginRecord failure : id=loginRecordId", ex);
			return RestUtils.error("remove LoginRecord failure : " + ex.getMessage());
		}
	}
}