<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <%@include file="../common/menus.jsp"%>
        </div>
        <div class="wu-toolbar-search">
            <label>房间类型名称:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>房型:</label>
            <select id="search-roomType" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.id }" price="${roomType.price }">${roomType.name }</option>
                </c:forEach>
            </select>
            <label>入住时间:</label><input type="text" id="search-arriveDate" name="arriveDate" class="wu-text easyui-datebox easyui-validatebox" style="width:100px"  />
            <label>离开时间:</label><input type="text" id="search-leaveDate" name="leaveDate" class="wu-text easyui-datebox easyui-validatebox" style="width:100px"  />
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<%@include file="../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">
	
	//搜索按钮监听
	$("#search-btn").click(function(){
		var option = {name:$("#search-name").val()};
		var roomType = $("#search-roomType").combobox('getValue');
		if(roomType != -1){
			option.roomTypeId = roomType;
		}
        option.arriveDate = $("#search-arriveDate").datebox('getValue');//option.leaveDate = $("#search-leaveDate").val();空值
        option.leaveDate = $("#search-leaveDate").datebox('getValue');
      //  alert(option.roomTypeId+"/t"+option.arriveDate);
		$('#data-datagrid').datagrid('reload',option);
	});
	
	
	/** 
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'listType',
		rownumbers:true,
		singleSelect:true,
		pageSize:20,           
		pagination:true,
		multiSort:true,
		fitColumns:true,
		idField:'id',
	    treeField:'name',
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
            { field:'photo',title:'房间类型图片',width:100,align:'center',formatter:function(value,row,index){
                    var img = '<img src="'+value+'" width="50px" />';
                    return img;
                }},
			{ field:'name',title:'名称',width:100,sortable:true},
			{ field:'price',title:'价格',width:100,sortable:true},
			{ field:'bedNum',title:'床位数',width:100,sortable:true},
			{ field:'roomNum',title:'房间数',width:100,sortable:true},
            { field:'avilableNum',title:'可用房间数',width:100,sortable:true},
			{ field:'status',title:'状态',width:100,formatter:function(value,row,index){
				switch(value){
					case 0:{
						return '房型已满';
					}
					case 1:{
						return '可预订可入住';
					}
                    case 2:{
                        return '维修中';
                    }
				}
				return value;
			}},
		]]
	});
</script>