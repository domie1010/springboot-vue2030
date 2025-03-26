package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

//    List<SysDeptEntity> queryList(Map<String, Object> params);

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
//    List<Long> queryDetpIdList(Long parentId);

    /**
     * 根据父部门，查询子部门
     *
     * @param parentId 父菜单ID
     */
    List<SysDeptEntity> queryListParentId(Long parentId);

    /**
     * 获取部门列表
     */
    List<SysDeptEntity> queryNotButtonList();

}
