import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/userPts/selectAll')
}

//添加
export const articleCategoryAddService = (categoryData)=>{
    return request.post('/userPts/add',categoryData)
}

//修改
export const articleCategoryUpdateService = (categoryData1)=>{
   return  request.put('/userPts/update',categoryData1)
}

//删除
export const articleCategoryDeleteService = (id)=>{
    return request.delete('/userPts/delete/'+id)
}

//列表查询
export const articleListService = (params)=>{
   return  request.get('/article',{params:params})
}
