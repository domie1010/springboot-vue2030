

package io.renren.modules.sys.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Inform;
import io.renren.modules.sys.service.InformService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/sys/inform")
public class InformController extends AbstractController {
    @Autowired
    private InformService InfoService;


    @GetMapping("/list")
    @RequiresPermissions("sys:inform:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = InfoService.queryPage(params);
        return R.ok().put("page", page);
    }


    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        Inform info = InfoService.getById(id);
        return R.ok().put("info", info);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:inform:save")
    public R save(@RequestBody Inform info){
        info.setCreateBy(getUser().getUsername());
        info.setCreateTime(new Date());
        InfoService.saveOrUpdate(info);
        return R.ok();
    }

    @PostMapping("/update")
    @RequiresPermissions("sys:inform:update")
    public R update(@RequestBody Inform info){
        InfoService.updateById(info);
        return R.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions("sys:inform:delete")
    public R update(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            InfoService.removeById(id);
        }
        return R.ok();
    }


}
