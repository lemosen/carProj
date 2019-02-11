/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.RebateGroupRecordBo;
import com.yi.core.activity.domain.entity.RebateGroupRecord;
import com.yi.core.activity.domain.vo.RebateGroupRecordListVo;
import com.yi.core.activity.domain.vo.RebateGroupRecordVo;
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
public interface IRebateGroupRecordService {



   Page<RebateGroupRecord> query(Pagination<RebateGroupRecord> query);

    /**
    * 创建RebateGroupRecord
    **/
    RebateGroupRecordVo addRebateGroupRecord(RebateGroupRecordBo rebateGroupRecord);

    /**
    * 更新RebateGroupRecord
    **/
    RebateGroupRecordVo updateRebateGroupRecord(RebateGroupRecordBo rebateGroupRecord);

    /**
    * 删除RebateGroupRecord
    **/
    void removeRebateGroupRecordById(int rebateGroupRecordId);

    /**
    * 根据ID得到RebateGroupRecordVo
    **/
    RebateGroupRecordVo getRebateGroupRecordVoById(int rebateGroupRecordId);

    /**
    * 根据ID得到RebateGroupRecordListVo
    **/
    RebateGroupRecordVo getListVoById(int rebateGroupRecordId);

    /**
    * 分页查询: RebateGroupRecord
    **/
    Page<RebateGroupRecordListVo> queryListVo(Pagination<RebateGroupRecord> query);



}
