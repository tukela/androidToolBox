package com.honglei.toolbox.service;

import com.honglei.toolbox.service.impl.ImageSDCardCache;

import java.io.Serializable;

/**
 * File name rule, used when saving images in {@link ImageSDCardCache}
 * 
 *  2012-7-6
 */
public interface FileNameRule extends Serializable {

    /**
     * get file name, include suffix, it's optional to include folder.
     * 
     * @param imageUrl the url of image
     * @return
     */
    public String getFileName(String imageUrl);
}
