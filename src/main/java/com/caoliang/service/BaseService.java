package com.caoliang.service;

import java.util.List;

public interface BaseService <T> {


    List<T> queryData(Object obj);

    int updateData(Object obj);

    int deleteData(Object obj);

}
