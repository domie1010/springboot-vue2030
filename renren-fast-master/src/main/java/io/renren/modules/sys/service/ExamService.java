package io.renren.modules.sys.service;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.Exam;
import io.renren.modules.sys.entity.File;

import java.util.Map;

public interface ExamService extends IService<Exam> {
    PageUtils queryPage(Map<String, Object> params);
}