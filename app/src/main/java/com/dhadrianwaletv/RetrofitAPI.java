package com.dhadrianwaletv;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by khushdeep-android on 15/2/18.
 */

public interface RetrofitAPI {

    @GET("search?key=AIzaSyDM8I406FbGnaYATQWRQdYNJRMxAc9cXSc&channelId=UCswIOlMY2_DT05glwBsxZyg&part=snippet,id&order=date&maxResults=20")
    Call<JsonElement> getYouTubeVideos();

    @GET("playlists?key=AIzaSyDM8I406FbGnaYATQWRQdYNJRMxAc9cXSc&channelId=UCswIOlMY2_DT05glwBsxZyg&part=snippet,id,contentDetails&maxResults=20")
    Call<JsonElement> getYouTubePlaylist();

    @GET("playlistItems?key=AIzaSyDM8I406FbGnaYATQWRQdYNJRMxAc9cXSc&channelId=UCswIOlMY2_DT05glwBsxZyg&part=snippet,id&maxResults=20")
    Call<JsonElement> getYouTubePlaylistNext(@Query("playlistId") String playlistId);

    @GET("search?key=AIzaSyDM8I406FbGnaYATQWRQdYNJRMxAc9cXSc&channelId=UCswIOlMY2_DT05glwBsxZyg&part=snippet,id&order=date&maxResults=20")
    Call<JsonElement> getYouTubeVideosNext(@Query("pageToken") String pageToken);

    @GET("search?key=AIzaSyDM8I406FbGnaYATQWRQdYNJRMxAc9cXSc&channelId=UCswIOlMY2_DT05glwBsxZyg&part=snippet,id&order=date&maxResults=20&eventType=live&type=video")
    Call<JsonElement> getYouTubeLive();

    @GET("get_category_index/?parent=18")
    Call<JsonElement> getGalleryList();

    @GET("core/get_category_posts/?")
    Call<JsonElement> getAlbumList(@Query("category_id")
                                           String categoryt_id,@Query("&include") String attachment);
}
