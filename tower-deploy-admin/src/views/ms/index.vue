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
      <el-table-column label="服務名稱">
        <template slot-scope="scope">
          {{scope.row.name}}
        </template>
      </el-table-column>
      <el-table-column label="git地址" width="110" align="center">
        <template slot-scope="scope">
          <span>{{scope.row.gitUrl}}</span>
        </template>
      </el-table-column>
      <el-table-column label="配置" width="110" align="center">
        <template slot-scope="scope">
          {{scope.row.configs}}
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
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
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
      }
    }
  }
</script>
