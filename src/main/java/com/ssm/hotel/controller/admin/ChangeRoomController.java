package com.ssm.hotel.controller.admin;

import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.ChangeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 换房信息后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/changeroom")
@Controller
public class ChangeRoomController {

    @Autowired
    private ChangeRoomService changeRoomService;

    /**
     * 换房信息查看（点击一个入住信息查看换房列表）
     * ${pageContext.request.contextPath}/admin/changeroom/getRoomType"+floorId,
     */
    @RequestMapping(value="/findChange",method= RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findChange(Long checkId, Page page){
        Map<String ,Object> ret=new HashMap<>();
        Map<String,Object> queryMap = new HashMap<String, Object>();

        queryMap.put("checkId",checkId);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());

        ret.put("rows", changeRoomService.findList(queryMap));
        ret.put("total", changeRoomService.getTotal(checkId));
        return  ret;
    }


}
