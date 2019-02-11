/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.commodity.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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

import com.yi.core.commodity.CommodityEnum;
import com.yi.core.commodity.dao.ProductDao;
import com.yi.core.commodity.domain.bo.ProductBo;
import com.yi.core.commodity.domain.entity.Attribute;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.domain.entity.Product;
import com.yi.core.commodity.domain.entity.Product_;
import com.yi.core.commodity.domain.simple.ProductSimple;
import com.yi.core.commodity.domain.vo.ProductListVo;
import com.yi.core.commodity.domain.vo.ProductVo;
import com.yi.core.commodity.service.IProductService;
import com.yi.core.common.Deleted;
import com.yi.core.common.NumberGenerateUtils;
import com.yi.core.supplier.dao.SupplierDao;
import com.yihz.common.convert.AbstractBeanConvert;
import com.yihz.common.convert.BeanConvert;
import com.yihz.common.convert.BeanConvertManager;
import com.yihz.common.convert.EntityListVoBoSimpleConvert;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.persistence.AttributeReplication;
import com.yihz.common.persistence.Pagination;

/**
 * 货品
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@Service
@Transactional
public class ProductServiceImpl implements IProductService, InitializingBean {

	private final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Resource
	private BeanConvertManager beanConvertManager;

	@Resource
	private ProductDao productDao;

	@Resource
	private SupplierDao supplierDao;

	private EntityListVoBoSimpleConvert<Product, ProductBo, ProductVo, ProductSimple, ProductListVo> productConvert;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<Product> query(Pagination<Product> query) {
		query.setEntityClazz(Product.class);
		query.setPredicate(((root, criteriaQuery, criteriaBuilder, list, list1) -> {
			list.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(Product_.deleted), Deleted.DEL_FALSE)));
		}));
		Page<Product> page = productDao.findAll(query, query.getPageRequest());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ProductListVo> queryListVo(Pagination<Product> query) {
		Page<Product> pages = this.query(query);
		List<ProductListVo> vos = productConvert.toListVos(pages.getContent());
		return new PageImpl<ProductListVo>(vos, query.getPageRequest(), pages.getTotalElements());
	}

	@Override
	public Product addProduct(Product product) {
		if (product == null || product.getCommodity() == null || CollectionUtils.isEmpty(product.getAttributes())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (StringUtils.isBlank(product.getProductImgPath())) {
			product.setProductImgPath(product.getCommodity().getImgPath());
		}
		product.setProductNo(NumberGenerateUtils.generateProductNo());
		product.setProductName(product.getCommodity().getCommodityName());
		product.setProductShortName(product.getCommodity().getCommodityShortName());
		product.setSupplier(product.getCommodity().getSupplier());
		product.setVolume(product.getCommodity().getVolume());
		product.setWeight(product.getCommodity().getWeight());
		product.setShelfState(product.getCommodity().getShelfState());
		product.getAttributes().forEach(tmpAttr -> {
			if (tmpAttr != null) {
				product.setProductName(product.getProductName() + " " + tmpAttr.getAttrValue());
				product.setProductShortName(product.getProductShortName() + " " + tmpAttr.getAttrValue());
			}
		});
		return productDao.save(product);
	}

	@Override
	public ProductVo addProduct(ProductBo productBo) {
		return productConvert.toVo(this.addProduct(productConvert.toEntity(productBo)));
	}

	@Override
	public Product updateProduct(Product product) {
		if (product == null || product.getId() < 1 || product.getCommodity() == null || CollectionUtils.isEmpty(product.getAttributes())) {
			throw new BusinessException("提交数据不能为空");
		}
		if (StringUtils.isBlank(product.getProductImgPath())) {
			product.setProductImgPath(product.getCommodity().getImgPath());
		}
		product.setProductName(product.getCommodity().getCommodityName());
		product.setProductShortName(product.getCommodity().getCommodityShortName());
		product.setSupplier(product.getCommodity().getSupplier());
		product.setVolume(product.getCommodity().getVolume());
		product.setWeight(product.getCommodity().getWeight());
		product.setShelfState(product.getCommodity().getShelfState());
		product.getAttributes().forEach(tmpAttr -> {
			if (tmpAttr != null) {
				product.setProductName(product.getProductName() + " " + tmpAttr.getAttrValue());
				product.setProductShortName(product.getProductShortName() + " " + tmpAttr.getAttrValue());
			}
		});
		Product dbProduct = productDao.getOne(product.getId());
		AttributeReplication.copying(product, dbProduct, Product_.productName, Product_.productShortName, Product_.sort, Product_.volume, Product_.weight, Product_.description,
				Product_.productImgPath, Product_.originalPrice, Product_.currentPrice, Product_.supplyPrice, Product_.vipPrice, Product_.sku, Product_.stockQuantity,
				Product_.shelfState);
		dbProduct.setAttributes(product.getAttributes());
		return dbProduct;
	}

	@Override
	public ProductVo updateProduct(ProductBo productBo) {
		return productConvert.toVo(this.updateProduct(productConvert.toEntity(productBo)));
	}

	@Override
	public void removeProductById(int productId) {
		Product dbProduct = productDao.getOne(productId);
		if (dbProduct != null) {
			dbProduct.setDeleted(Deleted.DEL_TRUE);
			dbProduct.setDelTime(new Date());
			// 删除中间表数据
			dbProduct.setAttributes(null);
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Product getById(int productId) {
		return productDao.getOne(productId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductVo getVoById(int productId) {
		return productConvert.toVo(this.productDao.getOne(productId));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductListVo getListVoById(int productId) {
		return productConvert.toListVo(this.productDao.getOne(productId));
	}

	/**
	 * 批量新增货品
	 */
	@Override
	public List<Product> batchAddProduct(Commodity commodity, Collection<Product> products, Map<String, Attribute> attributeMap) {
		if (commodity != null && CollectionUtils.isNotEmpty(products)) {
			products.forEach(product -> {
				if (product != null && CollectionUtils.isNotEmpty(product.getAttributes())) {
					product.setProductNo(NumberGenerateUtils.generateProductNo());
					product.setProductName(commodity.getCommodityName());
					product.setProductShortName(commodity.getCommodityShortName());
					product.setCommodity(commodity);
					product.setSupplier(commodity.getSupplier());
					product.setVolume(commodity.getVolume());
					product.setWeight(commodity.getWeight());
					product.setShelfState(commodity.getShelfState());
					if (StringUtils.isBlank(product.getProductImgPath())) {
						product.setProductImgPath(commodity.getImgPath());
					}
					product.getAttributes().forEach(tmpAttr -> {
						if (tmpAttr != null) {
							product.setProductName(product.getProductName() + " " + tmpAttr.getAttrValue());
							product.setProductShortName(product.getProductShortName() + " " + tmpAttr.getAttrValue());
							if (attributeMap.containsKey(tmpAttr.getAttrValue()) && attributeMap.get(tmpAttr.getAttrValue()).getAttrName().equals(tmpAttr.getAttrName())) {
								tmpAttr.setId(attributeMap.get(tmpAttr.getAttrValue()).getId());
								tmpAttr.setAttributeGroup(attributeMap.get(tmpAttr.getAttrValue()).getAttributeGroup());
							}
						}
					});
				}
			});
			return productDao.saveAll(products);
		}
		return null;
	}

	/**
	 * 批量修改货品
	 */
	@Override
	public List<Product> batchUpdateProduct(Commodity commodity, Collection<Product> products, Map<String, Attribute> attributeMap) {
		// 返回的数据
		List<Product> returnProducts = new ArrayList<>();
		if (commodity != null && commodity.getId() > 0 && CollectionUtils.isNotEmpty(products)) {
			// 封装 货品属性
			for (Product product : products) {
				if (CollectionUtils.isNotEmpty(product.getAttributes())) {
					for (Attribute tmpAttr : product.getAttributes()) {
						if (tmpAttr != null) {
							if (attributeMap.containsKey(tmpAttr.getAttrValue()) && attributeMap.get(tmpAttr.getAttrValue()).getAttrName().equals(tmpAttr.getAttrName())) {
								tmpAttr.setId(attributeMap.get(tmpAttr.getAttrValue()).getId());
								tmpAttr.setAttributeGroup(attributeMap.get(tmpAttr.getAttrValue()).getAttributeGroup());
								// tmpAttr.getProducts().add(product);
							}
						}
					}
				}
			}
			// 查询 当前商品下的货品
			Set<Product> dbProducts = productDao.findByCommodity_idAndDeleted(commodity.getId(), Deleted.DEL_FALSE);
			// 需要新增的数据
			Set<Product> saveSets = products.stream().filter(e1 -> dbProducts.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要更新的数据
			Set<Product> updateSets = products.stream().filter(e1 -> dbProducts.stream().anyMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 需要删除的数据
			Set<Product> deleteSets = dbProducts.stream().filter(e1 -> products.stream().noneMatch(e2 -> e1.getId() == e2.getId())).collect(Collectors.toSet());
			// 整理数据
			saveSets.forEach(product -> {
				if (product != null && CollectionUtils.isNotEmpty(product.getAttributes())) {
					product.setProductNo(NumberGenerateUtils.generateProductNo());
					product.setProductName(commodity.getCommodityName());
					product.setProductShortName(commodity.getCommodityShortName());
					product.setCommodity(commodity);
					product.setSupplier(commodity.getSupplier());
					product.setVolume(commodity.getVolume());
					product.setWeight(commodity.getWeight());
					product.setShelfState(commodity.getShelfState());
					if (StringUtils.isBlank(product.getProductImgPath())) {
						product.setProductImgPath(commodity.getImgPath());
					}
					product.getAttributes().forEach(tmpAttr -> {
						if (tmpAttr != null) {
							product.setProductName(product.getProductName() + " " + tmpAttr.getAttrValue());
							product.setProductShortName(product.getProductShortName() + " " + tmpAttr.getAttrValue());
							// if (attributeMap.containsKey(tmpAttr.getAttrValue()) &&
							// attributeMap.get(tmpAttr.getAttrValue()).getAttrName().equals(tmpAttr.getAttrName()))
							// {
							// tmpAttr.setId(attributeMap.get(tmpAttr.getAttrValue()).getId());
							// tmpAttr.setAttributeGroup(attributeMap.get(tmpAttr.getAttrValue()).getAttributeGroup());
							// tmpAttr.getProducts().add(product);
							// }
						}
					});
				}
			});
			// 保存数据
			returnProducts = productDao.saveAll(saveSets);
			// 修改数据
			for (Product tmp : updateSets) {
				if (tmp != null) {
					tmp.setCommodity(commodity);
					returnProducts.add(this.updateProduct(tmp));
				}
			}
			// 删除数据
			deleteSets.forEach(tmp -> {
				tmp.setDeleted(Deleted.DEL_TRUE);
				tmp.setDelTime(new Date());
				// 删除中间表数据
				tmp.setAttributes(null);
			});
		}
		return returnProducts;
	}

	/**
	 * 批量删除货品
	 */
	@Override
	public void batchDeleteProduct(Collection<Product> products) {
		if (CollectionUtils.isNotEmpty(products)) {
			products.forEach(tmp -> {
				if (tmp != null) {
					tmp.setDeleted(Deleted.DEL_TRUE);
					tmp.setDelTime(new Date());
					// 删除中间表数据
					tmp.setAttributes(null);
				}
			});
		}
	}

	protected void initConvert() {
		this.productConvert = new EntityListVoBoSimpleConvert<Product, ProductBo, ProductVo, ProductSimple, ProductListVo>(beanConvertManager) {
			@Override
			protected BeanConvert<Product, ProductVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Product, ProductVo>(beanConvertManager) {
					@Override
					protected void postConvert(ProductVo ProductVo, Product Product) {

					}
				};
			}

			@Override
			protected BeanConvert<Product, ProductListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Product, ProductListVo>(beanConvertManager) {
					@Override
					protected void postConvert(ProductListVo ProductListVo, Product Product) {

					}
				};
			}

			@Override
			protected BeanConvert<Product, ProductBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Product, ProductBo>(beanConvertManager) {
					@Override
					protected void postConvert(ProductBo ProductBo, Product Product) {

					}
				};
			}

			@Override
			protected BeanConvert<ProductBo, Product> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ProductBo, Product>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<Product, ProductSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<Product, ProductSimple>(beanConvertManager) {
				};
			}

			@Override
			protected BeanConvert<ProductSimple, Product> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
				return new AbstractBeanConvert<ProductSimple, Product>(beanConvertManager) {
					@Override
					public Product convert(ProductSimple ProductSimple) throws BeansException {
						return productDao.getOne(ProductSimple.getId());
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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Product checkProductForOrder(int productId) {
		Product dbProduct = productDao.getOne(productId);
		// 上架 未删除
		if (dbProduct != null && CommodityEnum.STATE_AGREE.getCode().equals(dbProduct.getCommodity().getState()) && Deleted.DEL_FALSE.equals(dbProduct.getDeleted())) {
			return dbProduct;
		} else {
			throw new BusinessException(dbProduct.getProductShortName() + " 已经下架");
		}
	}

	@Override
	public void deleteByCommodity(Integer commodityId) {
		if (commodityId != null && commodityId.intValue() > 0) {
			List<Product> products = productDao.findByCommodity_id(commodityId);
			if (productDao != null) {
				products.forEach(e -> {
					e.setDeleted(Deleted.DEL_TRUE);
					e.setDelTime(new Date());
					// 删除中间表
				});
			}
		}
	}

	/**
	 * 根据商品查询货品
	 * 
	 * @param commodityId
	 * @return
	 */
	@Override
	public List<Product> findByCommodity_idAndDeleted(Integer commodityId) {
		Set<Product> dbProducts = productDao.findByCommodity_idAndDeleted(commodityId, Deleted.DEL_FALSE);
		return dbProducts.stream().collect(Collectors.toList());
	}

}
