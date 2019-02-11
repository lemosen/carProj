/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.RebateRecordBo;
import com.yi.core.activity.domain.entity.RebateRecord;
import com.yi.core.activity.domain.vo.RebateRecordListVo;
import com.yi.core.activity.domain.vo.RebateRecordVo;
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
public interface IRebateRecordService {



   Page<RebateRecord> query(Pagination<RebateRecord> query);

    /**
    * 创建RebateRecord
    **/
    RebateRecordVo addRebateRecord(RebateRecordBo rebateRecord);

    /**
    * 更新RebateRecord
    **/
    RebateRecordVo updateRebateRecord(RebateRecordBo rebateRecord);

    /**
    * 删除RebateRecord
    **/
    void removeRebateRecordById(int rebateRecordId);

    /**
    * 根据ID得到RebateRecordVo
    **/
    RebateRecordVo getRebateRecordVoById(int rebateRecordId);

    /**
    * 根据ID得到RebateRecordListVo
    **/
    RebateRecordVo getListVoById(int rebateRecordId);

    /**
    * 分页查询: RebateRecord
    **/
    Page<RebateRecordListVo> queryListVo(Pagination<RebateRecord> query);



}
