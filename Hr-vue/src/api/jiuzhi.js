import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService6 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/jiuzhi/selectAll')
}

//添加
export const articleCategoryAddService6 = (categoryData)=>{
    return request.post('/jiuzhi/add',categoryData)
}

//修改
export const articleCategoryUpdateService6 = (categoryData1)=>{
   return  request.put('/jiuzhi/update',categoryData1)
}

//删除
export const articleCategoryDeleteService6 = (id)=>{
    return request.delete('/jiuzhi/delete/'+id)
}

//列表查询
export const articleListService6 = (params)=>{
   return  request.get('/jiuzhi',{params:params})
}