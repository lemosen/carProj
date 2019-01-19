/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.activity.dao.CommunityGroupRecordDao;
import com.yi.core.activity.domain.bo.CommunityGroupRecordBo;
import com.yi.core.activity.domain.entity.CommunityGroupRecord;
import com.yi.core.activity.domain.entity.CommunityGroupRecord_;
import com.yi.core.activity.domain.simple.CommunityGroupRecordSimple;
import com.yi.core.activity.domain.vo.CommunityGroupRecordListVo;
import com.yi.core.activity.domain.vo.CommunityGroupRecordVo;
import com.yi.core.activity.service.ICommunityGroupRecordService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;


/**
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommunityGroupRecordServiceImpl implements ICommunityGroupRecordService,InitializingBean{

    private final Logger LOG = LoggerFactory.getLogger(CommunityGroupRecordServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

@Resource
private CommunityGroupRecordDao communityGroupRecordDao;

   private EntityListVoBoSimpleConvert<CommunityGroupRecord, CommunityGroupRecordBo, CommunityGroupRecordVo, CommunityGroupRecordSimple, CommunityGroupRecordListVo> communityGroupRecordConvert;

/**
* 分页查询CommunityGroupRecord
**/
 @Override
    public Page<CommunityGroupRecord> query(Pagination<CommunityGroupRecord> query) {
    query.setEntityClazz(CommunityGroupRecord.class);
    Page<CommunityGroupRecord> page = communityGroupRecordDao.findAll(query, query.getPageRequest());
        return page;
        }

/**
* 创建CommunityGroupRecord
**/
@Override
public CommunityGroupRecordVo addCommunityGroupRecord(CommunityGroupRecordBo communityGroupRecordBo){
return communityGroupRecordConvert.toVo(communityGroupRecordDao.save(communityGroupRecordConvert.toEntity(communityGroupRecordBo)));
}

/**
* 更新CommunityGroupRecord
**/
@Override
public CommunityGroupRecordVo updateCommunityGroupRecord(CommunityGroupRecordBo communityGroupRecordBo){
CommunityGroupRecord dbCommunityGroupRecord=communityGroupRecordDao.getOne(communityGroupRecordBo.getId());
    AttributeReplication.copying(communityGroupRecordBo,dbCommunityGroupRecord,
        CommunityGroupRecord_.guid,
        CommunityGroupRecord_.communityGroup,
        CommunityGroupRecord_.groupCode,
        CommunityGroupRecord_.member,
        CommunityGroupRecord_.groupPeople,
        CommunityGroupRecord_.joinPeople,
        CommunityGroupRecord_.openTime,
        CommunityGroupRecord_.state,
        CommunityGroupRecord_.createTime,
        CommunityGroupRecord_.deleted,
        CommunityGroupRecord_.delTime
        );
    return communityGroupRecordConvert.toVo(dbCommunityGroupRecord);
}

/**
* 删除CommunityGroupRecord
**/
@Override
public void removeCommunityGroupRecordById(int id) {
communityGroupRecordDao.deleteById(id);
}

/**
* 根据ID得到CommunityGroupRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public CommunityGroupRecordVo getCommunityGroupRecordVoById(int id) {

return communityGroupRecordConvert.toVo(this.communityGroupRecordDao.getOne(id));
}

/**
* 根据ID得到CommunityGroupRecordVo
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public CommunityGroupRecordVo getListVoById(int id) {
    return communityGroupRecordConvert.toVo(this.communityGroupRecordDao.getOne(id));
}

/**
* 分页查询: CommunityGroupRecord
**/
@Override
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public Page
<CommunityGroupRecordListVo> queryListVo(Pagination<CommunityGroupRecord> query){

    Page<CommunityGroupRecord> pages = this.query(query);

    List
    <CommunityGroupRecordListVo> vos = communityGroupRecordConvert.toListVos(pages.getContent());
        return new PageImpl
        <CommunityGroupRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

            }

            protected void initConvert() {
            this.communityGroupRecordConvert = new EntityListVoBoSimpleConvert<CommunityGroupRecord, CommunityGroupRecordBo, CommunityGroupRecordVo, CommunityGroupRecordSimple, CommunityGroupRecordListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<CommunityGroupRecord, CommunityGroupRecordVo> createEntityToVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<CommunityGroupRecord, CommunityGroupRecordVo>(beanConvertManager) {
            @Override
            protected void postConvert(CommunityGroupRecordVo CommunityGroupRecordVo, CommunityGroupRecord CommunityGroupRecord) {

            }
            };
            }


            @Override
            protected BeanConvert<CommunityGroupRecord, CommunityGroupRecordListVo> createEntityToListVoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<CommunityGroupRecord, CommunityGroupRecordListVo>(beanConvertManager) {
            @Override
            protected void postConvert(CommunityGroupRecordListVo CommunityGroupRecordListVo, CommunityGroupRecord CommunityGroupRecord) {

            }
            };
            }

            @Override
            protected BeanConvert<CommunityGroupRecord, CommunityGroupRecordBo> createEntityToBoConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<CommunityGroupRecord, CommunityGroupRecordBo>(beanConvertManager) {
            @Override
            protected void postConvert(CommunityGroupRecordBo CommunityGroupRecordBo, CommunityGroupRecord CommunityGroupRecord) {

            }
            };
            }

            @Override
            protected BeanConvert
            <CommunityGroupRecordBo
            , CommunityGroupRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <CommunityGroupRecordBo
            , CommunityGroupRecord>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert<CommunityGroupRecord, CommunityGroupRecordSimple> createEntityToSimpleConvert(BeanConvertManager
            beanConvertManager) {
            return new AbstractBeanConvert<CommunityGroupRecord, CommunityGroupRecordSimple>(beanConvertManager) {
            };
            }

            @Override
            protected BeanConvert
            <CommunityGroupRecordSimple
            , CommunityGroupRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
            return new AbstractBeanConvert
            <CommunityGroupRecordSimple
            , CommunityGroupRecord>(beanConvertManager) {
            @Override
            public CommunityGroupRecord convert(CommunityGroupRecordSimple CommunityGroupRecordSimple) throws BeansException {
            return communityGroupRecordDao.getOne(CommunityGroupRecordSimple.getId());
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
