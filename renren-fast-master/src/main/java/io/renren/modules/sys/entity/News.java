package io.renren.modules.sys.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dj_news")
public class News{
    @TableId
    private long id;
    private String title;
    private String ty;
    private Date createTime;
    private String createBy;
    private String bz;
    private String unit;
    private String num;
    private String content;
    private String kind;
    private String img;
}


