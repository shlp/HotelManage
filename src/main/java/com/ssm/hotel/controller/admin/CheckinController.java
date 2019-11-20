package com.ssm.hotel.controller.admin;


import com.ssm.hotel.entity.*;
import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 入住管理后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/checkin")
@Controller
public class CheckinController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private ChangeRoomService changeRoomService;
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private  AccountScoreService accountScoreService;

    /**
     * 入住管理列表页面
     * @param model
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.addObject("roomTypeList", roomTypeService.findAll());
        model.addObject("roomList", roomService.findAll());
        model.setViewName("checkin/list");
        return model;
    }
    /**
     * 查看房间信息
     */
    @RequestMapping(value="/listRoom",method=RequestMethod.GET)
    public ModelAndView listType(ModelAndView model){
        model.addObject("floorList", floorService.findAll());
        model.addObject("roomTypeList", roomTypeService.findAll());
        model.setViewName("checkin/listRoom");
        return model;
    }
    /**
     *分页查看房间信息数
     * @return
     */
    @RequestMapping(value="/listRoom",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listType(
            @RequestParam(name="roomTypeId",required=false) Long roomTypeId,
            @RequestParam(name="floorId",required=false) Long floorId,
            @RequestParam(name="leaveDate",required=false) String leaveDate,
            Page page
    ){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
      //  Map<String,Object> queryMapOne= new HashMap<String, Object>();
        queryMap.put("floorId", floorId);
        queryMap.put("roomTypeId", roomTypeId);

        if(StringUtils.isEmpty(leaveDate)) {
            System.out.print("line79"+floorId+roomTypeId);
            List<Room> roomList=roomService.findList(queryMap);
            queryMap.put("offset", page.getOffset());
            queryMap.put("pageSize",page.getRows());
            ret.put("rows", roomList);
            ret.put("total", roomService.findList(queryMap));
            System.out.print("line79"+roomList);
        }else{
            queryMap.put("offset", page.getOffset());
            queryMap.put("pageSize",page.getRows());
            queryMap.put("leaveDate", leaveDate);
            //安排房间前，查询房间，如果提前可能房间还未打扫，查找今天(客人入住时间、之前的客人离店信息)
            //(为了需要打扫的或者告知将要走的然后通知时间，就可以预定了)
            //入住信息里得到今天即将离店的房间类型，通过房间类型查找房间id,显示出来
            List<Checkin> checkinList =checkinService.findList(queryMap);
            String roomIds = "";
            for(Checkin checkin:checkinList){
              //  typeIds += checkin.getRoomTypeId() + ",";
                  roomIds+=checkin.getRoomId()+",";
            }
            if(!StringUtils.isEmpty(roomIds)){
               // typeIds = typeIds.substring(0,typeIds.length()-1);
                roomIds = roomIds.substring(0,roomIds.length()-1);
            }
          //  List<Room> roomList = roomService.findListByIds(typeIds);
            List<Room> roomList = roomService.findListByRoomIds(roomIds);
            System.out.print("line102"+roomList+roomIds);
            ret.put("rows", roomList);
            ret.put("total", roomService.findTotalListByRoomIds(roomIds));
        }
        return ret;
    }


    /**
     * 入住信息添加操作(登记入住)
     * @param checkin
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> add(Checkin checkin, @RequestParam(name="bookOrderId",required=false) Long bookOrderId){
        Map<String, String> ret = new HashMap<String, String>();
        if(checkin == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的入住信息!");
            return ret;
        }
        if(checkin.getRoomId() == null){
            ret.put("type", "error");
            ret.put("msg", "房间不能为空!");
            return ret;
        }
        if(checkin.getRoomTypeId() == null){
            ret.put("type", "error");
            ret.put("msg", "房型不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(checkin.getName())){
            ret.put("type", "error");
            ret.put("msg", "入住联系人名称不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(checkin.getMobile())){
            ret.put("type", "error");
            ret.put("msg", "入住联系人手机号不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(checkin.getIdCard())){
            ret.put("type", "error");
            ret.put("msg", "联系人身份证号不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(checkin.getArriveDate())){
            ret.put("type", "error");
            ret.put("msg", "到达时间不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(checkin.getLeaveDate())){
            ret.put("type", "error");
            ret.put("msg", "离店时间不能为空!");
            return ret;
        }
        checkin.setCreateTime(new Date());
        if(checkin.getIsScore()==0){
            float typePrice=roomTypeService.find(checkin.getRoomTypeId()).getPrice();
            Long accountId=bookOrderService.find(checkin.getBookOrderId()).getAccountId();
            int score=accountScoreService.findByAccountId(accountId).getScore();
            float realPrice=typePrice-(score/200)*1;

            if((score/200)>30){
                checkin.setCheckinPrice(typePrice-30);
            }else{
                checkin.setCheckinPrice(realPrice);}

            AccountScore accountScore=accountScoreService.findByAccountId(accountId);
            int newScore=(int)typePrice*2;
            accountScore.setScore(newScore);
            accountScoreService.edit(accountScore);
            checkin.setCheckinPrice(realPrice);
        }
        if(checkinService.add(checkin) <= 0){
            ret.put("type", "error");
            ret.put("msg", "添加失败，请联系管理员!");
            return ret;
        }
        if(bookOrderId != null){
            //从预定来的入住单(入住既可以是直接入住也可以是已经预定的人来入住)
            BookOrder bookOrder = bookOrderService.find(bookOrderId);
//            /**
//             * 登记入住的时候换房（对于有预订的订单）,将预订单的房型和离店时间都变更，为了方便预订的查询是否空房
//             */
            if(bookOrder.getRoomTypeId().longValue()!=checkin.getRoomTypeId()){
                bookOrder.setRoomTypeId(checkin .getRoomTypeId());
                bookOrder.setLeaveDate(checkin.getLeaveDate());
            }
            bookOrder.setStatus(1);
            bookOrderService.edit(bookOrder);
        }
        Room room = roomService.find(checkin.getRoomId());
        if(room != null){
            //要把房间状态设置为已入住
            room.setStatus(1);
            roomService.edit(room);
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }
    /**
     *
     * 续住
     */
    @RequestMapping(value="/continueLive",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> continueLive(Checkin checkin){
        Map<String, String> ret = new HashMap<String, String>();
        if(checkin == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的房间信息!");
            return ret;
        }
        //续住更改续住时间，并追加价格（在原来的基础上把新的价格写上去）
        Checkin newcheck =checkinService.find(checkin.getId());
        newcheck.setCheckinPrice(newcheck.getCheckinPrice()+checkin.getCheckinPrice());
       // System.out.print("line196:"+newcheck.getCheckinPrice());
        newcheck.setLeaveDate(checkin.getLeaveDate());
        if(checkinService.continueLive(newcheck) <= 0){
                ret.put("type", "error");
                ret.put("msg", "修改失败，请联系管理员!");
            return ret;
        }
        //通过入住信息，得到预订预订订单信息，修改预订的离开时间,方便预订的时候查询数据
        if(newcheck.getBookOrderId()!=null) {
            BookOrder oldbook = bookOrderService.find(newcheck.getBookOrderId());
            oldbook.setLeaveDate(checkin.getLeaveDate());
            bookOrderService.edit(oldbook);
         //   System.out.print("line207:"+oldbook.getId());
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功!");
        return ret;
    }

    /**
     * 退房时，能够查看单个房间的信息（主要是看状态：3.检查有误，4：检查无误）
     * ${pageContext.request.contextPath}/admin/checkin/findRoom"+floorId,
     */
    @RequestMapping(value="/findRoom",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String>findRoom(Long id){
        Map<String ,String> ret=new HashMap<>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "房间编号不能为空!");
            return ret;
        }
        String msg="";

       if(roomService.find(id).getStatus()==3){
            msg="检查无误";
       }
       else if(roomService.find(id).getStatus()==4){
           msg=roomService.find(id).getRemark();
       }else{
           msg="未审核，不能结算!";
       }
        ret.put("type", "success");
        ret.put("msg", msg);
        System.out.println("line240:"+roomService.find(id).getStatus()+"msg:"+msg);
        return ret;
    }

    /**
    * 换房信息操作
    */
    @RequestMapping(value="/addChange",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String>addChange(ChangeRoom changeRoom){
        Map<String, String> ret = new HashMap<String, String>();
        if(changeRoom == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的房间信息!");
            return ret;
        }
        if(changeRoom.getRoomTypeId() == null){
            ret.put("type", "error");
            ret.put("msg", "房型不能为空!");
            return ret;
        }
        if(changeRoom.getRoomId() == null){
            ret.put("type", "error");
            ret.put("msg", "房间不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(changeRoom.getLeaveDate())){
            ret.put("type", "error");
            ret.put("msg", "离店时间不能为空!");
            return ret;
        }
        if(changeRoomService.addChange(changeRoom) <= 0){
            ret.put("type", "error");
            ret.put("msg", "换房失败，请联系管理员!");
            return ret;
        }
        System.out.println("okline308");
        Room room = roomService.find( checkinService.find(changeRoom.getCheckId()).getRoomId());
        if(room != null){
            room.setStatus(2);
            roomService.edit(room);
        }
        ret.put("type", "success");
        ret.put("msg", "换房成功!");
        return ret;
    }

    /**
     * 分页查询入住信息
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> list(
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="roomId",defaultValue="") Long roomId,
            @RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId,
            @RequestParam(name="idCard",defaultValue="") String idCard,
            @RequestParam(name="mobile",defaultValue="") String mobile,
            @RequestParam(name="status",required=false) Integer status,
            Page page){
        Map<String,Object> ret = new HashMap<String, Object>();
        Map<String,Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", name);
        queryMap.put("status", status);
        queryMap.put("roomId", roomId);
        queryMap.put("roomTypeId", roomTypeId);
        queryMap.put("idCard", idCard);
        queryMap.put("mobile", mobile);
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", checkinService.findList(queryMap));
        ret.put("total", checkinService.getTotal(queryMap));
        return ret;
    }
    /**
     *
     * 直接退房
     * @param id
     * @return
     */
    @RequestMapping(value="/updateByNo",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateByNo(Long id){
        Map<String, String> ret = new HashMap<String, String>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "请选择要退房的信息!");
            return ret;
        }
        Checkin newcheckin = checkinService.find(id);
        Details details=new Details();

        if(checkinService.updateByNo(id) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改失败，请联系管理员!");
            return ret;
        }
        System.out.println("line352:"+newcheckin.getId()+","+changeRoomService.findListByCheck(id));
        details.setCheckId(id);
        details.setRoomTypeId(newcheckin.getRoomTypeId());
        details.setCheckinPrice(newcheckin.getCheckinPrice());
        details.setArriveDate(newcheckin.getArriveDate());
        details.setDifferPrice(0);
        details.setLossPrice(0);
        if(newcheckin.getChangeRoom()!=null){
            ChangeRoom change=changeRoomService.findListByCheck(id).get(0);
            details.setDifferPrice(change.getDifferPrice());
            details.setLeaveDate(change.getLeaveDate());
            details.setRoomTypeIdByChange(change.getRoomTypeId());
            System.out.println("line363:"+details.getDifferPrice()+","+details.getArriveDate());
            detailsService.add(details);
        }else{

            details.setLeaveDate(newcheckin.getLeaveDate());
            detailsService.addByNoChange(details);
        }
        newcheckin.setStatus(1);
        if(checkinService.updateByNo(id) <= 0){
            ret.put("type", "error");
            ret.put("msg", "退房失败，请联系管理员!");
            return ret;
        }
        //首先操作房间状态
        Room room = roomService.find(id);
        if(room != null){
            room.setStatus(2);
            roomService.edit(room);
        }
        //判断是否来自预定，操作预订状态
        if(newcheckin.getBookOrderId() != null){
            BookOrder bookOrder = bookOrderService.find(id);
            bookOrder.setStatus(2);
            bookOrderService.edit(bookOrder);
        }
        ret.put("type", "success");
        ret.put("msg", "退房成功!");
        return ret;
    }
    /**
     * 退房操作
     * @param checkin
     * @return
     */
    @RequestMapping(value="/checkout",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkout(Checkin checkin){
        Map<String, String> ret = new HashMap<String, String>();
        if(checkin == null){
            ret.put("type", "error");
            ret.put("msg", "请选择数据!");
            return ret;
        }
       Checkin newcheckin = checkinService.find(checkin.getId());
        Details details=new Details();
        if(checkin == null){
            ret.put("type", "error");
            ret.put("msg", "请选择数据!");
            return ret;
        }
        int st=0;
        /**
         *
         * 退房时：
         * 1.存在换房记录，离开时间：换房的离开时间，追加费用为换房的费用，添加换房之后的类型
         * 2.存在换房记录有损失的，设置赔损费
         * 3.不存在记录，离开时间为入住的离开时间，追加费用0，赔损费用更新（0或者有）
         */
        System.out.println("line352:"+newcheckin.getId()+","+changeRoomService.findListByCheck(checkin.getId()));
        details.setCheckId(checkin.getId());
        details.setRoomTypeId(newcheckin.getRoomTypeId());
        details.setCheckinPrice(newcheckin.getCheckinPrice());
        details.setArriveDate(newcheckin.getArriveDate());
        details.setDifferPrice(0);
        details.setLossPrice(0);
        if(newcheckin.getChangeRoom()!=null){
            ChangeRoom change=changeRoomService.findListByCheck(checkin.getId()).get(0);
            st=roomService.find(change.getRoomId()).getStatus();
            details.setDifferPrice(change.getDifferPrice());
            details.setLeaveDate(change.getLeaveDate());
            details.setRoomTypeIdByChange(change.getRoomTypeId());
            System.out.println("line363:"+details.getDifferPrice()+","+details.getArriveDate());
            //房间检查有误，接收页面传来的价格
            if(st==4){
                details.setLossPrice(checkin.getCheckinPrice());
            }
            detailsService.add(details);
        }else{
            st=roomService.find(newcheckin.getRoomId()).getStatus();
            if(st==4){
                details.setLossPrice(newcheckin.getCheckinPrice());
            }
            details.setLeaveDate(newcheckin.getLeaveDate());
            detailsService.addByNoChange(details);
        }
        System.out.println("line469:"+newcheckin);
        newcheckin.setStatus(1);
        if(checkinService.edit(newcheckin) <= 0){
            ret.put("type", "error");
            ret.put("msg", "退房失败，请联系管理员!");
            return ret;
        }
        System.out.println("line469:"+roomService.find(newcheckin.getRoomId()));
        //首先操作房间状态
        Room room = roomService.find(newcheckin.getRoomId());
        if(room != null){
            room.setStatus(2);
            roomService.edit(room);
        }
        //判断是否来自预定，操作预订状态
        if(checkin.getBookOrderId() != null){
            BookOrder bookOrder = bookOrderService.find(newcheckin.getBookOrderId());
            bookOrder.setStatus(2);
            bookOrderService.edit(bookOrder);
        }
        ret.put("type", "success");
        ret.put("msg", "退房成功!");
        return ret;
    }

    /**
     * 根据房间类型获取房间
     * @param roomTypeId
     * @return
     */
    @RequestMapping(value="/load_room_list",method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> load_room_list(@RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId){
        List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("roomTypeId", roomTypeId);
        List<Room> roomList = roomService.findList(queryMap);
       // System.out.print("line500:"+roomList);
        for(Room room:roomList){
            Map<String, Object> option = new HashMap<String, Object>();
            option.put("value", room.getId());
            option.put("text", room.getSn());
            retList.add(option);
        }
        return retList;
    }
    /**
     *根据楼层二级联动查询房型信息
     */
    @RequestMapping(value="/getRoomType",method=RequestMethod.POST)
    @ResponseBody
    public Object getRoomType( @RequestParam(name="floorId",defaultValue="") Long floorId) {
        List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("floorId", floorId);

        List<Room> roomList=roomService.findList(queryMap);
        //System.out.print("line516checkin:"+roomList);
        String typeList = "";
        for(Room room:roomList){
            typeList += room.getRoomTypeId() + ",";
        }
        if(!StringUtils.isEmpty(typeList)){
            typeList = typeList.substring(0,typeList.length()-1);
        }
        List<RoomType> roomTypeList = roomTypeService.findListByIds(typeList);
       // System.out.print("line524checkin:"+"房型列表:"+roomTypeList);
        for(RoomType roomType:roomTypeList){
            Map<String, Object> option = new HashMap<String, Object>();
            option.put("value", roomType.getId());
            option.put("text", roomType.getName());
            retList.add(option);
        }
        return retList;
    }

    /**
     *
     * 加急打扫房间 //如果提前可能房间还未打扫，查找今天(客人入住时间、之前的客人离店信息)需要打扫的，加急打扫，正在入住的不能打扫
     */
    @RequestMapping(value="/swapquick",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> swapquick(Long id){
        Map<String, String> ret = new HashMap<String, String>();
        if(id == null){
            ret.put("type", "error");
            ret.put("msg", "请选择要加急打扫的房间的信息!");
            return ret;
        }
        //System.out.print("line550:"+id);
        if(roomService.swapquick(id) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改失败，请联系管理员!");
            return ret;
        }
            ret.put("type", "success");
            ret.put("msg", "修改成功!");
        return ret;
    }


}
