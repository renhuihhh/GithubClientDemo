package open.hui.ren.githubclientdemo.apiservices;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author renhui
 * @date 16-10-20
 * @desc open.hui.ren.githubclientdemo.apiservices
 */

public interface RepositoriesAPIService {
    /**
     * 获取当前登陆用户的 repos 列表
     */
    @GET("/user/repos")
    Call<ArrayList<Repo>> getRepos();

    /**
     * 获取 repo 列表
     *
     * @param userName  username
     * @param type      Can be one of all, owner, member. Default: owner
     * @param sort      Can be one of created, updated, pushed, full_name. Default: full_name
     * @param direction Can be one of asc or desc. Default: when using full_name: asc, otherwise desc
     */
    @GET("/users/{username}/repos")
    Call<ArrayList<Repo>> getUserRepos(@Path("username") String userName, @Query("type") String type, @Query("sort")
        String sort, @Query("direction") String direction);

}
