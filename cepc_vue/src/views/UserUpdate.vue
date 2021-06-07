<template>
  <el-form style="width: 60%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="姓名" prop="name">
      <el-input v-model.number="ruleForm.name"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model.number="ruleForm.gender"></el-input>
    </el-form-item>
    <el-form-item label="电话" prop="tel">
      <el-input v-model.number="ruleForm.tel"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="地址" prop="address">
      <el-input v-model.number="ruleForm.address"></el-input>
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
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('姓名不能为空'));
      }else {
        callback();
      }
    };
    var checkTel = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('电话不能为空'));
      }else {
        callback();
      }
    };
    var checkGender = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('性别不能为空'));
      }else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    var checkAddr = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('地址不能为空'));
      }else {
        callback();
      }
    };
    return {
      ruleForm: {
        id: 0,
        name:'默认',
        tel: '',
        gender: '',
        password: '',
        checkPass: '',
        address: '默认'
      },
      rules: {
        name: [
          { validator: checkName, trigger: 'blur' }
        ],
        tel: [
          { validator: checkTel, trigger: 'blur' }
        ],
        gender: [
          { validator: checkGender, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        address: [
          { validator: checkAddr, trigger: 'blur' }
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
          axios({
            url:'http://localhost:8021/users/upDateUser',
            method: 'post',
            data: qs.stringify(this.ruleForm),
            headers:{
              'Content-Type':'application/x-www-form-urlencoded'
            }
          }).then(function (resp){
            if(resp.data == 'success'){
              _this.$alert('修改成功', '消息', {
                confirmButtonText: '确定',
                callback: action => {
                  _this.$router.push('/users')
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
    exitForm(formName) {
      const _this = this
      _this.$router.push('/users')
    }
  },
  created(){
    const _this = this
    axios.get('http://localhost:8021/users/findById/'+this.$route.query.id).then(function (resp){
      _this.ruleForm.id = resp.data.id
      _this.ruleForm.password = resp.data.password
      _this.ruleForm.name = resp.data.name
      _this.ruleForm.address = resp.data.address
      _this.ruleForm.gender = resp.data.gender
      _this.ruleForm.tel = resp.data.tel
    })
  }
}
</script>