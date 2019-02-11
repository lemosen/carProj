package com.yi.supplier.web.stats;

import com.yi.core.stats.domain.vo.SupplierDataVo;
import com.yi.core.stats.service.ISupplierStatsService;
import com.yihz.common.exception.BusinessException;
import com.yihz.common.json.RestResult;
import com.yihz.common.utils.web.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/supplierpage")
public class SupplierStatisticsController {


    private static final Logger LOG = LoggerFactory.getLogger(HomepageController.class);

    @Resource
    private ISupplierStatsService supplierStatsService;

    /**
     * 查询 供应商数据
     **/
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public RestResult queryPlatformData() {
        try {
            SupplierDataVo supplierDataVo = supplierStatsService.getSupplierData();
            return RestUtils.successWhenNotNull(supplierDataVo);
        } catch (BusinessException ex) {
            LOG.error("query platformData failure : ", ex);
            return RestUtils.error("query failure : " + ex.getMessage());
        }
    }
}
