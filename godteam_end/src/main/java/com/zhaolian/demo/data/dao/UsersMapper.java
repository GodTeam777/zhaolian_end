package com.zhaolian.demo.data.dao;

import com.zhaolian.demo.data.entity.Users;
import com.zhaolian.demo.data.entity.UsersExample;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {

    //房屋认证修改额度
    int updateHome(Users record);
    //车辆认证修改额度
    int updateCAR(Users record);
    //学历认证修改额度
    int update(Users record);

    Users LoginBypetnameAnduspws(Map<String,Object> map);

    long countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(BigDecimal usersid);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(BigDecimal usersid);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}