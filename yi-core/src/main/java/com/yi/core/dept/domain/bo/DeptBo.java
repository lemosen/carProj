//package com.yi.core.dept.domain.bo;
//
//import com.google.common.collect.Lists;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yi.core.dept.domain.vo.DeptListVo;
//import com.yi.core.user.domain.simple.UserSimple;
//import com.yi.core.user.domain.vo.UserListVo;
//import com.yihz.common.convert.domain.BoDomain;
//import org.hibernate.validator.constraints.Length;
//import javax.validation.constraints.NotBlank;
//
//import javax.validation.constraints.Max;
//import java.util.Collection;
//
//public class DeptBo extends BoDomain {
//    /**
//     * 对象路径
//     */
//    private String objectPath;
//
//    /**
//     * 部门ID
//     */
//    private int deptId;
//    /**
//     * GUID
//     */
//    private String guid;
//    /**
//     * 上级部门
//     */
//    private DeptSimple parentDept;
//    /**
//     * 部门编码
//     */
//    @Length(max = 32)
//    private String deptCode;
//    /**
//     * 部门名称
//     */
//    @NotBlank
//    @Length(max = 256)
//    private String deptName;
//    /**
//     * 部门简称
//     */
//    @Length(max = 32)
//    private String simpleName;
//    /**
//     * 排序
//     */
//    @Max(32767)
//    private int showOrder;
//    /**
//     * 状态
//     */
//    private boolean enabled;
//
//    /**
//     * 该部门的下级子部门, 不包括下级子部门的下级子部门
//     */
//    private Collection<DeptListVo> children = Lists.newArrayList();
//
//    /**
//     * 部门员工
//     */
//    private Collection<UserSimple> users = Lists.newArrayList();
//
//    public int getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(int deptId) {
//        this.deptId = deptId;
//    }
//
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//
//    public DeptSimple getParentDept() {
//        return parentDept;
//    }
//
//    public void setParentDept(DeptSimple parentDept) {
//        this.parentDept = parentDept;
//    }
//
//    public String getDeptCode() {
//        return deptCode;
//    }
//
//    public void setDeptCode(String deptCode) {
//        this.deptCode = deptCode;
//    }
//
//    public String getDeptName() {
//        return deptName;
//    }
//
//    public void setDeptName(String deptName) {
//        this.deptName = deptName;
//    }
//
//    public String getSimpleName() {
//        return simpleName;
//    }
//
//    public void setSimpleName(String simpleName) {
//        this.simpleName = simpleName;
//    }
//
//    public int getShowOrder() {
//        return showOrder;
//    }
//
//    public void setShowOrder(int showOrder) {
//        this.showOrder = showOrder;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Collection<DeptListVo> getChildren() {
//        return children;
//    }
//
//    public void setChildren(Collection<DeptListVo> children) {
//        this.children = children;
//    }
//
//    public String getObjectPath() {
//        return objectPath;
//    }
//
//    public void setObjectPath(String objectPath) {
//        this.objectPath = objectPath;
//    }
//
//    public Collection<UserSimple> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Collection<UserSimple> users) {
//        this.users = users;
//    }
//}
