/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.admin.web.cms.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.cms.domain.bo.ArticleCommentBo;
import com.yi.core.cms.domain.entity.ArticleComment;
import com.yi.core.cms.domain.vo.ArticleCommentListVo;
import com.yi.core.cms.domain.vo.ArticleCommentVo;
import com.yi.core.cms.service.IArticleCommentService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/articleComment")
public class ArticleCommentController {

	private final Logger LOG = LoggerFactory.getLogger(ArticleCommentController.class);

	@Resource
	private IArticleCommentService articleCommentService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ArticleCommentListVo> queryAdvertisement(@RequestBody Pagination<ArticleComment> query) {
		Page<ArticleCommentListVo> page = articleCommentService.queryListVo(query);
		return page;
	}

	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewArticleComment(@RequestParam("id") int articleCommentId) {
		try {
			ArticleCommentVo articleComment = articleCommentService.getArticleCommentVoById(articleCommentId);
			return RestUtils.successWhenNotNull(articleComment);
		} catch (BusinessException ex) {
			LOG.error("get ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("get ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addArticleComment(@RequestBody ArticleCommentBo articleComment) {
		try {
			ArticleCommentListVo dbArticleComment = articleCommentService.addArticleComment(articleComment);
			return RestUtils.successWhenNotNull(dbArticleComment);
		} catch (BusinessException ex) {
			LOG.error("add ArticleComment failure : articleComment", articleComment, ex);
			return RestUtils.error("add ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateArticleComment(@RequestBody ArticleCommentBo articleComment) {
		try {
			ArticleCommentVo dbArticleComment = articleCommentService.updateArticleComment(articleComment);
			return RestUtils.successWhenNotNull(dbArticleComment);
		} catch (BusinessException ex) {
			LOG.error("update ArticleComment failure : articleComment", articleComment, ex);
			return RestUtils.error("update ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeArticleCommentById(@RequestParam("id") int articleCommentId) {
		try {
			articleCommentService.removeArticleCommentById(articleCommentId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("remove ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 回复爱生活评论
	 **/
	@RequestMapping(value = "reply", method = RequestMethod.GET)
	public RestResult reply(@RequestParam("id") int id, @RequestParam("replys") String replys) {
		try {
			return RestUtils.success(articleCommentService.reply(id, replys));
		} catch (Exception ex) {
			LOG.error("reply ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("reply ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 显示爱生活评论
	 **/
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public RestResult display(@RequestParam("id") int id, @RequestParam("state") int state) {
		try {
			return RestUtils.success(articleCommentService.display(id, state));
		} catch (Exception ex) {
			LOG.error("display ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("display ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 隐藏爱生活评论
	 **/
	@RequestMapping(value = "hide", method = RequestMethod.GET)
	public RestResult hide(@RequestParam("id") int id, @RequestParam("state") int state) {
		try {

			return RestUtils.success(articleCommentService.hide(id, state));
		} catch (Exception ex) {
			LOG.error("hide ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("hide ArticleComment failure : " + ex.getMessage());
		}
	}

}