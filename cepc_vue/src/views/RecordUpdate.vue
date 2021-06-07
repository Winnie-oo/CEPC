<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="用户姓名" prop="name" >
      <el-input v-model="ruleForm.name" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item label="属于哪四类" prop="patient">
      <el-select v-model="ruleForm.patient" placeholder="请选择">
        <el-option label="正常" value="正常"></el-option>
        <el-option label="确诊患者" value="确诊患者"></el-option>
        <el-option label="无症状感染" value="无症状感染"></el-option>
        <el-option label="密切接触" value="密切接触"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="日期" required>
      <el-col :span="11">
        <el-date-picker prop="date" placeholder="选择日期" v-model="ruleForm.date" value-format="yyyy-MM-dd" style="width: 100%;"></el-date-picker>
      </el-col>
    </el-form-item>
    <el-form-item label="体温" prop="temperature" >
      <el-input v-model="ruleForm.temperature" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address" >
      <el-input v-model="ruleForm.address" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
      <el-button @click="exitForm('ruleForm')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import qs from "qs";

export default {
  data() {
    return {
      ruleForm: {
        name: '',
        patient: '',
        date: '',
        temperature: '',
        address: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' },
        ],
        patient: [
          { required: true, message: '请选择种类', trigger: 'change' }
        ],
        date: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        temperature: [
          { required: true, message: '请输入体温', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
        ],

      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios({
            url:'http://localhost:8021/records/save',
            method: 'post',
            data: qs.stringify(this.ruleForm),
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            }
          }).then(function (resp){
            console.log(resp.data);
            if(resp.data == 'success'){
              _this.$alert('修改成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/records')
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
      _this.$router.push('/records')

    }
  },
  created(){
    const _this = this
    axios.get('http://localhost:8021/records/findById/'+this.$route.query.id).then(function (resp){
      _this.ruleForm.id = resp.data.id
      _this.ruleForm.temperature = resp.data.temperature
      _this.ruleForm.name = resp.data.name
      _this.ruleForm.date = resp.data.date
      _this.ruleForm.patient = resp.data.patient
      _this.ruleForm.address = resp.data.address
    })
  }
}
</script>