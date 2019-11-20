<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <%@include file="../common/menus.jsp"%>
        </div>
    </div>
    <!-- End of toolbar -->
    <div class="easyui-accordion" style="width:960px;height:600px;">
        <div title="营业统计分析" iconCls="icon-chart-curve" style="overflow:auto;padding:10px;">
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="charts-divMonth" style="width: 800px;height:500px;"></div>
            <div id="charts-divDis" style="width: 800px;height:500px;display:none" ></div>
            <%--<div id="charts-div" style="width: 800px;height:500px;"></div>--%>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp"%>
<script type="text/javascript" src="../../resources/admin/echarts/js/echarts.common.min.js"></script>
<script type="text/javascript" src="../../resources/admin/export/datagrid-export.js"></script>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    $(document).ready(function(){
        statsByMonth();
       // statsByType()
    });
    function statsByMonth(){
        $("#charts-divDis").css('display','none');
        $("#charts-divMonth").css("display","block");
        getData('month');
    }
    function statsByType(){
        $("#charts-divMonth").css('display','none');
        $("#charts-divDis").css("display","block");
        getData('distribution');
    }
    function getData(type){
        $.ajax({
            url:'get_stats',
            type:'post',
            dataType:'json',
            data:{type:type},
            success:function(data){
                if(data.type == 'success'){
                    var option;

                    if(type ==='distribution'){
                        console.log("1"+type)
                        console.log("2"+data.content.keyList1)
                        var title = '收入来源';
                         option = {
                            backgroundColor: '#FFF',
                            title: {
                                text: title,
                                left: 'center',
                                top: 20,
                                textStyle: {
                                    color: '#ccc'
                                }
                            },

                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },

                            visualMap: {
                                show: false,
                                min: 80,
                                max: 600,
                                inRange: {
                                    colorLightness: [0, 1]
                                }
                            },
                            series : [
                                {
                                    name:'收入来源',
                                    type:'pie',
                                    radius : '55%',
                                    center: ['50%', '50%'],
                                    data:[
                                       // {value:data.content.valueList, name:data.content.keyList}
                                        {value:data.content.valueList1, name:'单人间'},
                                        {value:data.content.valueList2, name:'普通大床房'},
                                        {value:data.content.valueList3, name:'豪华大床房'},
                                        {value:data.content.valueList4, name:'商务大床房'},
                                        {value:data.content.valueList5, name:'普通标准间'},
                                    ].sort(function (a, b) { return a.value - b.value; }),
                                    roseType: 'radius',
                                    label: {
                                        normal: {
                                            textStyle: {
                                                color: 'rgba(255, 255, 255, 0.3)'
                                            }
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            lineStyle: {
                                                color: 'rgba(255, 255, 255, 0.3)'
                                            },
                                            smooth: 0.2,
                                            length: 10,
                                            length2: 20
                                        }
                                    },
                                    itemStyle: {
                                        normal: {
                                            color: '#c23531',
                                            shadowBlur: 200,
                                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                                        }
                                    },

                                    animationType: 'scale',
                                    animationEasing: 'elasticOut',
                                    animationDelay: function (idx) {
                                        return Math.random() * 200;
                                    }
                                }
                            ]
                        }; myChartDis.setOption(option);

                    }
                    /*if(type ==='month')*/ else{
                        var title = '营业统计分析(按月统计)';
                        option= {
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'cross',
                                    crossStyle: {
                                        color: '#999'
                                    }
                                }
                            },
                            toolbox: {
                                feature: {
                                    dataView: {show: true, readOnly: false},
                                    magicType: {show: true, type: ['line', 'bar']},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            legend: {
                                data: ['顾客赔损费用', '入住费用', '收入总额']
                            },
                            xAxis: [
                                {
                                    type: 'category',
                                    data: data.content.keyList,
                                    axisPointer: {
                                        type: 'shadow'
                                    }
                                }
                            ],
                            yAxis: [
                                {
                                    type: 'value',
                                    name: '单项费用(赔损/入住)',
                                    min: 0,
                                    max: 960,
                                    interval: 192,
                                    axisLabel: {
                                        formatter: '{value} 元'
                                    }
                                },
                                {
                                    type: 'value',
                                    name: '收入总额',
                                    min: 0,
                                    max: 2300,
                                    interval: 460,
                                    axisLabel: {
                                        formatter: '{value} 元'
                                    }
                                }
                            ],
                            series: [
                                {
                                    name: '顾客赔损费用',
                                    type: 'bar',
                                    data: data.content.lossPrice,
                                },
                                {
                                    name: '入住费用',
                                    type: 'bar',
                                    data: data.content.checkPrice,
                                },
                                {
                                    name: '收入总额',
                                    type: 'line',
                                    yAxisIndex: 1,
                                    data: data.content.valueList,
                                }
                            ]
                        };
                         myChartMonth.setOption(option);
                        console.log(type)
                    }
                   // myChartMonth.setOption(option);
                }else{alert(data.msg)}
            }
        });
    }


    // 基于准备好的dom，初始化echarts实例
    var myChartMonth = echarts.init(document.getElementById('charts-divMonth'));
    var myChartDis = echarts.init(document.getElementById('charts-divDis'));

    // 指定图表的配置项和数据

    // 使用刚指定的配置项和数据显示图表。





</script>