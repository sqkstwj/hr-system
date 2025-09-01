import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService4 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/diaodu/selectAll')
}

//添加
export const articleCategoryAddService4 = (categoryData)=>{
    return request.post('/diaodu/add',categoryData)
}

//修改
export const articleCategoryUpdateService4 = (categoryData1)=>{
   return  request.put('/diaodu/update',categoryData1)
}

//删除
export const articleCategoryDeleteService4 = (id)=>{
    return request.delete('/diaodu/delete/'+id)
}

//列表查询
export const articleListService4 = (params)=>{
   return  request.get('/diaodu',{params:params})
}