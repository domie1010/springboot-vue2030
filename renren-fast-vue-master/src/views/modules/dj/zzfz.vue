<template>
  <div class="mod-role">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.title" placeholder="名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button  v-if="isAuth('sys:news:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button  v-if="isAuth('sys:news:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
            :data="dataList"
            border
            v-loading="dataListLoading"
            @selection-change="selectionChangeHandle"
            style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" width="60" label="ID"></el-table-column>
      <el-table-column prop="ty" header-align="center"  align="center" label="组织发展分类" width="120"></el-table-column>
      <el-table-column prop="unit" header-align="center"  align="center" label="发布单位" width="140"></el-table-column>
      <el-table-column prop="title" header-align="center"  align="center" label="组织发展标题" width="400"></el-table-column>
      <el-table-column prop="num" header-align="center"  align="center" label="发布编号" width="130"></el-table-column>
      <el-table-column prop="createBy" header-align="center"  align="center" label="发布人" ></el-table-column>
      <el-table-column prop="createTime" header-align="center"  align="center" label="发布时间" ></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center"  label="操作">
        <template slot-scope="scope">
          <el-button  v-if="isAuth('sys:news:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button  type="text" size="small" @click="addOrUpdateHandle2(scope.row.id)">查看</el-button>
          <el-button  v-if="isAuth('sys:news:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]"
                   :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper"></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <add-or-update2 v-if="addOrUpdateVisible2" ref="addOrUpdate2" @refreshDataList="getDataList"></add-or-update2>

  </div>
</template>
<script>
    import AddOrUpdate from './zzfz-add-or-update.vue'
    import addOrUpdate2 from './zzfz-add-or-update2'
    export default {
        data () {
            return {
                dataForm: {
                    username: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false,
                addOrUpdateVisible2: false

            }

        },
        components: {
            AddOrUpdate,addOrUpdate2
        },
        activated () {
            this.getDataList()
        },
        methods: {
            // 获取数据列表
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/sys/news/list_zzfz'),
                    method: 'get',
                    params: this.$http.adornParams({
                        'page': this.pageIndex,
                        'limit': this.pageSize,
                        'title': this.dataForm.title
                    })
                }).then(({data}) => {
                    if (data && data.code === 0) {
                        this.dataList = data.page.list
                        this.totalPage = data.page.totalCount
                    } else {
                        this.dataList = []
                        this.totalPage = 0
                    }
                    this.dataListLoading = false
                })
            },
            // 每页数
            sizeChangeHandle (val) {
                this.pageSize = val
                this.pageIndex = 1
                this.getDataList()
            },
            // 当前页
            currentChangeHandle (val) {
                this.pageIndex = val
                this.getDataList()
            },
            // 多选
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            // 新增 / 修改
            addOrUpdateHandle (id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })
            },
            // 查看详情
            addOrUpdateHandle2 (id) {
                this.addOrUpdateVisible2 = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate2.init(id)
                })
            },

            // 删除
            deleteHandle (id) {
                var ids = id ? [id] : this.dataListSelections.map(item => {
                        return item.id
                    })
                this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/sys/news/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({data}) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.getDataList()
                                }
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                }).catch(() => {})
            }
        }
    }
</script>
