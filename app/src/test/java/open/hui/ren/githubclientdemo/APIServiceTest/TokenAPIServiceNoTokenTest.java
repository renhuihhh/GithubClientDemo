package open.hui.ren.githubclientdemo.APIServiceTest;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import open.hui.ren.githubclientdemo.APIServiceTestBase;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.modules.NetModuleTest;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author renhui
 * @date 16-9-25
 * @desc open.hui.ren.githubclientdemo.APIServiceTest
 */
@Ignore
public class TokenAPIServiceNoTokenTest extends APIServiceTestBase {
    public static final String S_TOKEN_STR = "token 664a70a85fc8801a9fee63ca74a44687f08871bd";
    private UserInfo userInfo;


    @Override
    public void start() {
        netModuleTest = new NetModuleTest(mContext, "https://api.github.com", false);
        Response<UserInfo> response = null;
        Call<UserInfo>        call     = netModuleTest.getApiServiceNeedToken().getUserInfoByAuthorization(S_TOKEN_STR);
        try {
            response = call.execute();
        } catch (IOException e) {
            Assert.fail("network not available");
        }
        userInfo = response.body();
    }

    @Override
    public void end() {

    }

    @Test
    public void testUserInfoNotNull(){
        Assert.assertNotNull(userInfo);
    }
}
