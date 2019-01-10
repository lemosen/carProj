/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.NationalGroupBo;
import com.yi.core.activity.domain.entity.NationalGroup;
import com.yi.core.activity.domain.vo.NationalGroupListVo;
import com.yi.core.activity.domain.vo.NationalGroupVo;

import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.commodity.domain.entity.Stock;
import com.yi.core.common.Deleted;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * *
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 */
public interface INationalGroupService {


    Page<NationalGroup> query(Pagination<NationalGroup> query);

    /**
     * 创建NationalGroup
     **/
    NationalGroupVo addNationalGroup(NationalGroupBo nationalGroup);

    /**
     * 更新NationalGroup
     **/
    NationalGroupVo updateNationalGroup(NationalGroupBo nationalGroup);

    /**
     * 删除NationalGroup
     **/
    void removeNationalGroupById(int nationalGroupId);

    /**
     * 根据ID得到NationalGroupVo
     **/
    NationalGroupVo getNationalGroupVoById(int nationalGroupId);

    /**
     * 根据ID得到NationalGroupListVo
     **/
    NationalGroupVo getListVoById(int nationalGroupId);

    /**
     * 分页查询: NationalGroup
     **/
    Page<NationalGroupListVo> queryListVo(Pagination<NationalGroup> query);


    /**
     * 分页app查询
     * @param query
     * @return
     */
    Page<NationalGroupListVo> queryListVoApp(Pagination<NationalGroup> query);






    NationalGroupVo goGroupPurchase(int commodityId);

    /**
     * 查询昨天全国拼团已完成
     * @return
     */
    List<NationalGroupListVo> yesterdayPurchase();


    /**
     * 团购检查库存
     */
    boolean groupBuyCheckStock(Integer nationalGroupId, int quantity);

}
