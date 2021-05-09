<template>
  <div>
    <table>
      <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>体温</td>
        <td>打卡时期</td>
        <td>地点</td>
      </tr>
      <tr v-for="item in record">
        <td>{{ item.id }}</td>
        <td>{{ item.name }}</td>
        <td>{{ item.temperature }}</td>
        <td>{{ item.date }}</td>
        <td>{{ item.address }}</td>
      </tr>
    </table>
  </div>

</template>

<script>
export default {
  name: "Records",
  data(){
    return{
      record:[
        {
          id: 1,
          name: '小明',
          temperature: '36.2',
          date: '2021-5-1',
          address: '武汉'
        },
        {
          id: 2,
          name: '小we',
          temperature: '36.2',
          date: '2021-5-1',
          address: '新疆'
        },
        {
          id: 3,
          name: '小hh',
          temperature: '36.2',
          date: '2021-5-1',
          address: '上海'
        }
      ]
    }
  },
  created() {
    const _this = this
    axios.get('http://localhost:8021/records/findAll').then(function (resp){
      _this.record = resp.data
    })
  }
}
</script>

<style scoped>

</style>