<template>
  <div class="app">
    <el-header class="headTitle">社区疫情防控后台管理</el-header>
    <router-view></router-view>
  </div>
</template>


<style>

.headTitle{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  font-size: 30px;
  line-height: 60px;
}

</style>

<script>


export default {
  data() {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      temperature:'36.2',
      address: '上海市普陀区金沙江路 1518弄'
    };
    return {
      tableData: Array(20).fill(item)
    }
  }
};
</script>