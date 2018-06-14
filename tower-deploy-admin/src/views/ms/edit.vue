<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="服務名稱">
        <el-input v-model="form.name" :readonly="true"></el-input>
      </el-form-item>
      <el-form-item label="Git地址">
        <el-input v-model="form.gitUrl"></el-input>
      </el-form-item>
      <el-form-item label="配置">
        <el-input type="textarea" v-model="form.configs"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">修改</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {edit} from '@/api/ms'

  export default {

    created: function () {
      this.form = this.$route.params;
    },

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
        this.$message('submit!');
        this.form.configs = null;
        edit(this.form).then(response => {
          this.list = response.data
          if (response.code == 20000) {
            this.$message('修改成功!');
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
