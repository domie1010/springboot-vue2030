package io.renren.modules.sys.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.InformDao;
import io.renren.modules.sys.entity.Inform;
import io.renren.modules.sys.service.InformService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("informService")
public class InformServiceImpl extends ServiceImpl<InformDao, Inform> implements InformService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        IPage<Inform> page = this.page(
                new Query<Inform>().getPage(params),
                new QueryWrapper<Inform>()
                        .like(StringUtils.isNotBlank(title),"title", title)
        );
        return new PageUtils(page);
    }
}