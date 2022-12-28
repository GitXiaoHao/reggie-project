package com.yh.reggie.service;

import com.yh.reggie.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yu
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-12-27 11:21:42
*/
public interface CategoryService extends IService<Category> {
    /**
     * 根据id删除
     * 判断是否关联其他菜品
     * @param id 主键id
     * @return 删除成功返回true
     */
    boolean remove(Long id);

    /**
     * 根据分类id查询分类名称
     *
     * @param id
     * @return
     */
    String getNameById(Long id);
}
