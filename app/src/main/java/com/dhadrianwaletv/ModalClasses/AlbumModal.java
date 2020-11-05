package com.dhadrianwaletv.ModalClasses;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by khushdeep-android on 15/2/18.
 */

public class AlbumModal {
    public String img_url,title;
    public ArrayList<PicturesModal> picturesModals;
    public JSONArray jsonArray;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<PicturesModal> getPicturesModals(){
        return picturesModals;
    }

    public void setPicturesModals(ArrayList<PicturesModal> picturesModals){this.picturesModals = picturesModals;}

//    public AlbumModal(String title, String img_url,ArrayList<PicturesModal> picturesModals)
//    {
//        this.img_url = img_url;
//        this.title = title;
//        this.picturesModals = picturesModals;
//    }

    public AlbumModal(String title, String img_url,JSONArray jsonArray)
    {
        this.img_url = img_url;
        this.title = title;
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray(){return jsonArray;}

    public void setJsonArray(JSONArray jsonArray){this.jsonArray = jsonArray;}
}
