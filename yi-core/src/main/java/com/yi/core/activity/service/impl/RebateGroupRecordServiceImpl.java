/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.RebateGroupRecordDao;
import com.yi.core.activity.domain.bo.RebateGroupRecordBo;
import com.yi.core.activity.domain.entity.RebateGroupRecord;
import com.yi.core.activity.domain.entity.RebateGroupRecord_;
import com.yi.core.activity.domain.simple.RebateGroupRecordSimple;
import com.yi.core.activity.domain.vo.RebateGroupRecordListVo;
import com.yi.core.activity.domain.vo.RebateGroupRecordVo;
import com.yi.core.activity.service.IRebateGroupRecordService;
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
public class RebateGroupRecordServiceImpl implements IRebateGroupRecordService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(RebateGroupRecordServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

@Resource
private RebateGroupRecordDao rebateGroupRecordDao;

   private EntityListVoBoSimpleConvert<RebateGroupRecord, RebateGroupRecordBo, RebateGroupRecordVo, RebateGroupRecordSimple, RebateGroupRecordListVo> rebateGroupRecordConvert;

/**
* 分页查询RebateGroupRecord
**/
 @Override
    public Page<RebateGroupRecord> query(Pagination<RebateGroupRecord> query) {
    query.setEntityClazz(RebateGroupRecord.class);
    Page<RebateGroupRecord> page = rebateGroupRecordDao.findAll(query, query.getPageRequest());
        return page;
        }

/**
* 创建RebateGroupRecord
**/
@Override
public RebateGroupRecordVo addRebateGroupRecord(RebateGroupRecordBo rebateGroupRecordBo){
return rebateGroupRecordConvert.toVo(rebateGroupRecordDao.save(rebateGroupRecordConvert.toEntity(rebateGroupRecordBo)));
}

/**
* 更新RebateGroupRecord
**/
@Override
public RebateGroupRecordVo updateRebateGroupRecord(RebateGroupRecordBo rebateGroupRecordBo){
RebateGroupRecord dbRebateGroupRecord=rebateGroupRecordDao.getOne(rebateGroupRecordBo.getId());
    AttributeReplication.copying(rebateGroupRecordBo,dbRebateGroupRecord,
        RebateGroupRecord_.guid,
        RebateGroupRecord_.groupCode,
        RebateGroupRecord_.rebateGroup,
        RebateGroupRecord_.member,
        RebateGroupRecord_.groupPrice,
        RebateGroupRecord_.joinPeople,
        RebateGroupRecord_.consignee,
        RebateGroupRecord_.consigneePhone,
        RebateGroupRecord_.consigneeAddr,
        RebateGroupRecord_.state,
        RebateGroupRecord_.createTime,
        RebateGroupRecord_.deleted,
        RebateGroupRecord_.delTime
        );
    return rebateGroupRecordConvert.toVo(dbRebateGroupRecord);
}

/**
* 删除RebateGroupRecord
**/
@Override
public void removeRebateGroupRecordById(int id) {
rebateGroupRecordDao.deleteById(id);
}

/**
* 根据ID得到RebateGroupRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public RebateGroupRecordVo getRebateGroupRecordVoById(int id) {

return rebateGroupRecordConvert.toVo(this.rebateGroupRecordDao.getOne(id));
}

/**
* 根据ID得到RebateGroupRecordListVo
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public RebateGroupRecordVo getListVoById(int id) {
    return rebateGroupRecordConvert.toVo(this.rebateGroupRecordDao.getOne(id));
}

/**
* 分页查询: RebateGroupRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public Page
<RebateGroupRecordListVo> queryListVo(Pagination<RebateGroupRecord> query){

    Page<RebateGroupRecord> pages = this.query(query);

    List
    <RebateGroupRecordListVo> vos = rebateGroupRecordConvert.toListVos(pages.getContent());
        return new PageImpl
        <RebateGroupRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

            }

            protected void initConvert() {
            this.rebateGroupRecordConvert = new EntityListVoBoSimpleConvert<RebateGroupRecord, RebateGroupRecordBo, RebateGroupRecordVo, RebateGroupRecordSimple, RebateGroupRecordListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<RebateGroupRecord, RebateGroupRecordVo> createEntityToVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateGroupRecord, RebateGroupRecordVo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateGroupRecordVo RebateGroupRecordVo, RebateGroupRecord RebateGroupRecord) {

            }
            };
            }


            @Override
            protected BeanConvert<RebateGroupRecord, RebateGroupRecordListVo> createEntityToListVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateGroupRecord, RebateGroupRecordListVo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateGroupRecordListVo RebateGroupRecordListVo, RebateGroupRecord RebateGroupRecord) {

            }
            };
            }

            @Override
            protected BeanConvert<RebateGroupRecord, RebateGroupRecordBo> createEntityToBoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateGroupRecord, RebateGroupRecordBo>(beanConvertManager) {
            @Override
            protected void postConvert(RebateGroupRecordBo RebateGroupRecordBo, RebateGroupRecord RebateGroupRecord) {

            }
            };
            }

            @Override
            protected BeanConvert
            <RebateGroupRecordBo
            , RebateGroupRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <RebateGroupRecordBo
            , RebateGroupRecord>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert<RebateGroupRecord, RebateGroupRecordSimple> createEntityToSimpleConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<RebateGroupRecord, RebateGroupRecordSimple>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert
            <RebateGroupRecordSimple
            , RebateGroupRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <RebateGroupRecordSimple
            , RebateGroupRecord>(beanConvertManager) {
            @Override
            public RebateGroupRecord convert(RebateGroupRecordSimple RebateGroupRecordSimple) throws BeansException {
            return rebateGroupRecordDao.getOne(RebateGroupRecordSimple.getId());
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
