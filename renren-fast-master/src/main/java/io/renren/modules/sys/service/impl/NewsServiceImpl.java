package io.renren.modules.sys.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.NewsDao;
import io.renren.modules.sys.entity.News;
import io.renren.modules.sys.service.NewsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("newsService")
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String kind = (String)params.get("kind");
        IPage<News> page = this.page(
                new Query<News>().getPage(params),
                new QueryWrapper<News>()
                        .like(StringUtils.isNotBlank(title),"title", title).eq("kind",kind)
        );
        return new PageUtils(page);
    }
}