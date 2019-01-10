/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.promotion.dao;

import com.yi.core.promotion.domain.entity.GroupBuyActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
*  *
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
*/
public interface GroupBuyActivityDao extends JpaRepository<GroupBuyActivity, Integer> ,JpaSpecificationExecutor<GroupBuyActivity> {

}