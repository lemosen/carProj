package com.yi.webserver.web.article;

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
import com.yi.core.cms.domain.entity.Article;
import com.yi.core.cms.domain.vo.ArticleCommentListVo;
import com.yi.core.cms.domain.vo.ArticleListVo;
import com.yi.core.cms.domain.vo.ArticleVo;
import com.yi.core.cms.service.IArticleCommentService;
import com.yi.core.cms.service.IArticleService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.persistence.Pagination;
import com.yihz.common.utils.web.RestUtils;

/**
 * 爱生活 相关
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	private final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

	@Resource
	private IArticleService articleService;

	@Resource
	private IArticleCommentService articleCommentService;

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public Page<ArticleListVo> queryArticle(@RequestBody Pagination<Article> query) {
		Page<ArticleListVo> page = articleService.queryListVoForApp(query);
		return page;
	}

	/**
	 * 获取文章详情
	 *
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value = "getArticle", method = RequestMethod.GET)
	public RestResult getArticle(@RequestParam("articleId") int articleId) {
		try {
			ArticleVo dbArticleVo = articleService.getArticleVoByIdForApp(articleId);
			// 更新阅读量
			articleService.updateReadQuantity(articleId, 1);
			return RestUtils.successWhenNotNull(dbArticleVo);
		} catch (Exception ex) {
			LOG.error("get Article failure : {}", articleId, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

	/**
	 * 新增 爱生活评论
	 * 
	 * @param articleCommentBo
	 * @return
	 */
	@RequestMapping(value = "addArticleComment", method = RequestMethod.POST)
	public RestResult addArticleComment(@RequestBody ArticleCommentBo articleCommentBo) {
		try {
			ArticleCommentListVo dbArticleCommentListVo = articleCommentService.addArticleComment(articleCommentBo);
			// 更新评论量
			articleService.updateCommentQuantity(dbArticleCommentListVo.getArticle().getId(), 1);
			return RestUtils.successWhenNotNull(dbArticleCommentListVo);
		} catch (BusinessException ex) {
			LOG.error("add articleComment failure : {}", articleCommentBo, ex);
			return RestUtils.error(ex.getMessage());
		}
	}

}
