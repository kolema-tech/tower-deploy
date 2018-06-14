<template>
  <div class="app-container">

    <el-form ref="form" :model="form" label-width="120px" :rules="rules">

      <el-form-item label="服務名稱" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="提交信息" prop="commitMessage">
        <el-input v-model="form.commitMessage"></el-input>
      </el-form-item>

      <el-form-item label="文件">
        <el-upload
          class="upload-demo"
          ref="upload"
          v-bind:action="uploadUrl"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="onSuccess"
          :on-error="onError"
          :file-list="form.fileList"
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
          commitMessage: '',
          fileList: []
        },
        rules: {
          name: [
            {required: true, message: '请输入服務名称', trigger: 'blur'}],

          commitMessage: [
            {required: true, message: '请輸入提交信息', trigger: 'blur'}]
        }
      }
    },
    computed: {
      uploadUrl: function () {
        return `${process.env.TOWER_API}/api/ms/upload/${this.form.name}?commitMessage=${this.form.commitMessage}`;
      }
    },
    methods: {
      onSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            this.$refs.upload.submit();
          } else {
            this.$message('驗證失敗！');
            return false;
          }
        });
      },
      onCancel() {
        this.$router.push('ms');
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      onSuccess(response, file, fileList) {
        this.$message('保存成功！');
        this.$router.push('ms');
      },
      onError(err, file, fileList) {
        this.$message('保存失敗！' + err);
      }
    }
  }
</script>

<style scoped>
  .line {
    text-align: center;
  }
</style>

