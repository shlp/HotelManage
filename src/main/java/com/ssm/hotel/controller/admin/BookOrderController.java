package com.ssm.hotel.controller.admin;

import com.ssm.hotel.entity.BookOrder;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 预定订单管理后台控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/book_order")
@Controller
public class BookOrderController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private BookOrderService bookOrderService;
	@Autowired
	private CheckinService checkinService;
	@Autowired
	private ChangeRoomService changeRoomService;


	/**
	 * 预定订单管理列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.addObject("accountList", accountService.findAll());
		model.setViewName("book_order/list");
		return model;
	}

	/**
	 * 查看房间可预订数信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listType",method=RequestMethod.GET)
	public ModelAndView listType(ModelAndView model){
		model.addObject("roomTypeList", roomTypeService.findAll());
		//System.out.println("bookorderline65"+roomTypeService.findAll());
		model.setViewName("book_order/listType");
		return model;
	}

	/**
	 *分页查看房型可预定数
	 * @return
	 */
	@RequestMapping(value="/listType",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> listType(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="roomTypeId",required=false) Long roomTypeId,
			@RequestParam(name="arriveDate",required=false) String arriveDate,
			@RequestParam(name="leaveDate",required=false) String leaveDate,
			Page page
	){
		Map<String,Object> ret = new HashMap<String, Object>();
		Map<String,Object> queryMap = new HashMap<String, Object>();
		Map<String,Object> queryMapOne= new HashMap<String, Object>();
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("arriveDate", arriveDate);
		queryMap.put("leaveDate", leaveDate);
		queryMap.put("status",0);

		queryMapOne.put("name",name);
		queryMapOne.put("roomTypeId", roomTypeId);
		queryMapOne.put("offset", page.getOffset());
		queryMapOne.put("pageSize", page.getRows());
		//查询可用房间数，先计算出新的可用数以及判断是否满房（如果可用房间数为0）更改到数据库中，之后再查询
		if(roomTypeId!=null){
			int count=bookOrderService.getTotalByType(queryMap);//预定数
			int num=checkinService.getTotalByTypeNull(queryMap);//到店入住数（离店时间在内）
			int changeNum=changeRoomService.getTotalByType(queryMap);//换房数（离店时间在内）
			//System.out.println("bookorderline97:"+num+","+changeNum+","+count+","+roomTypeId+","+arriveDate);
			RoomType roomType=roomTypeService.find(roomTypeId);
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
			ret.put("rows", roomTypeService.findList(queryMapOne));
			ret.put("total", roomTypeService.getTotal(queryMapOne));
			//System.out.print("BOOKORDER112:"+roomTypeService.getTotal(queryMapOne));
		return ret;
	}

	/**
	 * 预定订单信息添加操作
	 * @param bookOrder
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(BookOrder bookOrder){
		Map<String, String> ret = new HashMap<String, String>();
		if(bookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预定订单信息!");
			return ret;
		}
		if(bookOrder.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "房型不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getName())){
			ret.put("type", "error");
			ret.put("msg", "预定订单联系人名称不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "预定订单联系人手机号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "联系人身份证号不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getArriveDate())){
			ret.put("type", "error");
			ret.put("msg", "到达时间不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getLeaveDate())){
			ret.put("type", "error");
			ret.put("msg", "离店时间不能为空!");
			return ret;
		}
		bookOrder.setCreateTime(new Date());
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("arriveDate", bookOrder.getArriveDate());
		queryMap.put("leaveDate",bookOrder.getLeaveDate());
		queryMap.put("roomTypeId",bookOrder.getRoomTypeId());
		//int count=bookOrderService.getTotalByType(queryMap);
//		roomTypeService.find(bookOrder.getRoomTypeId()).getRoomNum()
		//如果该类型房间预定数大于等于该房型房间数则预定失败
		if(bookOrderService.getTotalByType(queryMap)>=roomTypeService.find(bookOrder.getRoomTypeId()).getRoomNum()){
			ret.put("type", "error");
			ret.put("msg", "没有空房，不可预订!");
			return ret;
		}
		if(bookOrderService.add(bookOrder)<0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}

    /**
     * 预定订单信息编辑操作
     * @param
     * @return
     */
    @RequestMapping(value="/edit",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, String> edit(BookOrder bookOrder){
        Map<String, String> ret = new HashMap<String, String>();
        if(bookOrder == null){
            ret.put("type", "error");
            ret.put("msg", "请填写正确的预定订单信息!");
            return ret;
        }
        if(bookOrder.getRoomTypeId() == null){
            ret.put("type", "error");
            ret.put("msg", "房型不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(bookOrder.getName())){
            ret.put("type", "error");
            ret.put("msg", "预定订单联系人名称不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(bookOrder.getMobile())){
            ret.put("type", "error");
            ret.put("msg", "预定订单联系人手机号不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(bookOrder.getIdCard())){
            ret.put("type", "error");
            ret.put("msg", "联系人身份证号不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(bookOrder.getArriveDate())){
            ret.put("type", "error");
            ret.put("msg", "到达时间不能为空!");
            return ret;
        }
        if(StringUtils.isEmpty(bookOrder.getLeaveDate())){
            ret.put("type", "error");
            ret.put("msg", "离店时间不能为空!");
            return ret;
        }
        BookOrder existBookOrder = bookOrderService.find(bookOrder.getId());
        if(existBookOrder == null){
            ret.put("type", "error");
            ret.put("msg", "请选择正确的数据进行编辑!");
            return ret;
        }
        if(bookOrderService.edit(bookOrder) <= 0){
            ret.put("type", "error");
            ret.put("msg", "编辑失败，请联系管理员!");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功!");
        return ret;
    }

	/**
	 * 分页查询预定订单信息
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="accountId",defaultValue="") Long accountId,
			@RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId,
			@RequestParam(name="idCard",defaultValue="") String idCard,
			@RequestParam(name="mobile",defaultValue="") String mobile,
			@RequestParam(name="status",required=false) Integer status,
			Page page
	){
		Map<String,Object> ret = new HashMap<String, Object>();
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("status", status);
		queryMap.put("accountId", accountId);
		queryMap.put("roomTypeId", roomTypeId);
		queryMap.put("idCard", idCard);
		queryMap.put("mobile", mobile);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", bookOrderService.findList(queryMap));
		ret.put("total", bookOrderService.getTotal(queryMap));
		return ret;
	}


}
