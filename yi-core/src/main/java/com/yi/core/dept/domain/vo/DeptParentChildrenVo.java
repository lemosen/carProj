//package com.yi.core.dept.domain.vo;
//
//import com.google.common.collect.Lists;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yihz.common.convert.domain.VoDomain;
//
//import java.util.Collection;
//
///**
// * 部门, 上级部门, 直接下级部门信息(用于战略要素选择时的部门选择)
// */
//public class DeptParentChildrenVo extends VoDomain {
//    private DeptSimple dept;
//    private Collection<DeptSimple> parents = Lists.newArrayList();
//    private Collection<DeptSimple> children = Lists.newArrayList();
//
//    public DeptSimple getDept() {
//        return dept;
//    }
//
//    public void setDept(DeptSimple dept) {
//        this.dept = dept;
//    }
//
//    public Collection<DeptSimple> getParents() {
//        return parents;
//    }
//
//    public void setParents(Collection<DeptSimple> parents) {
//        this.parents = parents;
//    }
//
//    public Collection<DeptSimple> getChildren() {
//        return children;
//    }
//
//    public void setChildren(Collection<DeptSimple> children) {
//        this.children = children;
//    }
//}
