import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//列表查询
export const articleCategoryListService7 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/post/selectAll')
}

//添加
export const articleCategoryAddService7 = (categoryData)=>{
    return request.post('/post/add',categoryData)
}

//修改
export const articleCategoryUpdateService7 = (categoryData1)=>{
   return  request.put('/post/update',categoryData1)
}

//删除
export const articleCategoryDeleteService7 = (id)=>{
    return request.delete('/post/delete/'+id)
}

//列表查询
export const articleListService7 = (params)=>{
   return  request.get('/post',{params:params})
}