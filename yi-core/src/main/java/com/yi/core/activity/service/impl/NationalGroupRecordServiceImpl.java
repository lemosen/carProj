/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.service.impl;

import com.yi.core.activity.dao.NationalGroupDao;
import com.yi.core.activity.dao.NationalGroupRecordDao;
import com.yi.core.activity.domain.bo.NationalGroupRecordBo;
import com.yi.core.activity.domain.entity.NationalGroup;
import com.yi.core.activity.domain.entity.NationalGroupRecord;
import com.yi.core.activity.domain.entity.NationalGroupRecord_;
import com.yi.core.activity.domain.simple.NationalGroupRecordSimple;
import com.yi.core.activity.domain.vo.NationalGroupRecordListVo;
import com.yi.core.activity.domain.vo.NationalGroupRecordVo;
import com.yi.core.activity.domain.vo.NationalGroupVo;
import com.yi.core.activity.service.INationalGroupRecordService;
import com.yi.core.member.dao.MemberDao;
import com.yi.core.member.domain.entity.Member;
import com.yi.core.order.dao.SaleOrderDao;
import com.yi.core.order.domain.entity.SaleOrder;
import com.yi.core.order.service.ISaleOrderService;
import com.yihz.common.utils.date.DateUtils;
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
public class NationalGroupRecordServiceImpl implements INationalGroupRecordService, InitializingBean {

    private final Logger LOG = LoggerFactory.getLogger(NationalGroupRecordServiceImpl.class);

    @Resource
    private BeanConvertManager beanConvertManager;

    @Resource
    private NationalGroupRecordDao nationalGroupRecordDao;

    @Resource
    private NationalGroupDao nationalGroupDao;

    @Resource
    private MemberDao memberDao;

    @Resource
    private ISaleOrderService saleOrderService;

    private EntityListVoBoSimpleConvert<NationalGroupRecord, NationalGroupRecordBo, NationalGroupRecordVo, NationalGroupRecordSimple, NationalGroupRecordListVo> nationalGroupRecordConvert;

    /**
     * 分页查询NationalGroupRecord
     **/
    @Override
    public Page<NationalGroupRecord> query(Pagination<NationalGroupRecord> query) {
        query.setEntityClazz(NationalGroupRecord.class);
        Page<NationalGroupRecord> page = nationalGroupRecordDao.findAll(query, query.getPageRequest());
        return page;
    }

    /**
     * 创建NationalGroupRecord
     **/
    @Override
    public NationalGroupRecordVo addNationalGroupRecord(NationalGroupRecordBo nationalGroupRecordBo) {
        return nationalGroupRecordConvert.toVo(nationalGroupRecordDao.save(nationalGroupRecordConvert.toEntity(nationalGroupRecordBo)));
    }

    /**
     * 更新NationalGroupRecord
     **/
    @Override
    public NationalGroupRecordVo updateNationalGroupRecord(NationalGroupRecordBo nationalGroupRecordBo) {
        NationalGroupRecord dbNationalGroupRecord = nationalGroupRecordDao.getOne(nationalGroupRecordBo.getId());
        AttributeReplication.copying(nationalGroupRecordBo, dbNationalGroupRecord,
                NationalGroupRecord_.guid,
                NationalGroupRecord_.nationalGroup,
                NationalGroupRecord_.groupCode,
                NationalGroupRecord_.member,
                NationalGroupRecord_.groupPeople,
                NationalGroupRecord_.joinPeople,
                NationalGroupRecord_.openTime,
                NationalGroupRecord_.consignee,
                NationalGroupRecord_.consigneePhone,
                NationalGroupRecord_.consigneeAddr,
                NationalGroupRecord_.state,
                NationalGroupRecord_.createTime,
                NationalGroupRecord_.deleted,
                NationalGroupRecord_.delTime
        );
        return nationalGroupRecordConvert.toVo(dbNationalGroupRecord);
    }

    /**
     * 删除NationalGroupRecord
     **/
    @Override
    public void removeNationalGroupRecordById(int id) {
        nationalGroupRecordDao.deleteById(id);
    }

    /**
     * 根据ID得到NationalGroupRecord
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public NationalGroupRecordVo getNationalGroupRecordVoById(int id) {

        return nationalGroupRecordConvert.toVo(this.nationalGroupRecordDao.getOne(id));
    }

    /**
     * 根据ID得到NationalGroupRecordListVo
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public NationalGroupRecordVo getListVoById(int id) {
        return nationalGroupRecordConvert.toVo(this.nationalGroupRecordDao.getOne(id));
    }

    /**
     * 分页查询: NationalGroupRecord
     **/
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page
            <NationalGroupRecordListVo> queryListVo(Pagination<NationalGroupRecord> query) {

        Page<NationalGroupRecord> pages = this.query(query);

        List
                <NationalGroupRecordListVo> vos = nationalGroupRecordConvert.toListVos(pages.getContent());
        return new PageImpl
                <NationalGroupRecordListVo>(vos, query.getPageRequest(), pages.getTotalElements());

    }

    protected void initConvert() {
        this.nationalGroupRecordConvert = new EntityListVoBoSimpleConvert<NationalGroupRecord, NationalGroupRecordBo, NationalGroupRecordVo, NationalGroupRecordSimple, NationalGroupRecordListVo>(beanConvertManager) {
            @Override
            protected BeanConvert<NationalGroupRecord, NationalGroupRecordVo> createEntityToVoConvert(BeanConvertManager
                                                                                                              beanConvertManager) {
                return new AbstractBeanConvert<NationalGroupRecord, NationalGroupRecordVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(NationalGroupRecordVo NationalGroupRecordVo, NationalGroupRecord NationalGroupRecord) {

                    }
                };
            }


            @Override
            protected BeanConvert<NationalGroupRecord, NationalGroupRecordListVo> createEntityToListVoConvert(BeanConvertManager
                                                                                                                      beanConvertManager) {
                return new AbstractBeanConvert<NationalGroupRecord, NationalGroupRecordListVo>(beanConvertManager) {
                    @Override
                    protected void postConvert(NationalGroupRecordListVo NationalGroupRecordListVo, NationalGroupRecord NationalGroupRecord) {

                    }
                };
            }

            @Override
            protected BeanConvert<NationalGroupRecord, NationalGroupRecordBo> createEntityToBoConvert(BeanConvertManager
                                                                                                              beanConvertManager) {
                return new AbstractBeanConvert<NationalGroupRecord, NationalGroupRecordBo>(beanConvertManager) {
                    @Override
                    protected void postConvert(NationalGroupRecordBo NationalGroupRecordBo, NationalGroupRecord NationalGroupRecord) {

                    }
                };
            }

            @Override
            protected BeanConvert
                    <NationalGroupRecordBo
                            , NationalGroupRecord> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <NationalGroupRecordBo
                                , NationalGroupRecord>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert<NationalGroupRecord, NationalGroupRecordSimple> createEntityToSimpleConvert(BeanConvertManager
                                                                                                                      beanConvertManager) {
                return new AbstractBeanConvert<NationalGroupRecord, NationalGroupRecordSimple>(beanConvertManager) {
                };
            }

            @Override
            protected BeanConvert
                    <NationalGroupRecordSimple
                            , NationalGroupRecord> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
                return new AbstractBeanConvert
                        <NationalGroupRecordSimple
                                , NationalGroupRecord>(beanConvertManager) {
                    @Override
                    public NationalGroupRecord convert(NationalGroupRecordSimple NationalGroupRecordSimple) throws BeansException {
                        return nationalGroupRecordDao.getOne(NationalGroupRecordSimple.getId());
                    }
                };
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConvert();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public  List<NationalGroupRecordListVo> myCollage(int memberId){
        return  nationalGroupRecordConvert.toListVos(nationalGroupRecordDao.findByMemberId(memberId));
    }

    /**
     * 添加
     * @param nationalGroupRecord
     * @return
     */
    @Override
    public  NationalGroupRecord save(NationalGroupRecord nationalGroupRecord){
        return nationalGroupRecordDao.save(nationalGroupRecord);
    }


    /**
     * 根据id查询
     * @param nationalGroupRecordId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public  NationalGroupRecord getOne(int nationalGroupRecordId){
        return nationalGroupRecordDao.getOne(nationalGroupRecordId);
    }


    /**
     * entity转Vo
     * @param nationalGroupRecord
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public  NationalGroupRecordVo entityTurnVo(NationalGroupRecord nationalGroupRecord){
     return nationalGroupRecordConvert.toVo(nationalGroupRecord);
    }


}
