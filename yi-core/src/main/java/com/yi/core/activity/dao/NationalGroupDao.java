/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import com.yi.core.activity.domain.entity.NationalGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface NationalGroupDao extends JpaRepository<NationalGroup, Integer> ,JpaSpecificationExecutor<NationalGroup> {

    NationalGroup findByProductIdAndDeleted(int ProductId,int deleted);

    @Query(value = "select n from NationalGroup n where Date(n.endTime)=Date(:yesterday)")
    List<NationalGroup> findByYesterday(Date yesterday);

    NationalGroup findByIdAndDeleted(Integer id,Integer deleted);

 /*   @Query(value = "select n from NationalGroup n left join n.product  ")
    List<NationalGroup> findByPaid(Date yesterday);

    List<NationalGroup> */

}