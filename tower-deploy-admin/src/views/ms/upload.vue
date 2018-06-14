<template>
  <div class="app-container">

    <el-form ref="form" :model="form" label-width="120px">

      <el-form-item label="服務名稱">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="提交信息">
        <el-input v-model="form.commitMessage"></el-input>
      </el-form-item>

      <el-form-item label="文件">
        <el-upload
          class="upload-demo"
          ref="upload"
          v-bind:action="uploadUrl"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false">
          <el-button slot="trigger" size="small" type="success">选取文件</el-button>
        </el-upload>
      </el-form-item>

      <el-form-item :class="line">
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>


  </div>
</template>

<script>
  import {upload} from '@/api/ms'

  export default {
    data() {
      return {
        form: {
          name: '',
          commitMessage: ''
        },
        fileList: []
      }
    },
    computed: {
      uploadUrl: function () {
        return `${process.env.TOWER_API}/api/ms/upload/${this.form.name}?commitMessage=${this.form.commitMessage}`;
      }
    },
    methods: {
      onSubmit() {
        console.log(this.uploadUrl);
        this.$refs.upload.submit();
      },
      onCancel() {
        this.$router.push('ms');
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

