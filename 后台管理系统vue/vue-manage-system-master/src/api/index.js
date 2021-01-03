import request from '../utils/request';

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: data
  })
}

export function getGoodsList(data) {
  return request({
    url: '/item/list',
    method: 'get',
    params:data
  })
}


export function getVerifyCode(data) {
  return request({
    url: '/user/generateverifycode',
    method: 'get',
    params:data
  })
}


export function getLogInfoList(data) {
  return request({
    url: '/log/list',
    method: 'get',
    params:data
  })
}

export function getOrderList(data) {
  return request({
    url: '/order/list',
    method: 'get',
    params:data
  })
}

export function publishpromo(data) {
  return request({
    url: '/item/publishpromo',
    method: 'post',
    params:data
  })
}

export function changePassword(data) {
  return request({
    url: '/user/changePassword',
    method: 'post',
    params: data
  })
}

