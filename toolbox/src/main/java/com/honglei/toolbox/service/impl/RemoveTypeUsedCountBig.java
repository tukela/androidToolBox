package com.honglei.toolbox.service.impl;

import com.honglei.toolbox.entity.CacheObject;
import com.honglei.toolbox.service.CacheFullRemoveType;

/**
 * Remove type when cache is full.<br/>
 * when cache is full, compare used count of object in cache, if is bigger remove it first.<br/>
 * 
 *  2011-12-26
 */
public class RemoveTypeUsedCountBig<T> implements CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(CacheObject<T> obj1, CacheObject<T> obj2) {
        return (obj2.getUsedCount() > obj1.getUsedCount()) ? 1
                : ((obj2.getUsedCount() == obj1.getUsedCount()) ? 0 : -1);
    }
}
