<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :close-on-click-modal="false"
            :visible.sync="visible">
        <el-form :model="dataForm"  ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
            <el-form-item label="要闻标题" prop="title">
                <el-input v-model="dataForm.title" placeholder="党建要闻标题"></el-input>
            </el-form-item>
          <el-form-item label="要闻类型" prop="ty">
            <el-input v-model="dataForm.ty" placeholder="党建要闻类型"></el-input>
          </el-form-item>
          <el-form-item label="发布单位" prop="unit">
            <el-input v-model="dataForm.unit" placeholder="发布单位"></el-input>
          </el-form-item>
          <el-form-item label="发布编号" prop="num">
            <el-input v-model="dataForm.num" placeholder="发布编号"></el-input>
          </el-form-item>
            <el-form-item label="要闻内容" prop="mobile">
                <quill-editor ref="text" v-model="dataForm.content" class="myQuillEditor" :options="editorOption" />
            </el-form-item>
        </el-form>


        <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
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
                    ty: '',
                    num: '',
                    unit: '',
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
                console.log(this.$refs.text.value+"sadasdasd")
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/sys/news/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'title': this.dataForm.title,
                                'content': this.$refs.text.value,
                                'ty': this.dataForm.ty,
                                'num': this.dataForm.num,
                                'unit': this.dataForm.unit,
                                'bz': this.dataForm.bz,
                                'kind': '党建要闻'

                            })
                        }).then(({data}) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message.error(data.msg)
                            }
                        })
                    }
                })
            }
        }
    }
</script>
