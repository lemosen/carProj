/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yi.core.activity.ActivityEnum;
import com.yi.core.activity.dao.CouponDao;
import com.yi.core.activity.domain.entity.Coupon;
import com.yi.core.activity.domain.entity.CouponGrantConfig;
import com.yi.core.attachment.domain.vo.AttachmentVo;
import com.yi.core.attachment.service.IAttachmentService;
import com.yi.core.basic.domain.entity.Area_;
import com.yi.core.basic.domain.entity.Region;
import com.yi.core.basic.domain.entity.Region_;
import com.yi.core.basic.service.IRegionService;
import com.yi.core.cart.dao.CartDao;
import com.yi.core.cms.domain.entity.OperateCategory;
import com.yi.core.cms.service.IOperateCategoryService;
import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.dao.CommentDao;
import com.yi.core.commodity.dao.CommodityDao;
import com.yi.core.commodity.domain.bo.CommodityBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Commodity_;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.simple.CommodityLevelDiscountSimple;
import com.yi.core.commodity.domain.simple.CommoditySimple;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.commodity.domain.vo.CommodityListVo;
import com.yi.core.commodity.domain.vo.CommodityVo;
import com.yi.core.commodity.service.IAttributeGroupService;
import com.yi.core.commodity.service.ICategoryService;
import com.yi.core.commodity.service.ICommentService;
import com.yi.core.commodity.service.ICommodityLevelDiscountService;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.commodity.service.IStockService;
import com.yi.core.common.Deleted;
import com.yi.core.common.FileType;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.common.ObjectType;
import com.yi.core.member.service.IMemberLevelService;
import com.yi.core.supplier.domain.entity.Supplier_;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 商品
 *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class CommodityServiceImpl implements ICommodityService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(CommodityServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private CommodityDao commodityDao;

	@Resource
	private CartDao cartDao;

	@Resource
	private CommentDao commentDao;

	@Resource
	private ICommentService commentService;

	@Resource
	private IAttributeGroupService attributeGroupService;

	@Resource
	private IProductService productService;

	@Resource
	private IMemberLevelService memberLevelService;

	@Resource
	private ICategoryService categoryService;

	@Resource
	private CouponDao couponDao;

	@Resource
	private IRegionService regionService;

	@Resource
	private IAttachmentService attachmentService;

	@Resource
	private ICommodityLevelDiscountService commodityLevelDiscountService;

	@Resource
	private IStockService stockService;

	@Resource
	private IOperateCategoryService operateCategoryService;

	private EntityListVoBoSimpleConvert<Commodity, CommodityBo, CommodityVo, CommoditySimple, CommodityListVo> commodityConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Commodity> query(Pagination<Commodity> query) {
		query.setEntityClazz(Commodity.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.deleted), Deleted.DEL_FALSE)));
			// 根据报考分类id查询中间表 过滤商品分类
			Object categoryId = query.getParams().get("operateCategories.id");
			if (categoryId != null) {
				SetJoin<Commodity, OperateCategory> orgHospReportJoin = root.join(root.getModel().getDeclaredSet("operateCategories", OperateCategory.class), JoinType.LEFT);
				Predicate predicate = criteriaBuilder.equal(orgHospReportJoin.get("id").as(Integer.class), categoryId);
				list.add(predicate);
			}
			// 模糊
			Object name = query.getParams().get("name");
			if (name != null) {
				list.add(criteriaBuilder.like(root.get(Commodity_.commodityName), "%" + name + "%"));
			}
			// 供应商
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.supplier).get(Supplier_.id), supplierId)));
			}
		}));
		Page<Commodity> page = commodityDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommodityListVo> queryListVo(Pagination<Commodity> query) {
		query.setEntityClazz(Commodity.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.deleted), Deleted.DEL_FALSE)));
			list1.add(criteriaBuilder.desc(root.get(Commodity_.createTime)));
			// 根据报考分类id查询中间表 过滤商品分类
			Object categoryId = query.getParams().get("operateCategories.id");
			if (categoryId != null) {
				SetJoin<Commodity, OperateCategory> orgHospReportJoin = root.join(root.getModel().getDeclaredSet("operateCategories", OperateCategory.class), JoinType.LEFT);
				Predicate predicate = criteriaBuilder.equal(orgHospReportJoin.get("id").as(Integer.class), categoryId);
				list.add(predicate);
			}
			// 模糊
			Object name = query.getParams().get("name");
			if (name != null) {
				list.add(criteriaBuilder.like(root.get(Commodity_.commodityName), "%" + name + "%"));
			}
			// 供应商
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.supplier).get(Supplier_.id), supplierId)));
			}
		}));
		Page<Commodity> pages = commodityDao.findAll(query, query.getPageRequest());
		pages.getContent().forEach(tmp -> {
			tmp.setCategory(null);
		});
		List<CommodityListVo> vos = commodityConvert.toListVos(pages.getContent());
		return new PageImpl<CommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	/**
	 * 供应商分页查询: CommodityListVo
	 *
	 * @param query
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommodityListVo> queryListVoForSupplier(Pagination<Commodity> query) {
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list1.add(criteriaBuilder.asc(root.get(Commodity_.sort)));
		}));
		Page<Commodity> pages = this.query(query);
		List<CommodityListVo> vos = commodityConvert.toListVos(pages.getContent());
		return new PageImpl<CommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommodityListVo> queryListVoForApp(Pagination<Commodity> query) {
		query.setEntityClazz(Commodity.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			// 根据报考分类id查询中间表 过滤商品分类
			Object categoryId = query.getParams().get("operateCategories.id");
			if (categoryId != null) {
				SetJoin<Commodity, OperateCategory> orgHospReportJoin = root.join(root.getModel().getDeclaredSet("operateCategories", OperateCategory.class), JoinType.LEFT);
				Predicate predicate = criteriaBuilder.equal(orgHospReportJoin.get("id").as(Integer.class), categoryId);
				list.add(predicate);
			}
			// 根据定位城市查询商品
			Object city = query.getParams().get("city");
			if (city != null) {
				SetJoin<Commodity, Region> orgHospReportJoin = root.join(root.getModel().getDeclaredSet("regions", Region.class), JoinType.LEFT);
				Predicate predicate = criteriaBuilder.like(orgHospReportJoin.get(Region_.area).get(Area_.name), "%" + city + "%");
				list.add(predicate);
			}
			// 模糊
			Object name = query.getParams().get("name");
			if (name != null) {
				list.add(criteriaBuilder.like(root.get(Commodity_.commodityName), "%" + name + "%"));
			}
			// 供应商
			Object supplierId = query.getParams().get("supplier.id");
			if (supplierId != null) {
				list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.supplier).get(Supplier_.id), supplierId)));
			}
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.shelf), CommodityEnum.SHELF_ON.getCode())));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.state), CommodityEnum.STATE_AGREE.getCode())));
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Commodity_.deleted), Deleted.DEL_FALSE)));
//			list1.add(criteriaBuilder.asc(root.get(Commodity_.sort)));
//			list1.add(criteriaBuilder.desc(root.get(Commodity_.createTime)));
		}));
		Page<Commodity> pages = commodityDao.findAll(query, query.getPageRequest());
		List<CommodityListVo> vos = commodityConvert.toListVos(pages.getContent());
		return new PageImpl<CommodityListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Commodity getById(int commodityId) {
		if (commodityDao.existsById(commodityId)) {
			return commodityDao.getOne(commodityId);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommodityVo getVoById(int commodityId) {
		Commodity dbCommodity = this.getById(commodityId);
		if (dbCommodity != null) {
			if (dbCommodity.getProducts() != null) {
				dbCommodity.setProducts(null);
				dbCommodity.setProducts(productService.findByCommodity_idAndDeleted(commodityId));
			}
			CommodityVo dbCommodityVo = commodityConvert.toVo(dbCommodity);
			// 获取图片集合
			List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(commodityId, ObjectType.COMMODITY);
			dbCommodityVo.setAttachmentVos(attachmentVos);
			return dbCommodityVo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommodityVo getVoByIdForApp(int commodityId) {
		Commodity dbCommodity = this.getById(commodityId);
		if (dbCommodity != null) {
//			if (dbCommodity.getCouponGrantConfig() != null && ActivityEnum.STATE_DISABLE.getCode().equals(dbCommodity.getCouponGrantConfig().getState())) {
//				dbCommodity.setCouponGrantConfig(null);
//			} else {
//				dbCommodity.getCouponGrantConfig().setCouponGrantSteps(dbCommodity.getCouponGrantConfig().getCouponGrantSteps().stream()
//						.filter(tmp -> tmp.getGrantRate().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList()));
//			}
			if (dbCommodity.getProducts() != null) {
				dbCommodity.setProducts(null);
				dbCommodity.setProducts(productService.findByCommodity_idAndDeleted(commodityId));
			}
			CommodityVo dbCommodityVo = commodityConvert.toVo(dbCommodity);
			if (CollectionUtils.isNotEmpty(dbCommodityVo.getCommodityLevelDiscounts()) && CollectionUtils.isNotEmpty(dbCommodityVo.getProducts())) {
				List<CommodityLevelDiscountSimple> commodityLevelDiscounts = dbCommodityVo.getCommodityLevelDiscounts();
				for (ProductSimple product : dbCommodityVo.getProducts()) {
					if (product != null && CollectionUtils.isNotEmpty(commodityLevelDiscounts)) {
						List<Object> levelPrices = new ArrayList<>();
						for (CommodityLevelDiscountSimple levelDiscount : commodityLevelDiscounts) {
							if (levelDiscount != null && levelDiscount.getMemberLevel() != null && levelDiscount.getDiscount() != null) {
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("level", levelDiscount.getMemberLevel().getId());
								jsonObject.put("levelPrice",
										product.getCurrentPrice().multiply(levelDiscount.getDiscount()).divide(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_UP));
								levelPrices.add(jsonObject);
							}
						}
						product.setLevelPrices(levelPrices);
					}
				}
			}
			// 获取图片集合
			List<AttachmentVo> attachmentVos = attachmentService.findByObjectIdAndObjectType(commodityId, ObjectType.COMMODITY);
			dbCommodityVo.setAttachmentVos(attachmentVos);
			return dbCommodityVo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommodityListVo getListVoById(int commodityId) {
		CommodityListVo dbCommodityListVo = commodityConvert.toListVo(this.commodityDao.getOne(commodityId));
		return dbCommodityListVo;
	}

	@Override
	public Commodity addCommodity(Commodity commodity) {
		if (commodity == null || CollectionUtils.isEmpty(commodity.getProducts())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (commodity.getSupplier() == null) {
			throw new BusinessException("供应商不能为空");
		}
		if (CollectionUtils.isEmpty(commodity.getRegions())) {
			throw new BusinessException("销售地区不能为空");
		}
		if (commodity.getCategory() == null) {
			throw new BusinessException("商品分类不能为空");
		}
		if (commodity.getOperateCategories() == null) {
			throw new BusinessException("报考分类不能为空");
		}
		// 运费模板
		if (CommodityEnum.FREIGHT_SET_TEMPLATE.getCode().equals(commodity.getFreightSet())) {
			if (commodity.getFreightTemplate() == null) {
				throw new BusinessException("运费模板不能为空");
			}
		} else {
			commodity.setFreightTemplate(null);
		}
		// 商品编码
		if (StringUtils.isBlank(commodity.getCommodityNo())) {
			String code = NumberGenerateUtils.generateCommodityNo(Optional.ofNullable(commodity.getSupplier()).map(tmp -> tmp.getSupplierName()).orElse(""));
			commodity.setCommodityNo(code);
		}
		// 校验商品编码或名称
		commodity.setCommodityName(commodity.getCommodityName().trim());
		int checkCodeOrName = commodityDao.countBySupplierIdAndCommodityNoOrCommodityNameAndDeleted(commodity.getSupplier().getId(), commodity.getCommodityNo(),
				commodity.getCommodityName(), Deleted.DEL_FALSE);
		if (checkCodeOrName > 0) {
			throw new BusinessException("商品编码或名称不能重复");
		}
		// 计算商品显示价格
		commodity = this.calculateCommodityPrice(commodity);
		// 属性值 属性
		Map<String, Attribute> attributeMap = attributeGroupService.addAttributeGroupByCommodity(commodity.getCategory(), commodity.getAttributeGroups());
		// // 封装 货品属性
		// for (Product product : commodity.getProducts()) {
		// if (CollectionUtils.isNotEmpty(product.getAttributes())) {
		// for (Attribute tmpAttr : product.getAttributes()) {
		// if (tmpAttr != null) {
		// if (attributeMap.containsKey(tmpAttr.getAttrValue()) &&
		// attributeMap.get(tmpAttr.getAttrValue()).getAttrName().equals(tmpAttr.getAttrName()))
		// {
		// tmpAttr.setId(attributeMap.get(tmpAttr.getAttrValue()).getId());
		// tmpAttr.setAttributeGroup(attributeMap.get(tmpAttr.getAttrValue()).getAttributeGroup());
		// }
		// }
		// }
		// }
		// }
		Commodity dbCommodity = commodityDao.save(commodity);
		// 保存货品
		List<Product> dbProducts = productService.batchAddProduct(dbCommodity, commodity.getProducts(), attributeMap);
		// 保存库存
		if (CollectionUtils.isNotEmpty(dbProducts)) {
			stockService.batchAddStock(dbProducts, dbCommodity);
		}
		// 保存会员等级折扣
		commodityLevelDiscountService.batchAddLevelDiscount(dbCommodity, commodity.getCommodityLevelDiscounts());
		return dbCommodity;
	}

	@Override
	public CommodityVo addCommodity(CommodityBo commodityBo) {
		// 保存数据
		Commodity dbCommodity = this.addCommodity(commodityConvert.toEntity(commodityBo));
		// 保存商品图片
		if (CollectionUtils.isNotEmpty(commodityBo.getAttachmentVos())) {
			for (AttachmentVo tmp : commodityBo.getAttachmentVos()) {
				tmp.setAttachId(null);
				tmp.setObjectId(dbCommodity.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.COMMODITY);
				tmp.setDescription(commodityBo.getCommodityName());
				tmp.setFilePath(tmp.getUrl());
				tmp.setSupplierId(Optional.ofNullable(dbCommodity.getSupplier()).map(e -> e.getId()).orElse(null));
				tmp.setSupplierName(Optional.ofNullable(dbCommodity.getSupplier()).map(e -> e.getSupplierName()).orElse(null));
			}
			attachmentService.saveAll(commodityBo.getAttachmentVos());
		}
		// 保存商品详情图片
		if (CollectionUtils.isNotEmpty(commodityBo.getDescAttachmentVos())) {
			for (AttachmentVo tmp : commodityBo.getDescAttachmentVos()) {
				tmp.setObjectId(dbCommodity.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.COMMODITY_DETAIL);
				tmp.setDescription(commodityBo.getCommodityName());
				tmp.setFilePath(tmp.getUrl());
				tmp.setSupplierId(dbCommodity.getSupplier().getId());
				tmp.setSupplierName(dbCommodity.getSupplier().getSupplierName());
			}
			attachmentService.saveAll(commodityBo.getDescAttachmentVos());
		}
		return commodityConvert.toVo(dbCommodity);
	}

	@Override
	public Commodity updateCommodity(Commodity commodity) {
		if (commodity == null || commodity.getId() < 1 || CollectionUtils.isEmpty(commodity.getProducts())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (commodity.getSupplier() == null) {
			throw new BusinessException("供应商不能为空");
		}
		if (CollectionUtils.isEmpty(commodity.getRegions())) {
			throw new BusinessException("销售地区不能为空");
		}
		if (commodity.getCategory() == null) {
			throw new BusinessException("商品分类不能为空");
		}
		if (commodity.getOperateCategories() == null) {
			throw new BusinessException("报考分类不能为空");
		}
		// 运费模板
		if (CommodityEnum.FREIGHT_SET_TEMPLATE.getCode().equals(commodity.getFreightSet())) {
			if (commodity.getFreightTemplate() == null) {
				throw new BusinessException("运费模板不能为空");
			}
		} else {
			commodity.setFreightTemplate(null);
		}
		// 商品编码
		if (StringUtils.isBlank(commodity.getCommodityNo())) {
			String code = NumberGenerateUtils.generateCommodityNo(Optional.ofNullable(commodity.getSupplier()).map(tmp -> tmp.getSupplierName()).orElse(""));
			commodity.setCommodityNo(code);
		}
		// 校验商品编码或名称
		commodity.setCommodityName(commodity.getCommodityName().trim());
		int checkCodeOrName = commodityDao.countBySupplierIdAndCommodityNoOrCommodityNameAndDeletedAndIdNot(commodity.getSupplier().getId(), commodity.getCommodityNo(),
				commodity.getCommodityName(), Deleted.DEL_FALSE, commodity.getId());
		if (checkCodeOrName > 0) {
			throw new BusinessException("商品编码或名称不能重复");
		}
		// 计算商品显示价格
		commodity = this.calculateCommodityPrice(commodity);
		// 属性值 属性
		Map<String, Attribute> attributeMap = attributeGroupService.addAttributeGroupByCommodity(commodity.getCategory(), commodity.getAttributeGroups());
		// // 封装 货品属性
		// for (Product product : commodity.getProducts()) {
		// if (CollectionUtils.isNotEmpty(product.getAttributes())) {
		// for (Attribute tmpAttr : product.getAttributes()) {
		// if (tmpAttr != null) {
		// if (attributeMap.containsKey(tmpAttr.getAttrValue()) &&
		// attributeMap.get(tmpAttr.getAttrValue()).getAttrName().equals(tmpAttr.getAttrName()))
		// {
		// tmpAttr.setId(attributeMap.get(tmpAttr.getAttrValue()).getId());
		// tmpAttr.setAttributeGroup(attributeMap.get(tmpAttr.getAttrValue()).getAttributeGroup());
		// tmpAttr.getProducts().add(product);
		// }
		// }
		// }
		// }
		// }

		// 更新商品
		Commodity dbCommodity = commodityDao.getOne(commodity.getId());
		AttributeReplication.copying(commodity, dbCommodity, Commodity_.commodityType, Commodity_.commodityNo, Commodity_.commodityName, Commodity_.commodityShortName,
				Commodity_.imgPath, Commodity_.category, Commodity_.supplier, Commodity_.sort, Commodity_.originalPrice, Commodity_.currentPrice, Commodity_.supplyPrice,
				Commodity_.vipPrice, Commodity_.discountInfo, Commodity_.distribution, Commodity_.commissionRate, Commodity_.integralRate, Commodity_.freightSet,
				Commodity_.unifiedFreight, Commodity_.freightTemplate, Commodity_.stockSet, Commodity_.volume, Commodity_.weight, Commodity_.shelf, Commodity_.shelfState,
				Commodity_.description, Commodity_.couponGrantConfig);
		// 更新货品
		List<Product> dbProducts = productService.batchUpdateProduct(dbCommodity, commodity.getProducts(), attributeMap);
		// 更新库存
		if (CollectionUtils.isNotEmpty(dbProducts)) {
			stockService.batchUpdateStock(dbProducts, dbCommodity);
		}
		// 更新销售地区
		if (CollectionUtils.isNotEmpty(commodity.getRegions())) {
			List<Integer> ids = commodity.getRegions().stream().map(e -> e.getId()).collect(Collectors.toList());
			List<Region> tmpRegions = regionService.getRegionsByIds(ids);
			dbCommodity.setRegions(tmpRegions.stream().collect(Collectors.toSet()));
		}
		// 更新报考分类
		if (CollectionUtils.isNotEmpty(commodity.getOperateCategories())) {
			List<Integer> ids = commodity.getOperateCategories().stream().map(e -> e.getId()).collect(Collectors.toList());
			List<OperateCategory> tmpOperateCategories = operateCategoryService.getOperateCategoriesByIds(ids);
			dbCommodity.setOperateCategories(tmpOperateCategories.stream().collect(Collectors.toSet()));
		}
		// 更新会员折扣
		commodityLevelDiscountService.batchUpdateLevelDiscount(dbCommodity, commodity.getCommodityLevelDiscounts());
		return dbCommodity;
	}

	/**
	 * 修改商品销售数量
	 *
	 * @param saleQuantity
	 * @param commodityId
	 * @return
	 */
	@Override
	public int updateCommodityBySaleQuantity(int saleQuantity, int commodityId) {
		return commodityDao.updateCommodityBySaleQuantity(saleQuantity, commodityId);
	}

	/**
	 * 修改商品评价数量
	 *
	 * @param commodityId
	 * @return
	 */
	@Override
	public int updateCommodityByCommentQuantity(int commodityId) {
		return commodityDao.updateCommodityByCommentQuantity(commodityId);
	}

	@Override
	public CommodityVo updateCommodity(CommodityBo commodityBo) {
		// 更新数据
		Commodity dbCommodity = this.updateCommodity(commodityConvert.toEntity(commodityBo));
		// 保存图片
		if (CollectionUtils.isNotEmpty(commodityBo.getAttachmentVos())) {
			// 获取图片集合 整理图片
			List<AttachmentVo> dbAttachmentVos = attachmentService.findByObjectIdAndObjectType(commodityBo.getId(), ObjectType.COMMODITY);
			if (CollectionUtils.isEmpty(dbAttachmentVos)) {
				for (AttachmentVo tmp : commodityBo.getAttachmentVos()) {
					if (tmp != null) {
						tmp.setObjectId(commodityBo.getId());
						tmp.setFileType(FileType.PICTURES);
						tmp.setObjectType(ObjectType.COMMODITY);
						tmp.setDescription(commodityBo.getCommodityName());
						tmp.setFilePath(tmp.getUrl());
						tmp.setSupplierId(dbCommodity.getSupplier().getId());
						tmp.setSupplierName(dbCommodity.getSupplier().getSupplierName());
					}
				}
				attachmentService.saveAll(commodityBo.getAttachmentVos());
			} else {
				// 需要新增的数据
				List<AttachmentVo> saveLists = commodityBo.getAttachmentVos().stream().filter(e1 -> e1.getAttachId() == null).collect(Collectors.toList());
				// 需要删除的数据
				List<AttachmentVo> deleteLists = dbAttachmentVos.stream()
						.filter(e1 -> commodityBo.getAttachmentVos().stream().noneMatch(e2 -> e1.getAttachId().equals(e2.getAttachId()))).collect(Collectors.toList());
				for (AttachmentVo tmp : saveLists) {
					if (tmp != null) {
						tmp.setObjectId(commodityBo.getId());
						tmp.setFileType(FileType.PICTURES);
						tmp.setObjectType(ObjectType.COMMODITY);
						tmp.setDescription(commodityBo.getCommodityName());
						tmp.setFilePath(tmp.getUrl());
						tmp.setSupplierId(dbCommodity.getSupplier().getId());
						tmp.setSupplierName(dbCommodity.getSupplier().getSupplierName());
					}
				}
				attachmentService.saveAll(saveLists);
				attachmentService.deleteList(deleteLists);
			}
		}
		// 保存商品详情图片
		if (CollectionUtils.isNotEmpty(commodityBo.getDescAttachmentVos())) {
			// 删除图片 重新添加
			attachmentService.deleteByObjectIdAndObjectType(dbCommodity.getId(), ObjectType.COMMODITY_DETAIL);
			for (AttachmentVo tmp : commodityBo.getDescAttachmentVos()) {
				tmp.setAttachId(null);
				tmp.setObjectId(dbCommodity.getId());
				tmp.setFileType(FileType.PICTURES);
				tmp.setObjectType(ObjectType.COMMODITY_DETAIL);
				tmp.setDescription(dbCommodity.getCommodityName());
				tmp.setFilePath(tmp.getUrl());
				tmp.setSupplierId(dbCommodity.getSupplier().getId());
				tmp.setSupplierName(dbCommodity.getSupplier().getSupplierName());
			}
			attachmentService.saveAll(commodityBo.getDescAttachmentVos());
		}
		return commodityConvert.toVo(dbCommodity);
	}

	@Override
	public void removeCommodityById(int commodityId) {
		Commodity dbCommodity = commodityDao.getOne(commodityId);
		if (dbCommodity != null) {
			// 货品
			dbCommodity.getProducts().forEach(e -> {
				e.setDeleted(Deleted.DEL_TRUE);
				e.setDelTime(new Date());
			});
			// 评论
			dbCommodity.getComments().forEach(e -> {
				e.setDeleted(Deleted.DEL_TRUE);
				e.setDelTime(new Date());
			});
			dbCommodity.setDeleted(Deleted.DEL_TRUE);
			dbCommodity.setDelTime(new Date());
			// 删除属性组中间表数据
			// dbCommodity.setAttributeGroups(null);
			// 删除会员等级折扣中间表数据
			dbCommodity.setCommodityLevelDiscounts(null);
			// 删除报考分类中间表数据
			dbCommodity.setOperateCategories(null);
			// 删除销售地区中间表数据
			dbCommodity.setRegions(null);
			// // 删除文章中间表数据
			// dbCommodity.setArticles(null);
			// // 删除优惠券中间表
			// dbCommodity.setCoupons(null);
			// // 删除推荐中间表数据
			// dbCommodity.setRecommends(null);
		}
	}

	protected void initConvert() {
		this.commodityConvert = new EntityListVoBoSimpleConvert<Commodity, CommodityBo, CommodityVo, CommoditySimple, CommodityListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Commodity, CommodityVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Commodity, CommodityVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityVo commodityVo, Commodity commodity) {
					}
				};
			}

			@Override
			protected BeanConvert<Commodity, CommodityListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Commodity, CommodityListVo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityListVo commodityListVo, Commodity commodity) {
						if (commodity.getCouponGrantConfig() != null && ActivityEnum.STATE_ENABLE.getCode().equals(commodity.getCouponGrantConfig().getState())
								&& commodity.getCouponGrantConfig().getCoupon() != null) {
							commodityListVo.setReturnVoucher(commodity.getCouponGrantConfig().getCoupon().getParValue());
						}
					}
				};
			}

			@Override
			protected BeanConvert<Commodity, CommodityBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Commodity, CommodityBo>(beanConvertManager) {
					@Override
					protected void postConvert(CommodityBo commodityBo, Commodity commodity) {

					}
				};
			}

			@Override
			protected BeanConvert<CommodityBo, Commodity> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommodityBo, Commodity>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Commodity, CommoditySimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Commodity, CommoditySimple>(beanConvertManager) {
					@Override
					protected void postConvert(CommoditySimple commoditySimple, Commodity commodity) {
						if (commodity.getCouponGrantConfig() != null && ActivityEnum.STATE_ENABLE.getCode().equals(commodity.getCouponGrantConfig().getState())
								&& commodity.getCouponGrantConfig().getCoupon() != null) {
							commoditySimple.setReturnVoucher(commodity.getCouponGrantConfig().getCoupon().getParValue());
						}
					}
				};
			}

			@Override
			protected BeanConvert<CommoditySimple, Commodity> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<CommoditySimple, Commodity>(beanConvertManager) {
					@Override
					public Commodity convert(CommoditySimple commoditySimple) throws BeansException {
						return commodityDao.getOne(commoditySimple.getId());
					}
				};
			}
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initConvert();
	}

	/**
	 * 修改商品上下架状态
	 *
	 * @param commodityId
	 * @return
	 */
	public Commodity upAndDown(int commodityId) {
		Commodity dbCommodity = commodityDao.getOne(commodityId);
		if (dbCommodity != null) {
			if (CommodityEnum.SHELF_ON.getCode().equals(dbCommodity.getShelf())) {
				dbCommodity.setShelf(CommodityEnum.SHELF_WAREHOUSE.getCode());
			} else if (CommodityEnum.SHELF_WAREHOUSE.getCode().equals(dbCommodity.getShelf())) {
				dbCommodity.setShelf(CommodityEnum.SHELF_ON.getCode());
			}
		}
		return dbCommodity;
	}

	/**
	 * 商品重新申请上架
	 *
	 * @param commodityId
	 * @return
	 */
	public Commodity applyOnTheShelf(int commodityId) {
		Commodity dbCommodity = commodityDao.getOne(commodityId);
		if (dbCommodity != null) {
			dbCommodity.setState(CommodityEnum.STATE_APPLY.getCode());
		}
		return dbCommodity;
	}

	/**
	 * 确认审核
	 */
	@Override
	public Commodity updateEnable(int id) {
		Commodity commodity = commodityDao.getOne(id);
		commodity.setState(CommodityEnum.STATE_AGREE.getCode());
		return commodity;
	}

	/**
	 * 拒绝审核
	 */
	@Override
	public Commodity updateDisable(int id) {
		Commodity commodity = commodityDao.getOne(id);
		commodity.setState(CommodityEnum.STATE_REFUSE.getCode());
		return commodity;
	}

	@Override
	public int getCommodityNum() {
		return commodityDao.countByDeletedAndStateAndShelf(Deleted.DEL_FALSE, CommodityEnum.STATE_AGREE.getCode(), CommodityEnum.SHELF_ON.getCode());
	}

	/**
	 * 统计供应商商品数量
	 *
	 * @return
	 */
	@Override
	public int getCommodityNumBySupplier(int supplierId) {
		return commodityDao.countByDeletedAndStateAndShelfAndSupplierId(Deleted.DEL_FALSE, CommodityEnum.STATE_AGREE.getCode(), CommodityEnum.SHELF_ON.getCode(), supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Commodity> getAllNotInId() {
		List<Coupon> couponVos = couponDao.findByCouponTypeAndDeleted(CommodityEnum.COUPON_TYPE_GIVE.getCode(), Deleted.DEL_FALSE);
		System.out.println(couponVos.size());
		List<Commodity> commodityId = new ArrayList<>();
		for (Coupon coupon : couponVos) {
			if (CollectionUtils.isNotEmpty(couponVos)) {
				for (Commodity commodity : coupon.getCommodities()) {
					commodityId.add(commodity);
				}
			}
		}
		List<Commodity> resList = new ArrayList<>();// 去除子集后的集合
		for (Commodity bean : commodityDao.findByShelfAndDeleted(CommodityEnum.SHELF_ON.getCode(), Deleted.DEL_FALSE)) {
			boolean flag = true;
			for (Commodity newBean : commodityId) {
				if (newBean.getId() == bean.getId()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				resList.add(bean);
			}
		}
		List<Commodity> commodity = commodityDao.findAll();
		return resList;
	}

	/**
	 * 统计商品数量
	 *
	 * @param deleted
	 * @param state
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByDeletedAndStateAndShelf(Integer deleted, Integer state, Integer shelf) {
		return commodityDao.countByDeletedAndStateAndShelf(deleted, state, shelf);
	}

	/**
	 * TODO 待优化计算商品显示价格
	 *
	 * @param
	 * @return
	 */
	private Commodity calculateCommodityPrice(Commodity commodity) {
		if (commodity != null && CollectionUtils.isNotEmpty(commodity.getProducts())) {
			// 计算商品展示的价格
			BigDecimal originalPrice = BigDecimal.ZERO;
			BigDecimal currentPrice = BigDecimal.ZERO;
			BigDecimal vipPrice = BigDecimal.ZERO;
			BigDecimal supplyPrice = BigDecimal.ZERO;
			for (Product product : commodity.getProducts()) {
				if (product != null) {
					// 计算商品展示价格 按货品最低价格展示
					if (Optional.ofNullable(product.getOriginalPrice()).orElse(BigDecimal.ZERO).compareTo(originalPrice) < 0 || originalPrice.compareTo(BigDecimal.ZERO) == 0) {
						originalPrice = Optional.ofNullable(product.getOriginalPrice()).orElse(BigDecimal.ZERO);
					}
					if (Optional.ofNullable(product.getCurrentPrice()).orElse(BigDecimal.ZERO).compareTo(currentPrice) < 0 || currentPrice.compareTo(BigDecimal.ZERO) == 0) {
						currentPrice = Optional.ofNullable(product.getCurrentPrice()).orElse(BigDecimal.ZERO);
					}
					if (Optional.ofNullable(product.getVipPrice()).orElse(BigDecimal.ZERO).compareTo(vipPrice) < 0 || vipPrice.compareTo(BigDecimal.ZERO) == 0) {
						vipPrice = Optional.ofNullable(product.getVipPrice()).orElse(BigDecimal.ZERO);
					}
					if (Optional.ofNullable(product.getSupplyPrice()).orElse(BigDecimal.ZERO).compareTo(supplyPrice) < 0 || supplyPrice.compareTo(BigDecimal.ZERO) == 0) {
						supplyPrice = Optional.ofNullable(product.getSupplyPrice()).orElse(BigDecimal.ZERO);
					}
				}
			}
			commodity.setOriginalPrice(originalPrice);
			commodity.setCurrentPrice(currentPrice);
			commodity.setVipPrice(vipPrice);
			commodity.setSupplyPrice(supplyPrice);
		}
		return commodity;
	}

	/**
	 * 批量上架
	 *
	 * @param
	 * @return
	 */
	@Override
	public void batchUpperShelf(List<Integer> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			List<Commodity> dbCommodities = commodityDao.findAllById(ids);
			if (CollectionUtils.isNotEmpty(dbCommodities)) {
				dbCommodities.forEach(e -> {
					if (e != null && CommodityEnum.SHELF_WAREHOUSE.getCode().equals(e.getShelf())) {
						e.setShelf(CommodityEnum.SHELF_ON.getCode());
					}
				});
			}
		}
	}

	/**
	 * 批量下架
	 */
	@Override
	public void batchLowerShelf(List<Integer> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			List<Commodity> dbCommodities = commodityDao.findAllById(ids);
			if (CollectionUtils.isNotEmpty(dbCommodities)) {
				dbCommodities.forEach(e -> {
					if (e != null && CommodityEnum.SHELF_ON.getCode().equals(e.getShelf())) {
						e.setShelf(CommodityEnum.SHELF_WAREHOUSE.getCode());
					}
				});
			}
		}
	}

	@Override
	public void batchAddForVoucherGrant(Collection<Commodity> commodities, CouponGrantConfig couponGrantConfig) {
		if (CollectionUtils.isEmpty(commodities) || couponGrantConfig == null) {
			return;
		}
		for (Commodity tmp : commodities) {
			if (tmp != null && tmp.getId() > 0) {
				Commodity dbCommodity = this.getById(tmp.getId());
				if (dbCommodity != null) {
					dbCommodity.setCouponGrantConfig(couponGrantConfig);
				}
			}
		}
	}

	@Override
	public void batchUpdateForVoucherGrant(Collection<Commodity> commodities, CouponGrantConfig couponGrantConfig) {
		if (CollectionUtils.isEmpty(commodities) || couponGrantConfig == null) {
			return;
		}
		// 查询该发放方案原先的商品
		List<Commodity> dbCommodities = commodityDao.findByCouponGrantConfig_id(couponGrantConfig.getId());
		// 删除商品的发放方案
		dbCommodities.forEach(tmp -> {
			tmp.setCouponGrantConfig(null);
		});
		// 从新赋值新的发放方案
		for (Commodity tmp : commodities) {
			if (tmp != null && tmp.getId() > 0) {
				Commodity dbCommodity = this.getById(tmp.getId());
				if (dbCommodity != null) {
					dbCommodity.setCouponGrantConfig(couponGrantConfig);
				}
			}
		}
	}

}
