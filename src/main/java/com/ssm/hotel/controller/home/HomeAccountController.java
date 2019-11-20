package com.ssm.hotel.controller.home;
import com.ssm.hotel.entity.Account;
import com.ssm.hotel.entity.AccountScore;
import com.ssm.hotel.entity.BookOrder;
import com.ssm.hotel.entity.RoomType;
import com.ssm.hotel.service.AccountScoreService;
import com.ssm.hotel.service.AccountService;
import com.ssm.hotel.service.BookOrderService;
import com.ssm.hotel.service.RoomTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台用户控制器
 * @author Administrator
 *
 */
@RequestMapping("/home/account")
@Controller
public class HomeAccountController {

	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BookOrderService bookOrderService;

	@Autowired
	private AccountScoreService accountScoreService;

	/**
	 * 前台用户中心首页
	 * @param model
	 * @param
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model,HttpServletRequest request
	){
		Account account = (Account)request.getSession().getAttribute("account");
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("accountId", account.getId());
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 5);
		model.addObject("bookOrderList", bookOrderService.findList(queryMap));
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.setViewName("home/account/index");
		return model;
	}

	/**
	 * 预定房间页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.GET)
	public ModelAndView bookOrder(ModelAndView model,Long roomTypeId,HttpServletRequest request
	){
		model.addObject("roomType", roomTypeService.find(roomTypeId));
		Account account = (Account)request.getSession().getAttribute("account");
		int score=accountScoreService.findByAccountId(account.getId()).getScore();

		float typePrice=roomTypeService.find(roomTypeId).getPrice();
		float realPrice=typePrice-(score/200)*1;

		model.addObject("score",score);
		model.addObject("realPrice",realPrice);
		model.setViewName("home/account/book_order");
		return model;
	}


	/**
	 * 预定信息提交
	 * @param
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> bookOrderAct(BookOrder bookOrder, HttpServletRequest request){

		Map<String, String> ret = new HashMap<String, String>();
		if(bookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的预定订单信息!");
			return ret;
		}
		Account account = (Account)request.getSession().getAttribute("account");
		if(account == null){
			ret.put("type", "error");
			ret.put("msg", "客户不能为空!");
			return ret;
		}

		bookOrder.setAccountId(account.getId());
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
		System.out.println("line125:成功!");
		bookOrder.setCreateTime(new Date());
		bookOrder.setStatus(0);
		System.out.println("line141"+bookOrder.getIsScore());
		if("yes".equals(bookOrder.getIsScore())){
			System.out.println("line143"+bookOrder.getIsScore());
			float typePrice=roomTypeService.find(bookOrder.getRoomTypeId()).getPrice();
			int score=accountScoreService.findByAccountId(account.getId()).getScore();

			float realPrice=typePrice-(score/200)*1;
			if((score/200)>30){
				bookOrder.setBookPrice(typePrice-30);
			}else{
					bookOrder.setBookPrice(realPrice);}

			AccountScore accountScore=accountScoreService.findByAccountId(account.getId());
			int newScore=(int)typePrice*2;
			accountScore.setScore(newScore);
			accountScoreService.edit(accountScore);
		}
		System.out.println("line127:成功!"+bookOrder.getArriveDate()+bookOrder.getName());
		if(bookOrderService.add(bookOrder) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "预订成功!");
		return ret;
	}
	/**
	 * 取消预订操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView delete(ModelAndView model,Long id) throws ParseException {
		Map<String, String> ret = new HashMap<String, String>();
		BookOrder bookOrder=bookOrderService.find(id);
        System.out.println("line149:"+id+","+bookOrder);
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//将字符串形式的时间转化为Date类型的时间
		Date a=bookOrder.getCreateTime();
		Date b=new Date();
		//Date类的一个方法，如果a早于b返回true，否则返回false
		if(a.before(b)){bookOrder.setDefaultPrice((float)(bookOrder.getBookPrice()*0.1));}
		else{bookOrder.setDefaultPrice(0);}
		System.out.println("line154:"+a+","+bookOrder.getDefaultPrice());
		bookOrderService.delete(bookOrder) ;
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("accountId", bookOrder.getAccountId());

		model.addObject("bookOrderList",bookOrderService.findList(queryMap));
		model.addObject("roomTypeList",roomTypeService.findAll());
		model.setViewName("home/account/index");
		return model;
	}
	/**
	 * 修改个人信息提交
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/update_info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateInfoAct(Account account,HttpServletRequest request){
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
		Account loginedAccount = (Account)request.getSession().getAttribute("account");
		if(isExist(account.getName(),loginedAccount.getId())){
			retMap.put("type", "error");
			retMap.put("msg", "该用户名已经存在！");
			return retMap;
		}
		loginedAccount.setAddress(account.getAddress());
		loginedAccount.setIdCard(account.getIdCard());
		loginedAccount.setMobile(account.getMobile());
		loginedAccount.setName(account.getName());
		loginedAccount.setRealName(account.getRealName());
		if(accountService.edit(loginedAccount) <= 0){
			retMap.put("type", "error");
			retMap.put("msg", "修改失败，请联系管理员！");
			return retMap;
		}
		request.getSession().setAttribute("account", loginedAccount);
		retMap.put("type", "success");
		retMap.put("msg", "修改成功！");
		return retMap;
	}

	/**
	 * 修改密码提交
	 * @param
	 * @return
	 */
	@RequestMapping(value="/update_pwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updatePwdAct(String oldPassword,String newPassword,HttpServletRequest request){
		Map<String,String> retMap = new HashMap<String, String>();
		if(StringUtils.isEmpty(oldPassword)){
			retMap.put("type", "error");
			retMap.put("msg", "请填写原来的密码！");
			return retMap;
		}
		if(StringUtils.isEmpty(newPassword)){
			retMap.put("type", "error");
			retMap.put("msg", "请填写新密码！");
			return retMap;
		}
		Account loginedAccount = (Account)request.getSession().getAttribute("account");
		if(!oldPassword.equals(loginedAccount.getPassword())){
			retMap.put("type", "error");
			retMap.put("msg", "原密码错误！");
			return retMap;
		}
		loginedAccount.setPassword(newPassword);
		if(accountService.edit(loginedAccount) <= 0){
			retMap.put("type", "error");
			retMap.put("msg", "修改失败，请联系管理员！");
			return retMap;
		}
		retMap.put("type", "success");
		retMap.put("msg", "修改密码成功！");
		return retMap;
	}

	/**
	 * 判断用户是否存在
	 * @param name
	 * @param id
	 * @return
	 */
	private boolean isExist(String name,Long id){
		Account account = accountService.findByName(name);
		if(account == null)return false;
		if(account != null && account.getId().longValue() == id)return false;
		return true;
	}
}
