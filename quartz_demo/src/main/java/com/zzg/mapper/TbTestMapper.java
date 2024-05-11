package com.zzg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzg.bean.TbTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@Mapper
public interface TbTestMapper extends BaseMapper<TbTest> {


}
