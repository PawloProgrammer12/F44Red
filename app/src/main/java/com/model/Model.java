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
    public String title, image;
    public int type;

    public Model(int mType, String mTitle, String mImage){
        this.title = mTitle;
        this.type = mType;
        this.image = mImage;
    }
}
