/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import com.yi.core.activity.domain.entity.NationalGroupRecord;
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
public interface NationalGroupRecordDao extends JpaRepository<NationalGroupRecord, Integer> ,JpaSpecificationExecutor<NationalGroupRecord> {

    List<NationalGroupRecord> findByMemberId(int memberId);

    List<NationalGroupRecord> findByNationalGroup_IdAndPay(int id, Integer pay);
}