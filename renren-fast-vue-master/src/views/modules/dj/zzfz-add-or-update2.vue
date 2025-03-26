<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :close-on-click-modal="false"
            :visible.sync="visible">
        <el-form :model="dataForm"  ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="dataForm.title" placeholder="标题" readonly></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="ty">
            <el-input v-model="dataForm.ty" placeholder="类型" readonly></el-input>
          </el-form-item>
          <el-form-item label="发布单位" prop="unit">
            <el-input v-model="dataForm.unit" placeholder="发布单位" readonly></el-input>
          </el-form-item>
          <el-form-item label="发布编号" prop="num">
            <el-input v-model="dataForm.num" placeholder="发布编号" readonly></el-input>
          </el-form-item>
          <el-form-item label="要闻内容" prop="mobile">
            <quill-editor ref="text" v-model="dataForm.content" @focus="focus($event)"  class="myQuillEditor" :options="editorOption" />
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
    </el-dialog>


</template>

<script>
    import { quillEditor } from 'vue-quill-editor'
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import { treeDataTranslate } from '@/utils'
    export default {
        data () {
            return {
                content: '',
                editorOption: {},
                visible: false,
                dataForm: {
                    id: 0,
                    title: '',
                    content: '',
                    bz: ''
                },
                tempKey: -666666 // 临时key, 用于解决tree半选中状态项不能传给后台接口问题. # 待优化
            }
        },
        components: {
            quillEditor
        },
        methods: {
            init (id) {
                this.dataForm.id = id || 0
                this.$http({
                    url: this.$http.adornUrl('/sys/news/list'),
                    method: 'get',
                    params: this.$http.adornParams()
                }).then(({data}) => {
                    this.infoList = treeDataTranslate(data, 'id')
                }).then(() => {
                    this.visible = true
                    this.$nextTick(() => {
                        this.$refs['dataForm'].resetFields()
                    })
                }).then(() => {
                    if (this.dataForm.id) {
                        this.$http({
                            url: this.$http.adornUrl(`/sys/news/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({data}) => {
                          this.data = data.news;
                          this.dataForm.title = data.news.title,
                            this.dataForm.content = data.news.content,
                            this.dataForm.ty = data.news.ty,
                            this.dataForm.num = data.news.num,
                            this.dataForm.unit = data.news.unit,
                            this.dataForm.bz = data.news.bz
                        })
                    }
                })
            },
            submit () {
                console.log(this.$refs.text.value)
            },
          //获取焦点事件
          focus(event){
            event.enable(false);  //设置富文本编辑器不可编辑
          },
        }
    }
</script>
