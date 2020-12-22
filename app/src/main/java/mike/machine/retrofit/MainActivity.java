package mike.machine.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import mike.machine.retrofit.model.APIService;
import mike.machine.retrofit.model.ApiUtils;
import mike.machine.retrofit.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText titleEt = (EditText)findViewById(R.id.et_title);
        final EditText bodyEt = (EditText)findViewById(R.id.et_body);
        Button submitBtn = (Button)findViewById(R.id.btn_submit);
        mResponseTv = (TextView)findViewById(R.id.tv_response);

        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)){
                    sendPost(title,body);
                }
            }
        });
    }
/*
    public void sendPost(String title, String body){

        mAPIService.savePost(title,body,1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {
                        showResponse("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showResponse(e.toString());
                    }

                    @Override
                    public void onNext(Post post) {
                        showResponse(post.toString());

                    }
                });

    }
*/

    public void sendPost(String title, String body){

        mAPIService.savePost(title, body,1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                showResponse(response.body().toString());
                Log.i("MainActivity", "post submitted to API." + response.body().toString());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e("MainActivity", "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response){
        if (mResponseTv.getVisibility() == View.GONE){
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
}