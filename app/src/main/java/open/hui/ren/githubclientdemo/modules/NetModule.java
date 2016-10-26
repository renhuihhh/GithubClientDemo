package open.hui.ren.githubclientdemo.modules;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.apiservices.UserInfoAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author renhui
 * @date 16-9-22
 * @desc app网络访问相关业务模组加载
 */

@Module
public class NetModule {
    public static final int TIME_OUT_S = 30;

    String  mBaseUrl;
    Context mContext;

    public NetModule(Context context, String baseUrl) {
        this.mBaseUrl = baseUrl;
        this.mContext = context;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int   cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache     = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, PreferenceService sharedPreferences) {
        Authenticator authenticator = null;
        final String credential = sharedPreferences.getBasicCredential();
//        final String credential = ConstConfig.S_APP_TOKEN;
        if (!TextUtils.isEmpty(credential)) {
            authenticator = new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    System.out.println("Authenticating for response: " + response);
                    System.out.println("Challenges: " + response.challenges());
                    return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
                }
            };
        }
        //final String credential = "token 664a70a85fc8801a9fee63ca74a44687f08871bd";


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (authenticator != null) {
            builder.authenticator(authenticator);
        }
        OkHttpClient client = builder
            .connectTimeout(TIME_OUT_S, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_S, TimeUnit.SECONDS)
            .cache(cache)
            .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build();
        return retrofit;
    }

    @Provides
    @Singleton
    UserInfoAPIService provideAPIService(Retrofit retrofit) {
        return retrofit.create(UserInfoAPIService.class);
    }

    public String getmBaseUrl() {
        return mBaseUrl;
    }

    public Context getmContext() {
        return mContext;
    }
}