<template>
  <div>
    <el-steps :active="active" finish-status="success" simple>
      <el-step title="1.選擇服務"></el-step>
      <el-step title="2.配置參數"></el-step>
    </el-steps>

    <div class="app-container" v-show="active==0">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="服務名稱" prop="name">
          <el-input v-model="form.name" @change="queryVersions"></el-input>
        </el-form-item>
        <el-form-item label="版本">
          <el-select placeholder="请选择" v-model="form.version" prop="version">
            <el-option
              v-for="item in versions"
              :key="item.version"
              :label="item.fullMessage"
              :value="item.version">
              <span style="float: left">{{ item.fullMessage }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.version }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="upstream目錄" prop="upstreamDir">
          <el-input v-model="form.upstreamDir"></el-input>
        </el-form-item>

        <el-form-item label="upstream 實例" prop="upstreams">
          <el-input v-model="form.upstreams"></el-input>
        </el-form-item>

        <el-form-item label="源文件" prop="srcFile">
          <el-input v-model="form.srcFile"></el-input>
        </el-form-item>

        <el-form-item label="目標文件" prop="destFile">
          <el-input v-model="form.destFile"></el-input>
        </el-form-item>

      </el-form>

      <el-button style="margin-top: 12px;" @click="publish">發佈</el-button>

    </div>

  </div>
</template>
<script>

  import {getVersions} from '@/api/ms'
  import {deploy} from '@/api/deploy'

  export default {
    data() {
      return {
        form: {
          name: '',
          version: '',
          upstreamDir: "/usr/local/nginx/conf/vhost/upstream",
          upstreams: '192.168.2.211:8888,192.168.2.212:8888',
          srcFile: '/deploy/git/ms/sigma-0.0.1-SNAPSHOT.jar',
          destFile: '/deploy/app/ready/sigma-0.0.1-SNAPSHOT.jar'
        },
        versions: [],
        active: 0,
        rules: {
          name: [
            {required: true, message: '请输入服務名称', trigger: 'blur'}],

          version: [
            {required: true, message: '请輸入版本', trigger: 'change'}],

          upstreamDir: [
            {required: true, message: '請輸入upstream目錄', trigger: 'blur'}],

          upstreams: [
            {required: true, message: '請輸入實例', trigger: 'blur'}],

          srcFile: [
            {required: true, message: '請輸入源文件', trigger: 'blur'}],
          destFile: [
            {required: true, message: '请输入目標文件', trigger: 'blur'}]
        }
      };
    },

    methods: {
      next() {
        let next = false;

        if (this.active == 0) {
          this.$refs.form.validate(valid => {
            if (valid) {
              next = true;
            } else {
              this.$message('驗證失敗！');
              return false;
            }
          });
        }
        else {
          next = true;
        }

        if (next) {
          if (this.active != 2) {
            this.active++;
          }
        }
      },
      prev() {
        let prev = true;

        if (prev) {
          if (this.active != 0) {
            this.active--;
          }
        }
      },
      publish() {
        console.log(this.form);
        deploy(this.form).then(response => {
          this.$message('提交成功！');
        });
      },

      queryVersions() {
        getVersions(this.form.name).then(response => {
          this.versions = response.data;
        });
      }
    }
  }
</script>
