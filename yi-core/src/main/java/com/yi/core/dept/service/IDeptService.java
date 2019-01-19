//package com.yi.core.dept.service;
//
//import com.yi.core.common.ObjectChooseQuery;
//import com.yi.core.dept.domain.bo.DeptBo;
//import com.yi.core.dept.domain.entity.Dept;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yi.core.dept.domain.vo.DeptListVo;
//import com.yi.core.dept.domain.vo.DeptParentChildrenVo;
//import com.yi.core.dept.domain.vo.DeptTreeVo;
//import com.yi.core.dept.domain.vo.DeptVo;
//import com.yi.core.user.domain.entity.User;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.persistence.Pagination;
//import org.springframework.data.domain.Page;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
//public interface IDeptService {
//
//    /**
//     * 获取整个公司的组织架构树信息, 不包括停用的部门
//     *
//     * @return
//     */
//    <T> DeptTreeVo<T> getDeptTreeVo();
//
//    /**
//     * 获取部门根节点(上级部门为NULL, 不允许存在多个这样的节点)
//     *
//     * @return
//     */
//    Dept getTopDept();
//
//    /**
//     * 获取某个部门的所有上级部门, 根部门放在第一个元素(不包括制定部门本身)
//     *
//     * @param deptId
//     * @return
//     */
//    List<Dept> getAllParents(int deptId);
//
//    /**
//     * 获取某个部门的所有上级部门和下级部门, 根部门放在第一个元素
//     *
//     * @param deptId
//     * @return
//     */
//    DeptParentChildrenVo getParentsAndChildren(int deptId);
//
//    /**
//     * 获取某个部门的状态为启用的下级部门(不包括下级的下级)
//     *
//     * @param deptId
//     * @return
//     */
//    List<Dept> findChildren(int deptId);
//
//    /**
//     * 获取某个部门的状态为启用的所有下级部门, 包括下级的下级部门
//     *
//     * @param deptId
//     * @return
//     */
//    List<Dept> findAllChildren(int deptId);
//
//    /**
//     * 获取部门的状态为启用的员工数量
//     *
//     * @param deptId
//     * @return
//     */
//    int getUserCountByDept(int deptId);
//
//    /**
//     * 按条件查询部门实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param query
//     * @return
//     */
//    Page<Dept> query(Pagination<Dept> query);
//
//    Page<DeptVo> queryVo(Pagination<DeptVo> query);
//
//    Page<DeptListVo> queryListVo(Pagination<DeptListVo> query);
//
//    List<DeptSimple> queryObjectsByConditions(ObjectChooseQuery chooseQuery);
//
//    Page<DeptSimple> querySimple(Pagination query);
//
//    Page<DeptBo> queryBo(Pagination<DeptBo> query);
//
//    /**
//     * 按ID获取部门实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param deptId
//     * @return
//     */
//    Dept getById(int deptId);
//
//    /**
//     * 按ID获取部门实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param deptIds
//     * @return
//     */
//    Set<Dept> findByIds(int... deptIds);
//
//    /**
//     * 按ID获取部门实体, 注意该方法只限于各个Service调用, 不能在Controller调用
//     *
//     * @param deptIds
//     * @return
//     */
//    Set<Dept> findByIds(Collection<Integer> deptIds);
//
//    DeptBo getBoById(int deptId);
//
//    DeptVo getVoById(int deptId);
//
//    DeptListVo getListVoById(int deptId);
//
//    Collection<DeptSimple> findSimpleByIds(Set<Integer> deptIds);
//
//    DeptSimple getSimpleById(int deptId);
//
//    Dept add(DeptBo deptBo);
//
//    Dept update(DeptBo deptBo);
//
//    /**
//     * 修改部门显示顺序
//     *
//     * @param deptId
//     * @param delta  显示顺序增加值,上移时为-1, 下移时为+1
//     * @return
//     */
//    Dept changeShowOrder(int deptId, int delta);
//
//    /**
//     * 删除部门
//     *
//     * @param deptId
//     */
//    void removeById(int deptId);
//
//    /* ---------------------- 实体, VO, BO, Simple 转换 ---------------------- */
//    EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> getDeptConvert();
//}
