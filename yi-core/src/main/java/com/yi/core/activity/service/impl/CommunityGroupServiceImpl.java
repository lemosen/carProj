/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.CommunityGroupDao;
import com.yi.core.activity.domain.bo.CommunityGroupBo;
import com.yi.core.activity.domain.entity.CommunityGroup;
import com.yi.core.activity.domain.entity.CommunityGroup_;
import com.yi.core.activity.domain.simple.CommunityGroupSimple;
import com.yi.core.activity.domain.vo.CommunityGroupListVo;
import com.yi.core.activity.domain.vo.CommunityGroupVo;
import com.yi.core.activity.service.ICommunityGroupService;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.BeansException;
import com.yihz.common.persistence.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.data.domain.Page;
import com.yihz.common.persistence.Pagination;
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
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommunityGroupServiceImpl implements ICommunityGroupService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(CommunityGroupServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private CommunityGroupDao communityGroupDao;

    private EntityListVoBoSimpleConvert<CommunityGroup, CommunityGroupBo, CommunityGroupVo, CommunityGroupSimple,
            CommunityGroupListVo> communityGroupConvert;

    /**
     * 分页查询CommunityGroup
     **/
    @Override
    public Page<CommunityGroup> query(Pagination<CommunityGroup> query) {
        query.setEntityClazz(CommunityGroup.class);
        Page<CommunityGroup> page = communityGroupDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建CommunityGroup
     **/
    @Override
    public CommunityGroupVo addCommunityGroup(CommunityGroupBo communityGroupBo) {
        CommunityGroup communityGroup = communityGroupConvert.toEntity(communityGroupBo);
        return communityGroupConvert.toVo(communityGroupDao.save(communityGroup));
    }

    /**
     * 更新CommunityGroup
     **/
    @Override
    public CommunityGroupVo updateCommunityGroup(CommunityGroupBo communityGroupBo) {
        CommunityGroup dbCommunityGroup = communityGroupDao.getOne(communityGroupBo.getId());
        AttributeReplication.copying(communityGroupConvert.toEntity(communityGroupBo), dbCommunityGroup,
                CommunityGroup_.label,
                CommunityGroup_.startTime,
                CommunityGroup_.endTime,
                CommunityGroup_.activityCover,
                CommunityGroup_.shareTitle,
                CommunityGroup_.product,
                CommunityGroup_.community,
                CommunityGroup_.activityStock,
                CommunityGroup_.groupPrice,
                CommunityGroup_.groupPeople,
                CommunityGroup_.limitGroupTime,
                CommunityGroup_.limitQuantity,
                CommunityGroup_.limitPayTime,
                CommunityGroup_.rewardType,
                CommunityGroup_.rewardIntegral,
                CommunityGroup_.coupon,
                CommunityGroup_.freightSet,
                CommunityGroup_.freight,
                CommunityGroup_.state
        );
        return communityGroupConvert.toVo(dbCommunityGroup);
    }

    /**
     * 删除CommunityGroup
     **/
    @Override
    public void removeCommunityGroupById(int id) {
        communityGroupDao.deleteById(id);
    }

    /**
     * 根据ID得到CommunityGroup
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public CommunityGroupVo getCommunityGroupVoById(int id) {

        return communityGroupConvert.toVo(this.communityGroupDao.getOne(id));
    }

    /**
     * 根据ID得到CommunityGroupListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public CommunityGroupVo getListVoById(int id) {
        return communityGroupConvert.toVo(this.communityGroupDao.getOne(id));
    }

    /**
     * 分页查询: CommunityGroup
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page
            <CommunityGroupListVo> queryListVo(Pagination<CommunityGroup> query) {

        Page<CommunityGroup> pages = this.query(query);

        List
                <CommunityGroupListVo> vos = communityGroupConvert.toListVos(pages.getContent());
        return new PageImpl
                <CommunityGroupListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.communityGroupConvert = new EntityListVoBoSimpleConvert<CommunityGroup, CommunityGroupBo,
                CommunityGroupVo, CommunityGroupSimple, CommunityGroupListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<CommunityGroup, CommunityGroupVo> createEntityToVoConvert(BeanConvertManager
                                                                                                    beanConvertManager) {
                return new AbstractBeanConvert<CommunityGroup, CommunityGroupVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(CommunityGroupVo CommunityGroupVo, CommunityGroup CommunityGroup) {

                    }
                };
            }


            @Override
            protected BeanConvert<CommunityGroup, CommunityGroupListVo> createEntityToListVoConvert(BeanConvertManager
                                                                                                            beanConvertManager) {
                return new AbstractBeanConvert<CommunityGroup, CommunityGroupListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(CommunityGroupListVo CommunityGroupListVo, CommunityGroup
                            CommunityGroup) {

                    }
                };
            }

            @Override
            protected BeanConvert<CommunityGroup, CommunityGroupBo> createEntityToBoConvert(BeanConvertManager
                                                                                                    beanConvertManager) {
                return new AbstractBeanConvert<CommunityGroup, CommunityGroupBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(CommunityGroupBo CommunityGroupBo, CommunityGroup CommunityGroup) {

                    }
                };
            }

            @Override
            protected BeanConvert
                    <CommunityGroupBo
                            , CommunityGroup> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <CommunityGroupBo
                                , CommunityGroup>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<CommunityGroup, CommunityGroupSimple> createEntityToSimpleConvert(BeanConvertManager
                                                                                                            beanConvertManager) {
                return new AbstractBeanConvert<CommunityGroup, CommunityGroupSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert
                    <CommunityGroupSimple
                            , CommunityGroup> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <CommunityGroupSimple
                                , CommunityGroup>(beanConvertManager) {
                    @Override
                    public CommunityGroup convert(CommunityGroupSimple CommunityGroupSimple) throws BeansException {
                        return communityGroupDao.getOne(CommunityGroupSimple.getId());
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
