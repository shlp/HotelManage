<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="Author" content="实训1组">
  <meta name="Keywords" content="酒店信息管理系统">
  <meta name="Description" content="酒店信息管理系统">
  <link href="../resources/home/css/reservation.css" type="text/css" rel="Stylesheet"/>
  <link href="../resources/home/css/index.css" type="text/css" rel="Stylesheet"/>
    <link rel="stylesheet" href="../resources/home/css/jquery-ui.min.css">
    <script src="../resources/home/js/index.js"></script>
    <script src="../resources/home/js/jquery-1.11.3.js"></script>
    <script src="../resources/home/js/jquery-ui.min.js"></script>
  <title>实训一组|酒店管理系统首页</title>
</head>
<style type="text/css">
    .box{width: 1536px;height: 450px;margin: 0 auto;overflow: hidden;position: relative;}
    .box-1 ul li{height:450px;width:1536px;position: relative;overflow: hidden;}
    .box-1 ul li img{display:block;height:450px; width:1536px;}
    .box-2{position: absolute;right: 10px;bottom: 14px;}
    .box-2 ul li{float:left;width: 12px;height: 12px;overflow: hidden; margin: 0 5px; border-radius: 50%;
        background: rgba(0,0,0,0.5);text-indent: 100px;cursor: pointer;}
    .box-2 ul .on{background: rgba(255,255,255,0.6);}
    .box-3 span{position: absolute;color: white;background: rgba(125,125,120,.3);width: 50px;height: 80px;
        top:50%; font-family: "宋体";line-height: 80px;font-size:60px;margin-top: -40px;
        text-align: center;cursor: pointer;}
    .box-3 .prev{left: 10px;}
    .box-3 .next{right: 10px;}
    .box-3 span::selection{background: transparent;}
    .box-3 span:hover{background: rgba(125,125,120,.8);}
    #top1{
        width:1500px;
        position:absolute;
        margin:0 auto;
        top:5%;
        z-index:2;
    }
</style>
<body>

<!--主体内容-->
<section>
  <div id="subject">
      <div style="position:absolute;width: 1536px;height: 450px;background:#c6c6ca70;z-index:4;">
      <div id="top1" >
          <p style="font-size:12px;color: #fff;float:right;">
              <c:if test="${account == null }">
                  <a href="../home/login">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                  <a href="../home/reg">注册</a>&nbsp;
              </c:if>
              <c:if test="${account != null }">
                  <font color="red">欢迎您：${account.name }&nbsp;&nbsp;|&nbsp;&nbsp;</font>
                  <a href="account/index">用户中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                  <a href="../home/logout">注销登录</a>&nbsp;
              </c:if>
          </p>
      </div>
      <!--轮播图-->
      <%-- <img src="../resources/home/images/img3.jpg" alt="" height="450px" width="1200px">--%>

      <div class="box">
          <div class="box-1">
              <ul>
                  <li><img src="../resources/home/images/img3.jpg" height="450px" width="1536px" alt="这里是第一场图片"></li>
                  <li><img src="../resources/home/images/img9.jpg" height="450px" width="1536px" alt="这里是第二张图片"></li>
                  <li><img src="../resources/home/images/img4.jpg" height="450px" width="1536px" alt="这里是第三张图片"></li>
                  <li><img src="../resources/home/images/img5.jpg" height="450px" width="1536px" alt="这里是第四场图片"></li>
                  <li><img src="../resources/home/images/img6.jpg" height="450px" width="1536px" alt="这里是第五场图片"></li>
                  <li><img src="../resources/home/images/img7.jpg" height="450px" width="1536px" alt="这里是第六场图片"></li>
                  <li><img src="../resources/home/images/img1.jpg" height="450px" width="1536px" alt="这里是第七场图片"></li>
              </ul>
          </div>
          <div class="box-2"><ul></ul></div>
          <div class="box-3">
              <span class="prev"> < </span>
              <span class="next"> > </span>
          </div>
      </div>
<form id="search_form" action="${pageContext.request.contextPath}/home/search" method="post">
       <div class="choose_time">
           <label>入住时间&nbsp;&nbsp;</label><input type="text" name="arriveDate" id="arriveDate" class="datepicker"/>----
           <label>离店时间&nbsp;&nbsp;</label><input type="text" name="leaveDate" id="leaveDate" class="datepicker"/>
           <div class="chioce_type">
               关键字&nbsp;&nbsp;&nbsp;<input type="text" name="name" placeholder="关键字" value="${kw }" id="kw"/>
               <input type="submit" value="搜索" id="search-btn"/>
           </div>
       </div>
</form>
      <!--遮罩-->

      <ul class="shade_mag">
          <li><img src="../resources/home/images\s_02.png" alt=""></li>
          <li><img src="../resources/home/images\s_01.png" alt=""></li>
      </ul>
  </div>
   <%-- <img src="../resources/home/images\index_02.jpg" alt="" height="256px" width="1200px">--%>
  </div>
  <!---预订菜单--->
  <div id="due_menu">
    <!--关于-->
    
    <!--客房-->
        <div id="guest_rooms">
              <p class="booking_tab"><span></span>客房列表</p>
            <%--<div class="chioce">
                <input type="text" placeholder="关键字" value="${kw }" id="kw"/>
                <input type="button" value="搜索" id="search-btn"/>
            </div>--%>

            <div class="roomtype_list">
                  <form style="display:none;" action="index" method="get" id="search-form"><input type="hidden" name="name" id="search-name"></form>
                  <!--列表-->
                    <table id="pro_list" width="880"  >
                        <c:forEach items="${roomTypeList}" var="roomType">
                            <c:if test="#st.getIndex()%4==0">
                            <tr>
                            </c:if>
                            <td width="220" class="goods">
                                <div class="good">
                                    <div class="good-photo">
                                        <a href="#"><img src="${roomType.photo}" alt=""width="180" height="190"></a>
                                    </div>
                                    <div class="good-name"> <p>${roomType.name }</p></div>
                                    <div class="good-item">
                                        <div class="good-price">￥${roomType.price }</div>
                                        <div class="sub_txt">可住人数:${roomType.liveNum }&nbsp;&nbsp;&nbsp;床位数:${roomType.bedNum }<br>${roomType.remark }</div>
                                    </div>
                                    <div class="judge">
                                        <div class="judgeStatus">
                                            <c:if test="${roomType.status == 0 }">
                                                已满房
                                            </c:if>
                                            <c:if test="${roomType.status == 1 }">
                                                可预订
                                            </c:if>
                                        </div>
                                        <div class="judgeOrder">
                                            <c:if test="${roomType.status == 0 }">
                                            <input type="button" class="disable" value="满房" >
                                            </c:if>
                                            <c:if test="${roomType.status == 1 }">
                                            <input type="button" value="预订" id="want-buy" onclick="window.location.href='account/book_order?roomTypeId=${roomType.id}'" >
                                            </c:if>
                                     </div>
                                    </div>
                                </div>
                              </td>
                            <c:if test="#st.getIndex()%4==3">
                                </tr>
                            </c:if>
                            </c:forEach>
                    </table>
              </div>
            <div class="foot-page" >
                <p>第${page.page}/${page.totalPage}页 &nbsp;&nbsp;&nbsp;&nbsp;共${page.totalRows}条</p>
                <a href="${pageContext.request.contextPath}/home/roomType_list?page=1">首页</a>|
                <a href="${pageContext.request.contextPath}/home/roomType_list?page=${page.page-1}" onclick="return checkFirst()">上一页</a>|
                <a href="${pageContext.request.contextPath}/home/roomType_list?page=${page.page+1}" onclick="return checkNext()">下一页</a>|
                <a href="${pageContext.request.contextPath}/home/roomType_list?page=${page.totalPage}">尾页</a> <%--跳转到:
                            <input type="text" style="width:30px" id="turnPage" />页--%>
                <%--  <input type="button" onclick="startTurn()" value="跳转" />--%></div>
        </div>
  </div>
</section>
<div id="footer1" style="position: absolute;left:21%;color:#ccc;"><%@include file="../common/footer.jsp"%></div>

<script>
$(function(){
    $(".datepicker").datepicker({"dateFormat": "yy-mm-dd"});
    $("#search-btn").click(function (){
        var arriveDate = $("#arriveDate").val();
        var leaveDate = $("#leaveDate").val();
        if (arriveDate == '' || leaveDate == '') {
            alert('请选择时间!');
            return;
        }
        $("#search-name").val($("#kw").val());
        $("#search-form").submit();
    });
});

function checkFirst(){
    if(${page.page>1}){
        return true;
    }
    alert("已到页首,无法加载更多");
    return false;
}

function checkNext(){
    if(${page.page<page.totalPage}){
        return true;
    }
    alert("已到页尾，无法加载更多页");
    return false;

}

</script>
</body>