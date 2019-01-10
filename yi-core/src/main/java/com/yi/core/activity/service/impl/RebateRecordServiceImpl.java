/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.RebateRecordDao;
import com.yi.core.activity.domain.bo.RebateRecordBo;
import com.yi.core.activity.domain.entity.RebateRecord;
import com.yi.core.activity.domain.entity.RebateRecord_;
import com.yi.core.activity.domain.simple.RebateRecordSimple;
import com.yi.core.activity.domain.vo.RebateRecordListVo;
import com.yi.core.activity.domain.vo.RebateRecordVo;
import com.yi.core.activity.service.IRebateRecordService;
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
public class RebateRecordServiceImpl implements IRebateRecordService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(RebateRecordServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

@Resource
private RebateRecordDao rebateRecordDao;

   private EntityListVoBoSimpleConvert<RebateRecord, RebateRecordBo, RebateRecordVo, RebateRecordSimple, RebateRecordListVo> rebateRecordConvert;

/**
* 分页查询RebateRecord
**/
 @Override
    public Page<RebateRecord> query(Pagination<RebateRecord> query) {
    query.setEntityClazz(RebateRecord.class);
    Page<RebateRecord> page = rebateRecordDao.findAll(query, query.getPageRequest());
        return page;
        }

/**
* 创建RebateRecord
**/
@Override
public RebateRecordVo addRebateRecord(RebateRecordBo rebateRecordBo){
return rebateRecordConvert.toVo(rebateRecordDao.save(rebateRecordConvert.toEntity(rebateRecordBo)));
}

/**
* 更新RebateRecord
**/
@Override
public RebateRecordVo updateRebateRecord(RebateRecordBo rebateRecordBo){
RebateRecord dbRebateRecord=rebateRecordDao.getOne(rebateRecordBo.getId());
    AttributeReplication.copying(rebateRecordBo,dbRebateRecord,
        RebateRecord_.guid,
        RebateRecord_.rebateGroupRecord,
        RebateRecord_.member,
        RebateRecord_.groupPrice,
        RebateRecord_.rebateAmount,
        RebateRecord_.state,
        RebateRecord_.createTime
        );
    return rebateRecordConvert.toVo(dbRebateRecord);
}

/**
* 删除RebateRecord
**/
@Override
public void removeRebateRecordById(int id) {
rebateRecordDao.deleteById(id);
}

/**
* 根据ID得到RebateRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public RebateRecordVo getRebateRecordVoById(int id) {

return rebateRecordConvert.toVo(this.rebateRecordDao.getOne(id));
}

/**
* 根据ID得到RebateRecordListVo
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public RebateRecordVo getListVoById(int id) {
    return rebateRecordConvert.toVo(this.rebateRecordDao.getOne(id));
}

/**
* 分页查询: RebateRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public Page
<RebateRecordListVo> queryListVo(Pagination<RebateRecord> query){

    Page<RebateRecord> pages = this.query(query);

    List
    <RebateRecordListVo> vos = rebateRecordConvert.toListVos(pages.getContent());
        return new PageImpl
        <RebateRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

            }

            protected void initConvert() {
            this.rebateRecordConvert = new EntityListVoBoSimpleConvert<RebateRecord, RebateRecordBo, RebateRecordVo, RebateRecordSimple, RebateRecordListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<RebateRecord, RebateRecordVo> createEntityToVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateRecord, RebateRecordVo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateRecordVo RebateRecordVo, RebateRecord RebateRecord) {

            }
            };
            }


            @Override
            protected BeanConvert<RebateRecord, RebateRecordListVo> createEntityToListVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateRecord, RebateRecordListVo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateRecordListVo RebateRecordListVo, RebateRecord RebateRecord) {

            }
            };
            }

            @Override
            protected BeanConvert<RebateRecord, RebateRecordBo> createEntityToBoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateRecord, RebateRecordBo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateRecordBo RebateRecordBo, RebateRecord RebateRecord) {

            }
            };
            }

            @Override
            protected BeanConvert
            <RebateRecordBo
            , RebateRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <RebateRecordBo
            , RebateRecord>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert<RebateRecord, RebateRecordSimple> createEntityToSimpleConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateRecord, RebateRecordSimple>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert
            <RebateRecordSimple
            , RebateRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <RebateRecordSimple
            , RebateRecord>(beanConvertManager) {
            @Override
            public RebateRecord convert(RebateRecordSimple RebateRecordSimple) throws BeansException {
            return rebateRecordDao.getOne(RebateRecordSimple.getId());
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
