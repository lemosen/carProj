package com.yi.webserver.web.sharing;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.core.sharing.SharingService;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;

/**
 * 商品分享
 * 
 * @ClassName SharingController
 * @Author jstin
 * @Date 2018/12/27 13:51
 * @Version 1.0
 **/
@RestController
@RequestMapping("/sharing")
public class SharingController {

	private final Logger LOG = LoggerFactory.getLogger(SharingController.class);

	@Resource
	private SharingService sharingService;

	/**
	 * 合成二维码海报图片
	 * 
	 * @param url
	 * @param commodityId
	 * @return
	 */
	@RequestMapping(value = "getSharingImgApp", method = RequestMethod.GET)
	public RestResult getSharingImgApp(String url, int commodityId) {
		try {
			return RestUtils.successWhenNotNull(sharingService.getSharingImgApp(url, commodityId));
		} catch (Exception e) {
			LOG.error("合成分享图片二维码失败==>{}", e.getMessage(), e);
			return RestUtils.error("合成分享图片二维码失败!请重试");
		}
	}
}
