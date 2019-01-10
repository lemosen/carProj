/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.RebateGroupDao;
import com.yi.core.activity.dao.RebateSetDao;
import com.yi.core.activity.domain.bo.RebateGroupBo;
import com.yi.core.activity.domain.entity.RebateGroup;
import com.yi.core.activity.domain.entity.RebateGroup_;
import com.yi.core.activity.domain.entity.RebateSet;
import com.yi.core.activity.domain.simple.RebateGroupSimple;
import com.yi.core.activity.domain.vo.RebateGroupListVo;
import com.yi.core.activity.domain.vo.RebateGroupVo;
import com.yi.core.activity.service.IRebateGroupService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yihz.common.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
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
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class RebateGroupServiceImpl implements IRebateGroupService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(RebateGroupServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private IRebateGroupService rebateGroupService;

    @Resource
    private RebateGroupDao rebateGroupDao;

    @Resource
    private RebateSetDao rebateSetDao;


    private EntityListVoBoSimpleConvert<RebateGroup, RebateGroupBo, RebateGroupVo, RebateGroupSimple, RebateGroupListVo> rebateGroupConvert;

    /**
     * 分页查询RebateGroup
     **/
    @Override
    public Page<RebateGroup> query(Pagination<RebateGroup> query) {
        query.setEntityClazz(RebateGroup.class);
        Page<RebateGroup> page = rebateGroupDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建RebateGroup
     **/
    @Override
    public RebateGroupVo addRebateGroup(RebateGroupBo rebateGroup) {

        if (rebateGroup == null) {
            throw new BusinessException("提交数据不能为空");
        }
        if (CollectionUtils.isEmpty(rebateGroup.getRebateSets())) {
            throw new BusinessException("返现设置不能为空");
        }
        if (rebateGroup.getProduct() == null) {
            throw new BusinessException("货品不能为空");
        }

        if (CollectionUtils.isNotEmpty(rebateGroup.getRebateSets())) {
            rebateGroup.getRebateSets().forEach(tmp -> {
                if (tmp != null) {
                    tmp.setRebateGroup(rebateGroup);
                }
            });
        }

        RebateGroup rebateGroup1 = rebateGroupConvert.toEntity(rebateGroup);
        System.out.println("{}{}" + rebateGroup1.getRebateSets().iterator().next().getId());
        rebateSetDao.saveAll(rebateGroup1.getRebateSets());
        return rebateGroupConvert.toVo(rebateGroupDao.save(rebateGroup1));
    }

    /**
     * 更新RebateGroup
     **/
    @Override
    public RebateGroupVo updateRebateGroup(RebateGroupBo rebateGroupBo) {
        RebateGroup dbRebateGroup = rebateGroupDao.getOne(rebateGroupBo.getId());
        AttributeReplication.copying(rebateGroupBo, dbRebateGroup,
                RebateGroup_.guid,
                RebateGroup_.label,
                RebateGroup_.startTime,
                RebateGroup_.endTime,
                RebateGroup_.activityCover,
                RebateGroup_.shareTitle,
                RebateGroup_.product,
                RebateGroup_.activityStock,
                RebateGroup_.limitGroupTime,
                RebateGroup_.limitQuantity,
                RebateGroup_.limitPayTime,
                RebateGroup_.rewardType,
                RebateGroup_.rewardIntegral,
                RebateGroup_.coupon,
                RebateGroup_.freightSet,
                RebateGroup_.freight,
                RebateGroup_.state,
                RebateGroup_.createTime,
                RebateGroup_.deleted,
                RebateGroup_.delTime
        );
        return rebateGroupConvert.toVo(dbRebateGroup);
    }

    /**
     * 删除RebateGroup
     **/
    @Override
    public void removeRebateGroupById(int id) {
        rebateGroupDao.deleteById(id);
    }

    /**
     * 根据ID得到RebateGroup
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RebateGroupVo getRebateGroupVoById(int id) {
        RebateGroup rebateGroup = this.rebateGroupDao.getOne(id);
        System.out.println(rebateGroup.getRebateSets().size() + "长长");
        RebateGroupVo rebateGroupVo = rebateGroupConvert.toVo(rebateGroup);
        return rebateGroupVo;
    }

    /**
     * 根据ID得到RebateGroupListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RebateGroupVo getListVoById(int id) {
        return rebateGroupConvert.toVo(this.rebateGroupDao.getOne(id));
    }

    /**
     * 分页查询: RebateGroup
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page
            <RebateGroupListVo> queryListVo(Pagination<RebateGroup> query) {

        Page<RebateGroup> pages = this.query(query);

        List
                <RebateGroupListVo> vos = rebateGroupConvert.toListVos(pages.getContent());
        return new PageImpl
                <RebateGroupListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.rebateGroupConvert = new EntityListVoBoSimpleConvert<RebateGroup, RebateGroupBo, RebateGroupVo, RebateGroupSimple, RebateGroupListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<RebateGroup, RebateGroupVo> createEntityToVoConvert(BeanConvertManager
                                                                                              beanConvertManager) {
                return new AbstractBeanConvert<RebateGroup, RebateGroupVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(RebateGroupVo RebateGroupVo, RebateGroup RebateGroup) {

                    }
                };
            }


            @Override
            protected BeanConvert<RebateGroup, RebateGroupListVo> createEntityToListVoConvert(BeanConvertManager
                                                                                                      beanConvertManager) {
                return new AbstractBeanConvert<RebateGroup, RebateGroupListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(RebateGroupListVo RebateGroupListVo, RebateGroup RebateGroup) {

                    }
                };
            }

            @Override
            protected BeanConvert<RebateGroup, RebateGroupBo> createEntityToBoConvert(BeanConvertManager
                                                                                              beanConvertManager) {
                return new AbstractBeanConvert<RebateGroup, RebateGroupBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(RebateGroupBo RebateGroupBo, RebateGroup RebateGroup) {

                    }
                };
            }

            @Override
            protected BeanConvert
                    <RebateGroupBo
                            , RebateGroup> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <RebateGroupBo
                                , RebateGroup>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<RebateGroup, RebateGroupSimple> createEntityToSimpleConvert(BeanConvertManager
                                                                                                      beanConvertManager) {
                return new AbstractBeanConvert<RebateGroup, RebateGroupSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert
                    <RebateGroupSimple
                            , RebateGroup> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <RebateGroupSimple
                                , RebateGroup>(beanConvertManager) {
                    @Override
                    public RebateGroup convert(RebateGroupSimple RebateGroupSimple) throws BeansException {
                        return rebateGroupDao.getOne(RebateGroupSimple.getId());
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
