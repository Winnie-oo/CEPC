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
            width="60">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="temperature"
            label="体温"
            width="80">
        </el-table-column>
        <el-table-column
            prop="patient"
            label="何类人员"
            width="100">
        </el-table-column>
        <el-table-column
            prop="date"
            label="日期"
            width="120">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="130">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle size="small"></el-button>
            <el-button @click="deleteRecord(scope.row)" type="danger" icon="el-icon-delete" circle size="small"></el-button>
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
    deleteRecord(row){
      const _this = this
      _this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete('http://localhost:8021/records/deleteById/'+row.id).then(function (resp) {
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
        path:'/addRecord'
      })
    },
    edit(row) {
      this.$router.push({
        path:'/recordUpdate',
        query:{
          id:row.id
        }
      })
    },
    page(currentPage){
      const _this = this
      axios.get('http://localhost:8021/records/findAll/'+currentPage+'/'+_this.totalSize).then(function (resp) {
        console.log(resp)
        _this.tableData = resp.data.content
        _this.totalElement = resp.data.totalElements
      })
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8021/records/findAll/1/'+_this.totalSize).then(function (resp){
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
        name: '小明',
        temperature: 36.2,
        date: '2021-5-1',
        address: '武汉',
        patient: '否'
      }]
    }
  }
}
</script>