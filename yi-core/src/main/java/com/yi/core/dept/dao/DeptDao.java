//package com.yi.core.dept.dao;
//
//import com.yi.core.dept.domain.entity.Dept;
//import com.yihz.common.orm.BaseDao;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface DeptDao extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept>, BaseDao<Dept> {
//
//    //根据名字和上级部门查找部门
//    Dept findByDeptNameAndParentDept_DeptId(String deptName, int parentDeptId);
//
//    Dept findByLayerCodeAndEnabled(String layerCode, boolean enable);
//
//    @Query("select dp from Dept dp left join fetch dp.parentDept dpp where dpp.deptId=?1 order by dp.layerCode DESC")
//    List<Dept> findChildsByParentId(int deptId);
//
//    @Query("select d.layerCode from Dept d where d.parentDept=?1 order by d.layerCode asc")
//    List<String> getDeptLevelCodesByParentDept(Dept dept);
//
//    @Modifying
//    @Query("update Dept d set d.parentDept.deptId=?1,d.layerCode=?2 where d.deptId=?3")
//    void updateParentDeptByDept(int parentDeptId, String layerCode, int deptId);
//
//    @Modifying
//    @Query("update Dept d set d.layerCode=?1 where d.deptId=?2")
//    void updateLayerCodeByDept(String layerCode, int deptId);
//
//    @Query("select d from Dept d where d.deptName = ?1")
//    List<Dept> findByDeptName(String deptName);
//
//    @Query("select d from Dept d where d.deptName = ?1")
//    List<Dept> findByDeptCode(String deptCode);
//
//}
