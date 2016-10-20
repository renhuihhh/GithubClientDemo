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

public interface APIServiceUnderBasicCredential {

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

    /**
     * 获取当前登陆用户的  starred 列表
     * */
    @GET("/user/starred")
    Call<ArrayList<Repo>> getStarredRepos();

    /**
     * 获取当前登录用户的 following 列表
     * */
    @GET("/user/following")
    Call<ArrayList<UserInfo>> getFollowings();

    /**
     * 获取当前登陆用户的 followers
     */
    @GET("/user/followers")
    Call<ArrayList<UserInfo>> getFollowers();

    /**
     * 获取当前登陆用户的 repos 列表
     */
    @GET("/user/repos")
    Call<ArrayList<Repo>> getRepos();

    ///***************************************************************************/

    /**
     * 获取 started 列表
     */
    @GET("/users/starred")
    Call<ArrayList<Repo>> getUserStarred(@Path("username") String userName);

    /**
     * 获取 followers 列表
     */
    @GET("/users/{username}/followers")
    Call<ArrayList<UserInfo>> getUserFollowers(@Path("useraname") String userName);

    /**
     * 获取 following 列表
     */
    @GET("/users/{username}/following")
    Call<ArrayList<UserInfo>> getUserFollowings(@Path("username") String userName);

    /**
     * 获取 subscriptions 列表
     */
    @GET("/users/{username}/subscriptions")
    Call<ArrayList<Repo>> getUserSubscriptions(@Path("username") String userName);

    /**
     * 获取 repo 列表
     */
    @GET("/users/{username}/repos")
    Call<ArrayList<Repo>> getUserRepos(@Path("username") String userName);

    /**
     * 获取 events 列表
     */
    @GET("/users/{username}/events")
    Call<ArrayList<Event>> getUserEvents(@Path("username") String userName);
}
