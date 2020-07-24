<template>
  <j-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :xs="24" :sm="12">
            <a-form-item label="树苗名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['demandName']" placeholder="请输入树苗名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="需求树苗价格" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['demandPrice']" placeholder="请输入需求树苗价格" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="树苗数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['demandAmount']" placeholder="请输入树苗数量" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="联系人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactName']" placeholder="请输入联系人姓名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['contactUmber']" placeholder="请输入联系人电话" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="联系人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['contactSite']" placeholder="请输入联系人地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="发布地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['demandArea']" placeholder="请输入发布地区"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="树苗备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['demandComunt']" placeholder="请输入树苗备注"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xs="24" :sm="12">
            <a-form-item label="介绍费" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['ifMoeny']" placeholder="请输入介绍费" style="width: 100%"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="树苗规格" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="demandSpecificationTable.loading"
            :columns="demandSpecificationTable.columns"
            :dataSource="demandSpecificationTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'SaplingDemandModal',
    mixins: [JEditableTableMixin],
    components: {
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
        },
        refKeys: ['demandSpecification', ],
        tableKeys:['demandSpecification', ],
        activeKey: 'demandSpecification',
        // 树苗规格
        demandSpecificationTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '径高',
              key: 'height',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '径粗',
              key: 'widht',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '年份',
              key: 'year',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/sapling/saplingDemand/add",
          edit: "/sapling/saplingDemand/edit",
          demandSpecification: {
            list: '/sapling/saplingDemand/queryDemandSpecificationByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'demandName','demandPrice','demandAmount','contactName','contactUmber','contactSite','demandArea','demandComunt','ifMoeny')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.demandSpecification.list, params, this.demandSpecificationTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          demandSpecificationList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'demandName','demandPrice','demandAmount','contactName','contactUmber','contactSite','demandArea','demandComunt','ifMoeny'))
     },

    }
  }
</script>

<style scoped>
</style>