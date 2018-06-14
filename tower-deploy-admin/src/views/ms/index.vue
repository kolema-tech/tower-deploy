<template>
  <div class="app-container">
    <el-button type="primary" @click="goto('msAdd')">添加服務</el-button>
    <el-button type="primary" @click="goto('msUpload')">上傳</el-button>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="Loading" border fit highlight-current-row>
      <el-table-column align="center" label='ID' width="95">
        <template slot-scope="scope">
          {{scope.$index}}
        </template>
      </el-table-column>
      <el-table-column label="服務名稱" width="100">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="git地址">
        <template slot-scope="scope">
          <a :href="scope.row.gitUrl" target="_blank">{{scope.row.gitUrl}}</a>
        </template>
      </el-table-column>
      <el-table-column label="配置" width="210" align="center">
        <template slot-scope="scope">
          {{scope.row.configs}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {getAllList} from '@/api/ms'

  export default {
    data() {
      return {
        list: null,
        listLoading: true
      }
    },
    filters: {
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getAllList().then(response => {
          this.list = response.data
          this.listLoading = false
        })
      },
      goto(page) {
        this.$router.push(page);
      },
      handleEdit(index, row) {
        console.log(index, row);
        this.$router.push({ name: 'msEdit', params: row});
      },
      handleDelete(index, row) {
        console.log(index, row);
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          //TODO: add.

          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    }
  }
</script>
