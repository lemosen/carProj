/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import com.yi.core.activity.domain.entity.Seckill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface SeckillDao extends JpaRepository<Seckill, Integer> ,JpaSpecificationExecutor<Seckill> {

}