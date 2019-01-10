//package com.yi.core.dept.domain.entity;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import javax.validation.constraints.NotBlank;
//
//import javax.persistence.*;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import java.io.Serializable;
//
//import static javax.persistence.GenerationType.IDENTITY;
//
///**
// * 部门
// */
//@Entity
//@DynamicInsert
//@DynamicUpdate
//@Table
//public class Dept implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 部门ID
//     */
//    private int deptId;
//    /**
//     * GUID
//     */
//    @NotBlank
//    private String guid;
//    /**
//     * 上级部门
//     */
//    private Dept parentDept;
//    /**
//     * 部门编码
//     */
//    @NotBlank
//    private String deptCode;
//    /**
//     * 部门名称
//     */
//    @NotBlank
//    private String deptName;
//    /**
//     * 部门简称
//     */
//    private String simpleName;
//    /**
//     * 排序
//     */
//    @Min(0)
//    @Max(32767)
//    private int showOrder;
//    /***
//     * 节点编码
//     */
//    @NotBlank
//    private String layerCode;
//    /**
//     * 状态
//     */
//    private boolean enabled;
//
//    public Dept() {
//
//    }
//
//    public Dept(int deptId) {
//        this.deptId = deptId;
//    }
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    @Column(unique = true, nullable = false, length = 10)
//    public int getDeptId() {
//        return this.deptId;
//    }
//
//    public void setDeptId(int deptId) {
//        this.deptId = deptId;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getDeptCode() {
//        return deptCode;
//    }
//
//    public void setDeptCode(String deptCode) {
//        this.deptCode = deptCode;
//    }
//
//    @Column(unique = false, nullable = false, length = 256)
//    public String getDeptName() {
//        return this.deptName;
//    }
//
//    public void setDeptName(String deptName) {
//        this.deptName = deptName;
//    }
//
//    @Column(unique = false, nullable = true, length = 32)
//    public String getSimpleName() {
//        return this.simpleName;
//    }
//
//    public void setSimpleName(String simpleName) {
//        this.simpleName = simpleName;
//    }
//
//    @Column(unique = false, nullable = false, length = 5)
//    public int getShowOrder() {
//        return this.showOrder;
//    }
//
//    public void setShowOrder(int showOrder) {
//        this.showOrder = showOrder;
//    }
//
//    @Column(nullable = false, length = 32)
//    public String getLayerCode() {
//        return layerCode;
//    }
//
//    public void setLayerCode(String layerCode) {
//        this.layerCode = layerCode;
//    }
//
//    @Column(nullable = false, length = 1)
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    @Column(nullable = false, length = 64, updatable = false)
//    public String getGuid() {
//        return guid;
//    }
//
//    public void setGuid(String guid) {
//        this.guid = guid;
//    }
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PARENT_DEPT_ID")
//    public Dept getParentDept() {
//        return parentDept;
//    }
//
//    public void setParentDept(Dept parentDept) {
//        this.parentDept = parentDept;
//    }
//
//}