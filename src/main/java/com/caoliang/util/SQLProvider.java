package com.caoliang.util;


import com.caoliang.anno.ColumnAnno;
import com.caoliang.anno.Table;
import com.caoliang.entity.OrderInfo;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

public class SQLProvider {

    // select xxx,xxx,xxx from table where xx=xx and xx=xx order by xxx xxx;
    public String genQuerySql(OrderInfo orderInfo) throws Exception{
        StringBuffer sql = new StringBuffer();
        StringBuffer selectSql = new StringBuffer("SELECT ");
        StringBuffer whereSql = new StringBuffer(" WHERE 1=1");

        Class<? extends OrderInfo> clazz = orderInfo.getClass();
        Table tableAnno = clazz.getAnnotation(Table.class);

        for (Field field : clazz.getDeclaredFields()) {
            ColumnAnno columnAnno = field.getAnnotation(ColumnAnno.class);

            if(null == columnAnno){
                continue;
            }

            selectSql.append(columnAnno.columnName()).append(",");

            String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Object fieldValue = orderInfo.getClass().getMethod(getterName).invoke(orderInfo);

            if(StringUtils.isEmpty(fieldValue)){
                continue;
            }

            //类型转换
            if(field.getType() == String.class){
                fieldValue = "'" + fieldValue + "'";
            }else if (field.getType() == int.class || field.getType() == Integer.class){
                //....
            }

            whereSql.append(" AND ").append(columnAnno.columnName()).append(" = ").append(fieldValue);
        }

        sql.append(selectSql.substring(0,selectSql.length() - 1)).append(" FROM ").append(tableAnno.tableName()).append(whereSql);

        if(!StringUtils.isEmpty(tableAnno.orderBy())){
            sql.append(" order by ").append(tableAnno.orderBy());
            if(!StringUtils.isEmpty(tableAnno.order())){
                sql.append(" " + tableAnno.order());
            }
        }

        return sql.toString();
    }

    // update table set xxx=xxx, xxx=xxx where pk = xxx
    public String genUpdateSql(OrderInfo orderInfo) throws Exception{

        StringBuffer sql = new StringBuffer("UPDATE ");
        StringBuffer setSql = new StringBuffer(" SET ");
        StringBuffer whereSql = new StringBuffer();

        Class<? extends OrderInfo> clazz = orderInfo.getClass();
        Table tableAnno = clazz.getAnnotation(Table.class);

        for (Field field : clazz.getDeclaredFields()) {
            ColumnAnno columnAnno = field.getAnnotation(ColumnAnno.class);

            String getterName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
            Object fieldValue = orderInfo.getClass().getMethod(getterName).invoke(orderInfo);

            if(null == columnAnno || StringUtils.isEmpty(fieldValue)){
                continue;
            }

            //类型转换
            if(field.getType() == String.class){
                fieldValue = "'" + fieldValue + "'";
            }else if (field.getType() == int.class || field.getType() == Integer.class){
                //....
            }

            if(StringUtils.isEmpty(columnAnno.isPK())){
                setSql.append(columnAnno.columnName()).append(" = ").append(fieldValue).append(",");
            }


            if(!StringUtils.isEmpty(columnAnno.isPK()) && StringUtils.isEmpty(whereSql.toString())){
                if(null == fieldValue){
                    throw new Exception("主键列不能为空");
                }

                whereSql.append(" WHERE ").append(columnAnno.columnName()).append(" = ").append(fieldValue);
            }
        }

        sql.append(tableAnno.tableName()).append(setSql.substring(0,setSql.length()-1)).append(whereSql);
        return sql.toString();
    }

    // delete from table where pk = xxx
    public String genDeleteSql(OrderInfo orderInfo) throws Exception {

        StringBuffer sql = new StringBuffer("delete from ");
        StringBuffer whereSql = new StringBuffer();

        Table tableAnno = orderInfo.getClass().getAnnotation(Table.class);

        for (Field field : orderInfo.getClass().getDeclaredFields()) {
            ColumnAnno columnAnno = field.getAnnotation(ColumnAnno.class);

            if(!StringUtils.isEmpty(columnAnno.isPK()) && StringUtils.isEmpty(whereSql.toString())){

                String getterName = "get" + field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                Object fieldValue = orderInfo.getClass().getMethod(getterName).invoke(orderInfo);

                if(null == fieldValue){
                    throw new Exception("主键列不能为空");
                }

                //类型转换
                if(field.getType() == String.class){
                    fieldValue = "'" + fieldValue + "'";
                }else if (field.getType() == int.class || field.getType() == Integer.class){
                    //....
                }

                whereSql.append(" WHERE ").append(columnAnno.columnName()).append(" = ").append(fieldValue);
            }
        }

        if(StringUtils.isEmpty(whereSql)){
            throw new Exception("未设置主键，无法删除");
        }

        sql.append(tableAnno.tableName()).append(whereSql);
        return sql.toString();
    }
}
