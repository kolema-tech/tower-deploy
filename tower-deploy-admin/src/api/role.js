import request2 from '@/utils/request2'

export function getRoles() {
  return request2({
    url: '/api/role/getRoleTree',
    method: 'get'
  })
}

export function getRoleContent(file) {
  return request2({
    url: '/api/role/getRoleContent?file=' + file,
    method: 'get'
  })
}
