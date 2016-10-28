package open.hui.ren.githubclientdemo.apiservices;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.entities.OAuthResult;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author renhui
 * @date 16-9-22
 * @desc retrofit网络接口service
 */

public interface UserInfoAPIService {

    /**
     * 生成已校验用户对应的 token
     * 慎用
     * 无token, 使用Basic username:password的header
     *
     * @param json 生成校验用户token对应的配置文件, 描述token所具有的权限
     */
    @POST("/authorizations")
    Call<OAuthResult> genOAuthToken(@Body JsonObject json);

    /**
     * 获取认证 OAuth 列表
     * 无token, 使用Basic username:password的header
     */
    @GET("/authorizations")
    Call<ArrayList<OAuthResult>> getOAuthList();

    /**
     * 获取当前登陆用户 events 列表
     * */
    @GET("/user/events")
    Call<ArrayList<Event>> getEvents();

    /**
     * 获取当前登陆用户的 subscriptions 列表
     * */
    @GET("/user/subscriptions")
    Call<ArrayList<Repo>> getSubscriptions();


    ///***************************************************************************/



    /**
     * 获取 subscriptions 列表
     */
    @GET("/users/{username}/subscriptions")
    Call<ArrayList<Repo>> getUserSubscriptions(@Path("username") String userName);


    /**
     * 获取 events 列表
     */
    @GET("/users/{username}/events")
    Call<ArrayList<Event>> getUserEvents(@Path("username") String userName);




    ///##############################################################################/
    /**
     * 无任何校验即可查看user名字对应的github用户的信息, 仅限每月60次, 校验后无限制
     * 所以, 建议使用username password的header
     * @param user
     */
    @GET("/users/{username}")
    Call<UserInfo> getUserInfo(@Path("username") String user);
}
