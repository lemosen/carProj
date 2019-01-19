/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yi.core.promotion.dao.GroupBuyActivityProductDao;
import com.yi.core.promotion.domain.bo.GroupBuyActivityProductBo;
import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct;
import com.yi.core.promotion.domain.entity.GroupBuyActivityProduct_;
import com.yi.core.promotion.domain.listVo.GroupBuyActivityProductListVo;
import com.yi.core.promotion.domain.simple.GroupBuyActivityProductSimple;
import com.yi.core.promotion.domain.vo.GroupBuyActivityProductVo;
import com.yi.core.promotion.service.IGroupBuyActivityProductService;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 *
 * @author yihz
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class GroupBuyActivityProductServiceImpl implements IGroupBuyActivityProductService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(GroupBuyActivityProductServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private GroupBuyActivityProductDao groupBuyActivityProductDao;

	private EntityListVoBoSimpleConvert<GroupBuyActivityProduct, GroupBuyActivityProductBo, GroupBuyActivityProductVo, GroupBuyActivityProductSimple, GroupBuyActivityProductListVo> groupBuyActivityProductConvert;

	/**
	 * 分页查询GroupBuyActivityProduct
	 **/
	@Override
	public Page<GroupBuyActivityProduct> query(Pagination<GroupBuyActivityProduct> query) {
		query.setEntityClazz(GroupBuyActivityProduct.class);
		Page<GroupBuyActivityProduct> page = groupBuyActivityProductDao.findAll(query, query.getPageRequest());
		return page;
	}

	/**
	 * 分页查询: GroupBuyActivityProduct
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<GroupBuyActivityProductListVo> queryListVo(Pagination<GroupBuyActivityProduct> query) {
		Page<GroupBuyActivityProduct> pages = this.query(query);
		List<GroupBuyActivityProductListVo> vos = groupBuyActivityProductConvert.toListVos(pages.getContent());
		return new PageImpl<GroupBuyActivityProductListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 创建GroupBuyActivityProduct
	 **/
	@Override
	public GroupBuyActivityProduct addGroupBuyActivityProduct(GroupBuyActivityProduct groupBuyActivityProduct) {
		return groupBuyActivityProductDao.save(groupBuyActivityProduct);
	}

	/**
	 * 创建GroupBuyActivityProduct
	 **/
	@Override
	public GroupBuyActivityProductListVo addGroupBuyActivityProduct(GroupBuyActivityProductBo groupBuyActivityProductBo) {
		return groupBuyActivityProductConvert.toListVo(groupBuyActivityProductDao.save(groupBuyActivityProductConvert.toEntity(groupBuyActivityProductBo)));
	}

	/**
	 * 更新GroupBuyActivityProduct
	 **/
	@Override
	public GroupBuyActivityProduct updateGroupBuyActivityProduct(GroupBuyActivityProduct groupBuyActivityProduct) {
		GroupBuyActivityProduct dbGroupBuyActivityProduct = groupBuyActivityProductDao.getOne(groupBuyActivityProduct.getId());
		AttributeReplication.copying(groupBuyActivityProduct, dbGroupBuyActivityProduct, GroupBuyActivityProduct_.groupBuyActivity,
				GroupBuyActivityProduct_.commodity, GroupBuyActivityProduct_.product, GroupBuyActivityProduct_.groupBuyPrice, GroupBuyActivityProduct_.stockUpQuantity);
		return dbGroupBuyActivityProduct;
	}

	/**
	 * 更新GroupBuyActivityProduct
	 **/
	@Override
	public GroupBuyActivityProductListVo updateGroupBuyActivityProduct(GroupBuyActivityProductBo groupBuyActivityProductBo) {
		GroupBuyActivityProduct dbGroupBuyActivityProduct = groupBuyActivityProductDao.getOne(groupBuyActivityProductBo.getId());
		AttributeReplication.copying(groupBuyActivityProductBo, dbGroupBuyActivityProduct, GroupBuyActivityProduct_.guid, GroupBuyActivityProduct_.groupBuyActivity,
				GroupBuyActivityProduct_.commodity, GroupBuyActivityProduct_.product, GroupBuyActivityProduct_.groupBuyPrice, GroupBuyActivityProduct_.stockUpQuantity,
				GroupBuyActivityProduct_.injectWater, GroupBuyActivityProduct_.boughtQuantity);
		return groupBuyActivityProductConvert.toListVo(dbGroupBuyActivityProduct);
	}

	/**
	 * 删除GroupBuyActivityProduct
	 **/
	@Override
	public void removeGroupBuyActivityProductById(int id) {
		groupBuyActivityProductDao.deleteById(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityProductBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityProduct getById(int id) {
		return this.groupBuyActivityProductDao.getOne(id);
	}

	/**
	 * 根据ID得到GroupBuyActivityProductBo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityProductBo getBoById(int id) {
		return groupBuyActivityProductConvert.toBo(this.groupBuyActivityProductDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityProductVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityProductVo getVoById(int id) {
		return groupBuyActivityProductConvert.toVo(this.groupBuyActivityProductDao.getOne(id));
	}

	/**
	 * 根据ID得到GroupBuyActivityProductListVo
	 **/
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public GroupBuyActivityProductListVo getListVoById(int id) {
		return groupBuyActivityProductConvert.toListVo(this.groupBuyActivityProductDao.getOne(id));
	}

	protected void initConvert() {
		this.groupBuyActivityProductConvert = new EntityListVoBoSimpleConvert<GroupBuyActivityProduct, GroupBuyActivityProductBo, GroupBuyActivityProductVo, GroupBuyActivityProductSimple, GroupBuyActivityProductListVo>(
				beanConvertManager) {
			@Override
			protected BeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityProductVo groupBuyActivityProductVo, GroupBuyActivityProduct groupBuyActivityProduct) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductListVo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityProductListVo groupBuyActivityProductListVo, GroupBuyActivityProduct groupBuyActivityProduct) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductBo>(beanConvertManager) {
					@Override
					protected void postConvert(GroupBuyActivityProductBo groupBuyActivityProductBo, GroupBuyActivityProduct groupBuyActivityProduct) {
					}
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityProductBo, GroupBuyActivityProduct> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProductBo, GroupBuyActivityProduct>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProduct, GroupBuyActivityProductSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<GroupBuyActivityProductSimple, GroupBuyActivityProduct> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<GroupBuyActivityProductSimple, GroupBuyActivityProduct>(beanConvertManager) {
					@Override
					public GroupBuyActivityProduct convert(GroupBuyActivityProductSimple groupBuyActivityProductSimple) throws BeansException {
						return groupBuyActivityProductDao.getOne(groupBuyActivityProductSimple.getId());
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
	public void batchAddGroupBuyActivityProduct(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityProduct> groupBuyActivityProducts) {
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityProducts)) {
			groupBuyActivityProducts.forEach(tmp -> {
				tmp.setGroupBuyActivity(groupBuyActivity);
			});
			groupBuyActivityProductDao.saveAll(groupBuyActivityProducts);
		}
	}

	@Override
	public void batchUpdateGroupBuyActivityProduct(GroupBuyActivity groupBuyActivity, Collection<GroupBuyActivityProduct> groupBuyActivityProducts) {
		// TODO Auto-generated method stub
		if (groupBuyActivity != null && CollectionUtils.isNotEmpty(groupBuyActivityProducts)) {
			// 查询当前活动的会员集合
			List<GroupBuyActivityProduct> dbGroupBuyActivityProducts = groupBuyActivityProductDao.findByGroupBuyActivity_Id(groupBuyActivity.getId());
			// 需要新增的数据
			Set<GroupBuyActivityProduct> saveSets = groupBuyActivityProducts.stream().filter(e1 -> dbGroupBuyActivityProducts.stream().noneMatch(e2 -> e1.getId() == e2.getId()				)).collect(Collectors.toSet());
			// 需要更新的数据
			Set<GroupBuyActivityProduct> updateSets = groupBuyActivityProducts.stream().filter(e1 -> dbGroupBuyActivityProducts.stream().anyMatch(e2 -> e1.getId() == 				e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<GroupBuyActivityProduct> deleteSets = dbGroupBuyActivityProducts.stream().filter(e1 -> groupBuyActivityProducts.stream().noneMatch(e2 -> e1.getId() == 				e2.getId())).collect(Collectors.toSet());
			// 保存数据
			saveSets.forEach(e -> {
				e.setGroupBuyActivity(groupBuyActivity);
			});
			groupBuyActivityProductDao.saveAll(saveSets);
			// 删除数据
			deleteSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					groupBuyActivityProductDao.delete(e);
				}
			});
			// 修改数据
			updateSets.forEach(e -> {
				if (e != null && e.getId() > 0) {
					e.setGroupBuyActivity(groupBuyActivity);
					this.updateGroupBuyActivityProduct(e);
				}
			});
		}
	}
}
