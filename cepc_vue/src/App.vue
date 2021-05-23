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
      date: '2021-05-02',
      name: 'aaa',
      temperature:'36.2',
      address: '武汉市青山区友谊大道'
    };
    return {
      tableData: Array(20).fill(item)
    }
  }
};
</script>