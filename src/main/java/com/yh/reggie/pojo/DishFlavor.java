package com.yh.reggie.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 菜品口味关系表
 *
 * @TableName dish_flavor
 */
@TableName(value = "dish_flavor")
@Data
public class DishFlavor implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 菜品
     */
    private Long dishId;
    /**
     * 口味名称
     */
    @NotBlank(message = "口味不能为空")
    private String name;
    /**
     * 口味数据list
     */
    private String value;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;
}