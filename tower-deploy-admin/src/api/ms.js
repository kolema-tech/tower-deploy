import request2 from '@/utils/request2'

export function getAllList() {
  return request2({
    url: '/api/ms/',
    method: 'get'
  })
}
