package open.hui.ren.githubclientdemo.apiservices;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.apiservices
 */

public interface StarsAPIService {
    /**
     * 获取当前登陆用户的  starred 列表
     */
    @GET("/user/starred")
    Call<ArrayList<Repo>> getStarredRepos();

    /**
     * 获取 started 列表
     */
    @GET("/users/{username}/starred")
    Call<ArrayList<Repo>> getUserStarred(@Path("username") String userName);

}
