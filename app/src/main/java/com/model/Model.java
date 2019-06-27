/**
 * com.model contains model classes.
 */

package com.model;

/**
 * Model class for setting title, subtitle and image.
 * @author Paweł Turoń
 */

public class Model {
    public static final int IMAGE_TYPE = 1;
    public String title, subtitle, image;
    public int type;

    public Model(int mType, String mTitle, String mSubtitle, String mImage){
        this.title = mTitle;
        this.subtitle = mSubtitle;
        this.type = mType;
        this.image = mImage;
    }
}
