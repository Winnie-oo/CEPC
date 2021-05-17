<template>
  <div>
    <el-header class="welcome">首页统计</el-header>
    <el-row style="height: 70px" :gutter="12">
      <el-col :span="8">
        <el-card shadow="hover">
          总人数统计:
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          今日打卡:
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          疑似患者:
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {

    }
  },
  created(){
    /**
     * 获取当天健康表信息
     */



    /**
     *根据用户类型进行用户信息获取(该方法主要用来获取总检测人数==>学生人数)
     */

  },
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
</style>