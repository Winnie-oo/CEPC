<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="id"
          label="编号"
          width="150">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="password"
          label="密码"
          width="120">
      </el-table-column>
      <el-table-column
          prop="dayMark"
          label="连续打卡"
          width="120">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
          width="300">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">修改</el-button>
          <el-button type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="totalSize"
        :total="totalElement"
        @current-change="page">
    </el-pagination>
  </div>
</template>

<script>
export default {
  methods: {
    handleClick(row) {
      console.log(row);
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8021/users/findAll/'+currentPage+'/'+_this.totalSize).then(function (resp) {
        console.log(resp)
        _this.tableData = resp.data.content
        _this.totalElement = resp.data.totalElements
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8021/users/findAll/1/'+_this.totalSize).then(function (resp){
      console.log(resp)
      _this.tableData = resp.data.content
      _this.totalElement = resp.data.totalElements
    })
  },
  data() {
    return {
      totalElement:null,
      totalSize:7,
      tableData: [{
        id: 1,
        name: '王庆',
        password: '123',
        day_mark: '7',
        address: '武汉'
      }]
    }
  }
}
</script>