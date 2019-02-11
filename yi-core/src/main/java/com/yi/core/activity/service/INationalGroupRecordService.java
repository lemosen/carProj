/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.NationalGroupRecordBo;
import com.yi.core.activity.domain.entity.NationalGroupRecord;
import com.yi.core.activity.domain.vo.NationalGroupRecordListVo;
import com.yi.core.activity.domain.vo.NationalGroupRecordVo;
import com.yi.core.activity.domain.vo.NationalGroupVo;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.Page;

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
public interface INationalGroupRecordService {


    Page<NationalGroupRecord> query(Pagination<NationalGroupRecord> query);

    /**
     * 创建NationalGroupRecord
     **/
    NationalGroupRecordVo addNationalGroupRecord(NationalGroupRecordBo nationalGroupRecord);

    /**
     * 更新NationalGroupRecord
     **/
    NationalGroupRecordVo updateNationalGroupRecord(NationalGroupRecordBo nationalGroupRecord);

    /**
     * 删除NationalGroupRecord
     **/
    void removeNationalGroupRecordById(int nationalGroupRecordId);

    /**
     * 根据ID得到NationalGroupRecordVo
     **/
    NationalGroupRecordVo getNationalGroupRecordVoById(int nationalGroupRecordId);

    /**
     * 根据ID得到NationalGroupRecordListVo
     **/
    NationalGroupRecordVo getListVoById(int nationalGroupRecordId);

    /**
     * 分页查询: NationalGroupRecord
     **/
    Page<NationalGroupRecordListVo> queryListVo(Pagination<NationalGroupRecord> query);


    List<NationalGroupRecordListVo> myCollage(int memberId);


    /**
     * 添加
     * @param nationalGroupRecord
     * @return
     */
    NationalGroupRecord save(NationalGroupRecord nationalGroupRecord);

    /**
     * 根据id查询
     * @param nationalGroupRecordId
     * @return
     */
    NationalGroupRecord getOne(int nationalGroupRecordId);

    /**
     * entity转Vo
     * @param nationalGroupRecord
     * @return
     */
    NationalGroupRecordVo entityTurnVo(NationalGroupRecord nationalGroupRecord);
}
