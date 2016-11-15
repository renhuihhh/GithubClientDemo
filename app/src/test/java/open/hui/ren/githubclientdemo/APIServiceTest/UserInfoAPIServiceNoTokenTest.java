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
 * @date 16-9-23
 * @desc 使用renhuihhh的github账号进行接口测试
 */
@Ignore
public class UserInfoAPIServiceNoTokenTest extends APIServiceTestBase {
    private UserInfo      userInfo;

    @Override
    public void start() {
        netModuleTest = new NetModuleTest(mContext, "https://api.github.com", false);
        Response<UserInfo> response = null;
        Call<UserInfo>     call     = netModuleTest.getApiServiceNoAuth().getUserInfo("renhuihhh");
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

    /**
     * 测试用户的bean对象是否not null
     */
    @Test
    public void testUserInfoApi() {
        Assert.assertNotNull(userInfo);
    }

    /**
     * 测试用户的id数据是否为预期结果
     */
    @Test
    public void testUserIdIsRight() {
        Assert.assertEquals(17830234, userInfo.id);
    }
}
