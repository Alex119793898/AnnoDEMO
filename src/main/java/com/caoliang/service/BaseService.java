package com.caoliang.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService <T> {


    List<T> queryData(Object obj) throws Exception;

    int updateData(Object obj) throws Exception;

    int deleteData(Object obj) throws Exception;

}
