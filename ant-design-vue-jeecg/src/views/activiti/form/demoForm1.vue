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
      jsonData: {"list":[{"type":"textarea","label":"姓名","options":{"width":"100%","minRows":4,"maxRows":6,"maxLength":null,"defaultValue":"","clearable":false,"hidden":false,"disabled":false,"placeholder":"请选择姓名"},"model":"name","key":"textarea_1600852844888","rules":[{"required":true,"message":"必填项"}]},{"type":"select","label":"年龄","options":{"width":"100%","multiple":false,"disabled":false,"clearable":false,"hidden":false,"placeholder":"请选择年龄","dynamicKey":"","dynamic":false,"options":[{"value":"22","label":"22"},{"value":"23","label":"23"},{"value":"24","label":"24"}],"showSearch":false,"defaultValue":"22"},"model":"age","key":"select_1600852847119","rules":[{"required":true,"message":"必填项"}]},{"type":"select","label":"性别","options":{"width":"100%","multiple":false,"disabled":false,"clearable":false,"hidden":false,"placeholder":"请选择sex","dynamicKey":"","dynamic":false,"options":[{"value":"男","label":"男"},{"value":"女","label":"女"}],"showSearch":false,"defaultValue":"女"},"model":"sex","key":"select_1600853318914","rules":[{"required":true,"message":"必填项"}]},{"type":"date","label":"生日","icon":"icon-calendar","options":{"width":"100%","defaultValue":"","rangeDefaultValue":[],"range":false,"showTime":false,"disabled":false,"hidden":false,"clearable":false,"placeholder":"请选择","rangePlaceholder":["开始时间","结束时间"],"format":"YYYY-MM-DD HH:mm:ss"},"model":"birthday","key":"date_1600933441441","rules":[{"required":true,"message":"必填项"}]}],"config":{"layout":"horizontal","labelCol":{"span":4},"wrapperCol":{"span":18},"hideRequiredMark":false,"customStyle":""}},

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