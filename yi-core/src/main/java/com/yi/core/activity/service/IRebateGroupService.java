/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.RebateGroupBo;
import com.yi.core.activity.domain.entity.RebateGroup;
import com.yi.core.activity.domain.vo.RebateGroupListVo;
import com.yi.core.activity.domain.vo.RebateGroupVo;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;
import java.io.*;
import java.net.*;
import java.util.*;

/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface IRebateGroupService {



   Page<RebateGroup> query(Pagination<RebateGroup> query);

    /**
    * 创建RebateGroup
    **/
    RebateGroupVo addRebateGroup(RebateGroupBo rebateGroup);

    /**
    * 更新RebateGroup
    **/
    RebateGroupVo updateRebateGroup(RebateGroupBo rebateGroup);

    /**
    * 删除RebateGroup
    **/
    void removeRebateGroupById(int rebateGroupId);

    /**
    * 根据ID得到RebateGroupVo
    **/
    RebateGroupVo getRebateGroupVoById(int rebateGroupId);

    /**
    * 根据ID得到RebateGroupVo
    **/
    RebateGroupVo getListVoById(int rebateGroupId);

    /**
    * 分页查询: RebateGroup
    **/
    Page<RebateGroupListVo> queryListVo(Pagination<RebateGroup> query);



}
