<template>
  <div>
    <code>Markdown is based on
      <a href="https://github.com/nhnent/tui.editor" target="_blank">tui.editor</a> ，Simply encapsulated in Vue.
      <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/feature/component/markdown-editor.html">
        Documentation </a>
    </code>
    <el-form ref="workForm" :model="workForm" :rules="rules" label-width="80px" class="workForm">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-card class="box-card">
            <el-form-item label="项目" prop="project">
              <el-select v-model="workForm.project" placeholder="请选择项目" multiple style="width: 100%;">
                <el-option label="阳光" value="阳光"/>
                <el-option label="泰康" value="泰康"/>
                <el-option label="华夏" value="华夏"/>
                <el-option label="百年" value="百年"/>
                <el-option label="中宏" value="中宏"/>
              </el-select>
            </el-form-item>
            <div slot="header" class="clearfix">
              <span>属性</span>
            </div>
            <el-form-item label="标题" prop="title">
              <el-input v-model="workForm.title"/>
            </el-form-item>
            <el-form-item label="发起人" prop="person">
              <el-select v-model="workForm.person" placeholder="请选择发起人" style="width: 100%;">
                <el-option label="张三" value="张三"/>
                <el-option label="李四" value="李四"/>
              </el-select>
            </el-form-item>
            <el-form-item label="分类" prop="category">
              <el-select v-model="workForm.category" placeholder="请选择分类" style="width: 100%;">
                <el-option label="系统问题" value="系统问题"/>
                <el-option label="新需求" value="新需求"/>
                <el-option label="功能修改" value="功能修改"/>
              </el-select>
            </el-form-item>
            <el-form-item label="重要程度" prop="importance">
              <el-select v-model="workForm.importance" placeholder="请选择重要程度" style="width: 100%;">
                <el-option label="系统问题" value="系统问题"/>
                <el-option label="新需求" value="新需求"/>
                <el-option label="功能修改" value="功能修改"/>
              </el-select>
            </el-form-item>
            <el-form-item label="完成时间" prop="finishTime">
              <el-date-picker v-model="workForm.finishTime" type="datetime" placeholder="选择日期时间" style="width: 100%;"/>
            </el-form-item>
          </el-card>
        </el-col>
        <el-col :span="16">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>内容</span>
            </div>
            <markdown-editor v-model="workForm.content" height="300px"/>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import MarkdownEditor from '@/components/MarkdownEditor'

export default {
  components: { MarkdownEditor },
  data() {
    return {
      workForm: {
        project: '',
        title: '123',
        describe: '',
        person: '',
        content: '',
        category: '新需求',
        importance: '',
        finishTime: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在3到5个字符', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
<style>
.workForm {
  margin: 10px;
}
</style>
