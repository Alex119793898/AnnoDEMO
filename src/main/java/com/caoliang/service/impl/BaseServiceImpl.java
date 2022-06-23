package com.caoliang.service.impl;

import com.caoliang.anno.ColumnAnno;
import com.caoliang.service.BaseService;
import com.caoliang.util.DBUtil;
import com.caoliang.util.GenSQLProvider;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    private Connection connection = DBUtil.getConnection();

    private GenSQLProvider sqlProvider = new GenSQLProvider();

    public List<T> queryData(Object obj) throws Exception {

        ArrayList<T> result = new ArrayList<>();

        //获得该类带有泛型的 由Type表示的 父类
        Type genericSuperclass = this.getClass().getGenericSuperclass();

        //强制转换为 ParameterizedType 参数化类型，即具有的所有泛型
        ParameterizedType parameterizedType =(ParameterizedType)genericSuperclass;

        //获取范型：这里范型只有一个
        Class<T> clazz = (Class<T>)parameterizedType.getActualTypeArguments()[0];

        String sql = sqlProvider.genQuerySql(obj);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            Object instance = clazz.newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                ColumnAnno columnAnno = field.getAnnotation(ColumnAnno.class);
                String columnKey;
                Object fieldRes;

                //如果有添加注解就使用 注解的 columnName 值，否则用 成员变量名
                if(null != columnAnno){
                    columnKey = columnAnno.columnName();
                }else{
                    columnKey = field.getName();
                }

                try{
                    fieldRes = resultSet.getObject(columnKey,field.getType());
                }catch (SQLException e){
                    continue;
                }

                String setterName = "set" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                //等价于instance.setxxx(fieldRes)
                clazz.getMethod(setterName,field.getType()).invoke(instance,fieldRes);
            }

            result.add((T)instance);
        }

        return result;
    }

    public int updateData(Object obj) throws Exception {
        String sql = sqlProvider.genUpdateSql(obj);
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(sql);
        return i;
    }

    public int deleteData(Object obj) throws Exception {
        String sql = sqlProvider.genDeleteSql(obj);
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(sql);
        return i;
    }
}
