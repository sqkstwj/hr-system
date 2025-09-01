import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService3 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/repun/selectAll')
}

//添加
export const articleCategoryAddService3 = (categoryData)=>{
    return request.post('/repun/add',categoryData)
}

//修改
export const articleCategoryUpdateService3 = (categoryData1)=>{
   return  request.put('/repun/update',categoryData1)
}

//删除
export const articleCategoryDeleteService3 = (id)=>{
    return request.delete('/repun/delete/'+id)
}

//列表查询
export const articleListService3 = (params)=>{
   return  request.get('/repun',{params:params})
}