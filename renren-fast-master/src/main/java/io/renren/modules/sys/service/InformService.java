package io.renren.modules.sys.service;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Inform;

import java.util.Map;

public interface InformService extends IService<Inform> {
    PageUtils queryPage(Map<String, Object> params);
}