package com.ssm.hotel.controller.admin;

import com.ssm.hotel.entity.Floor;
import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.FloorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 楼层管理后台控制器
 * @author
 *
 */
@RequestMapping("/admin/floor")
@Controller
public class FloorController {

	@Autowired
	private FloorService floorService;

	/**
	 * 楼层管理列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("floor/list");
		return model;
	}

	/**
	 * 楼层信息添加操作
	 * @param floor
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Floor floor){
		Map<String, String> ret = new HashMap<String, String>();
		if(floor == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的楼层信息!");
			return ret;
		}
		if(StringUtils.isEmpty(floor.getName())){
			ret.put("type", "error");
			ret.put("msg", "楼层名称不能为空!");
			return ret;
		}
		if(floorService.add(floor) <= 0){
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功!");
		return ret;
	}

	/**
	 * 楼层信息编辑（维修）操作
	 * @param floor
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Floor floor){
		Map<String, String> ret = new HashMap<String, String>();
		if(floor == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的楼层信息!");
			return ret;
		}
		if(StringUtils.isEmpty(floor.getName())){
			ret.put("type", "error");
			ret.put("msg", "楼层名称不能为空!");
			return ret;
		}
		if(floorService.edit(floor) <= 0){
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功!");
		return ret;
	}

	/**
	 * 分页查询楼层信息
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(
			@RequestParam(name="name",defaultValue="") String name,
			@RequestParam(name="status",required=false) Integer status,
			Page page
	){
		Map<String,Object> ret = new HashMap<String, Object>();
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("status", status);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", floorService.findList(queryMap));
		ret.put("total", floorService.getTotal(queryMap));
		return ret;
	}

	/**
	 * 楼层信息删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/updateFloor",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "请选择要维修的楼层!");
			return ret;
		}
		try {
			if(floorService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "维修楼层更改失败，请联系管理员!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该楼层下存在预订房间信息，请先等待该楼层下用户离开!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		return ret;
	}
}
