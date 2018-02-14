package com.emejia.list_topics.API;

import android.content.Context;
import android.util.Log;

import com.emejia.list_topics.utils.JsonMapperUtility;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by emejia on 08/03/2017.
 */

public class APIClient {

   //private static LISTTOPICSAPIInterface listtopicsapiInterface;
    // Create a client to perform networking
    static AsyncHttpClient client;
           //= new AsyncHttpClient();

    private static String END_POINT = "https://www.reddit.com";


    public static AsyncHttpClient getAPIInterface(Context contxt) throws MalformedURLException {
        if (client == null) {

            JacksonConverter converter = new JacksonConverter(JsonMapperUtility.getObjectMapper());

            OkHttpClient okHttpClient = new OkHttpClient();
            //okHttpClient.networkInterceptors().add(new CustomInterceptor());
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setConverter(converter)
                    .setEndpoint(END_POINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new RestAdapter.Log() {
                        public void log(String msg) {
                            Log.i("RETROFIT MSG", msg);
                        }
                    }).setClient(new OkClient(okHttpClient))
                    .build();
            client = restAdapter.create(AsyncHttpClient.class);
        }
        return client;
    }


    public interface AsyncHttpClient {

        @Headers({
                "Accept: application/json",
                "Content-type: application/json"
        })
        @GET("/reddits.json")
        void getListTopics( JsonHttpResponseHandler cb);

    }

}
