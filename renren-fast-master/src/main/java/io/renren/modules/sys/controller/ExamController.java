package io.renren.modules.sys.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.JsonUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.Exam;
import io.renren.modules.sys.service.ExamService;
import io.renren.modules.sys.service.impl.ExamServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ExamController extends AbstractController {

    @Autowired
    public ExamService ExamService;

    @Autowired
    public ExamServiceImpl ExamServiceImpl;

    /**
     * PC_列表
     */
    @RequiresPermissions("sys:exam:list")
    @RequestMapping(value = "exam/list",method = {RequestMethod.POST,RequestMethod.GET})
    public R list(@RequestParam Map<String, Object> params) {
        params.put("ty","用户记录");
        PageUtils page = ExamServiceImpl.queryPage(params);
        return R.ok().put("page", page);
    }



    //模拟入党测试
    @RequiresPermissions("sys:exam:submitExam")
    @RequestMapping(value = "exam/submitExam",method = {RequestMethod.POST,RequestMethod.GET})
    public R save(@RequestBody Map<String,Object>params){
         String lists = params.get("lists").toString();
        List<Map<String,Object>> list = JsonUtil.fromJson(lists, List.class);
        //默认0分 答对一题10分 60分合格
        int score=0;
        for (int i = 0; i <list.size() ; i++) {
            int id = Integer.parseInt(list.get(i).get("id").toString());
            String value = list.get(i).get("value").toString();
            Exam exam = ExamService.getById(id);
            if(value.equals(exam.getAnsw())){
                score+=10;
            }
        }

        //保存用户分数
        Exam  exam=new Exam();
        exam.setExamTime(new Date());
        exam.setUserName(getUser().getUsername());
        exam.setMobile(getUser().getMobile());
        exam.setPName(getUser().getParentName());
        exam.setIsQualified(score>=60?"合格":"不合格");
        exam.setUserScore(score);
        exam.setTy("用户记录");
        ExamService.save(exam);
        System.out.println("ssa"+score);
        return R.ok().put("score",score);


    }


    @PostMapping("exam/delete")
    @RequiresPermissions("sys:exam:delete")
    public R delete(@RequestBody  Long[] ids){
        for (int i = 0; i < ids.length; i++) {
            Long id=ids[i];
            ExamService.removeById(id);
        }
        return R.ok();
    }




}
