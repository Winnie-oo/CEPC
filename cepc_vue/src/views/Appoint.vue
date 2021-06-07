<template>
  <div>
    <el-col style="width: 80%">
      <el-table
          :data="tableData"
          border
          style="width: 100%">
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
            <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle size="small" v-if="scope.row.had_appoint==='否'"></el-button>
            <el-button @click="changeData(scope.row)" type="success" icon="el-icon-check" circle size="small" v-if="scope.row.had_appoint==='否'" ></el-button>
            <el-button @click="deleteAppoint(scope.row)" type="danger" icon="el-icon-delete" circle size="small" v-if="scope.row.had_appoint==='否'"></el-button>
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
    </el-col>
    <el-col style="width: 10%">
      <el-button @click="add()" icon="el-icon-plus" circle ></el-button>
    </el-col>
  </div>
</template>

<script>
export default {
  methods: {
    deleteAppoint(row){
      const _this = this
      _this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8021/appointRecord/deleteById/'+row.id).then(function (resp) {
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
      const _this = this
      _this.$router.push({
        path:'/addAppoint'
      })
    },
    edit(row) {
      this.$router.push({
        path:'/appointUpdate',
        query:{
          id:row.id
        }
      })
    },
    changeData(row) {
      const _this = this
      _this.$confirm('确定接种成功?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8021/appointRecord/change/'+row.id).then(function (resp) {
          _this.$message({
            type: 'success',
            message: '接种成功!'
          });
        })
        window.location.reload()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
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
        had_appoint: '否'
      }]
    }
  }
}
</script>

<style scoped>

</style>