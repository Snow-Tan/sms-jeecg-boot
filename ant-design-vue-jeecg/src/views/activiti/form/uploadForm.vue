<template>
  <!--流程业务表单-->
  <div class="form-main">
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">

        <!--此处加入的是集成的k-form-design表单编辑器生成的代码-->
        <div>
            <k-form-build
              :value="jsonData"
              ref="KFB"
              @submit="handleSubmit"
            />
          </div>

        <a-form @submit="handleSubmit" :form="form">
         <a-form-item v-if="!disabled"
                             :wrapperCol="{ span: 24 }"
                             style="text-align: center"
                >
                  <a-button type="primary" :disabled="disabled||btndisabled" @click="getData">保存</a-button>
                  <a-button style="margin-left: 8px" :disabled="disabled" @click="close">取消</a-button>
         </a-form-item>

        <a-form-item v-if="task"
                     :wrapperCol="{ span: 24 }"
                     style="text-align: center"
        >
          <a-button type="primary"  @click="passTask">通过</a-button>
          <a-button style="margin-left: 8px"  @click="backTask">驳回</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script>
  import pick from "lodash.pick";

  export default {
    name: 'demoForm',
    props:{
      /*全局禁用，可表示查看*/
      disabled:{
        type:Boolean,
        default:false,
        required:false
      },
      /*流程数据*/
      processData:{
        type:Object,
        default:()=>{return {}},
        required:false
      },
      /*是否新增*/
      isNew: {type: Boolean, default: false, required: false},
      /*是否处理流程*/
      task: {type: Boolean, default: false, required: false}
    },
    data () {
      return {

      //k-form-design表单编辑器生成的代码需要用到的json数据
      jsonData: {"list":[{"type":"input","label":"标题","options":{"type":"text","width":"100%","defaultValue":"","placeholder":"请输入","clearable":false,"maxLength":null,"hidden":false,"disabled":false},"model":"title","key":"input_1601168855652","rules":[{"required":false,"message":"必填项"}]},{"type":"textarea","label":"描述","options":{"width":"100%","minRows":4,"maxRows":6,"maxLength":null,"defaultValue":"","clearable":false,"hidden":false,"disabled":false,"placeholder":"请输入"},"model":"descript","key":"textarea_1601168857720","rules":[{"required":false,"message":"必填项"}]},{"type":"uploadFile","label":"上传文件","options":{"defaultValue":"[]","multiple":false,"disabled":false,"hidden":false,"drag":false,"downloadWay":"a","dynamicFun":"","width":"100%","limit":3,"data":"{}","fileName":"file","headers":{},"action":"http://localhost:8080/jeecg-boot/upload/file","placeholder":"上传"},"model":"uploadFile_1601168873529","key":"uploadFile_1601168873529","rules":[{"required":false,"message":"必填项"}]},{"type":"uploadImg","label":"上传图片","options":{"defaultValue":"[]","multiple":false,"hidden":false,"disabled":false,"width":"100%","data":"{}","limit":3,"placeholder":"上传","fileName":"image","headers":{},"action":"http://localhost:8080/jeecg-boot/upload/pic","listType":"picture-card"},"model":"uploadImg_1601168874860","key":"uploadImg_1601168874860","rules":[{"required":false,"message":"必填项"}]}],"config":{"layout":"horizontal","labelCol":{"span":4},"wrapperCol":{"span":18},"hideRequiredMark":false,"customStyle":""}},

        url:{
          getForm:'/actBusiness/getForm',
          addApply:'/actBusiness/add',
          editForm:'/actBusiness/editForm',
        },
        description: '流程表单demo，按例开发表单。需在 activitiMixin.js 中加入写好的表单',
        // form
        form: this.$form.createForm(this),
        /*表单回显数据*/
        data:{},
        btndisabled: false
      }
    },
    created() {
      console.log("流程数据",this.processData)
      if (!this.isNew){
        this.init();
      }
    },
    methods: {
      /*回显数据*/
      init(){
        this.btndisabled = true;
        var r = this.processData;
        this.getAction(this.url.getForm,{
          tableId:r.tableId,
          tableName:r.tableName,
        }).then((res)=>{
          if (res.success){
            let formData = res.result;
            formData.tableName = r.tableName;
            this.data = formData;
            console.log("表单回显数据",this.data)
            this.$nextTick(() => {
              this.form.setFieldsValue(pick(this.data,'name'))
            });
            this.btndisabled = false;
          }else {
            this.$message.error(res.message)
          }
        })
      },
      // handler
      handleSubmit (e) {
        e.preventDefault()
        this.form.validateFields((err, values) => {
          if (!err) {
            let formData = Object.assign(this.data||{}, values)
            formData.procDefId = this.processData.id;
            formData.procDeTitle = this.processData.name;
            if (!formData.tableName)formData.tableName = this.processData.businessTable;
            formData.filedNames = _.keys(values).join(",");
            console.log('formData', values)

            var url = this.url.addApply;
            if (!this.isNew){
              url = this.url.editForm;
            }
            this.btndisabled = true;
            this.postFormAction(url,formData).then((res)=>{
              if (res.success){
                this.$message.success("保存成功！")
                //todo 将表单的数据传给父组件
                this.$emit('afterSubmit',formData)
              }else {
                this.$message.error(res.message)
              }
            }).finally(()=>{
              this.btndisabled = false;
            })
          }
        })
      },

      //k-form-design表单编辑器生成的代码需要用到的方法
     getData() {
     // 通过函数获取数据
     this.$refs.KFB.getData().then(values => {
     // 获取数据成功
     //alert(JSON.stringify(values))
     let formData = Object.assign(this.data||{}, values)
                 formData.procDefId = this.processData.id;
                 formData.procDeTitle = this.processData.name;
                 if (!formData.tableName)formData.tableName = this.processData.businessTable;
                 formData.filedNames = _.keys(values).join(",");
                 console.log('formData', values)

                 var url = this.url.addApply;
                 if (!this.isNew){
                   url = this.url.editForm;
                 }
                 this.btndisabled = true;
                 this.postFormAction(url,formData).then((res)=>{
                   if (res.success){
                     this.$message.success("保存成功！")
                     //todo 将表单的数据传给父组件
                     this.$emit('afterSubmit',formData)
                   }else {
                     this.$message.error(res.message)
                   }
                 }).finally(()=>{
                   this.btndisabled = false;
                 })

      })
      .catch(err => {
      console.log(err, '校验失败')
      })
     },
      close() {
        //todo 关闭后的回调
        this.$emit('close')
      },
      /*通过审批*/
      passTask() {
        this.$emit('passTask')
      },
      /*驳回审批*/
      backTask() {
        this.$emit('backTask')
      }
    }
  }
</script>
<style lang="less" scoped>
  .form-main{
  }
</style>