import request2 from '@/utils/request2'

export function deploy(params) {
  return request2({
    url: `/api/deploy/${params.name}/${params.version}`,
    method: 'post',
    data: params
  })
}
