package open.hui.ren.githubclientdemo.apiservices;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo.apiservices
 */

public interface APIServiceNoAuth {
    /**
     * 无任何校验即可查看user名字对应的github用户的信息, 仅限每月60次, 校验后无限制
     * 所以, 建议使用username password的header
     *
     * @param user
     */
    @GET("/users/{username}")
    Call<UserInfo> getUserInfo(@Path("username") String user);
}
