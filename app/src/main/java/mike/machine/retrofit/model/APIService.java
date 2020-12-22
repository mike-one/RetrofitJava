package mike.machine.retrofit.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {
    @POST("/posts")  //Esto se agregará a la URL
    @FormUrlEncoded // Esto indicará que la petición tendrá su tipo MIME y UTF8
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);

}
