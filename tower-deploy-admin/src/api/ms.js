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

export function edit(params) {
  return request2({
    url: '/api/ms/',
    method: 'put',
    data: params
  })
}

export function getVersions(name) {
  return request2({
    url: '/api/ms/getVersions/?name=' + name,
    method: 'get'
  })
}
