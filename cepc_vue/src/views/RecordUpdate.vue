<template>
  <el-form style="width: 60%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="姓名" prop="name">
      <el-input v-model.number="ruleForm.name" disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="是否为四类患者" prop="patient">
      <el-input v-model="ruleForm.patient"></el-input>
    </el-form-item>
    <el-form-item label="体温" prop="temperature">
      <el-input v-model="ruleForm.temperature"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
      <el-button @click="resetForm('ruleForm')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    var checkTemp = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('体温不能为空'));
      }else {
        callback();
      }
    };
    var checkPatient = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('身份不能为空'));
      }else {
        callback();
      }
    };
    return {
      ruleForm: {
        id: 0,
        temperature: 0,
        date: '',
        address: '',
        patient: ''
      },
      rules: {
        temperature: [
          { validator: checkTemp(), trigger: 'blur' }
        ],
        patient: [
          { validator: checkPatient(), trigger: 'blur' }
        ]
      }

    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('submit!');
          axios.put('http://localhost:8021/records/save',this.ruleForm).then(function (resp){
            if(resp.data == 'success'){
              _this.$alert('修改成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/records')
                }
              });
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
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
      _this.ruleForm.patient = resp.data.patient
      _this.ruleForm.address = resp.data.address
    })
  }
}
</script>