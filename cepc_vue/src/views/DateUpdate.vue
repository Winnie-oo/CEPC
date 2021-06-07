<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="日期" required>
      <el-col :span="11">
        <el-date-picker prop="date" placeholder="选择日期" v-model="ruleForm.date" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
      </el-col>
    </el-form-item>
    <el-form-item label="数量" prop="number" >
      <el-input v-model="ruleForm.number" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
      <el-button @click="exitForm('ruleForm')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        id:'',
        date: '',
        number: '',
      },
      rules: {
        date: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        number: [
          { required: true, message: '请输入疫苗数量', trigger: 'blur' },
        ],

      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.ruleForm.id = this.$route.query.id
          axios.post('http://localhost:8021/vaccines/update/',this.ruleForm).then(function (resp){
            console.log(resp.data);
            if(resp.data == 'success'){
              _this.$alert('修改成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/vaccineDate')
                  console.log('submit!');
                }
              });
            }
          })
        } else {
          console.log('error submit!');
          return false;
        }
      });
    },
    exitForm(formName) {
      const _this = this
      _this.$router.push('/vaccineDate')
    }
  },
  created(){
    const _this = this
    axios.get('http://localhost:8021/vaccines/findById/'+this.$route.query.id).then(function (resp){
      _this.ruleForm.date = resp.data.date
      _this.ruleForm.number = resp.data.number
    })
  }
}
</script>

<style scoped>

</style>