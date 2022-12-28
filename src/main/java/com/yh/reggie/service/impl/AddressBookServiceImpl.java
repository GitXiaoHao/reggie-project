package com.yh.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.reggie.pojo.AddressBook;
import com.yh.reggie.service.AddressBookService;
import com.yh.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author yu
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-12-28 20:13:46
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




