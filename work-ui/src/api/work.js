import request from '@/utils/request'

export function doSaveWork(formData) {
  formData.access_token = '72b5bcfa-5c07-4046-b958-8e75df5db87a'
  return request({
    url: '/api-w/work/doSave',
    method: 'get',
    params: formData
  })
}
