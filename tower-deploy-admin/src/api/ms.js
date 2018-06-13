import request2 from '@/utils/request2'

export function getAllList() {
  return request2({
    url: '/api/ms/',
    method: 'get'
  })
}

export function add(params) {
  return request2({
    url: '/api/ms/',
    method: 'post',
    data: params
  })
}

export function upload(ms, params) {
  return request2({
    url: '/api/ms/upload/' + ms,
    method: 'post',
    data: params
  })
}
