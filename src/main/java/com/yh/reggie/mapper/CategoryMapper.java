package com.yh.reggie.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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
    /**
     * 根据id查询分类名
     * @param id 分类id
     * @return 只有name的分类
     */
    List<Category> selectNameById(@Param("id") Long id);
}




