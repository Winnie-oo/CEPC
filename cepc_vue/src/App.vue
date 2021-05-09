<template>
  <div class="app">
    <router-view></router-view>
  </div>
</template>


<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
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