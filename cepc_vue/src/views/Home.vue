<template>
  <div>
    <el-header class="welcome">首页统计</el-header>
    <el-row style="height: 70px" :gutter="12">
      <el-col :span="8">
        <el-card shadow="hover">
          社区总人数:20238
        </el-card>
      </el-col>
<!--      <el-col :span="8">-->
<!--        <el-card shadow="hover">-->
<!--          注册总人数:12123-->
<!--        </el-card>-->
<!--      </el-col>-->
      <el-col :span="8">
        <el-card shadow="hover">
          今日打卡:12322
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          疑似患者:35
        </el-card>
      </el-col>
    </el-row>

    <div class="home" style="width:60%;height:100%;float:left;">
      <!-- 定义图表外层容器 -->
        <div id="myLineChart" ref="LineEcharts"></div>
    </div>

    <div class="home" style="width:40%;height:100%;float:left;">
      <!-- 定义图表外层容器 -->
      <div id="myPieChart" ref="PieEcharts"></div>
    </div>

  </div>
</template>

<script>

export default {
  data() {
    return {}
  },
  created() {
    /**
     * 获取当天健康表信息
     */
  },
  mounted() {
    this.dataChart()
  },
  methods: {
    // 绘制图表
    dataChart() {
      //初始化图表，this.$refs.homeEcharts获取到图表容器
      var myChart = this.$echarts.init(this.$refs.LineEcharts);
      var myPie = this.$echarts.init(this.$refs.PieEcharts);
      // 初始化配置（官网实例详情左侧代码，直接复制过来按项目需求修改即可）
      var optionLine = {
        title: {
          text: '社区一周近况'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['每日填报人数', '社区感染人数', '社区疫苗数量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '每日填报人数',
            type: 'line',
            stack: '总量',
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: '社区感染人数',
            type: 'line',
            stack: '总量',
            data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: '社区疫苗数量',
            type: 'line',
            stack: '总量',
            data: [150, 232, 201, 154, 190, 330, 410]
          }
        ]
      }

      var optionPie = {
        title: {
          text: '社区感染患者占比',
          subtext: '疫情防控',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: '50%',
            data: [
              {value: 8, name: '确诊用户'},
              {value: 35, name: '疑似确诊'},
              {value: 1048, name: '健康用户'},
              // {value: 484, name: '联盟广告'},
              // {value: 300, name: '视频广告'}
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      // 把参数配置放到容器里
      myChart.setOption(optionLine)
      myPie.setOption(optionPie)
    },
    // setEchartsOptions() {   //生成ECharts
    //   var myLineChart = this.$echarts.init(document.getElementById("myLineChart"));
    //   myLineChart.setOption(this.options);
    //   myLineChart.resize(); //重绘,动态获取options时不会出现渲染异常
    // },

  }

}
</script>

<style >
.welcome{
  background-color: honeydew;
  color: #333;
  text-align: center;
  font-size: 30px;
  line-height: 60px;
}
#myLineChart {
  width: 600px;
  height: 300px;
}
#myPieChart {
  width: 500px;
  height: 400px;
}
</style>