//package com.yi.core.dept.domain.vo;
//
//import com.google.common.collect.Lists;
//
//import java.util.Collection;
//
//public class DeptTreeVo<T> {
//    /**
//     * 部门ID路径, A/A1/A11的路径为 [A, A1, A11], 当前用于ag-grid
//     */
//    private Collection<Integer> deptIdPath = Lists.newArrayList();
//
//    /**
//     * 部门ID
//     */
//    private int deptId;
//    /**
//     * 部门GUID
//     */
//    private String guid;
//    /**
//     * 部门编码
//     */
//    private String deptCode;
//    /**
//     * 部门名称
//     */
//    private String deptName;
//    /**
//     * 部门简称
//     */
//    private String simpleName;
//    /**
//     * 直接下级部门
//     */
//    private Collection<DeptTreeVo<T>> children = Lists.newArrayList();
//    /**
//     * 部门的详情数据
//     */
//    private T data;
//    /**
//     * 下级部门是否已折叠
//     */
//    private boolean collapsed;
//
//    /**
//     * 增加下级部门节点
//     *
//     * @param child
//     */
//    public void addChild(DeptTreeVo<T> child) {
//        if (children == null) {
//            children = Lists.newArrayList();
//        }
//        children.add(child);
//    }
//
//    public void addIdPath(int deptId) {
//        if (deptIdPath == null) {
//            deptIdPath = Lists.newArrayList();
//        }
//        deptIdPath.add(deptId);
//    }
//
//    public void addIdPath(Collection<Integer> ids) {
//        if (deptIdPath == null) {
//            deptIdPath = Lists.newArrayList();
//        }
//
//        if (ids != null) {
//            deptIdPath.addAll(ids);
//        }
//    }
//
//    public Collection<Integer> getDeptIdPath() {
//        return deptIdPath;
//    }
//
//    public void setDeptIdPath(Collection<Integer> deptIdPath) {
//        this.deptIdPath.clear();
//        if (deptIdPath != null) {
//            this.deptIdPath.addAll(deptIdPath);
//        }
//    }
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
//    public Collection<DeptTreeVo<T>> getChildren() {
//        return children;
//    }
//
//    public void setChildren(Collection<DeptTreeVo<T>> children) {
//        this.children = children;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public boolean isCollapsed() {
//        return collapsed;
//    }
//
//    public void setCollapsed(boolean collapsed) {
//        this.collapsed = collapsed;
//    }
//}
