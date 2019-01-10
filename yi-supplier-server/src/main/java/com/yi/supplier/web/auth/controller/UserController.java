/**Powered By[yihz-framework]*Web Site:yihz*Since 2018-2018*/

package com.yi.supplier.web.auth.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yi.core.auth.service.IUserService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 用户
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Resource
	private IUserService userService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<UserListVo> queryUser(@RequestBody Pagination<User> query) {
		Page<UserListVo> page = userService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewUser(@RequestParam("id") int userId) {
		try {
			UserVo user = userService.getUserVoById(userId);
			return RestUtils.successWhenNotNull(user);
		} catch (BusinessException ex) {
			LOG.error("get User failure : id=userId", ex);
			return RestUtils.error("get User failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addUser(@RequestBody User user) {
		try {
			User dbUser = userService.addUser(user);
			return RestUtils.successWhenNotNull(dbUser);
		} catch (BusinessException ex) {
			LOG.error("add User failure : user", user, ex);
			return RestUtils.error("add User failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateUser(@RequestBody User user) {
		try {
			User dbUser = userService.updateUser(user);
			return RestUtils.successWhenNotNull(dbUser);
		} catch (BusinessException ex) {
			LOG.error("update User failure : user", user, ex);
			return RestUtils.error("update User failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeUserById(@RequestParam("id") int userId) {
		try {
			userService.removeUserById(userId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove User failure : id=userId", ex);
			return RestUtils.error("remove User failure : " + ex.getMessage());
		}
	}
}