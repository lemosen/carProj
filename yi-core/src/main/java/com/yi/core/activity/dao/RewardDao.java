/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2019
 */

package com.yi.core.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yi.core.activity.domain.entity.Reward;

/**
 * *
 * 
 * @author yihz
 * @version 1.0
 * @since 1.0
 *
 */
public interface RewardDao extends JpaRepository<Reward, Integer>, JpaSpecificationExecutor<Reward> {

}