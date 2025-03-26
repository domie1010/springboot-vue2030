package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {


    @Override
    public List<SysDeptEntity> queryList(Map<String, Object> map) {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return null;
    }


    @Override
    public List<Long> getSubDeptIdList(Long deptId){
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    @Override
    public List<SysDeptEntity> queryListParentId(Long parentId) {

        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysDeptEntity> queryNotButtonList() {

        return baseMapper.queryNotButtonList();
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
        for(Long deptId : subIdList){
            List<Long> list = queryDetpIdList(deptId);
            if(list.size() > 0){
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }

    public void delete(Long deptId){
        //删除菜单
        this.removeById(deptId);
        //删除菜单与角色关联
    }
}
