<template>
  <div class="app-container">
    <el-input placeholder="Filter keyword" v-model="filterText" style="margin-bottom:30px;"></el-input>

    <el-container>
      <el-aside width="200px">
        <el-tree class="filter-tree"
                 :data="treeData"
                 :props="defaultProps"
                 node-key="parent"
                 default-expand-all
                 :filter-node-method="filterNode"
                 @node-click="nodeClick"
                 ref="tree1">

        </el-tree>
      </el-aside>
      <el-main>
        <el-form ref="form" :model="form">
          <el-form-item label="文本內容">
            <el-input type="textarea" v-model="form.text"></el-input>
          </el-form-item>
        </el-form>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">保存</el-button>
        </el-form-item>
      </el-main>
    </el-container>

  </div>
</template>

<script>
  import {getRoleContent, getRoles} from '@/api/role'

  export default {
    created() {
      getRoles().then(response => {
        this.treeData = [];
        this.treeData.push(response.data);
      })
    },
    watch: {
      filterText(val) {
        this.$refs.tree1.filter(val)
      }
    },

    methods: {
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      nodeClick(data, node, tree) {
        if (node.isLeaf) {
          getRoleContent(data.parent + "/" + data.label).then(response => {
            this.form.text = response.data;
          });
        }
      },
      onSubmit() {

      }
    },

    data() {
      return {
        form: {
          text: null
        },
        filterText: '',
        treeData: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    }
  }
</script>

