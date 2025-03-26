package io.renren.modules.sys.service;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Inform;
import io.renren.modules.sys.entity.News;

import java.util.Map;

public interface NewsService extends IService<News> {
    PageUtils queryPage(Map<String, Object> params);
}