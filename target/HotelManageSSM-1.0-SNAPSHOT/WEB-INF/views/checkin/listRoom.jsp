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
            <label>楼层:</label>
            <select id="search-floor"  class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${floorList}" var="floor">
                    <option value="${floor.id}" >${floor.name}</option>
                </c:forEach>
            </select>
            <label>房型:</label>
            <select id="search-roomType"  class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="">--请选择房型--</option>
                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.id}" >${roomType.name}</option>
                </c:forEach>
            </select>
            <%--<label>入住时间:</label><input type="text" id="search-arriveDate" name="arriveDate" class="wu-text easyui-datebox easyui-validatebox" style="width:100px"  />--%>
            <label>入住时间:</label><input type="text" id="search-leaveDate" name="leaveDate" class="wu-text easyui-datebox easyui-validatebox" style="width:100px"  />
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<%@include file="../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">
    $("#search-floor").combobox({
        onSelect:function(data){
            $("#search-roomType").combobox('clear');
            $("#search-roomType").combobox('reload','getRoomType?floorId='+data.value);
        }
    })
    /* function getRoomType(){
         var floorId = $("#search-floor option:selected").val();
         $("#search-roomType").html('<option value="">--请选择房型--</option>');
         alert(floorId+"${pageContext.request.contextPath}/admin/checkin/getRoomType");
        $.ajax({
            url : "${pageContext.request.contextPath}/admin/checkin/getRoomType",
            type : "post",
            data: {"floorId":floorId},
            dataType : "json",
            success : function(data){
                if(data!=null){
                    $(data).each(function(index){
                        $("#search-roomType").append(
                            '<option value="'+data[index].id+'">'+data[index].name+'</option>'
                        );
                    });
                }
            }
        });
    }*/
    function swapquick(){
        $.messager.confirm('信息提示','确定要加急打扫房间吗？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelected');
                if(item == null || item.length == 0){
                    $.messager.alert('信息提示','请选择房间！','info');
                    return;
                }

                $.ajax({
                    url:'swapquick',
                    dataType:'json',
                    type:'post',
                    data:{id:item.id},
                    success:function(data){
                        if(data.type == 'success'){
                            $.messager.alert('信息提示','修改成功！','info');
                            $('#data-datagrid').datagrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }
    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {leaveDate:$("#search-leaveDate").datebox('getValue')};
        var floor= $("#search-floor").combobox('getValue');
        var roomType = $("#search-roomType").combobox('getValue');
        if(roomType != -1){
            option.roomTypeId = roomType;
        }
        if(floor != -1){
            option.floorId = floor;
        }
        // option.leaveDate = $("#search-leaveDate").datebox('getValue');   //option.leaveDate = $("#search-leaveDate").val();空值
        //  alert(option.roomTypeId+"/t"+option.arriveDate);
        $('#data-datagrid').datagrid('reload',option);
    });


    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'listRoom',
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
            { field:'photo',title:'房间图片',width:100,align:'center',formatter:function(value,row,index){
                    var img = '<img src="'+value+'" width="50px" style="margin-top:5px;" />';
                    return img;
                }},
            { field:'sn',title:'房间编号',width:100,sortable:true},
            { field:'isWindow',title:'是否有窗',width:100,sortable:true},
            { field:'status',title:'状态',width:100,formatter:function(value,row,index){
                    switch(value){
                        case 0:{
                            return '可入住';
                        }
                        case 1:{
                            return '已入住';
                        }
                        case 2:{
                            return '打扫中';
                        }
                        case 3:{
                            return '检查无误';
                        }
                        case 4:{
                            return '检查有误';
                        }
                        case 5:{
                            return '维修中';
                        }
                        case 6:{
                            return '加急打扫';
                        }
                    }
                    return value;
                }},
        ]]
    });
</script>