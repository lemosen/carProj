/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.supplier.web.commodity.controller;

import javax.annotation.Resource;

import com.yi.core.commodity.domain.vo.CommentListVo;
import com.yi.core.commodity.domain.vo.CommentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.commodity.domain.entity.Comment;
import com.yi.core.commodity.service.ICommentService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品评论
 * 
 * @author lemosen
 * @version 1.0
 * @since 1.0
 **/
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

	private final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	@Resource
	private ICommentService commentService;

	/**
	 * 分页查询
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<CommentListVo> queryComment(@RequestBody Pagination<Comment> query) {
		Page<CommentListVo> page = commentService.queryListVo(query);
		return page;
	}


	/**
	 * 显示评论
	 **/
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public RestResult display(@RequestParam("id") int id, @RequestParam("display") int display) {
		try {
			return RestUtils.success(commentService.display(id, display));
		} catch (Exception ex) {
			LOG.error("display ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("display ArticleComment failure : " + ex.getMessage());
		}
	}

	/**
	 * 隐藏评论
	 **/
	@RequestMapping(value = "hide", method = RequestMethod.GET)
	public RestResult hide(@RequestParam("id") int id, @RequestParam("display") int display) {
		try {
			return RestUtils.success(commentService.hide(id, display));
		} catch (Exception ex) {
			LOG.error("hide ArticleComment failure : id=articleCommentId", ex);
			return RestUtils.error("hide ArticleComment failure : " + ex.getMessage());
		}
	}



	/**
	 * 查看对象
	 **/
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public RestResult viewComment(@RequestParam("id") int commentId) {
		try {
			CommentVo comment = commentService.getCommentVoById(commentId);
			return RestUtils.successWhenNotNull(comment);
		} catch (BusinessException ex) {
			LOG.error("get Comment failure : id=commentId", ex);
			return RestUtils.error("get Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存新增对象
	 **/
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public RestResult addComment(@RequestBody Comment comment) {
		try {
			Comment dbComment = commentService.addComment(comment);
			return RestUtils.successWhenNotNull(dbComment);
		} catch (BusinessException ex) {
			LOG.error("add Comment failure : comment", comment, ex);
			return RestUtils.error("add Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 保存更新对象
	 **/
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public RestResult updateComment(@RequestBody Comment comment) {
		try {
			Comment dbComment = commentService.updateComment(comment);
			return RestUtils.successWhenNotNull(dbComment);
		} catch (BusinessException ex) {
			LOG.error("update Comment failure : comment", comment, ex);
			return RestUtils.error("update Comment failure : " + ex.getMessage());
		}
	}

	/**
	 * 删除对象
	 **/
	@RequestMapping(value = "removeById", method = RequestMethod.GET)
	public RestResult removeCommentById(@RequestParam("id") int commentId) {
		try {
			commentService.removeCommentById(commentId);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Comment failure : id=commentId", ex);
			return RestUtils.error("remove Comment failure : " + ex.getMessage());
		}
	}
	/**
	 * 回复
	 */
	@RequestMapping(value = "reply", method = RequestMethod.GET)
	public RestResult reply(@RequestParam("id") int commentId,@RequestParam("replyContent") String replyContent) {
		try {
			commentService.replyComment(commentId,replyContent);
			return RestUtils.success(true);
		} catch (Exception ex) {
			LOG.error("remove Comment failure : id=commentId", ex);
			return RestUtils.error("remove Comment failure : " + ex.getMessage());
		}
	}

}