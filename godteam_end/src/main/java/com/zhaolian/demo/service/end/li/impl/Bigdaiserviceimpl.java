package com.zhaolian.demo.service.end.li.impl;

import com.zhaolian.demo.data.dao.BigdaiMapper;
import com.zhaolian.demo.data.dao.BigdaiVlidateMapper;
import com.zhaolian.demo.data.entity.Bigdai;
import com.zhaolian.demo.data.entity.BigdaiVlidate;
import com.zhaolian.demo.service.end.li.Bigdaiservice;
import com.zhaolian.demo.web.util.BigdaiDTO;

import com.zhaolian.demo.web.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "Bigdaiserviceimpl")
public class Bigdaiserviceimpl implements Bigdaiservice {
    @Resource
BigdaiMapper dao;
    @Resource
    BigdaiVlidateMapper dao1;
    @Override
    public int daiinsert(Bigdai record) {
int a=0;
a=dao.insert(record);
        return a;
    }

    @Override
    public PageBean<Bigdai> getByPage(BigdaiDTO dto, int pageNo, int pageSize) {
        System.out.println("进入业务层分页和高级查询方法getByPage");
        PageBean<Bigdai> pd=new PageBean<>();
        System.out.println("数据层getByPage   DTO"+dto.toString());
        Map<String,Object> parms=new HashMap<>();
        parms.put("dto",dto);
        int start=(pageNo-1)*pageSize+1;
        int end=pageNo*pageSize;
        parms.put("startIndex",start);
        parms.put("endIndex",end);
        pd.setRows(this.dao.selectBigdaiByPage(parms));
        pd.setTotal(this.dao.getBigdaiCount(dto));
        pd.setPageSize(pageSize);

        return pd;


    }
    @Override
  public   Integer updateByPrimaryKeySelective(Bigdai record){
        Bigdai bigdai=dao.selectByPrimaryKey(record.getBdid());
        if(bigdai.getBdpath()!="" && record.getBdpath()!=null){
            System.out.println("要删除的图片"+bigdai.getBdpath());
            System.out.println("要删除的图片"+record.getBdpath());
            File file=new File(System.getProperty("user.dir")+"/upload/"+bigdai.getBdpath());
            file.delete();
        }

        int a= dao.updateByPrimaryKeySelective(record);
        return a;
    };
    @Override
    public  int deleteByPrimaryKey(BigDecimal bdid){
         dao.deleteByPrimaryKey(bdid);
      return 1;
    }

    @Override
    public int insert(Bigdai record) {
        Bigdai bigdai=dao.selectByPrimaryKey(record.getBdid());
dao.insert(record);


        return 0;
    }

    ;
}
