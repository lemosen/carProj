package com.yi.core.sharing;

import java.io.File;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yi.base.attachment.IAttachmentStorageExecutor;
import com.yi.core.commodity.domain.entity.Commodity;
import com.yi.core.commodity.service.ICommodityService;
import com.yi.core.utils.QRCodeUtil;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.utils.RandomUtils;

/**
 * 分享合成二维码图片
 * 
 * @ClassName SharingService
 * @Author jstin
 * @Date 2018/12/27 14:01
 * @Version 1.0
 **/
@Component
@Transactional
public class SharingService {
	private static final Logger LOG = LoggerFactory.getLogger(SharingService.class);

	@Resource
	private IAttachmentStorageExecutor attachmentStorageExecutor;

	/**
	 * 文件保存路径
	 */
	@Value("${YI_HOME}/uploads")
	private String rootPath;

	@Value("${yi.attachment.domain:http://localhost:8080}")
	private String domainName;

	@Resource
	private ICommodityService commodityService;

	public String getSharingImgApp(String url, int commodityId) throws Exception {
		Commodity commodity = commodityService.getById(commodityId);
		if (commodity == null) {
			LOG.info("合成二维码图片商品信息为空==>");
			throw new BusinessException("商品信息为空！");
		}
		String uuid = commodity.getImgPath().substring(commodity.getImgPath().lastIndexOf("/") + 1, commodity.getImgPath().length());
		String filePath = attachmentStorageExecutor.getLocalFilePath(uuid);
		String codePath = filePath.substring(0, filePath.lastIndexOf("/")) + RandomUtils.randomString(11, RandomUtils.RANDRULE.RAND_LOWER) + ".png";
		LOG.info("开始合成二维码图片==>{}", codePath);
		File file = attachmentStorageExecutor.getLocalFileNameAndUuid(uuid);
		String qrCodePath = QRCodeUtil.generateQRCode(url, 180, 180, "png", codePath);
		String base64 = QRCodeUtil.combineCodeAndPicToBase64(file, new File(qrCodePath), commodity);
		return base64;
	}
}
