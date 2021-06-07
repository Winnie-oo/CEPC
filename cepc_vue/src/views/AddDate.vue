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
      <el-button type="primary" @click="submitForm('ruleForm')">添加</el-button>
      <el-button @click="backForm('ruleForm')">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import qs from "qs";

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
          axios.post('http://localhost:8021/vaccines/save',this.ruleForm).then(function (resp){
                console.log(resp.data);
                if(resp.data == 'success'){
                  _this.$alert('添加成功', '消息', {
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
    backForm(formName) {
      const _this = this
      _this.$router.push('/vaccineDate')
    }
  },

}
</script>

<style scoped>

</style>