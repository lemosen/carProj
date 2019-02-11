package com.yi.core.common;

import org.springframework.data.domain.Page;

import com.yihz.common.persistence.Pagination;

/**
 * 
 * @author xuyh
 *
 * @param <T>
 *            entity
 * @param <S>
 *            simple
 * @param <V>
 *            vo
 * @param <L>
 *            listVo
 * @param <B>
 *            bo
 * @param <ID>
 *            ID
 */
public interface IBaseService<T, S, V, L, B, ID> {

	Page<T> query(Pagination<T> query);

	Page<L> queryListVo(Pagination<T> query);

	T add(T t);

	V addByBo(B b);

	T update(T t);

	V updateByBo(V b);

	T getById(ID id);

	V getVoById(ID id);

	L getListVoById(ID id);

}
