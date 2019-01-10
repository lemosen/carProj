/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service;

import com.yi.core.activity.domain.bo.SeckillBo;
import com.yi.core.activity.domain.entity.Seckill;
import com.yi.core.activity.domain.vo.SeckillListVo;
import com.yi.core.activity.domain.vo.SeckillVo;
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
public interface ISeckillService {



   Page<Seckill> query(Pagination<Seckill> query);

    /**
    * 创建Seckill
    **/
    SeckillVo addSeckill(SeckillBo seckill);

    /**
    * 更新Seckill
    **/
    SeckillVo updateSeckill(SeckillBo seckill);

    /**
    * 删除Seckill
    **/
    void removeSeckillById(int seckillId);

    /**
    * 根据ID得到SeckillVo
    **/
    SeckillVo getSeckillVoById(int seckillId);

    /**
    * 根据ID得到SeckillListVo
    **/
    SeckillVo getListVoById(int seckillId);

    /**
    * 分页查询: Seckill
    **/
    Page<SeckillListVo> queryListVo(Pagination<Seckill> query);



}
