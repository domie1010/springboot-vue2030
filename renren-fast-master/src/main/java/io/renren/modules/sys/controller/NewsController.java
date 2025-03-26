

package io.renren.modules.sys.controller;

import io.renren.common.utils.FileUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Inform;
import io.renren.modules.sys.entity.News;
import io.renren.modules.sys.service.NewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/news")
public class NewsController extends AbstractController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/list")
    @RequiresPermissions("sys:news:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("kind","党建要闻");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list_zzfc")
    @RequiresPermissions("sys:news:list")
    public R list_zzfc(@RequestParam Map<String, Object> params){
        params.put("kind","组织风采");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/list_zzfz")
    @RequiresPermissions("sys:news:list")
    public R list_zzfz(@RequestParam Map<String, Object> params){
        params.put("kind","组织发展");
        PageUtils page = newsService.queryPage(params);
        return R.ok().put("page", page);
    }



    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:news:info")
    public R info(@PathVariable("id") Long id){
        News news = newsService.getById(id);
        return R.ok().put("news", news);
    }

    @PostMapping("/save")
    @RequiresPermissions("sys:news:save")
    public R save(@RequestBody News news){
        news.setCreateBy(getUser().getUsername());
        news.setCreateTime(new Date());
        newsService.saveOrUpdate(news);
        return R.ok();
    }


    @PostMapping("/saveZZFC")
    @RequiresPermissions("sys:news:save")
    public R saveZZFC(HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String ty = request.getParameter("ty");
        String num = request.getParameter("num");
        String unit = request.getParameter("unit");
        String bz = request.getParameter("bz");
        String kind = request.getParameter("kind");
        News news = new News();
        MultipartHttpServletRequest multipartRequest = WebUtils
                .getNativeRequest(request, MultipartHttpServletRequest.class);
        if (multipartRequest != null) {
            Iterator<String> names = multipartRequest.getFileNames();
            while (names.hasNext()) {
                List<MultipartFile> files = multipartRequest.getFiles(names.next());
                if (files != null && files.size() > 0) {
                    for (MultipartFile file : files) {
                        //保持数据
                        news.setBz(bz);
                        news.setImg("1");
                        news.setTitle(title);
                        news.setContent(content);
                        news.setNum(num);
                        news.setTy(ty);
                        news.setKind(kind);
                        news.setUnit(unit);
                        news.setCreateBy(getUser().getUsername());
                        news.setCreateTime(new Date());
                        String uuid = FileUtil.uuid();
                        String fileExt = FileUtil.getFileExt(file.getOriginalFilename());
                        String img = uploadMobileFile(uuid, fileExt, file);
                        String base="http://localhost:8080/renren-fast/image/";
                        news.setImg(base+img);
                    }
                }
            }
        }
        newsService.saveOrUpdate(news);
        return R.ok();
    }


    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String uploadMobileFile(String uuid,String SUFFIX,MultipartFile file) throws IllegalStateException, IOException{
        String basePath="C:\\Users\\Administrator\\Desktop\\11月毕设\\";
        File realFile = new File(basePath);
        if (!realFile.exists() && !realFile.isDirectory()) {
            realFile.mkdirs();
        }
        String fullFile = realFile + File.separator+uuid+"."+SUFFIX;
        file.transferTo(new File(fullFile));
        return uuid+"."+SUFFIX;

    }


    @PostMapping("/update")
    @RequiresPermissions("sys:news:update")
    public R update(@RequestBody News news){
        newsService.updateById(news);
        return R.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions("sys:news:delete")
    public R delete(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            newsService.removeById(id);
        }
        return R.ok();
    }




}
