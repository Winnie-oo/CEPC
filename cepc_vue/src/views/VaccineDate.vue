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
          prop="date"
          label="预约日期"
          width="120">
      </el-table-column>
      <el-table-column
          prop="number"
          label="剩余数量"
          width="300">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="editDate(scope.row)" type="text" size="small">修改</el-button>
          <el-button @click="deleteDate(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  methods: {
    deleteDate(row){
      const _this = this
      axios.delete('http://localhost:8021/users/deleteById/'+row.id).then(function (resp) {
        _this.$alert(row.name+'删除成功', '消息', {
          confirmButtonText: '确定',
          callback: action => {
            window.location.reload()
          }
        });
      })
    },
    editDate(row) {
      this.$router.push({
        path:'/VaccineDateUpdate',
        query:{
          id:row.id
        }
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8021/vaccines/findAll').then(function (resp){
      console.log(resp)
      _this.tableData = resp.data
    })
  },
  data() {
    return {
      totalElement:null,
      totalSize:7,
      tableData: [{
        id: 1,
        date: '5-29',
        number: 53
      }]
    }
  }
}
</script>