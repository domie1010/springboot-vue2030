package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
public interface SysDeptService extends IService<SysDeptEntity> {

    /**
     * 删除
     */
    void delete(Long deptId);

    List<SysDeptEntity> queryList(Map<String, Object> map);

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 获取子部门ID，用于数据过滤
     */
    List<Long> getSubDeptIdList(Long deptId);


    /**
     * 根据父部门，查询子部门
     * @param parentId 父菜单ID
     */
    List<SysDeptEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的部门列表
     */
    List<SysDeptEntity> queryNotButtonList();



}
