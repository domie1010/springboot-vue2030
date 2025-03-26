package io.renren.modules.sys.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("exam")
public class Exam {
    @TableId
    private long id;
    private String title;
    private String answ;
    private String userName;
    private Date examTime;
    private int userScore;
    private String isQualified;
    private String ty;
    private String pName;
    private String mobile;

}



