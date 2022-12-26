package com.yh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.reggie.common.Result;
import com.yh.reggie.pojo.Category;
import com.yh.reggie.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

/**
 * date 2022/12/27
 *
 * @author yu
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category 实体类
     * @return 是否成功
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category){
        boolean save = categoryService.save(category);
        return save ? Result.success("添加成功") : Result.error("添加失败");
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/page")
    public Result<Page<Category>> page(int page , int pageSize){
        Page<Category> categoryPage = new Page<>(page,pageSize);
        //排序条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage,wrapper);
        return Result.success(categoryPage);
    }
    /**
     * 删除数据
     */
    @DeleteMapping
    public Result<String> delete(Long id){
        Optional<Long> optional = Optional.ofNullable(id);
        boolean remove = categoryService.remove(optional.orElse(0L));
        return remove ? Result.success("删除成功") : Result.error("删除失败");
    }
    /**根据id修改分类信息*/
    @PutMapping
    public Result<String > update(@RequestBody Category category){
        boolean update = categoryService.updateById(category);
        return update ? Result.success("修改成功") : Result.error("修改失败");
    }
    /**获取分类*/
    @GetMapping("/list")
    public Result<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //添加实体
        wrapper.eq(category.getType() != null, Category::getType,category.getType())
                //添加排序条件
                .orderByAsc(Category::getSort)
                .orderByAsc(Category::getUpdateTime);
        List<Category> categoryList = categoryService.list(wrapper);
        return Result.success(categoryList);
    }
}
