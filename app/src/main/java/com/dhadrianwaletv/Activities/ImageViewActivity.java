package com.dhadrianwaletv.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridView;
import com.dhadrianwaletv.Adapters.ImageGridAdapter;
import com.dhadrianwaletv.ModalClasses.PicturesModal;
import com.dhadrianwaletv.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ImageViewActivity extends AppCompatActivity {

    ImageGridAdapter imageGridAdapter;
    GridView gridView;
    ArrayList<PicturesModal> arrayList= new ArrayList<>();
    PicturesModal picturesModal;

    @Override
    public boolean onKeyUp( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            super.onBackPressed();
            return true;
        }
        return super.onKeyUp( keyCode, event );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        gridView = findViewById(R.id.gridview);
        //arrayList = (ArrayList<PicturesModal>)getIntent().getSerializableExtra("imageArray");
        imageGridAdapter = new ImageGridAdapter(this,arrayList);
        gridView.setAdapter(imageGridAdapter);

        try {
            getImages();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getImages() throws JSONException {

        JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("imageArray"));

        for(int i = 0 ;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject jsonObject1 = jsonObject.getJSONObject("images");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("full");
                picturesModal = new PicturesModal(jsonObject2.getString("url"));
                arrayList.add(picturesModal);
        }
        imageGridAdapter.notifyDataSetChanged();
    }
}