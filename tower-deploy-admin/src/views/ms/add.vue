<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="服務名稱">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="Git地址">
        <el-input v-model="form.gitUrl"></el-input>
      </el-form-item>
      <el-form-item label="配置">
        <el-input type="textarea" v-model="form.configs"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {add} from '@/api/ms'

  export default {
    data() {
      return {
        form: {
          name: '',
          gitUrl: '',
          configs: {}
        }
      }
    },
    methods: {
      onSubmit() {
        this.$message('submit!')
        add(this.form).then(response => {
          this.list = response.data
          this.listLoading = false
          if (response.code == 20000) {
            this.$message('創建成功!');
            this.$router.push('ms');
          }
        })
      },
      onCancel() {
        this.$router.push('ms');
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

