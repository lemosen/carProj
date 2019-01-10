package com.yi.admin.web.order.controller;

import com.yi.base.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 快递查询
 * 
 * @author xuyh
 *
 */
@RestController
@RequestMapping("express")
public class ExpressController {

	private final Logger LOG = LoggerFactory.getLogger(ExpressController.class);
	/** 快递100路径 */
	private static final String KUAI_DI_100_URL = "https://m.kuaidi100.com/query";

	/**
	 * 快递100 查询快递进度
	 *
	 * @param type
	 * @param postid
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.GET)
	public String query(@RequestParam("type") String type, @RequestParam("postid") String postid) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		params.put("postid", postid);
		String result = null;
		try {
			result = HttpUtils.doGet(KUAI_DI_100_URL, params);
		} catch (Exception e) {
			LOG.error("请求失败");
		}
		return result;
	}

}
