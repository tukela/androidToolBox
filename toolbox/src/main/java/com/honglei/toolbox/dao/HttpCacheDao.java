package com.honglei.toolbox.dao;


import com.honglei.toolbox.entity.HttpResponse;

import java.util.Map;

/**
 * HttpCacheDao
 * 
 *  2013-11-04
 */
public interface HttpCacheDao {

    /**
     * insert HttpResponse
     * 
     * @param httpResponse
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insertHttpResponse(HttpResponse httpResponse);

    /**
     * get HttpResponse by url
     * 
     * @param url
     * @return
     */
    public HttpResponse getHttpResponse(String url);

    /**
     * get HttpResponses by type
     * 
     * @param type
     * @return
     */
    public Map<String, HttpResponse> getHttpResponsesByType(int type);

    /**
     * delete all http response
     * 
     * @return
     */
    public int deleteAllHttpResponse();
}
