<template>
  <div class="app-container">
    <el-input placeholder="Filter keyword" v-model="filterText" style="margin-bottom:30px;"></el-input>

    <el-tree class="filter-tree" :data="data2"
             :props="defaultProps"
             default-expand-all
             :filter-node-method="filterNode"
             ref="tree2">

    </el-tree>

  </div>
</template>

<script>
  export default {
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },

    methods: {
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      }
    },

    data() {
      return {
        filterText: '',
        data2: [{
          id: 1,
          label: 'ansible',
          children: [{
            id: 4,
            label: 'roles',
            children: [{
              id: 9,
              label: 'Level three 1-1-1',
              children: null
            }, {
              id: 10,
              label: 'Level three 1-1-2',
              children: null
            }]
          }]
        }, {
          id: 9,
          label: 'copy-file.inv'
        }, {
          id: 10,
          label: 'copy-file.inv'
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    }
  }
</script>

