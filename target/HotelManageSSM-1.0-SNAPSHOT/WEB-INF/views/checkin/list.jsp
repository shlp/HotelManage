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
            <label>入住姓名:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>身份证号:</label><input id="search-idCard" class="wu-text" style="width:100px">
            <label>手机号码:</label><input id="search-mobile" class="wu-text" style="width:100px">
            <label>房间:</label>
            <select id="search-room" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${roomList}" var="room">
                    <option value="${room.id}">${room.sn}</option>
                </c:forEach>
            </select>
            <label>房型:</label>
            <select id="search-roomType" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.id }" price="${roomType.price}">${roomType.name }</option>
                </c:forEach>
            </select>
            <label>状态:</label>
            <select id="search-status" class="easyui-combobox" panelHeight="auto" style="width:120px">
                <option value="-1">全部</option>
                <option value="0">入住中</option>
                <option value="1">已结算离店</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 订单选择弹框 -->
<div id="show-order-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:750px;height:500px; padding:10px;">
    <table id="order-datagrid" class="easyui-datagrid"></table>
</div>
<!-- 添加弹框 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td align="right">预定订单:</td>
                <td>
                    <input type="text" readonly="readonly" id="add-bookOrderId" name="bookOrderId" class="wu-text" />
                </td>
                <td><a href="#" class="select-order-btn easyui-linkbutton" iconCls="icon-add" plain="true">选择</a></td>
            </tr>
            <tr>
                <td align="right">房型:</td>
                <td>
                    <select id="add-roomTypeId" name="roomTypeId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择房型'">
                        <c:forEach items="${roomTypeList}" var="roomType">
                            <option value="${roomType.id }" price="${roomType.price}">${roomType.name }</option>
                        </c:forEach>
                    </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">房间:</td>
                <td>
                    <select id="add-roomId" name="roomId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择房间'">
                        <c:forEach items="${roomList}" var="room">
                            <option value="${room.id }">${room.sn }</option>
                        </c:forEach>
                    </select>
                </td>
                <td></td>
            </tr>

            <tr>
                <td align="right">入住价格:</td>
                <td><input type="text" id="add-checkinPrice" name="checkinPrice" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入入住价格'" /></td>
                <td></td>
            </tr>

            <tr>
                <td align="right">使用积分:</td>
                <td><select id="add-score" class="easyui-combobox" panelHeight="auto" style="width:120px">
                    <option value="-1">全部</option>
                    <option value="0">使用积分</option>
                    <option value="1">不使用积分</option>
                </select></td>
                <td></td>
            </tr>

            <tr>
                <td align="right">押金:</td>
                <td><input type="text" id="add-deposit" name="deposit" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入押金'" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">入住人姓名:</td>
                <td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入入住人姓名'" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">身份证号:</td>
                <td><input type="text" id="add-idCard" name="idCard" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入身份证号'"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">手机号码:</td>
                <td><input type="text" id="add-mobile" name="mobile" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入手机号'" /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">入住日期:</td>
                <td><input type="text" id="add-arriveDate" name="arriveDate" class="wu-text easyui-datebox easyui-validatebox"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">离店日期:</td>
                <td><input type="text" id="add-leaveDate" name="leaveDate" class="wu-text easyui-datebox easyui-validatebox"  /></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">状态:</td>
                <td>
                    <select id="add-status" name="status" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择状态'">
                        <option value="0">已入住</option>
                        <option value="1">已结算离店</option>
                    </select>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="right">备注:</td>
                <td><textarea id="add-remark" name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
                <td></td>
            </tr>
        </table>
    </form>
</div>
<!-- 办理续住窗口 -->
<div id="continueLive-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="continueLive-form" method="post">
        <input type="hidden" name="id" id="continueLive-id">
        <table>
            <tr>
                <td align="right">离店日期:</td>
                <td><input type="text" id="continueLive-leaveDate" name="leaveDate" class="wu-text easyui-datebox easyui-validatebox"  /></td>
            </tr>
            <tr>
                <td align="right">追加价格:</td>
                <td><input type="text" id="continueLive-checkinPrice" name="checkinPrice" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入姓名'" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- 办理退房窗口 -->
<div id="checkout-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="checkout-form" method="post">
        <input type="hidden" name="id" id="checkout-id">
        <table>
            <tr>
                <td align="right">赔损费用:</td>
                <td><input type="text" id="checkout-lossPrice" name="checkinPrice" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入姓名'" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- 办理换房窗口 -->
<div id="changeRoom-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="changeRoom-form" method="post">
        <input type="hidden" name="checkId" id="changeRoom-checkid">
        <table>
            <tr>
                <td align="right">房型:</td>
                <td>
                    <select id="changeRoom-roomTypeId" name="roomTypeId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择房型'">
                        <c:forEach items="${roomTypeList}" var="roomType">
                            <option value="${roomType.id } ">${roomType.name }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">房间:</td>
                <td>
                    <select id="changeRoom-roomId" name="roomId" class="easyui-combobox" panelHeight="auto" style="width:268px" data-options="required:true, missingMessage:'请选择客户'">
                        <c:forEach items="${roomList}" var="room">
                            <option value="${room.id }">${room.sn }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">追加费用:</td>
                <td><input type="text" id="changeRoom-differPrice" name="differPrice" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请输入姓名'" /></td>
            </tr>
            <tr>
                <td align="right">离店日期:</td>
                <td><input type="text" id="changeRoom-leaveDate" name="leaveDate" class="wu-text easyui-datebox easyui-validatebox"  /></td>
            </tr>
            <tr>
                <td align="right">换房情况:</td>
                <td><textarea id="changeRoom-changeRemark" name="changeRemark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<!-- 查看换房信息弹框 -->
<div id="show-findChange-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:750px;height:500px; padding:10px;">
    <table id="changeRoom-datagrid" class="easyui-datagrid"></table>
</div>
<!--查看房间信息审核情况-->
<div id="show-findRoom-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <table>
        <tr>
            <td align="right"><textarea id="findRoom-status" name="status" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
        </tr>
    </table>
</div>
<%@include file="../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">
    function updateByNo(){
        $.messager.confirm('信息提示','确定要退房吗？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelected');
                if(item == null || item.length == 0){
                    $.messager.alert('信息提示','请选择要维修的房间！','info');
                    return;
                }

                $.ajax({
                    url:'updateByNo',
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
    /**
     * 编辑退房所需信息记录
     */
    function checkout(){
        var validate = $("#checkout-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#checkout-form").serialize();
        $.ajax({
            url:'checkout',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','退房成功！','info');
                    $('#checkout-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }
    /**
     * 编辑续住所需信息记录
     */
    function continueLive(){
        var validate = $("#continueLive-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#continueLive-form").serialize();
        $.ajax({
            url:'continueLive',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $('#continueLive-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }
    /**
     *  添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','添加成功！','info');
                    $("#add-name").val('');
                    $("#add-remark").val('');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }
    /**
     *  添加换房记录
     */
    function addChange(){
        var validate = $("#changeRoom-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#changeRoom-form").serialize();
        $.ajax({
            url:'addChange',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','换房成功！','info');
                    $('#changeRoom-dialog').dialog('close');
                    $('#data-datagrid').datagrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }


    //打开退房窗口
    function openCheckOut(){
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要退房的房间！','info');
            return;
        }
        if(item.status != 0){
            $.messager.alert('信息提示','该状态不能结算！','info');
            return;
        }
        $('#checkout-dialog').dialog({
            closed: false,
            modal:true,
            title: "输入赔损费用",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: checkout
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#checkout-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                //$("#add-form input").val('')
                $("#checkout-id").val(item.id);
            }
        });
    }
    /**
     * Name 打开编辑续住信息窗口
     */
    function opencontinueLive(){
        //$('#add-form').form('clear');
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item.status != 0){
            $.messager.alert('信息提示','该状态不能续住！','info');
            return;
        }
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要编辑的数据！','info');
            return;
        }
        $('#continueLive-dialog').dialog({
            closed: false,
            modal:true,
            title: "编辑续住信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: continueLive
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#continueLive-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                //$("#add-form input").val('')
                $("#continueLive-id").val(item.id);
                $("#continueLive-leaveDate").val(item.leaveDate);
                $("#continueLive-checkinPrice").val(item.checkinPrice);
            }
        });
    }
    /**
     * Name 打开添加换房信息信息窗口
     */
    function openChange(){
        //$('#add-form').form('clear');
        var item = $('#data-datagrid').datagrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要换房的数据！','info');
            return;
        }
        $('#changeRoom-dialog').dialog({
            closed: false,
            modal:true,
            title: "编辑换房信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: addChange
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#changeRoom-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#changeRoom-form input").val('')
                $("#changeRoom-checkid").val(item.id);

            }
        });
    }
    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        //$('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "登记入住信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#add-form input").val('');

            }
        });
    }

    //搜索按钮监听
    $("#search-btn").click(function(){
        var option = {name:$("#search-name").val()};
        var status = $("#search-status").combobox('getValue');
        var account = $("#search-room").combobox('getValue');
        var roomType = $("#search-roomType").combobox('getValue');
        if(status != -1){
            option.status = status;
        }
        if(account != -1){
            option.accountId = account;
        }
        if(roomType != -1){
            option.roomTypeId = roomType;
        }
        option.idCard = $("#search-idCard").val();
        option.mobile = $("#search-mobile").val();
        $('#data-datagrid').datagrid('reload',option);
    });
    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo){
        //shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }

    $("#add-roomTypeId").combobox({
        onSelect:function(data){
            $("#add-roomId").combobox('clear');
            $("#add-roomId").combobox('reload','load_room_list?roomTypeId='+data.value);
        }
    });

    $("#changeRoom-roomTypeId").combobox({
        onSelect:function(data){
            // alert(data.value+data.price);
            $("#changeRoom-roomId").combobox('clear');
            $("#changeRoom-roomId").combobox('reload','load_room_list?roomTypeId='+data.value);
        }
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
            { field:'roomId',title:'房间',width:100,formatter:function(value,row,index){
                    var roomIdList = $("#search-room").combobox('getData');
                    //console.log(roomIdList);
                    for(var i=0;i<roomIdList.length;i++){
                        if(roomIdList[i].value == value){
                            var test = '<a class="roomStatus-find" onclick="findRoom('+value+')">'+roomIdList[i].text+'</a>';
                            return test;
                        }
                        //    return roomIdList[i].text;
                    }
                    return value;
                }},
            { field:'roomTypeId',title:'房型',width:150,formatter:function(value,row,index){
                    var roomTypeList = $("#search-roomType").combobox('getData');
                    for(var i=0;i<roomTypeList.length;i++){
                        if(roomTypeList[i].value == value){
                            return roomTypeList[i].text + '(￥:' + $("#search-roomType option[value='"+value+"']").attr('price') + ')';
                        }
                    }
                    return value;
                }},
            { field:'checkinPrice',title:'入住价格',width:60,sortable:true},
            { field:'name',title:'入住人',width:60,sortable:true},
            { field:'idCard',title:'身份证号',width:100,sortable:true},
            { field:'mobile',title:'手机号',width:100,sortable:true},
            { field:'arriveDate',title:'入住日期',width:100,sortable:true},
            { field:'leaveDate',title:'离店日期',width:100,sortable:true},
            { field:'deposit',title:'押金',width:100,sortable:true},
            { field:'status',title:'状态',width:100,formatter:function(value,row,index){
                    switch(value){
                        case 0:{
                            return '入住中';
                        }
                        case 1:{
                            return '已结算离店';
                        }
                    }
                    return value;
                }},
            { field:'createTime',title:'预定时间',width:150,formatter:function(value,row,index){
                    return format(value);
                }},
            { field:'remark',title:'备注',width:120,sortable:true},
            { field:'icon',title:'操作',width:100,formatter:function(value,row,index){
                    var test = '<a class="roomStatus-find" onclick="findChange('+row.id+')">查看换房信息</a>'
                    return test;
                }},
        ]]
    });
    //查看房间审核信息
    function findRoom(roomId){
        $('#show-findRoom-dialog').dialog({
            closed: false,
            modal:true,
            title: "房间审核信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#show-findRoom-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $.ajax({
                    url:'findRoom',
                    data:{id:roomId},
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        if(data.type == 'success'){
                            // alert(data.msg);
                            $('#findRoom-status').val(data.msg);
                        }else{
                            $.messager.alert('信息提示',data.msg,'info');
                        }
                    }
                });

            }
        });
    }
    //查看换房信息
    function findChange(checkId){
        $('#show-findChange-dialog').dialog({
            closed: false,
            modal:true,
            title: "换房信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    $('#show-findChange-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $('#changeRoom-datagrid').datagrid({
                    url:'../changeroom/findChange',
                    rownumbers:true,
                    singleSelect:true,
                    pageSize:20,
                    pagination:true,
                    multiSort:true,
                    fitColumns:true,
                    idField:'id',
                    treeField:'name',
                    checkOnSelect: false,
                    queryParams:{checkId:checkId},
                    fit:true,
                    columns:[[
                        { field:'chk',checkbox:true},
                        { field:'roomId',title:'房间',width:100,formatter:function(value,row,index){
                                var roomIdList = $("#search-room").combobox('getData');
                                //console.log(roomIdList);
                                for(var i=0;i<roomIdList.length;i++){
                                    if(roomIdList[i].value == value){
                                        var test = '<a class="roomStatus-find" onclick="findRoom('+value+')">'+roomIdList[i].text+'</a>';
                                        return test;
                                    }
                                    //    return roomIdList[i].text;
                                }
                                return value;
                            }},
                        { field:'roomTypeId',title:'房型',width:150,formatter:function(value,row,index){
                                var roomTypeList = $("#search-roomType").combobox('getData');
                                for(var i=0;i<roomTypeList.length;i++){
                                    if(roomTypeList[i].value == value){
                                        return roomTypeList[i].text + '(￥:' + $("#search-roomType option[value='"+value+"']").attr('price') + ')';
                                    }
                                }
                                return value;
                            }},
                        { field:'differPrice',title:'追加费用',width:100,sortable:true},
                        { field:'leaveDate',title:'离店日期',width:100,sortable:true},
                        { field:'changeRemark',title:'换房备注',width:120,sortable:true}
                    ]]
                });

            }
        });
    }
    //选择订单数据
    $(".select-order-btn").click(function(){
        $('#show-order-dialog').dialog({
            closed: false,
            modal:true,
            title: "选择预定订单信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    var selectedOrder = $('#order-datagrid').datagrid('getSelected');
                    if(selectedOrder == null || selectedOrder.length == 0){
                        $.messager.alert('信息提示','请选择订单信息！','info');
                        return;
                    }
                    $("#add-name").val(selectedOrder.name);
                    $("#add-arriveDate").datebox('setValue',selectedOrder.arriveDate);
                    $("#add-leaveDate").datebox('setValue',selectedOrder.leaveDate);
                    $("#add-idCard").val(selectedOrder.idCard);
                    $("#add-mobile").val(selectedOrder.mobile);
                    $("#add-checkinPrice").val(selectedOrder.bookPrice);
                    $("#add-bookOrderId").val(selectedOrder.id);
                    $("#add-status").combobox('setValue',selectedOrder.status);
                    $("#add-roomTypeId").combobox('setValue',selectedOrder.roomTypeId);
                    $("#add-remark").val(selectedOrder.remark);

                    $("#edit-name").val(selectedOrder.name);
                    $("#edit-arriveDate").datebox('setValue',selectedOrder.arriveDate);
                    $("#edit-leaveDate").datebox('setValue',selectedOrder.leaveDate);
                    $("#edit-idCard").val(selectedOrder.idCard);
                    $("#edit-mobile").val(selectedOrder.mobile);
                    $("#edit-bookOrderId").val(selectedOrder.id);
                    $("#edit-status").combobox('setValue',selectedOrder.status);
                    $("#edit-roomTypeId").combobox('setValue',selectedOrder.roomTypeId);
                    $("#edit-remark").val(selectedOrder.remark);

                    $('#show-order-dialog').dialog('close');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#show-order-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $('#order-datagrid').datagrid({
                    url:'../book_order/list',
                    rownumbers:true,
                    singleSelect:true,
                    pageSize:20,
                    pagination:true,
                    multiSort:true,
                    fitColumns:true,
                    idField:'id',
                    treeField:'name',
                    queryParams:{status:0},
                    fit:true,
                    columns:[[
                        { field:'chk',checkbox:true},
                        { field:'roomTypeId',title:'房型',width:150,formatter:function(value,row,index){
                                var roomTypeList = $("#search-roomType").combobox('getData');
                                for(var i=0;i<roomTypeList.length;i++){
                                    if(roomTypeList[i].value == value){
                                        return roomTypeList[i].text + '(￥:' + $("#search-roomType option[value='"+value+"']").attr('price') + ')';
                                    }
                                }
                                return value;
                            }},
                        { field:'name',title:'预定人',width:60,sortable:true},
                        { field:'arriveDate',title:'入住日期',width:100,sortable:true},
                        { field:'leaveDate',title:'离店日期',width:100,sortable:true},
                        { field:'createTime',title:'预定时间',width:150,formatter:function(value,row,index){
                                return format(value);
                            }},
                        { field:'status',title:'状态',width:100,formatter:function(value,row,index){
                                switch(value){
                                    case 0:{
                                        return '预定中';
                                    }
                                    case 1:{
                                        return '已入住';
                                    }
                                    case 2:{
                                        return '已结算离店';
                                    }
                                }
                                return value;
                            }},
                        { field:'remark',title:'备注',width:120,sortable:true}
                    ]]
                });

            }
        });
    });
</script>