package io.renren.modules.sys.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExamDao;
import io.renren.modules.sys.entity.Exam;
import io.renren.modules.sys.service.ExamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("examService")
public class ExamServiceImpl extends ServiceImpl<ExamDao, Exam> implements ExamService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String ty = (String)params.get("ty");
        IPage<Exam> page = this.page(
                new Query<Exam>().getPage(params),
                new QueryWrapper<Exam>().eq("ty",ty)

        );
        return new PageUtils(page);
    }
}