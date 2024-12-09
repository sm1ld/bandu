package com.sm1ld.mapper;

import com.sm1ld.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE id = #{id} AND password=#{password}")
    void GetByIdPw(Admin admin);
}
