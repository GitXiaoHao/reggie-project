package com.yh.reggie.mapper;

import com.yh.reggie.pojo.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

/**
* @author yu
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-12-28 20:13:46
* @Entity com.yh.reggie.pojo.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




