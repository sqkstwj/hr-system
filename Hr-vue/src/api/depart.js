import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService5 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/depart/selectAll')
}

//添加
export const articleCategoryAddService5 = (categoryData)=>{
    return request.post('/depart/add',categoryData)
}

//修改
export const articleCategoryUpdateService5 = (categoryData1)=>{
   return  request.put('/depart/update',categoryData1)
}

//删除
export const articleCategoryDeleteService5 = (id)=>{
    return request.delete('/depart/delete/'+id)
}

//列表查询
export const articleListService5 = (params)=>{
   return  request.get('/depart',{params:params})
}