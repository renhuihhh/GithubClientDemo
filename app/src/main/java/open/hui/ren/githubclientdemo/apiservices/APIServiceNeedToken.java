package open.hui.ren.githubclientdemo.apiservices;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo.apiservices
 */

public interface APIServiceNeedToken {
    /**
     * @param authorization 通过token或者username password获取用户的信息
     */
    @GET("/user")
    Call<UserInfo> getUserInfoByAuthorization(@Header("Authorization") String authorization);
}
