/**Powered By[yihz-framework]*Web Site:yihz*Since 2018-2018*/

package com.yi.supplier.web.auth.controller;

import javax.annotation.Resource;

import com.yi.core.auth.domain.bo.DeptBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.auth.domain.entity.Dept;
import com.yi.core.auth.domain.vo.DeptListVo;
import com.yi.core.auth.domain.vo.DeptVo;
import com.yi.core.auth.service.IDeptService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 部门
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

	private final Logger LOG = LoggerFactory.getLogger(DeptController.class);

	@Resource
	private IDeptService deptService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<DeptListVo> queryDept(@RequestBody Pagination<Dept> query) {
		Page<DeptListVo> page = deptService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewDept(@RequestParam("id") int deptId) {
		try {
			DeptVo dept = deptService.getDeptVoById(deptId);
			return RestUtils.successWhenNotNull(dept);
		} catch (BusinessException ex) {
			LOG.error("get Dept failure : id=deptId", ex);
			return RestUtils.error("get Dept failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addDept(@RequestBody DeptBo dept) {
		try {
			DeptVo dbDept = deptService.addDept(dept);
			return RestUtils.successWhenNotNull(dbDept);
		} catch (BusinessException ex) {
			LOG.error("add Dept failure : dept", dept, ex);
			return RestUtils.error("add Dept failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateDept(@RequestBody DeptBo deptBo) {
		try {
			DeptVo dbDept = deptService.updateDept(deptBo);
			return RestUtils.successWhenNotNull(dbDept);
		} catch (BusinessException ex) {
			LOG.error("update Dept failure : dept", deptBo, ex);
			return RestUtils.error("update Dept failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeDeptById(@RequestParam("id") int deptId) {
		try {
			deptService.removeDeptById(deptId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Dept failure : id=deptId", ex);
			return RestUtils.error("remove Dept failure : " + ex.getMessage());
		}
	}
}