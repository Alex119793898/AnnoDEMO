package com.caoliang.service.impl;

import com.caoliang.service.BaseService;

import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {

    public List<T> queryData(Object obj) {
        return null;
    }

    public int updateData(Object obj) {
        return 0;
    }

    public int deleteData(Object obj) {
        return 0;
    }
}
