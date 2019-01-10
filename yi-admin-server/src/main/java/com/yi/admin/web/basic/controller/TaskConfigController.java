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

import com.yi.core.basic.domain.bo.TaskConfigBo;
import com.yi.core.basic.domain.entity.TaskConfig;
import com.yi.core.basic.domain.vo.TaskConfigListVo;
import com.yi.core.basic.domain.vo.TaskConfigVo;
import com.yi.core.basic.service.ITaskConfigService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 任务配置
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/taskConfig")
public class TaskConfigController {

	private final Logger LOG = LoggerFactory.getLogger(TaskConfigController.class);

	@Resource
	private ITaskConfigService taskConfigService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<TaskConfigListVo> queryTaskConfig(@RequestBody Pagination<TaskConfig> query) {
		Page<TaskConfigListVo> page = taskConfigService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewTaskConfig(@RequestParam("id") int taskConfigId) {
		try {
			TaskConfigVo taskConfig = taskConfigService.getTaskConfigVoById(taskConfigId);
			return RestUtils.successWhenNotNull(taskConfig);
		} catch (BusinessException ex) {
			LOG.error("get TaskConfig failure : id=taskConfigId", ex);
			return RestUtils.error("get TaskConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addTaskConfig(@RequestBody TaskConfigBo taskConfig) {
		try {
			TaskConfigVo dbTaskConfig = taskConfigService.addTaskConfig(taskConfig);
			return RestUtils.successWhenNotNull(dbTaskConfig);
		} catch (BusinessException ex) {
			LOG.error("add TaskConfig failure : taskConfig", taskConfig, ex);
			return RestUtils.error("add TaskConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateTaskConfig(@RequestBody TaskConfigBo taskConfig) {
		try {
			TaskConfigVo dbTaskConfig = taskConfigService.updateTaskConfig(taskConfig);
			return RestUtils.successWhenNotNull(dbTaskConfig);
		} catch (BusinessException ex) {
			LOG.error("update TaskConfig failure : taskConfig", taskConfig, ex);
			return RestUtils.error("update TaskConfig failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeTaskConfigById(@RequestParam("id") int taskConfigId) {
		try {
			taskConfigService.removeTaskConfigById(taskConfigId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove TaskConfig failure : id=taskConfigId", ex);
			return RestUtils.error("remove TaskConfig failure : " + ex.getMessage());
		}
	}
}