<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <title>酒店管理系统预定房间</title>
   <meta name="Author" content="实训1组">
  <meta name="Keywords" content="酒店信息管理系统">
  <meta name="Description" content="酒店信息管理系统">
    <link rel="stylesheet" href="../../resources/home/css/index.css">
    <link rel="stylesheet" href="../../resources/home/css/order.css">
    <link rel="stylesheet" href="../../resources/home/css/jquery-ui.min.css">
    <script src="../../resources/home/js/jquery-1.11.3.js"></script>
    <script src="../../resources/home/js/jquery-ui.min.js"></script>
    <%--<link rel="stylesheet" href="../../resources/home/My97DatePicker/skin/whyGreen/WdatePicker.css">--%>
   <%--<script src="<%=basePath%>/resources/home/My97DatePicker/WdatePicker.js"></script>--%>
</head>

<body>
<!--- 页头--->
<div class="c_top"><img src="../../resources/admin/login/images/aa.jpg" style="width: 1183px;height:260px;" alt=""> </div>
<!----主体-->
<div id="section">
    <!--客房信息-->
    <div class="hotel_inf_w">

        <div class="hotel_roominfobox">
            <a href="#"><img src="${roomType.photo }" alt=""/></a>
            <div class="info">
            <h2>${roomType.name }</h2>
            <!--豪华双人房&#45;&#45;&#45;&#45;预定-->
            </div>
            <ul class="hotel_detail">
            <li><span>房价:</span>${roomType.price }</li>
            <li><span>床位数:</span>${roomType.bedNum }</li>
            <li><span>可住:</span>${roomType.liveNum }人</li>
            <li><span>其他:</span>${roomType.remark }</li>
            </ul>
        </div>
        <div class="jump">
            <a href="../index">更多房型</a>
        </div>
    </div>
    <!--预定信息-->
    <div class="book_info">
        <form id="order_info">
            <ul>
                <input type="hidden" name="rid" value=""/>
                <li>
                    <h3>预定信息</h3>
                    <div class="info_group">
                        <label>入住时间</label><input type="text" name="arriveDate" id="arriveDate" class="datepicker" <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endTime\',{M:-1})}',maxDate:'#F{$dp.$D(\'endTime\') || \'%y-%M-%d {%H+1}:%m:%s\'}'})"--%>/>
						<label>离店时间</label><input type="text" name="leaveDate" id="leaveDate" class="datepicker"  <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'#F{$dp.$D(\'startTime\',{d+7}) || \'%y-%M-%d {%H+1}:%m:%s\'}'})"--%>/>
                    </div>
                    <div class="info_group">
                        <label>房费总计</label><span class="total">￥${roomType.price }</span> <span class="realtotal">￥${realPrice}</span>
                        <span id="total"> <p><input type="checkbox"  class="account_score"value="积分" />使用积分，当前拥有积分数：${score}</p>   </span>
                        <input type="hidden" name="price" value="${roomType.price }"/><input type="hidden" id="isScore" name="isScore" />
                    </div>
                </li>
                <li>
                    <h3>入住信息</h3>
                    <div class="info_group">
                        <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label><input type="text" name="name" id="name" value="${account.name}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</label><input type="text" maxlength="11" name="mobile" id="mobile" value="${account.mobile}"/><span class="msg"></span>
                    </div>
					<div class="info_group">
                        <label>身份证号</label><input type="text" name="idCard" id="idCard" value="${account.idCard}"/><span class="msg"></span>
                    </div>
                    <div class="info_group">
                        <label >留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言</label>
                         <textarea id="remark" name="remark" style="width:200px;"></textarea>
                    </div>

                </li>
                <li>
                    <div class="msg">
                        预定须知:请携带好本人的身份证，办理入住手续，本店办理入住需要在前台缴费押金 ￥500
                        入住日期之前18点取消订单，不收取费用，18点之后收取房费的10%，如果到期未入住，按照入住计算

                    </div>
                    <div id="btn_booking">确认预定</div>

                </li>
            </ul>
        </form>
    </div>
    <div class="malog">
        <div class="message">
            <p class="msgs"></p>
            <p>您可以在 <a href="index#order">我的订单</a>查看详情</p>
            <p>系统会在<span class="num">5</span>秒后跳转会 <a href="../index">菜单列表</a></p>
        </div>
    </div>
</div>
<!----页底--->
<div id="c_footer"></div>

</body>
<script>
    $(function() {
    $(".datepicker").datepicker({"dateFormat":"yy-mm-dd"});

        if($(".account_score").attr("checked") !==true){
            $(".realtotal").css("visibility","hidden");
        }
    $(".account_score").click(function(){
            $(".realtotal").css("visibility","visible")
        $(".total").css("visibility","hidden");
    })
    $("#btn_booking").click(function(){
    	var arriveDate = $("#arriveDate").val();
    	var leaveDate = $("#leaveDate").val();
    	if(arriveDate == '' || leaveDate == ''){
    		alert('请选择时间!');
    		return;
    	}
    	var name = $("#name").val();
    	if(name == ''){
    		$("#name").next("span.msg").text('请填写入住人!');
    		return;
    	}
    	$("#name").next("span.msg").text('');
    	var mobile = $("#mobile").val();
    	if(mobile == ''){
    		$("#mobile").next("span.msg").text('请填写手机号!');
    		return;
    	}
    	$("#mobile").next("span.msg").text('');
    	var idCard = $("#idCard").val();
    	if(idCard == ''){
    		$("#idCard").next("span.msg").text('请填写身份证号!');
    		return;
    	}
    	$("#idCard").next("span.msg").text('');
    	var remark = $("#remark").val();
        var isScore;
        if($(".account_score").attr("checked") ==true){
           // $(".realtotal").css("visibility","hidden");
             isScore="no";
        }else{ isScore="yes";}
    	$.ajax({
    		url:'book_order',
    		type:'post',
    		dataType:'json',
    		data:{roomTypeId:'${roomType.id }',isScore:isScore,name:name,mobile:mobile,idCard:idCard,remark:remark,arriveDate:arriveDate,leaveDate:leaveDate,bookPrice:'${roomType.price}'},
    		success:function(data){
    			if(data.type == 'success'){
    				$(".malog").show();
    				setTimeout(function(){
    					window.location.href = 'index';
    				},1000)
    			}else{
    				alert(data.msg);
    			}
    		}
    	});
    })
  });
  </script>
</html>