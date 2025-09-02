import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//文章分类列表查询
export const articleCategoryListService1 = ()=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据,都不需要.value
    //return request.get('/category',{headers:{'Authorization':tokenStore.token}})
    return request.get('/user/userInfo')
}

//添加
export const articleCategoryAddService1 = (categoryData)=>{
    return request.post('/user/addUser',categoryData)
}

//文章分类修改
export const articleCategoryUpdateService1 = (categoryData)=>{
   return  request.put('/user/users',categoryData)
}

//文章分类删除
export const articleCategoryDeleteService1 = (id)=>{
    return request.delete('/user/delete/'+id)
}


export const articleAddService2 = (uid)=>{
    return request.get('/user/selects/'+uid);

}