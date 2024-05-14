package com.zzg.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzg.config.db.BaseDao;
import com.zzg.config.db.DbPoolConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class PageRouterController {

    /**
     * 通用界面路由
     * @param page 页面名称
     * @return
     */
    @GetMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @GetMapping("/apitest1")
    @ResponseBody
    public JSONObject apitest1(String name){
        System.out.println(name);
        JSONObject res = new JSONObject();
        List<Map<String, Object>> maps = null;
        try {
            maps = new BaseDao().doSQLQueryToMapList(
                    DbPoolConnection.DbNameEnum.db127, "select * from test");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        res.put("data",maps);
        return res;
    }
}
