import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService2 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/salary/selectAll')
}

//添加
export const articleCategoryAddService2 = (categoryData)=>{
    return request.post('/salary/add',categoryData)
}

//修改
export const articleCategoryUpdateService2 = (categoryData1)=>{
   return  request.put('/salary/update',categoryData1)
}

//删除
export const articleCategoryDeleteService2 = (id)=>{
    return request.delete('/salary/delete/'+id)
}

//列表查询
export const articleListService2 = (params)=>{
   return  request.get('/salary',{params:params})
}