package open.hui.ren.githubclientdemo.apiservices;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author renhui
 * @date 16-10-25
 * @desc 获取followers列表的apiservice类
 */

public interface FollowersAPIService {
    /**
     * 获取当前登陆用户的 followers
     */
    @GET("/user/followers")
    Call<ArrayList<UserInfo>> getFollowers();

    /**
     * 获取 followers 列表
     */
    @GET("/users/{username}/followers")
    Call<ArrayList<UserInfo>> getUserFollowers(@Path("username") String userName);
}
