<template>
  <el-dialog
    :title="!dataForm.id ? '入党基本测试' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <div v-for="(item,index) in lists" style="margin-bottom: 10px">
        <span>{{ item.id = (index + 1) }} {{ item.title }} </span><br/>
        <el-radio-group v-model="item.value">
          <el-radio :label="lab=(index1+1)" v-for="(item1,index1) in item.li">{{ item1.value }}</el-radio>

        </el-radio-group>
      </div>


    </el-form>


    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>

  </el-dialog>


</template>

<script>
import {treeDataTranslate} from '@/utils'

export default {
  data() {
    return {
      lists: [
        {
          title: '.无产阶级政党的第一个党纲是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、《共产党宣言》'
            },
            {
              lab: '2',
              value: 'B、《反杜林论》'
            },
            {
              lab: '3',
              value: 'C、《哥达纲领批判》'
            }
          ]
        }, {
          title: '.中共第一次全国代表大会召开的地点是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、北京'
            },
            {
              lab: '2',
              value: 'B、广州'
            },
            {
              lab: '3',
              value: 'C、上海'
            }
          ]
        }, {
          title: '.把毛泽东思想确立为我们党的指导思想的会议是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、党的七大'
            },
            {
              lab: '2',
              value: 'B、七届二中全会'
            },
            {
              lab: '3',
              value: 'C、六届七中全会'
            }
          ]
        }, {
          title: '.党的七届二中全会提出的“两个务必”的具体内容是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、谦虚谨慎、不骄不躁的作风'
            },
            {
              lab: '2',
              value: 'B、优良的学习传统，为人民服务宗旨'
            },
            {
              lab: '3',
              value: 'C、革命统一战线，坚持“-国两制”的方针'
            }
          ]
        }, {
          title: '.邓小平理论的精髓是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、“解放思想、实事求是”'
            },
            {
              lab: '2',
              value: 'B、一切为人民服务'
            },
            {
              lab: '3',
              value: 'C、立党为公、执政为民'
            }
          ]
        }, {
          title: '.中国共产党是中国工人阶级的先锋队，同时是( )，是中国特色社会主义事业的领导核心',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、中国各族人民的先锋队'
            },
            {
              lab: '2',
              value: 'B、中国人民和中华民族的先锋队'
            },
            {
              lab: '3',
              value: 'C、中国社会各阶层的先锋队'
            },
          ]
        }, {
          title: '.预备党员的预备期为( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、一年'
            },
            {
              lab: '2',
              value: 'B、二年'
            },
            {
              lab: '3',
              value: 'C、三年'
            },

          ]
        }, {
          title: '8.科学发展观的本质和核心是( )',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A、坚持以人为本'
            },
            {
              lab: '2',
              value: 'B、解放和发展生产力'
            },
            {
              lab: '3',
              value: 'C、实事求是'
            },

          ]
        }, {
          title: '9.全面建设小康社会，最根本的是坚持以( )，不断解放和发展社会生产力',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A.改革开放为动力'
            },
            {
              lab: '2',
              value: 'B.经济建设为中心'
            },
            {
              lab: '3',
              value: 'C.科技进步为先导'
            },

          ]
        }, {
          title: '10.党的基层委员会每届任期( )年至五年，总支部委员会，支部委员会每届任期两年或三年',
          value: 0,
          li: [
            {
              lab: '1',
              value: 'A.三'
            },
            {
              lab: '2',
              value: 'B.四'
            },
            {
              lab: '3',
              value: 'C.二'
            },

          ]
        },
      ],
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
    }
  },

  methods: {
    init(id) {
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
            this.dataForm.title = data.info.title
            this.dataForm.content = data.info.content
            this.dataForm.bz = data.info.bz
          })
        }
      })
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/exam/submitExam`),
            method: 'post',
            data: this.$http.adornData({
              'lists': JSON.stringify(this.lists),
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              if(data.score >= 60){
                this.$message({
                  message: '成绩'+data.score+"分，测试通过",
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              }else{
                this.$message({
                  message: '成绩'+data.score+"分，测试不通过",
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              }
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
