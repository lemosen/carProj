/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.SeckillDao;
import com.yi.core.activity.domain.bo.SeckillBo;
import com.yi.core.activity.domain.entity.Seckill;
import com.yi.core.activity.domain.entity.Seckill_;
import com.yi.core.activity.domain.simple.SeckillSimple;
import com.yi.core.activity.domain.vo.SeckillListVo;
import com.yi.core.activity.domain.vo.SeckillVo;
import com.yi.core.activity.service.ISeckillService;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.data.domain.Page;
import com.yihz.common.persistence.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import org.springframework.beans.factory.InitializingBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class SeckillServiceImpl implements ISeckillService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

@Resource
private SeckillDao seckillDao;

   private EntityListVoBoSimpleConvert<Seckill, SeckillBo, SeckillVo, SeckillSimple, SeckillListVo> seckillConvert;

/**
* 分页查询Seckill
**/
 @Override
    public Page<Seckill> query(Pagination<Seckill> query) {
    query.setEntityClazz(Seckill.class);
    Page<Seckill> page = seckillDao.findAll(query, query.getPageRequest());
        return page;
        }

/**
* 创建Seckill
**/
@Override
public SeckillVo addSeckill(SeckillBo seckillBo){
    Seckill seckill = seckillConvert.toEntity(seckillBo);
    return seckillConvert.toVo(seckillDao.save(seckill));
}

/**
* 更新Seckill
**/
@Override
public SeckillVo updateSeckill(SeckillBo seckillBo){
Seckill dbSeckill=seckillDao.getOne(seckillBo.getId());
    AttributeReplication.copying(seckillConvert.toEntity(seckillBo),dbSeckill,
        Seckill_.guid,
        Seckill_.label,
        Seckill_.startTime,
        Seckill_.endTime,
        Seckill_.activityCover,
        Seckill_.shareTitle,
        Seckill_.product,
        Seckill_.activityStock,
        Seckill_.seckillPrice,
        Seckill_.limitQuantity,
        Seckill_.limitPayTime,
        Seckill_.rewardType,
        Seckill_.rewardIntegral,
        Seckill_.coupon,
        Seckill_.freightSet,
        Seckill_.freight,
        Seckill_.state,
        Seckill_.createTime,
        Seckill_.deleted,
        Seckill_.delTime
        );
    return seckillConvert.toVo(dbSeckill);
}

/**
* 删除Seckill
**/
@Override
public void removeSeckillById(int id) {
seckillDao.deleteById(id);
}

/**
* 根据ID得到Seckill
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public SeckillVo getSeckillVoById(int id) {

return seckillConvert.toVo(this.seckillDao.getOne(id));
}

/**
* 根据ID得到SeckillListVo
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public SeckillVo getListVoById(int id) {
    return seckillConvert.toVo(this.seckillDao.getOne(id));
}

/**
* 分页查询: Seckill
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public Page
<SeckillListVo> queryListVo(Pagination<Seckill> query){

    Page<Seckill> pages = this.query(query);

    List
    <SeckillListVo> vos = seckillConvert.toListVos(pages.getContent());
        return new PageImpl
        <SeckillListVo>(vos, query.getPageRequest(), pages.getTotalElements());

            }

            protected void initConvert() {
            this.seckillConvert = new EntityListVoBoSimpleConvert<Seckill, SeckillBo, SeckillVo, SeckillSimple, SeckillListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<Seckill, SeckillVo> createEntityToVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<Seckill, SeckillVo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillVo SeckillVo, Seckill Seckill) {

            }
            };
            }


            @Override
            protected BeanConvert<Seckill, SeckillListVo> createEntityToListVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<Seckill, SeckillListVo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillListVo SeckillListVo, Seckill Seckill) {

            }
            };
            }

            @Override
            protected BeanConvert<Seckill, SeckillBo> createEntityToBoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<Seckill, SeckillBo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillBo SeckillBo, Seckill Seckill) {

            }
            };
            }

            @Override
            protected BeanConvert
            <SeckillBo
            , Seckill> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <SeckillBo
            , Seckill>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert<Seckill, SeckillSimple> createEntityToSimpleConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<Seckill, SeckillSimple>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert
            <SeckillSimple
            , Seckill> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <SeckillSimple
            , Seckill>(beanConvertManager) {
            @Override
            public Seckill convert(SeckillSimple SeckillSimple) throws BeansException {
            return seckillDao.getOne(SeckillSimple.getId());
            }
            };
            }
            };
            }

            @Override
            public void afterPropertiesSet() throws Exception {
            initConvert();
            }
            }
