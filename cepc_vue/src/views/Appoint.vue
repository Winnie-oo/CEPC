<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 80%">
      <el-table-column
          fixed
          prop="id"
          label="编号"
          width="100">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="120">
      </el-table-column>
      <el-table-column
          prop="date"
          label="日期"
          width="120">
      </el-table-column>
      <el-table-column
          prop="id_card"
          label="身份证号码"
          width="180">
      </el-table-column>
      <el-table-column
          prop="had_appoint"
          label="是否接种">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="180">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row)" type="text" size="small">修改</el-button>
          <el-button @click="edit(scope.row)" type="text" size="small">接种成功</el-button>
          <el-button @click="deleteAppoint(scope.row)" type="text" size="small">取消预约</el-button>
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
    deleteAppoint(row){
      const _this = this
      axios.delete('http://localhost:8021/appointRecord/deleteById/'+row.id).then(function (resp) {
        _this.$alert(row.name+'删除成功', '消息', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()
          }
        });
      })
    },
    edit(row) {
      this.$router.push({
        path:'/userUpdate',
        query:{
          id:row.id
        }
      })
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8021/appointRecord/findAll/'+currentPage+'/'+_this.totalSize).then(function (resp) {
        console.log(resp)
        _this.tableData = resp.data.content
        _this.totalElement = resp.data.totalElements
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8021/appointRecord/findAll/1/'+_this.totalSize).then(function (resp){
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
        date: '2021-06-01',
        id_card: '17437887655',
        had_appoint: '是'
      }]
    }
  }
}
</script>

<style scoped>

</style>