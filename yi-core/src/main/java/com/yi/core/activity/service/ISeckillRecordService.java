/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.SeckillRecordBo;
import com.yi.core.activity.domain.entity.SeckillRecord;
import com.yi.core.activity.domain.vo.SeckillRecordListVo;
import com.yi.core.activity.domain.vo.SeckillRecordVo;

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
public interface ISeckillRecordService {



   Page<SeckillRecord> query(Pagination<SeckillRecord> query);

    /**
    * 创建SeckillRecord
    **/
    SeckillRecordVo addSeckillRecord(SeckillRecordBo seckillRecord);

    /**
    * 更新SeckillRecord
    **/
    SeckillRecordVo updateSeckillRecord(SeckillRecordBo seckillRecord);

    /**
    * 删除SeckillRecord
    **/
    void removeSeckillRecordById(int seckillRecordId);

    /**
    * 根据ID得到SeckillRecordVo
    **/
    SeckillRecordVo getSeckillRecordVoById(int seckillRecordId);

    /**
    * 根据ID得到SeckillRecordListVo
    **/
    SeckillRecordVo getListVoById(int seckillRecordId);

    /**
    * 分页查询: SeckillRecord
    **/
    Page<SeckillRecordListVo> queryListVo(Pagination<SeckillRecord> query);



}
