//package com.yi.core.dept.domain;
//
//import com.yi.core.dept.domain.vo.DeptTreeVo;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.util.Collection;
//
///**
// * 机构树遍历
// *
// * @param <T>
// */
//public abstract class DeptTreeTraverse<T> {
//    /**
//     * 处理指定的部门节点
//     *
//     * @param node
//     */
//    protected abstract void map(DeptTreeVo<T> node);
//
//    /**
//     * 遍历整个机构树
//     *
//     * @param root
//     */
//    public void traverse(DeptTreeVo<T> root) {
//        map(root);
//        forEach(root.getChildren());
//    }
//
//    /**
//     * 遍历机构树列表和所有下级部门
//     *
//     * @param nodes
//     */
//    private void forEach(Collection<DeptTreeVo<T>> nodes) {
//        if (CollectionUtils.isEmpty(nodes)) {
//            return;
//        }
//
//        for (DeptTreeVo<T> node : nodes) {
//            map(node);
//
//            forEach(node.getChildren());
//        }
//    }
//}
