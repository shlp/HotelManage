package com.ssm.hotel.controller.admin;

import com.ssm.hotel.entity.Details;
import com.ssm.hotel.entity.RoomType;
import com.ssm.hotel.page.Page;
import com.ssm.hotel.service.ChangeRoomService;
import com.ssm.hotel.service.DetailsService;
import com.ssm.hotel.service.RoomTypeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 营业统计控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/stats")
@Controller
public class StatsController {

	@Autowired
	private DetailsService detailsService;
	@Autowired
	private ChangeRoomService changeRoomService;
	@Autowired
	private RoomTypeService roomTypeService;

	/**
	 * 统计页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/stats",method=RequestMethod.GET)
	public ModelAndView stats(ModelAndView model){
		model.setViewName("stats/stats");
		return model;
	}

	/**
	 * 根据类型来获取统计数据
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/get_stats",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getStats(String type) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (StringUtils.isEmpty(type)) {
			ret.put("type", "error");
			ret.put("msg", "请选择统计类型!");
			return ret;
		}
		switch (type) {
			case "month": {
				System.out.println("line54:type:" + type);
				ret.put("type", "success");
				System.out.println("line58:detailsService.getStatsByMonth():");
				System.out.println("line59:detailsService.getStatsByMonth():" + detailsService.getStatsByMonth());
				ret.put("content", getStatsValue(detailsService.getStatsByMonth()));
				ret.put("stype", "month");
				return ret;
			}
			case "distribution": {
				System.out.println("line72:type:" + type);
				ret.put("type", "success");
				System.out.println("line75:detailsService.getStatsInType():" + detailsService.getStatsInCheck() + changeRoomService.getStatsInChange());
				ret.put("content", getStatsValueByType(detailsService.getStatsInCheck()));
				ret.put("stype", "distribution");
				return ret;
			}
			default:{
				ret.put("type", "error");
				ret.put("msg", "请选择正确的统计类型!");
				return ret;
			}
		}
	}

	/**
	 * 把数据的键和值分开保存
	 * @param statsValue
	 * @return
	 */
	private Map<String, Object> getStatsValue(List<Map> statsValue){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<String> keyList = new ArrayList<String>();
		List<Float> lossPrice = new ArrayList<Float>();
		List<Float> checkPrice = new ArrayList<Float>();
		List<Float> valueList = new ArrayList<Float>();
		for(Map m:statsValue){
			keyList.add(m.get("stats_date").toString());
			lossPrice.add(Float.valueOf(m.get("lossPriceSum").toString()));
			checkPrice.add(Float.valueOf(m.get("checkPriceSum").toString()));
			valueList.add(Float.valueOf(m.get("checkPriceSum").toString())+Float.valueOf(m.get("differPriceSum").toString())+Float.valueOf(m.get("lossPriceSum").toString()));
		}
		System.out.println("line86:"+keyList+","+valueList);
		ret.put("keyList", keyList);
		ret.put("valueList", valueList);
		ret.put("lossPrice", lossPrice);
		ret.put("checkPrice", checkPrice);
		return ret;
	}

	/**
	 * 结账明细列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.addObject("roomTypeList", roomTypeService.findAll());
		System.out.println("line134:"+roomTypeService.findAll());
		model.setViewName("stats/list");
		return model;
	}

	/**
	 * 分页查询信息
	 * @param
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> list(@RequestParam(name="roomTypeId",defaultValue="") Long roomTypeId, Page page){
					Map<String,Object> ret = new HashMap<String, Object>();
					Map<String,Object> queryMap = new HashMap<String, Object>();

					queryMap.put("roomTypeId", roomTypeId);
					queryMap.put("offset", page.getOffset());
					queryMap.put("pageSize", page.getRows());
					ret.put("rows", detailsService.findList(queryMap));
					ret.put("total", detailsService.getTotal(queryMap));
			return ret;
	}
	/**
	 * 导出excel表
	 */
	@RequestMapping(value="/exportExcel",method=RequestMethod.GET)
    public void excel(HttpServletResponse response )throws IOException {
		//1.创建工作簿
		HSSFWorkbook hwb =new HSSFWorkbook();
		//1.1创建合并单元格
		//CellRangeAddress cellRangeAddress =new CellRangeAddress(0,0,0,4);
		//2.创建工作表
		HSSFSheet sheet = hwb.createSheet("结账明细信息表");
		//2.1添加合并单元格
		//sheet.addMergedRegion(cellRangeAddress);
		//3.1创建第一行及单元格
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue("结账明细");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		sheet.setColumnWidth(0, 3000);
		HSSFCellStyle cellStyle = hwb.createCellStyle();  //创建设置EXCEL表格样式对象 cellStyle
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		//3.2创建第二行及单元格
		HSSFRow row2 = sheet.createRow(1);
		String[] row2Cell = {"编号","房间类型","换房类型","入住价格","追加费用","赔损费用","入住时间","离店时间"};
		for (int i =0 ; i < row2Cell.length ; i++ ){
			row2.createCell(i).setCellValue(row2Cell[i]);
		}
		List<Details>detailsList=detailsService.findAll();
		//3.3创建第三行及单元格
		if(detailsList!= null && detailsList.size()>0){
			for(int j=0 ; j<detailsList.size() ;j++){
				HSSFRow rowUser = sheet.createRow(j+2);
				rowUser.createCell(0).setCellValue(detailsList.get(j).getId());
				rowUser.createCell(1).setCellValue(getType(detailsList.get(j).getRoomTypeId()));
				rowUser.createCell(2).setCellValue(getType(detailsList.get(j).getRoomTypeIdByChange()));
				rowUser.createCell(3).setCellValue(detailsList.get(j).getCheckinPrice());
				rowUser.createCell(4).setCellValue(detailsList.get(j).getDifferPrice());
				rowUser.createCell(5).setCellValue(detailsList.get(j).getLossPrice());
				rowUser.createCell(6).setCellValue(detailsList.get(j).getArriveDate());
				rowUser.createCell(7).setCellValue(detailsList.get(j).getLeaveDate());
				//rowUser.createCell(4).setCellValue(userList.get(j).getId());
			}
		}
		//5.输出
		response.setHeader("Content-Disposition","attachment;filename="+new String("结账明细信息.xls".getBytes(),"ISO-8859-1"));
		response.setContentType("application/x-excel;charset=UTF-8");
		OutputStream outputStream = response.getOutputStream();
		hwb.write(outputStream);
		outputStream.close();

    }




	/**
	 *
	 * 键值分开收入来源
	 */
	private Map<String, Object> getStatsValueByType(List<Map> statsValueByCheck){
		Map<String, Object> ret = new HashMap<String, Object>();
		List<String> keyList1 = new ArrayList<String>();List<String> keyList2 = new ArrayList<String>();
		List<String> keyList3 = new ArrayList<String>();List<String> keyList4 = new ArrayList<String>();
		List<String> keyList5 = new ArrayList<String>();List<String> keyList6 = new ArrayList<String>();
        List<String> keyList7 = new ArrayList<String>();List<String> keyList8 = new ArrayList<String>();
        List<String> keyList9 = new ArrayList<String>();List<String> keyList10 = new ArrayList<String>();
		List<Integer> valueList1 = new ArrayList<Integer>();List<Integer> valueList2 = new ArrayList<Integer>();
        List<Integer> valueList4 = new ArrayList<Integer>();List<Integer> valueList3 = new ArrayList<Integer>();
        List<Integer> valueList5 = new ArrayList<Integer>();List<Integer> valueList7 = new ArrayList<Integer>();
        List<Integer> valueList6 = new ArrayList<Integer>();List<Integer> valueList8 = new ArrayList<Integer>();
        List<Integer> valueList10 = new ArrayList<Integer>();List<Integer> valueList9 = new ArrayList<Integer>();
		//List<Integer> valueList = new ArrayList<Integer>();

		for(Map check:statsValueByCheck){
			System.out.println("line117:"+Integer.valueOf(check.get("roomTypeIdByCheck").toString()));
			if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==1){
				  keyList1.add("单人间");
                  valueList1.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==2){
				  keyList2.add("普通大床房");
                valueList2.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==3){
				keyList3.add("豪华大床房");
                valueList3.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==4){
				keyList4.add("商务大床房");
                valueList4.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==5){
				keyList5.add("普通标准间");
                valueList5.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==6){
				keyList6.add("豪华标准房");
                valueList6.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==7){
				keyList7.add("商务标准间");
                valueList7.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==8){
				keyList8.add("普通套房");
                valueList8.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else if(Integer.valueOf(check.get("roomTypeIdByCheck").toString())==9){
				keyList9.add("豪华套房");
                valueList9.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}else {
				keyList10.add("总统套房");
                valueList10.add(Integer.valueOf(check.get("countBycheckin").toString()));
			}

		}
		System.out.println("line86:"+keyList1+","+valueList1);
		ret.put("keyList1", keyList1);
		ret.put("valueList1", valueList1);
        ret.put("keyList2", keyList2);
        ret.put("valueList2", valueList2);
        ret.put("keyList3", keyList3);
        ret.put("valueList3", valueList3);
        ret.put("keyList4", keyList4);
        ret.put("valueList4", valueList4);
        ret.put("keyList5", keyList5);
        ret.put("valueList5", valueList5);
        ret.put("keyList6", keyList6);
        ret.put("valueList6", valueList6);
		return ret;
	}
//			for(Map change:statsValueByChange){
//				System.out.println("line143:"+Integer.valueOf(change.get("roomTypeIdByChange").toString())+","+Integer.valueOf(check.get("roomTypeIdByCheck").toString()));
//				//int a=Integer.valueOf(check.get("roomTypeIdByCheck").toString());
//				//int b=Integer.valueOf(change.get("roomTypeIdByChange").toString());
//				System.out.println(!Integer.valueOf(check.get("roomTypeIdByCheck").toString()).equals(Integer.valueOf(change.get("roomTypeIdByChange").toString())));
//				if(!Integer.valueOf(check.get("roomTypeIdByCheck").toString()).equals(Integer.valueOf(change.get("roomTypeIdByChange").toString()))){
//					System.out.println("line147:"+Integer.valueOf(change.get("countBycheckin").toString()));
//					valueList.add(Integer.valueOf(check.get("countBycheckin").toString()));
//				}
//				if(Integer.valueOf(check.get("roomTypeIdByCheck").toString()).equals(Integer.valueOf(change.get("roomTypeIdByChange").toString()))){
//					valueList.add(Integer.valueOf(check.get("countBycheckin").toString())+Integer.valueOf(check.get("countByChange").toString()));
//				}
//			}
    private String  getType(Long id) {
	    String type="";
        if (id == 1) {
          type="单人间";
        } else if (id== 2) {
            type="普通大床房";
        } else if (id == 3) {
            type="豪华大床房";
        } else if (id== 4) {
            type="商务大床房";
        } else if (id== 5) {
            type="普通标准间";
        } else if (id == 6) {
            type="豪华标准房";
        } else if (id == 7) {
            type="商务标准间";
        } else if (id == 8) {
            type="普通套房";
        } else if (id == 9) {
            type="豪华套房";
        } else {
            type="总统套房";
        }
        return type;
    }


}
