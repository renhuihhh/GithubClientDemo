package open.hui.ren.githubclientdemo.apiservices;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.apiservices
 */

public interface FollowingAPIService {

    /**
     * 获取当前登录用户的 following 列表
     */
    @GET("/user/following")
    Call<ArrayList<UserInfo>> getFollowings();


    /**
     * 获取 following 列表
     */
    @GET("/users/{username}/following")
    Call<ArrayList<UserInfo>> getUserFollowings(@Path("username") String userName);

}
