package open.hui.ren.githubclientdemo.modules;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import open.hui.ren.githubclientdemo.apiservices.APIServiceNeedToken;
import open.hui.ren.githubclientdemo.apiservices.APIServiceNoAuth;
import open.hui.ren.githubclientdemo.apiservices.APIServiceUnderBasicCredential;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author renhui
 * @date 16-9-23
 * @desc open.hui.ren.githubclientdemo.component
 */

public class NetModuleTest {

    public static final int TIME_OUT_S = 30;
    Cache                          cache;
    GsonBuilder                    gsonBuilder;
    OkHttpClient                   client;
    Retrofit                       retrofit;
    APIServiceUnderBasicCredential mApiServiceUnderBasicCredential;
    APIServiceNeedToken            apiServiceNeedToken;
    APIServiceNoAuth               apiServiceNoAuth;

    String  mBaseUrl;
    Context mContext;

    public NetModuleTest(Context context, String baseUrl, boolean isAuthorization) {
        this.mBaseUrl = baseUrl;
        this.mContext = context;
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        cache = new Cache(context.getCacheDir(), cacheSize);
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);


        Authenticator authenticator = null;
        String        credential    = "";
        if (isAuthorization) {
            credential = Credentials.basic("renhuihhh", "198943371hhh");
        } else {
            credential = "";
        }
        if (!credential.isEmpty()) {
            final String finalCredential = credential;
            authenticator = new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    System.out.println("Authenticating for response: " + response);
                    System.out.println("Challenges: " + response.challenges());
                    return response.request().newBuilder()
                        .header("Authorization", finalCredential)
                        .build();
                }
            };
        }
        //final String credential = "token 664a70a85fc8801a9fee63ca74a44687f08871bd";
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (authenticator != null) {
            builder.authenticator(authenticator);
        }
        client = builder.connectTimeout(TIME_OUT_S, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_S, TimeUnit.SECONDS)
            .cache(cache)
            .build();
        retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .baseUrl(baseUrl)
            .client(client)
            .build();
    }


    public APIServiceUnderBasicCredential getApiServiceUnderBasicCredential() {
        mApiServiceUnderBasicCredential = retrofit.create(APIServiceUnderBasicCredential.class);
        return mApiServiceUnderBasicCredential;
    }

    public APIServiceNeedToken getApiServiceNeedToken() {
        apiServiceNeedToken = retrofit.create(APIServiceNeedToken.class);
        return apiServiceNeedToken;
    }

    public APIServiceNoAuth getApiServiceNoAuth() {
        apiServiceNoAuth = retrofit.create(APIServiceNoAuth.class);
        return apiServiceNoAuth;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
