package com.zhaolian.demo.web.control.front;

import com.github.pagehelper.Page;
import com.zhaolian.demo.data.dao.UsersMapper;
import com.zhaolian.demo.data.entity.SamlldaiOrder;
import com.zhaolian.demo.data.entity.Users;
import com.zhaolian.demo.service.front.lg.IBigDaiService;
import com.zhaolian.demo.service.front.lg.ISmallDaiService;
import com.zhaolian.demo.service.util.PageBean;
import com.zhaolian.demo.web.dto.lg.BigDaiDTO;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class LgMyControl {

    @Resource
    UsersMapper userDao;
    @Resource
    ISmallDaiService smallDaiService;
    @Resource
    IBigDaiService bigDaiService;

    @ModelAttribute
    public void myInit(HttpSession session){

    }

    @RequestMapping("/smalldai_home")
    public @ResponseBody
    Map smalldaihome(HttpSession session){
//        Users user1 = userDao.selectByPrimaryKey(new BigDecimal(4));
//        session.setAttribute("myuser",user1);
        Users user =(Users) session.getAttribute("myuser");
        System.out.println("aaa:"+user.toString());
        Map list=smallDaiService.smallDai_home(user);
        return list;
    }

    //获得银行卡信息
    @RequestMapping("/mybankcard")
    public @ResponseBody
    List<Object> mybankcard(HttpSession session){
        Users user =(Users) session.getAttribute("myuser");
        List<Object> list=smallDaiService.smalldai_brank(user);
        return list;
    }
    //验证支付密码
    @RequestMapping("/zhifupswp")
    public @ResponseBody
    String zhifupswp(HttpSession session, @RequestBody Map data){
        Users user =(Users) session.getAttribute("myuser");
        System.out.println("前台传过来的支付密码"+data.get("zhifupws"));
        System.out.println("用户的支付密码"+(user.getZfpws().toString()));
        if ((user.getZfpws().toString()).equals(data.get("zhifupws"))){
            return "1";
        }else {
            return "0";
        }
    }

    //记录小额贷款
    @RequestMapping("/smalldaio")
    public @ResponseBody
    boolean smalldaio(HttpSession session, @RequestBody Map datas){
        Map data=(Map) datas.get("form");
        Users user =(Users) session.getAttribute("myuser");
        System.out.println("贷款记录+"+data.toString());
        //System.out.println((BigDecimal)data.get("smalldai"));
        SamlldaiOrder so=new SamlldaiOrder();
        so.setDaiDate(new Date());
        so.setDaimoney(new BigDecimal(data.get("smalldai").toString()));
        so.setOnemoney(new BigDecimal(data.get("meiri").toString()));
        so.setMou(Short.parseShort(data.get("dai_date").toString()));

        so.setHuanCard(data.get("brank2").toString());
        so.setShouCard(data.get("brank1").toString());
        so.setUsersid(user.getUsersid());
        so.setYihuan(new BigDecimal(0));
        return this.smallDaiService.smalldai(user,so);
    }

    //记录大额贷款
    @RequestMapping("/bigdaiall_home")
    public @ResponseBody
    PageBean smalldaio(@RequestBody Map query){
        System.out.println("大额贷款传递的值："+query.toString());
        BigDaiDTO dto=new BigDaiDTO();
        if(query.get("seach_type")!=null&&query.get("seach_type")!=""){
        dto.setSeach_type(query.get("seach_type").toString());}
        if(query.get("seach_date")!=null&&query.get("seach_date")!=""){
        dto.setSeach_date(new BigDecimal(query.get("seach_date").toString()));}
        if(query.get("seach_lilv1")!=null&&query.get("seach_lilv1")!=""){
        dto.setSeach_lilv1(new BigDecimal(query.get("seach_lilv1").toString()));}
        if(query.get("seach_lilv2")!=null&&query.get("seach_lilv2")!=""){
        dto.setSeach_lilv2(new BigDecimal(query.get("seach_lilv2").toString()));}
        if(query.get("seach_money1")!=null&&query.get("seach_money1")!=""){
        dto.setSeach_money1(new BigDecimal(query.get("seach_money1").toString()));}
        if(query.get("seach_money2")!=null&&query.get("seach_money2")!=""){
        dto.setSeach_money2(new BigDecimal(query.get("seach_money2").toString()));}
        Integer pageNo=(Integer) query.get("pageNo");
        Integer pageSize=(Integer)query.get("pageSize");
        return bigDaiService.allBigdai(dto,pageNo,pageSize);
    }
}
