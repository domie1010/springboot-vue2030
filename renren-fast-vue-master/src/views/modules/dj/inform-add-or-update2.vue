<template>
    <el-dialog
            :title="!dataForm.id ? '新增' : '修改'"
            :close-on-click-modal="false"
            :visible.sync="visible">
        <el-form :model="dataForm"  ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
            <el-form-item label="公告标题" prop="title" >
                <el-input v-model="dataForm.title" placeholder="公告标题" readonly></el-input>
            </el-form-item>
            <el-form-item label="公告内容" prop="mobile">
                <quill-editor ref="text" v-model="dataForm.content" class="myQuillEditor" @focus="focus($event)" :options="editorOption"  />
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
                    url: this.$http.adornUrl('/sys/inform/list'),
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
                            url: this.$http.adornUrl(`/sys/inform/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({data}) => {
                            this.dataForm.title = data.info.title
                            this.dataForm.content = data.info.content
                            this.dataForm.bz = data.info.bz
                        })
                    }
                })
            },
          //获取焦点事件
          focus(event){
            event.enable(false);  //设置富文本编辑器不可编辑
          },
            submit () {
                console.log(this.$refs.text.value)
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/sys/inform/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'title': this.dataForm.title,
                                'content': this.$refs.text.value,
                                'bz': this.dataForm.bz
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
