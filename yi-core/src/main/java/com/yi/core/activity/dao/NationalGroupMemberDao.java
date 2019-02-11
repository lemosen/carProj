/*
 * Powered By [yihz-framework]
 * Web Site: yihz
 * Since 2018 - 2018
 */

package com.yi.core.activity.dao;

import com.yi.core.activity.domain.entity.NationalGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
*  *
 * @author lemosen
 * @version 1.0
 * @since 1.0
 *
*/
public interface NationalGroupMemberDao extends JpaRepository<NationalGroupMember, Integer> ,JpaSpecificationExecutor<NationalGroupMember> {

    NationalGroupMember findByPayAndId(Integer pay,Integer id);
}