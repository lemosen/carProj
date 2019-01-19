/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.SeckillRecordDao;
import com.yi.core.activity.domain.bo.SeckillRecordBo;
import com.yi.core.activity.domain.entity.SeckillRecord;
import com.yi.core.activity.domain.entity.SeckillRecord_;
import com.yi.core.activity.domain.simple.SeckillRecordSimple;
import com.yi.core.activity.domain.vo.SeckillRecordListVo;
import com.yi.core.activity.domain.vo.SeckillRecordVo;
import com.yi.core.activity.service.ISeckillRecordService;
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
public class SeckillRecordServiceImpl implements ISeckillRecordService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(SeckillRecordServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

@Resource
private SeckillRecordDao seckillRecordDao;

   private EntityListVoBoSimpleConvert<SeckillRecord, SeckillRecordBo, SeckillRecordVo, SeckillRecordSimple, SeckillRecordListVo> seckillRecordConvert;

/**
* 分页查询SeckillRecord
**/
 @Override
    public Page<SeckillRecord> query(Pagination<SeckillRecord> query) {
    query.setEntityClazz(SeckillRecord.class);
    Page<SeckillRecord> page = seckillRecordDao.findAll(query, query.getPageRequest());
        return page;
        }

/**
* 创建SeckillRecord
**/
@Override
public SeckillRecordVo addSeckillRecord(SeckillRecordBo seckillRecordBo){
return seckillRecordConvert.toVo(seckillRecordDao.save(seckillRecordConvert.toEntity(seckillRecordBo)));
}

/**
* 更新SeckillRecord
**/
@Override
public SeckillRecordVo updateSeckillRecord(SeckillRecordBo seckillRecordBo){
SeckillRecord dbSeckillRecord=seckillRecordDao.getOne(seckillRecordBo.getId());
    AttributeReplication.copying(seckillRecordBo,dbSeckillRecord,
        SeckillRecord_.guid,
        SeckillRecord_.seckill,
        SeckillRecord_.member,
        SeckillRecord_.consignee,
        SeckillRecord_.consigneePhone,
        SeckillRecord_.consigneeAddr,
        SeckillRecord_.state,
        SeckillRecord_.createTime,
        SeckillRecord_.deleted,
        SeckillRecord_.delTime
        );
    return seckillRecordConvert.toVo(dbSeckillRecord);
}

/**
* 删除SeckillRecord
**/
@Override
public void removeSeckillRecordById(int id) {
seckillRecordDao.deleteById(id);
}

/**
* 根据ID得到SeckillRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public SeckillRecordVo getSeckillRecordVoById(int id) {

return seckillRecordConvert.toVo(this.seckillRecordDao.getOne(id));
}

/**
* 根据ID得到SeckillRecordListVo
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public SeckillRecordVo getListVoById(int id) {
    return seckillRecordConvert.toVo(this.seckillRecordDao.getOne(id));
}

/**
* 分页查询: SeckillRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public Page
<SeckillRecordListVo> queryListVo(Pagination<SeckillRecord> query){

    Page<SeckillRecord> pages = this.query(query);

    List
    <SeckillRecordListVo> vos = seckillRecordConvert.toListVos(pages.getContent());
        return new PageImpl
        <SeckillRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

            }

            protected void initConvert() {
            this.seckillRecordConvert = new EntityListVoBoSimpleConvert<SeckillRecord, SeckillRecordBo, SeckillRecordVo, SeckillRecordSimple, SeckillRecordListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<SeckillRecord, SeckillRecordVo> createEntityToVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<SeckillRecord, SeckillRecordVo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillRecordVo SeckillRecordVo, SeckillRecord SeckillRecord) {

            }
            };
            }


            @Override
            protected BeanConvert<SeckillRecord, SeckillRecordListVo> createEntityToListVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<SeckillRecord, SeckillRecordListVo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillRecordListVo SeckillRecordListVo, SeckillRecord SeckillRecord) {

            }
            };
            }

            @Override
            protected BeanConvert<SeckillRecord, SeckillRecordBo> createEntityToBoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<SeckillRecord, SeckillRecordBo>(beanConvertManager) {
            @Override
            protected void postConvert(SeckillRecordBo SeckillRecordBo, SeckillRecord SeckillRecord) {

            }
            };
            }

            @Override
            protected BeanConvert
            <SeckillRecordBo
            , SeckillRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <SeckillRecordBo
            , SeckillRecord>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert<SeckillRecord, SeckillRecordSimple> createEntityToSimpleConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<SeckillRecord, SeckillRecordSimple>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert
            <SeckillRecordSimple
            , SeckillRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <SeckillRecordSimple
            , SeckillRecord>(beanConvertManager) {
            @Override
            public SeckillRecord convert(SeckillRecordSimple SeckillRecordSimple) throws BeansException {
            return seckillRecordDao.getOne(SeckillRecordSimple.getId());
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
