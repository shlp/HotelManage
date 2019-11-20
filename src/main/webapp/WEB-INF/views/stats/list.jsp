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
            <label>房型:</label>
            <select id="search-roomType" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.id }" price="${roomType.price}">${roomType.name }</option>
                </c:forEach>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<%@include file="../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">

    function outExport(){
        window.location.href='${pageContext.request.contextPath}/admin/stats/exportExcel';
    }

    //搜索按钮监听
    $("#search-btn").click(function(){
        //var option=null ;
        var roomTypeId = $("#search-roomType").combobox('getValue');
        // if(roomType != -1){
        //     option.roomTypeId = roomType;
        // }
        $('#data-datagrid').datagrid('reload',roomTypeId);
    });

    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'list',
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        checkOnSelect: false,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'chk',checkbox:true},
            { field:'roomTypeId',title:'房间类型',width:150,formatter:function(value,row,index){
                    var roomTypeList = $("#search-roomType").combobox('getData');
                    for(var i=0;i<roomTypeList.length;i++){
                        if(roomTypeList[i].value == value){
                            return roomTypeList[i].text + '(￥:' + $("#search-roomType option[value='"+value+"']").attr('price') + ')';
                        }
                    }
                    return value;
                }},
            { field:'roomTypeIdByChange',title:'换房类型',width:60,formatter:function(value,row,index){
                    var roomTypeList = $("#search-roomType").combobox('getData');
                    for(var i=0;i<roomTypeList.length;i++){
                        if(roomTypeList[i].value == value){
                            return roomTypeList[i].text + '(￥:' + $("#search-roomType option[value='"+value+"']").attr('price') + ')';
                        }
                    }
                    return value;
                }},
            { field:'differPrice',title:'追加费用',width:60,sortable:true},
            { field:'lossPrice',title:'赔损费用',width:100,sortable:true},
            { field:'checkinPrice',title:'入住价格',width:100,sortable:true},
            { field:'arriveDate',title:'入住日期',width:100,sortable:true},
            { field:'leaveDate',title:'离店日期',width:100,sortable:true},
        ]]
    });
</script>