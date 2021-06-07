<template>
  <div>
    <el-col style="width: 50%">
      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="id"
            label="编号"
            width="60">
        </el-table-column>
        <el-table-column
            prop="date"
            label="预约日期"
            width="120">
        </el-table-column>
        <el-table-column
            prop="number"
            label="剩余数量">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="120">
          <template slot-scope="scope">
            <el-button @click="editDate(scope.row)" type="primary" icon="el-icon-edit" circle size="small"></el-button>
            <el-button @click="deleteDate(scope.row)" type="danger" icon="el-icon-delete" circle size="small"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <el-col style="width: 50%">
      <el-button @click="add()" icon="el-icon-plus" circle></el-button>
      <!--      <el-button type="info" icon="el-icon-message" circle></el-button>-->
      <!--      <el-button type="warning" icon="el-icon-star-off" circle></el-button>-->
      <!--      <el-button icon="el-icon-search" circle></el-button>-->
    </el-col>
  </div>
</template>

<script>
export default {
  methods: {
    deleteDate(row){
      const _this = this
      _this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8021/vaccines/deleteById/'+row.id).then(function (resp) {
          _this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
        window.location.reload()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    add(){
      this.$router.push('/addDate')
    },
    editDate(row) {
      this.$router.push({
        path:'/DateUpdate',
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