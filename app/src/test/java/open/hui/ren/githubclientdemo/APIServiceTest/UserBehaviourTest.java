package open.hui.ren.githubclientdemo.APIServiceTest;


import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Credentials;
import open.hui.ren.githubclientdemo.APIServiceTestBase;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.modules.NetModuleTest;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo.APIServiceTest
 */

public class UserBehaviourTest extends APIServiceTestBase {
    String username;
    String password;

    @Override
    public void start() {
        username = "renhuihhh";
        password = "198943371hhh";
    }

    @Override
    public void end() {

    }

    @Test
    public void testLogin() {
        netModuleTest = new NetModuleTest(mContext, "https://api.github.com", false);
        String         credential = Credentials.basic(username, password);
        Call<UserInfo> call       = netModuleTest.getApiServiceNeedToken().getUserInfoByAuthorization(credential);
        Response<UserInfo> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("network not available");
        }
        Assert.assertNotNull(response.body());
    }

    @Test
    public void testGetToken(){

    }
}
