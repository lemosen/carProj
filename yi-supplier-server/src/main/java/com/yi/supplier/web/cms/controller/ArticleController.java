/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.cms.controller;

import javax.annotation.Resource;

import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.cms.domain.vo.ArticleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.service.IArticleService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 文章
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

	private final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@Resource
	private IArticleService articleService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ArticleListVo> queryArticle(@RequestBody Pagination<Article> query) {
		Page<ArticleListVo> page = articleService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewArticle(@RequestParam("id") int articleId) {
		try {
			ArticleVo article = articleService.getArticleVoById(articleId);

			return RestUtils.successWhenNotNull(article);
		} catch (BusinessException ex) {
			LOG.error("get Article failure : id=articleId", ex);
			return RestUtils.error("get Article failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addArticle(@RequestBody Article article) {
		try {
			Article dbArticle = articleService.addArticle(article);
			return RestUtils.successWhenNotNull(dbArticle);
		} catch (BusinessException ex) {
			LOG.error("add Article failure : article", article, ex);
			return RestUtils.error("add Article failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateArticle(@RequestBody Article article) {
		try {
			Article dbArticle = articleService.updateArticle(article);
			return RestUtils.successWhenNotNull(dbArticle);
		} catch (BusinessException ex) {
			LOG.error("update Article failure : article", article, ex);
			return RestUtils.error("update Article failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeArticleById(@RequestParam("id") int articleId) {
		try {
			articleService.removeArticleById(articleId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Article failure : id=articleId", ex);
			return RestUtils.error("remove Article failure : " + ex.getMessage());
		}
	}

	/**
	 * 上下架
	 **/
	@RequestMapping(value = "upAndDown", method = RequestMethod.GET)
	public RestResult upAndDown(@RequestParam("articleId") int articleId) {
		try {
			articleService.updateDisplayState(articleId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Article failure : id=articleId", ex);
			return RestUtils.error("remove Article failure : " + ex.getMessage());
		}
	}
}