package com.yh.reggie.mapper;

import com.yh.reggie.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-12-27 11:21:42
* @Entity com.yh.reggie.pojo.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




