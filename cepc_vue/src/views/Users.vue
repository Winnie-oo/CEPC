<template>
  <div>
    <el-col style="width: 90%">
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
            prop="password"
            label="密码"
            width="120">
        </el-table-column>
        <el-table-column
            prop="tel"
            label="电话"
            width="120">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="60">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="120">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle size="small"></el-button>
            <el-button @click="deleteUser(scope.row)" type="danger" icon="el-icon-delete" circle size="small"></el-button>
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
    deleteUser(row){
      const _this = this
      _this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8021/users/deleteById/'+row.id).then(function (resp) {
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
        path:'/addUser'
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
        gender: '女',
        tel: '17437887655',
        address: '武汉'
      }]
    }
  }
}
</script>