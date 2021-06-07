<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="编号" prop="id" >
      <el-input v-model="ruleForm.id" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item label="用户姓名" prop="name" >
      <el-input v-model="ruleForm.name" style="width: 46%"></el-input>
    </el-form-item>
    <el-form-item label="预约日期" prop="patient">
      <el-select v-model="ruleForm.date" placeholder="请选择">
        <el-option  v-for="item in appointDate"
                    :label="item"
                    :value="item"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="身份证号" prop="id_card" >
      <el-input v-model="ruleForm.id_card" style="width: 46%"></el-input>
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
        id:'',
        name: '',
        date: '',
        id_card: '',
      },
      appointDate: [''],
      rules: {
        id: [
          { required: true, message: '请输入编号', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' },
        ],
        date: [
          { required: true, message: '请选择预约日期', trigger: 'change' }
        ],
        id_card: [
          { required: true, message: '请输入身份证号码', trigger: 'blur' },
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8021/appointRecord/update',this.ruleForm).then(function (resp){
            console.log(resp.data);
            if(resp.data == 'success'){
              _this.$alert('修改成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/appoint')
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
      _this.$router.push('/appoint')
    }
  },
  created(){
    const _this = this
    axios.get('http://localhost:8021/appointRecord/findById/'+this.$route.query.id).then(function (resp){
      _this.ruleForm.id = resp.data.id
      _this.ruleForm.date = resp.data.date
      _this.ruleForm.name = resp.data.name
      _this.ruleForm.id_card = resp.data.id_card
    })

    axios.get('http://localhost:8021/vaccines/findDate').then(function (resp){
      _this.appointDate = resp.data
    })
  }
}
</script>

<style scoped>

</style>