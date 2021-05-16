<template>
  <div>
    <el-form :rules="rules" ref="loginForm" :model="loginForm"class="loginContainer">
      <h3 class="loginTitle">管理员登录</h3>
      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击图片更换验证码"style="width: 180px;margin-right: 5px"></el-input>
        <img :src="captchaUrl" @click="getImage">
      </el-form-item>
      <el-checkbox v-model="checked"class="loginRemember">记住我</el-checkbox>
      <el-button type="primary" style="width:100%" @click="submitLogin">登录</el-button>
    </el-form>
  </div>

</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      captchaUrl:'',
      loginForm:{
        username:'admin',
        password:123,
        code:''
      },
      checked:true,
      rules:{
        username: [{required:true,message:'请输入用户名', trigger: 'blur' }],
        password: [{required:true,message:'请输入密码', trigger: 'blur' }],
        code: [{required:true,message:'请输入验证码', trigger: 'blur' }],
      }
    }
  },
  methods:{
//用来更换验证码
    getImage(){
      this.getSrc();
    },
    //获取验证码，代码复用，便于调用
    getSrc(){
      var _this = this;
      //console.log("xxxx");
      //异步请求：请求验证码图片
      axios.get("http://localhost:8021/manager/getImage?time="+Math.random()).then(res=>{
        console.log(res.data);
        //把图片赋给url属性
        _this.captchaUrl = res.data;
      });
    },
    submitLogin(){
      this.$refs.loginForm.validate((valid) => {
        if(valid){
          this.$router.replace('/home');
          alert('submit!');
        }else {
          this.$message.error('请输入所有字段');
          return false;
        }
      });
    }
  },
  created() {
    this.getSrc();
  }
}
</script>

<style >
.loginContainer{
  border-radius: 15px;
  background-clip: padding-box;
  margin: 75px auto;
  width: 350px;
  padding: 15px 35px 15px 35px;
  background: white;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.loginTitle{
  margin: 0px auto 40px auto;
  text-align: center;
}
.loginRemember{
  text-align: left;
  margin: 0px 0px 15px 0px;
}

</style>