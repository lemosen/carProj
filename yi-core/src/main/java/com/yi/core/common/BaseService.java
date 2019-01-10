package com.yi.core.common;

import org.springframework.data.domain.Page;

import com.yi.core.auth.domain.bo.UserBo;
import com.yi.core.auth.domain.entity.User;
import com.yi.core.auth.domain.simple.UserSimple;
import com.yi.core.auth.domain.vo.UserListVo;
import com.yi.core.auth.domain.vo.UserVo;
import com.yihz.common.persistence.Pagination;

public class BaseService implements IBaseService<User, UserSimple, UserVo, UserListVo, UserBo, Integer>{

	@Override
	public Page<User> query(Pagination<User> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserListVo> queryListVo(Pagination<User> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User add(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo addByBo(UserBo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo updateByBo(UserVo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo getVoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserListVo getListVoById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
