/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yi.core.cms.domain.bo.RecommendBo;
import com.yi.core.cms.domain.entity.Recommend;
import com.yi.core.cms.domain.vo.RecommendListVo;
import com.yi.core.cms.domain.vo.RecommendVo;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
public interface IRecommendService {

	/**
	 * 根据ID得到Recommend
	 * 
	 * @param recommendId
	 * @return
	 */
	Recommend getRecommendById(int recommendId);

	/**
	 * 根据ID得到RecommendVo
	 * 
	 * @param recommendId
	 * @return
	 */
	RecommendVo getRecommendVoById(int recommendId);

	/**
	 * 根据ID得到RecommendListVo
	 * 
	 * @param recommendId
	 * @return
	 */
	RecommendVo getRecommendListVoById(int recommendId);

	/**
	 * 根据Entity创建Recommend
	 * 
	 * @param recommend
	 * @return
	 */
	Recommend addRecommend(Recommend recommend);

	/**
	 * 根据BO创建Recommend
	 * 
	 * @param recommendBo
	 * @return
	 */
	RecommendVo addRecommend(RecommendBo recommendBo);

	/**
	 * 根据Entity更新Recommend
	 * 
	 * @param recommend
	 * @return
	 */
	Recommend updateRecommend(Recommend recommend);

	/**
	 * 根据BO更新Recommend
	 * 
	 * @param recommendBo
	 * @return
	 */
	RecommendVo updateRecommend(RecommendBo recommendBo);

	/**
	 * 删除Recommend
	 * 
	 * @param recommendId
	 */
	void removeRecommendById(int recommendId);

	/**
	 * 分页查询: Recommend
	 * 
	 * @param query
	 * @return
	 */
	Page<Recommend> query(Pagination<Recommend> query);

	/**
	 * 分页查询: RecommendListVo
	 * 
	 * @param query
	 * @return
	 */
	Page<RecommendListVo> queryListVo(Pagination<Recommend> query);

	/**
	 * 分页查询: RecommendListVo
	 * 
	 * @param query
	 * @return
	 */
	List<RecommendVo> queryVoForApp(Integer positionType, String city);

	/**
	 * 禁用
	 * 
	 * @return
	 */
	RecommendVo updateStateDisable(int recommendId);

	/**
	 * 启用
	 * 
	 * @return
	 */
	RecommendVo updateStateEnable(int recommendId);

	/**
	 * 根据位置获取推荐数据
	 * 
	 * @param positionType
	 * @return
	 */
	List<RecommendVo> getRecommendsByPositionType(Integer positionType, String city);

	/**
	 * 根据位置获取推荐数据
	 * 
	 * @param positionType
	 * @param city
	 * @return
	 */
	List<RecommendVo> queryRecommends(Integer positionType, String city);

	/**
	 * 根据商品id获取推荐位
	 * 
	 * @param id
	 * @return
	 */
	List<RecommendVo> getRecommendsByCommodityId(Integer id, Integer type);
}
