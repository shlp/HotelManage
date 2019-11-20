package com.ssm.hotel.controller.home;

import com.ssm.hotel.entity.Account;
import com.ssm.hotel.entity.AccountScore;
import com.ssm.hotel.entity.RoomType;
import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台首页控制器
 */
@RequestMapping("/home")
@Controller
public class HomeController {

    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private ChangeRoomService changeRoomService;
    @Autowired
    private  AccountScoreService accountScoreService;


/**
 * 前台首页
 * @param model
 * @param name
 * @return
 */
@RequestMapping(value="/index",method= RequestMethod.GET)
public ModelAndView list(ModelAndView model,
                         @RequestParam(name="name",defaultValue="") String name
){
    Page page=new Page();
    Map<String,Object> queryMap = new HashMap<String, Object>();
    queryMap.put("name", name);
    queryMap.put("offset", 0);
    queryMap.put("pageSize",4);
    model.addObject("roomTypeList", roomTypeService.findList(queryMap));
    model.setViewName("home/index/index");
    model.addObject("kw", name);
    page.setRows(4);
    page.setTotalRows(roomTypeService.getTotal(queryMap));
    model.addObject("page",page);
    System.out.println(roomTypeService.findList(queryMap)+"\t"+roomTypeService.getTotal(queryMap));
    return model;
}

    /**
     * 搜索查询
     * @param model
     * @param name
     * @return
     */
    @RequestMapping(value="/search",method= RequestMethod.POST)
    public ModelAndView listSearch(ModelAndView model,
                             @RequestParam(name="name",defaultValue="") String name,
                             @RequestParam(name="arriveDate",required=false) String arriveDate,
                             @RequestParam(name="leaveDate",required=false) String leaveDate
    ){
        Map<String,Object> queryMap = new HashMap<String, Object>();
        Map<String,Object> queryMapOne = new HashMap<String, Object>();
        Map<String,Object> queryMapTwo = new HashMap<String, Object>();
        queryMapTwo.put("name",name);

      //  model.addObject("page",page);

        RoomType roomType=roomTypeService.findList(queryMapTwo).get(0);
        Long  roomTypeId=roomType.getId();

        queryMap.put("roomTypeId", roomTypeId);
        queryMap.put("arriveDate", arriveDate);
        queryMap.put("leaveDate", leaveDate);
        queryMap.put("status",0);


        queryMapOne.put("name",name);
        queryMapOne.put("roomTypeId", roomTypeId);
         System.out.println("line94:"+roomTypeId);
        //查询可用房间数，先计算出新的可用数以及判断是否满房（如果可用房间数为0）更改到数据库中，之后再查询
        if(roomTypeId!=null){
            int count=bookOrderService.getTotalByType(queryMap);//预定数
            System.out.println("bookorderline98:"+count);
            int num=checkinService.getTotalByTypeNull(queryMap);//到店入住数（离店时间在内）
            int changeNum=changeRoomService.getTotalByType(queryMap);//换房数（离店时间在内）
            System.out.println("bookorderline97:"+num+","+changeNum+","+count+","+roomTypeId+","+arriveDate);
           //RoomType roomType1=roomTypeService.find(roomTypeId);
            roomType.setAvilableNum(roomType.getRoomNum()-count-num-changeNum);
            //System.out.println("bookorderline100:"+roomType.getAvilableNum()+roomType.getRoomNum());
            //System.out.println(roomType.getAvilableNum()==roomType.getRoomNum());
            if(roomType.getAvilableNum()==0){
                roomType.setStatus(0);
                System.out.println("OK");
            }

            roomTypeService.updateAvilableNum(roomType);
            //System.out.println("bookorderline103:"+roomType);
        }
        model.addObject("roomTypeList", roomTypeService.findList(queryMap));
        //System.out.print("BOOKORDER112:"+roomTypeService.getTotal(queryMapOne));
        model.setViewName("home/index/index");
        return model;
    }

    /**
     * 分页查询
     * @param page
     * @param name
     * @return
     */
    @RequestMapping(value="/roomType_list",method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRoomTypeList(Page page,
                                           @RequestParam(name="name",required=false,defaultValue="") String name,ModelAndView model
    ){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        page.setRows(4);
        queryMap.put("offset", page.getOffset());//传递的rows进行了计算	this.offset = (page - 1) * rows;
        queryMap.put("pageSize", page.getRows());//每页显示记录数
        queryMap.put("name", name);
        List<RoomType> findList = roomTypeService.findList(queryMap);
        model.addObject("roomTypeList", roomTypeService.findList(queryMap));
//        page.setTotalPage(roomTypeService.getTotal(queryMap));
        page.setTotalRows(roomTypeService.getTotal(queryMap));
        model.addObject("page",page);
        model.setViewName("home/index/index");
        System.out.println("line82:"+"每页记录数："+page.getRows()+"当前页数："+page.getPage()+findList);
        return model;
    }

    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value="/login",method=RequestMethod.GET)
    public ModelAndView login(ModelAndView model
    ){
        model.setViewName("home/index/login");
        return model;
    }

    /**
     * 注册页面
     * @param model
     * @return
     */
    @RequestMapping(value="/reg",method=RequestMethod.GET)
    public ModelAndView reg(ModelAndView model
    ){
        model.setViewName("home/index/reg");
        return model;
    }

    /**
     * 登录信息提交
     * @param account
     * @return
     */
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,String> loginAct(Account account, String vcode, HttpServletRequest request){
        Map<String,String> retMap = new HashMap<String, String>();
        if(account == null){
            retMap.put("type", "error");
            retMap.put("msg", "请填写正确的用户信息！");
            return retMap;
        }
        if(StringUtils.isEmpty(account.getName())){
            retMap.put("type", "error");
            retMap.put("msg", "用户名不能为空！");
            return retMap;
        }
        if(StringUtils.isEmpty(account.getPassword())){
            retMap.put("type", "error");
            retMap.put("msg", "密码不能为空！");
            return retMap;
        }
        if(StringUtils.isEmpty(vcode)){
            retMap.put("type", "error");
            retMap.put("msg", "验证码不能为空！");
            return retMap;
        }
        Object attribute = request.getSession().getAttribute("accountLoginCpacha");
        if(attribute == null){
            retMap.put("type", "error");
            retMap.put("msg", "验证码过期，请刷新！");
            return retMap;
        }
        if(!vcode.equalsIgnoreCase(attribute.toString())){
            retMap.put("type", "error");
            retMap.put("msg", "验证码错误！");
            return retMap;
        }
        Account findByName = accountService.findByName(account.getName());
        if(findByName == null){
            retMap.put("type", "error");
            retMap.put("msg", "用户名不存在！");
            return retMap;
        }
        if(!account.getPassword().equals(findByName.getPassword())){
            retMap.put("type", "error");
            retMap.put("msg", "密码错误！");
            return retMap;
        }
        if(findByName.getStatus() == -1){
            retMap.put("type", "error");
            retMap.put("msg", "该用户已被禁用，请联系管理员！");
            return retMap;
        }
        request.getSession().setAttribute("account", findByName);
        request.getSession().setAttribute("accountLoginCpacha", null);
        retMap.put("type", "success");
        retMap.put("msg", "登录成功！");
        return retMap;
    }

    /**
     * 注册信息提交
     * @param account
     * @return
     */
    @RequestMapping(value="/reg",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,String> regAct(Account account){
        Map<String,String> retMap = new HashMap<String, String>();
        if(account == null){
            retMap.put("type", "error");
            retMap.put("msg", "请填写正确的用户信息！");
            return retMap;
        }
        if(StringUtils.isEmpty(account.getName())){
            retMap.put("type", "error");
            retMap.put("msg", "用户名不能为空！");
            return retMap;
        }
        if(StringUtils.isEmpty(account.getPassword())){
            retMap.put("type", "error");
            retMap.put("msg", "密码不能为空！");
            return retMap;
        }
        if(StringUtils.isEmpty(account.getMobile())){
            retMap.put("type", "error");
            retMap.put("msg", "手机号不能为空！");
            return retMap;
        }
        if(isExist(account.getName())){
            retMap.put("type", "error");
            retMap.put("msg", "该用户名已经存在！");
            return retMap;
        }
        if(accountService.add(account) <= 0){

            retMap.put("type", "error");
            retMap.put("msg", "注册失败，请联系管理员！");
            return retMap;
        }
        AccountScore  accountScore=new AccountScore();
        Account account1=accountService.findByName(account.getName());
        accountScore.setAccountId(account1.getId());
        accountScore.setScore(0);
        accountScoreService.add(accountScore);
        retMap.put("type", "success");
        retMap.put("msg", "注册成功！");
        return retMap;
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute("account", null);
        return "redirect:login";
    }

    private boolean isExist(String name){
        Account account = accountService.findByName(name);
        if(account == null)return false;
        return true;
    }
}
