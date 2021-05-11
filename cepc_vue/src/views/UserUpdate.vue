<template>
  <el-form style="width: 60%" :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="姓名" prop="name">
      <el-input v-model.number="ruleForm.name"></el-input>
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
      <el-button @click="resetForm('ruleForm')">取消</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  data() {
    var checkName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('姓名不能为空'));
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
        password: '',
        checkPass: '',
        address: '默认'
      },
      rules: {
        name: [
          { validator: checkName, trigger: 'blur' }
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
          axios.put('http://localhost:8021/users/upDate',this.ruleForm).then(function (resp){
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
    resetForm(formName) {
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
    })
  }
}
</script>