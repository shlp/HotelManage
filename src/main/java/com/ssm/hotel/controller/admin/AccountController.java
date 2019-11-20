package com.ssm.hotel.controller.admin;

import com.ssm.hotel.entity.Account;
import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.AccountService;
import com.ssm.hotel.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/*
用户管理后台
 */
@RequestMapping("/admin/account")
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;


    /**
     * 客户管理列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        Map<String, Object> queryMap = new HashMap<String, Object>();
        model.addObject("roleList", roleService.findList(queryMap));
        model.setViewName("account/list");
        return model;
    }


    /**
     * 客户信息添加操作
     * @param account
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(Account account){
        Map<String, String> ret = new HashMap<String, String>();
        if(account == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的用户信息!");
            return ret;
        }
        if(StringUtils.isEmpty(account.getName())){
            ret.put("type", "error");
            ret.put("msg", "用户名称不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(account.getPassword())){
            ret.put("type", "error");
            ret.put("msg", "用户密码不能为空!");
            return ret;
        }
        if(account.getRoleId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属角色！");
            return ret;
        }
        if(isExist(account.getName(), 0l)){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在!");
            return ret;
        }
        if(accountService.add(account) <= 0){
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }

    /**
     * 客户信息编辑操作
     * @param account
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(Account account){
        Map<String, String> ret = new HashMap<String, String>();
        if(account == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的用户信息!");
            return ret;
        }
        if(StringUtils.isEmpty(account.getName())){
            ret.put("type", "error");
            ret.put("msg", "用户名称不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(account.getPassword())){
            ret.put("type", "error");
            ret.put("msg", "用户密码不能为空!");
            return ret;
        }
        if(account.getRoleId() == null){
            ret.put("type", "error");
            ret.put("msg", "请选择所属角色！");
            return ret;
        }
        if(isExist(account.getName(), account.getId())){
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在!");
            return ret;
        }
        if(accountService.edit(account) <= 0){
            ret.put("type", "error");
            ret.put("msg", "修改失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功!");
        return ret;
    }

    /**
     * 分页查询客户信息
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="roleId",required=false) Long roleId,
            @RequestParam(name="sex",required=false) Integer sex,
            @RequestParam(name="realName",defaultValue="") String realName,
            @RequestParam(name="idCard",defaultValue="") String idCard,
            @RequestParam(name="mobile",defaultValue="") String mobile,
            @RequestParam(name="status",required=false) Integer status,
            Page page
    ){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        queryMap.put("roleId", roleId);
        queryMap.put("status", status);
        queryMap.put("sex", sex);
        queryMap.put("realName", realName);
        queryMap.put("idCard", idCard);
        queryMap.put("mobile", mobile);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", accountService.findList(queryMap));
        ret.put("total", accountService.getTotal(queryMap));
        return ret;
    }

    /**
     * 客户信息删除操作
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delete(Long id){
        Map<String, String> ret = new HashMap<String, String>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的信息!");
            return ret;
        }
        try {
            if(accountService.delete(id) <= 0){
                ret.put("type", "error");
                ret.put("msg", "删除失败，请联系管理员!");
                return ret;
            }
        } catch (Exception e) {
            // TODO: handle exception
            ret.put("type", "error");
            ret.put("msg", "该客户下存在订单信息，请先删除该客户下的所有订单信息!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }
    /**
     * 上传图片
     * @param photo
     * @param request
     * @return
     */
    @RequestMapping(value="/upload_photo",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo, HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
        if(photo == null){
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if(photo.getSize() > 1024*1024*1024){
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
        if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }
        String savePath = request.getSession().getServletContext().getRealPath("/") + "/resources/upload/";
        File savePathFile = new File(savePath);
        if(!savePathFile.exists()){
            //若不存在改目录，则创建目录
            savePathFile.mkdir();
        }
        String filename = new Date().getTime()+"."+suffix;
        try {
            //将文件保存至指定目录
            photo.transferTo(new File(savePath+filename));
        }catch (Exception e) {
            // TODO Auto-generated catch block
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "用户删除成功！");
        ret.put("filepath",request.getSession().getServletContext().getContextPath() + "/resources/upload/" + filename );
        return ret;
    }
    /**
     * 判断用户名是否存在
     * @param name
     * @param id
     * @return
     */
    private boolean isExist(String name,Long id){
        Account findByName = accountService.findByName(name);
        if(findByName == null)return false;
        if(findByName.getId().longValue() == id.longValue())return false;
        return true;
    }
}
