package com.zzg.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.ToString;

import java.io.Serializable;

@TableName("test")
@ToString
public class TbTest implements Serializable {
    private static final long serialVersionUID = -4425232294147067362L;

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "sid")
    private String sid;

    @TableField("name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
