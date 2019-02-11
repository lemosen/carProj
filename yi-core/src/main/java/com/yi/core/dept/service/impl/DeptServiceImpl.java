//package com.yi.core.dept.service.impl;
//
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//
//import com.yi.core.common.ObjectChooseQuery;
//import com.yi.core.common.ObjectType;
//import com.yi.core.dept.dao.DeptDao;
//import com.yi.core.dept.domain.bo.DeptBo;
//import com.yi.core.dept.domain.entity.Dept;
//import com.yi.core.dept.domain.simple.DeptSimple;
//import com.yi.core.dept.domain.vo.DeptListVo;
//import com.yi.core.dept.domain.vo.DeptParentChildrenVo;
//import com.yi.core.dept.domain.vo.DeptTreeVo;
//import com.yi.core.dept.domain.vo.DeptVo;
//import com.yi.core.dept.service.IDeptService;
//import com.yi.core.user.dao.UserDao;
//import com.yi.core.user.domain.bo.UserBo;
//import com.yi.core.user.domain.entity.User;
//import com.yi.core.user.domain.simple.UserSimple;
//import com.yi.core.user.domain.vo.UserListVo;
//import com.yi.core.user.domain.vo.UserVo;
//import com.yi.core.user.service.IUserService;
//import com.yi.core.utils.LayerCodeUtils;
//import com.yi.core.utils.ObjectPathUtils;
//import com.yihz.common.convert.AbstractBeanConvert;
//import com.yihz.common.convert.BeanConvert;
//import com.yihz.common.convert.BeanConvertManager;
//import com.yihz.common.convert.EntityListVoBoSimpleConvert;
//import com.yihz.common.exception.BusinessException;
//import com.yihz.common.persistence.Pagination;
//import com.yihz.common.utils.ValueUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.criteria.Predicate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional
//public class DeptServiceImpl implements IDeptService, InitializingBean {
//    private final Logger LOG = LoggerFactory.getLogger(DeptServiceImpl.class);
//
//    @Autowired
//    private BeanConvertManager beanConvertManager;
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private DeptDao deptDao;
//    @Autowired
//    private UserDao userDao;
//
////    @Autowired
////    private DeptUserDao deptUserDao;
//
//    private EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> deptConvert;
//
//    @Override
//    public <T> DeptTreeVo<T> getDeptTreeVo() {
//        Dept rootDept = getTopDept();
//        if (rootDept == null) {
//            DeptTreeVo node = new DeptTreeVo();
//            return node;
//        }
//
//        Stack<Integer> idPath = new Stack<Integer>();
//        Set<Integer> usedDeptIds = Sets.newHashSet();
//
//        DeptTreeVo root = makeTreeNode(rootDept, idPath, usedDeptIds);
//        return root;
//    }
//
//    /**
//     * 获取部门信息, 包括员工数量和下级部门信息
//     *
//     * @param dept
//     * @param idPath
//     * @param usedDeptIds
//     * @return
//     */
//    private <T> DeptTreeVo<T> makeTreeNode(Dept dept, Stack<Integer> idPath, Set<Integer> usedDeptIds) {
//        final int deptId = dept.getDeptId();
//        idPath.push(deptId);
//        usedDeptIds.add(deptId);
//
//        DeptTreeVo node = new DeptTreeVo();
//        node.addIdPath(idPath);
//        node.setDeptId(dept.getDeptId());
//        node.setGuid(dept.getGuid());
//        node.setDeptCode(dept.getDeptCode());
//        node.setDeptName(dept.getDeptName());
//        node.setSimpleName(dept.getSimpleName());
//        node.setCollapsed(false);
//
//        Collection<Dept> children = findChildren(deptId);
//        for (Dept child : children) {
//            if (!child.isEnabled()) {
//                continue;
//            }
//
//            if (!usedDeptIds.contains(child.getDeptId())) {
//                node.addChild(makeTreeNode(child, idPath, usedDeptIds));
//            }
//        }
//
//        idPath.pop();
//        return node;
//    }
//
//    @Override
//    public List<Dept> getAllParents(int deptId) {
//        Dept dept = getById(deptId);
//        if (dept == null) {
//            return Collections.EMPTY_LIST;
//        }
//
//        List<Dept> parents = Lists.newArrayList();
//        while (dept.getParentDept() != null) {
//            parents.add(0, dept.getParentDept());
//            dept = dept.getParentDept();
//        }
//
//        return parents;
//    }
//
//    @Override
//    public DeptParentChildrenVo getParentsAndChildren(int deptId) {
//        Dept dept = getById(deptId);
//        if (dept == null) {
//            return new DeptParentChildrenVo();
//        }
//
//        DeptParentChildrenVo vo = new DeptParentChildrenVo();
//        vo.setDept(deptConvert.toSimple(dept));
//
//        // 记录已经处理的部门ID, 避免数据异常时的死循环
//        Set<Integer> ids = Sets.newHashSet();
//        ids.add(deptId);
//
//        List<Dept> parents = Lists.newArrayList();
//        Dept parent = dept.getParentDept();
//        while (parent != null) {
//            if (ids.contains(parent.getDeptId())) {
//                break;
//            }
//
//            ids.add(parent.getDeptId());
//            parents.add(0, parent);
//
//            parent = parent.getParentDept();
//        }
//
//        vo.setParents(deptConvert.toSimples(parents));
//        vo.setChildren(deptConvert.toSimples(findChildren(deptId)));
//
//        return vo;
//    }
//
//    /**
//     * 获取部门根节点(上级部门为NULL, 不允许存在多个这样的节点)
//     *
//     * @return
//     */
//    @Override
//    public Dept getTopDept() {
//        Dept dept = deptDao.getOne((root, query, cb) -> {
////            return cb.isNull(root.get(Dept_.parentDept));
//            return null;
//        });
//
//        return dept;
//    }
//
//    /**
//     * 获取某个部门的直接下级部门
//     *
//     * @param deptId
//     * @return
//     */
//    @Override
//    public List<Dept> findChildren(int deptId) {
//        List<Dept> list = deptDao.findAll((root, query, cb) -> {
//            // 按 showOrder + deptCode 排序
////            query.orderBy(cb.asc(root.get(Dept_.showOrder)), cb.asc(root.get(Dept_.deptCode)));
//            return cb.and(
////                    cb.equal(root.get(Dept_.parentDept).get(Dept_.deptId), deptId),
////                    cb.isTrue(root.get(Dept_.enabled))
//            );
//        });
//
//        return list;
//    }
//
//    /**
//     * 获取某个部门的所有下级部门
//     *
//     * @param deptId
//     * @return
//     */
//    @Override
//    public List<Dept> findAllChildren(int deptId) {
//        Dept dept = deptDao.getOne(deptId);
//        if (dept == null) {
//            return Collections.EMPTY_LIST;
//        }
//
//        List<Dept> list = deptDao.findAll((root, query, cb) -> {
////            query.orderBy(cb.asc(root.get(Dept_.parentDept)), cb.asc(root.get(Dept_.showOrder)), cb.asc(root.get(Dept_.deptCode)));
//            return cb.and(
////                    cb.like(root.get(Dept_.layerCode), dept.getLayerCode() + "%"),
////                    cb.isTrue(root.get(Dept_.enabled))
//            );
//        });
//
//        return list;
//    }
//
//    /**
//     * 获取部门的启用员工数量
//     *
//     * @param deptId
//     * @return
//     */
//    @Override
//    public int getUserCountByDept(int deptId) {
////        long count = deptUserDao.count((root, query, cb) -> {
////            return cb.and(
//////                    cb.equal(root.get(DeptUser_.dept).get(Dept_.deptId), deptId),
//////                    cb.isTrue(root.get(DeptUser_.user).get(User_.enabled))
////            );
////        });
//
////        return (int) count;
//        return (int) 0;
//    }
//
//    @Override
//    public Page<Dept> query(Pagination<Dept> query) {
//        query.setEntityClazz(Dept.class);
//        Page<Dept> page = deptDao.findAll(query, query.getPageRequest());
//        return page;
//    }
//
//    @Override
//    public Page<DeptVo> queryVo(Pagination query) {
//        Page<Dept> pages = this.query(query);
//
//        List<DeptVo> vos = deptConvert.toVos(pages.getContent());
//        Page<DeptVo> voPage = new PageImpl<DeptVo>(vos, query.getPageRequest(), pages.getTotalElements());
//        return voPage;
//    }
//
//    @Override
//    public Page<DeptListVo> queryListVo(Pagination query) {
//        Page<Dept> pages = this.query(query);
//
//        List<DeptListVo> vos = deptConvert.toListVos(pages.getContent());
//        Page<DeptListVo> voPage = new PageImpl<DeptListVo>(vos, query.getPageRequest(), pages.getTotalElements());
//        return voPage;
//    }
//
//    @Override
//    public List<DeptSimple> queryObjectsByConditions(ObjectChooseQuery chooseQuery) {
//        List<Dept> depts = deptDao.findAll((root, query, cb) -> {
//            Predicate predicate = cb.conjunction();
////            int deptId = chooseQuery.getDeptId();
////            if (StringUtils.isNotBlank(chooseQuery.getQueryText())) {
////                predicate = cb.and(predicate, cb.like(root.get(Dept_.deptName), "%" + chooseQuery.getQueryText() + "%"));
////            }
////            if (deptId == 0) {
////                predicate = cb.and(predicate, cb.equal(root.get(Dept_.deptId), 1));
////            } else {
////                predicate = cb.and(predicate, cb.equal(root.get(Dept_.parentDept).get(Dept_.deptId), deptId));
////            }
////
////            predicate = cb.and(predicate, cb.isTrue(root.get(Dept_.enabled)));
////
////            query.orderBy(cb.asc(root.get(Dept_.showOrder)), cb.asc(root.get(Dept_.deptCode)));
//            return predicate;
//        });
//        return deptConvert.toSimples(depts);
//    }
//
//    @Override
//    public Page<DeptSimple> querySimple(Pagination query) {
//        Page<Dept> pages = this.query(query);
//
//        List<DeptSimple> simples = deptConvert.toSimples(pages.getContent());
//        Page<DeptSimple> listVoPage = new PageImpl<DeptSimple>(simples, query.getPageRequest(), pages.getTotalElements());
//        return listVoPage;
//    }
//
//    @Override
//    public Page<DeptBo> queryBo(Pagination query) {
//        Page<Dept> pages = this.query(query);
//
//        List<DeptBo> bos = deptConvert.toBos(pages.getContent());
//        Page<DeptBo> boPage = new PageImpl<DeptBo>(bos, query.getPageRequest(), pages.getTotalElements());
//        return boPage;
//    }
//
//    @Override
//    public Dept getById(int deptId) {
//        return deptDao.getOne(deptId);
//    }
//
//    @Override
//    public Set<Dept> getOnes(int... deptIds) {
//        if (ArrayUtils.isEmpty(deptIds)) {
//            return null;
//        }
//
//        Set<Dept> set = Sets.newLinkedHashSet();
//        for (int index = 0; index < deptIds.length; ++index) {
//            Dept dept = deptDao.getOne(deptIds[index]);
//            if (dept != null) {
//                set.add(dept);
//            }
//        }
//
//        return set;
//    }
//
//    @Override
//    public Set<Dept> getOnes(Collection<Integer> deptIds) {
//        if (CollectionUtils.isEmpty(deptIds)) {
//            return null;
//        }
//
//        Set<Dept> set = Sets.newLinkedHashSet();
//        for (Integer deptId : deptIds) {
//            if (deptId == null) {
//                continue;
//            }
//
//            Dept dept = deptDao.getOne(deptId);
//            if (dept != null) {
//                set.add(dept);
//            }
//        }
//
//        return set;
//    }
//
//    @Override
//    public DeptBo getBoById(int deptId) {
//        return deptConvert.toBo(getById(deptId));
//    }
//
//    @Override
//    public DeptVo getVoById(int deptId) {
//        return deptConvert.toVo(getById(deptId));
//    }
//
//    @Override
//    public DeptListVo getListVoById(int deptId) {
//        return deptConvert.toListVo(getById(deptId));
//    }
//
//    @Override
//    public Collection<DeptSimple> findSimpleByIds(Set<Integer> deptIds) {
//        Collection<Dept> depts = getOnes(deptIds);
//        return deptConvert.toSimples(depts);
//    }
//
//    @Override
//    public DeptSimple getSimpleById(int deptId) {
//        return deptConvert.toSimple(getById(deptId));
//    }
//
//    @Override
//    public Dept add(DeptBo deptBo) {
//        if (StringUtils.isBlank(deptBo.getGuid())) {
//            deptBo.setGuid(ValueUtils.newGUID());
//        }
//
//        Dept dept = deptConvert.toEntity(deptBo);
//
//        Dept parentDept = dept.getParentDept();
//        if (parentDept == null) {
//            throw new BusinessException("notFound","找不到该部门的上级部门资料");
//
//        } else if (!parentDept.isEnabled()) {
//            throw new BusinessException("superiorDeptDisabled","所属上级部门不是启用状态");
//        }
//
//        // 计算部门的层级编码
//        String parentLayerCode = parentDept != null ? parentDept.getLayerCode() : "";
//        List<String> levelCodeStrings = deptDao.getDeptLevelCodesByParentDept(parentDept);
//        dept.setLayerCode(LayerCodeUtils.calParentLevelCodeThree(parentLayerCode, levelCodeStrings));
//
//        Dept dbDept = deptDao.save(dept);
//
//        return dbDept;
//    }
//
//    @Override
//    public Dept update(DeptBo deptBo) {
//        Dept dept = deptConvert.toEntity(deptBo);
//
//        Dept dbDept = deptDao.getOne(dept.getDeptId());
//
//        final Dept oldParentDept = dept.getParentDept();
//        final Dept newParentDept = dbDept.getParentDept();
//        final boolean isTop = oldParentDept == null;
//
//        if (isTop && newParentDept != null) {
//            throw new BusinessException("cannotEditSuperiorDept","不能修改公司的上级部门属性");
//        }
//
////        if (isTop) {
////            // 部门根节点, 不允许修改名称,是否启用资料
////            beanConvertManager.copying(dept, dbDept,
////                    Dept_.simpleName, Dept_.deptCode, Dept_.showOrder
////            );
////
////        } else {
////            beanConvertManager.copying(dept, dbDept,
////                    Dept_.deptName, Dept_.simpleName, Dept_.enabled,
////                    Dept_.deptCode, Dept_.showOrder
////            );
////        }
//
//        boolean parentDeptChanged = !isSameDept(oldParentDept, newParentDept);
//        if (parentDeptChanged) {
//            List<String> levelCodeStrings = deptDao.getDeptLevelCodesByParentDept(newParentDept);
//            dept.setLayerCode(LayerCodeUtils.calParentLevelCodeThree(newParentDept.getLayerCode(), levelCodeStrings));
//        }
//
//        dbDept.setParentDept(newParentDept);
//        deptDao.flush();
//
//        if (parentDeptChanged) {
//            // 更新下级的编码
//            List<Dept> children = deptDao.findAll((root, query, cb) -> {
//                if (dbDept.getParentDept() != null) {
////                    return cb.equal(root.get(Dept_.parentDept), dbDept.getParentDept());
//                } else {
////                    return cb.isNull(root.get(Dept_.parentDept));
//                }
//                return null;
//            });
//
//            String parentLayerCode = dbDept.getLayerCode();
//            int order = 0;
//            for (Dept child : children) {
//                child.setLayerCode(parentLayerCode + LayerCodeUtils.decimalFormatThree.get().format(order));
//                order++;
//            }
//            deptDao.flush();
//        }
//
//        return dbDept;
//    }
//
//    @Override
//    public Dept changeShowOrder(int deptId, int delta) {
//        Dept dept = getById(deptId);
//        if (dept == null) {
//            throw new BusinessException("notFoundData", "找不到部门资料");
//        }
//
//        if (dept.getParentDept() == null) {
//            // 根节点,不需要处理
//            return dept;
//        }
//
//        List<Dept> list = deptDao.findAll((root, query, cb) -> {
//            // 按 showOrder + deptCode 排序
////            query.orderBy(cb.asc(root.get(Dept_.showOrder)), cb.asc(root.get(Dept_.deptCode)));
//            return cb.and(
////                    cb.equal(root.get(Dept_.parentDept), dept.getParentDept()),
////                    cb.isTrue(root.get(Dept_.enabled))
//            );
//        });
//
//        int index = list.size() - 1;
//        while (index >= 0) {
//            if (list.get(index).getDeptId() == deptId) {
//                break;
//            }
//            index--;
//        }
//
//        if (index == -1) {
//            return dept;
//        }
//
//        list.remove(index);
//
//        int pos = index + delta;
//        if (pos < 0) {
//            list.add(0, dept);
//        } else if (pos >= list.size()) {
//            list.add(dept);
//        } else {
//            list.add(pos, dept);
//        }
//
//        pos = 0;
//        for (Dept item : list) {
//            item.setShowOrder(pos++);
//        }
//        deptDao.flush();
//
//        return dept;
//    }
//
//    /**
//     * 判断部门是否相等
//     *
//     * @param dept1
//     * @param dept2
//     * @return
//     */
//    protected boolean isSameDept(Dept dept1, Dept dept2) {
//        if (dept1 == dept2) {
//            return true;
//        }
//        if (dept1 == null || dept2 == null) {
//            return false;
//        }
//        return dept1.getDeptId() == dept2.getDeptId();
//    }
//
//    @Override
//    public void removeById(final int deptId) {
//        Dept dept = getById(deptId);
//        if (dept == null) {
//            return;
//        }
//
//        if (dept.getParentDept() == null) {
//            throw new BusinessException("rootNotRemove","公司不允许删除");
//        }
//
//        // 因为一个员工至少属于一个部门, 如果有员工只属于这个要删除的部门时, 该部门不允许删除
//        List<User> users = userDao.findAll((root, query, cb) -> {
////            return cb.equal(root.join(User_.deptUsers, JoinType.INNER).get(DeptUser_.dept).get(Dept_.deptId), deptId);
//            return null;
//        });
//
//        List<User> mustMoveUsers = Lists.newArrayList();
////        for (User user : users) {
////            long count = deptUserDao.count((root, query, cb) -> {
////                return cb.and(
//////                        cb.equal(root.get(DeptUser_.user).get(User_.userId), user.getUserId()),
//////                        cb.notEqual(root.get(DeptUser_.dept).get(Dept_.deptId), deptId)
////                );
////            });
////            if (count == 0) {
////                mustMoveUsers.add(user);
////            }
////        }
//
//        if (mustMoveUsers.size() > 0) {
//            String userNames = mustMoveUsers.stream()
//                    .map(user -> user.getUserName())
//                    .collect(Collectors.joining(","));
//            throw new BusinessException("mustMoveUser", "请先将员工[ " + userNames + " ]移动到其他部门后才能删除本部门");
//        }
//
//        deptDao.deleteById(deptId);
//    }
//
//    /* --------------------- 实体,VO,BO 转换实现 --------------------- */
//
//    /**
//     * 获取部门的对象路径
//     *
//     * @param dept
//     * @return
//     */
//    private String getDeptObjectPath(Dept dept) {
//        return ObjectPathUtils.concatObjectPath(ObjectType.DEPT, dept.getDeptId());
//    }
//
//    @Override
//    public EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo> getDeptConvert() {
//        return deptConvert;
//    }
//
//    protected void initConvert() {
//        this.deptConvert = new EntityListVoBoSimpleConvert<Dept, DeptBo, DeptVo, DeptSimple, DeptListVo>(beanConvertManager) {
//            @Override
//            protected BeanConvert<Dept, DeptVo> createEntityToVoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<Dept, DeptVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(DeptVo deptVo, Dept dept) {
//                        EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> userConvert = userService.getUserConvert();
//                        int deptId = dept.getDeptId();
////                        deptVo.setUsers(userConvert.toListVos(findUsersByDept(deptId)));
//
//                        deptVo.setChildren(deptConvert.toListVos(findChildren(deptId)));
//
//                        deptVo.setObjectPath(getDeptObjectPath(dept));
//                    }
//                };
//            }
//
//
//            @Override
//            protected BeanConvert<Dept, DeptListVo> createEntityToListVoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<Dept, DeptListVo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(DeptListVo deptListVo, Dept dept) {
//                        EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> userConvert = userService.getUserConvert();
//                        int deptId = dept.getDeptId();
////                        deptListVo.setUsers(userConvert.toListVos(findUsersByDept(deptId)));
//
//                        deptListVo.setObjectPath(getDeptObjectPath(dept));
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<Dept, DeptBo> createEntityToBoConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<Dept, DeptBo>(beanConvertManager) {
//                    @Override
//                    protected void postConvert(DeptBo deptBo, Dept dept) {
//                        EntityListVoBoSimpleConvert<User, UserBo, UserVo, UserSimple, UserListVo> userConvert = userService.getUserConvert();
//                        int deptId = dept.getDeptId();
//
////                        deptBo.setUsers(userConvert.toSimples(findUsersByDept(deptId)));
//
//                        deptBo.setChildren(deptConvert.toListVos(findChildren(deptId)));
//
//                        deptBo.setObjectPath(getDeptObjectPath(dept));
//                    }
//                };
//            }
//
//            @Override
//            protected BeanConvert<DeptBo, Dept> createBoToEntityConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<DeptBo, Dept>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<Dept, DeptSimple> createEntityToSimpleConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<Dept, DeptSimple>(beanConvertManager) {
//                };
//            }
//
//            @Override
//            protected BeanConvert<DeptSimple, Dept> createSimpleToEntityConvert(BeanConvertManager beanConvertManager) {
//                return new AbstractBeanConvert<DeptSimple, Dept>(beanConvertManager) {
//                    @Override
//                    public Dept convert(DeptSimple deptSimple) throws BeansException {
//                        return deptDao.getOne(deptSimple.getDeptId());
//                    }
//                };
//            }
//        };
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        initConvert();
//    }
//}
