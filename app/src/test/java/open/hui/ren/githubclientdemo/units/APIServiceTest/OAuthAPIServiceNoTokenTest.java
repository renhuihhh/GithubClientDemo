package open.hui.ren.githubclientdemo.units.APIServiceTest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import open.hui.ren.githubclientdemo.APIServiceTestBase;
import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.entities.OAuthResult;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.units.modules.NetModuleTest;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author renhui
 * @date 16-9-25
 * @desc open.hui.ren.githubclientdemo
 */
@Ignore
public class OAuthAPIServiceNoTokenTest extends APIServiceTestBase {
    private String requestData = new String(ConstConfig.GIT_HUB_SCOPE);
    private String      credential;
    private OAuthResult oAuthResult;
    private UserInfo    userInfo;

    private ArrayList<OAuthResult> oAuthResults;

    String username;
    String password;


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    /**
     * 测试在验证情况下正常获取'认证'列表
     */
    @Test
    public void testOAuthListNotEmpty() {//认证列表的获取不需要token需要username password验证
        netModuleTest = new NetModuleTest(mContext, "https://api.github.com", true);
        Response<ArrayList<OAuthResult>> response = null;
        Call<ArrayList<OAuthResult>>     call     = netModuleTest.getUserInfoApiService().getOAuthList();
        try {
            response = call.execute();
        } catch (IOException e) {
            Assert.fail("network not available");
            e.printStackTrace();
        }
        oAuthResults = response.body();
        Assert.assertNotNull(oAuthResults);
    }

    /**
     * 测试useranme passwrod验证下的授权,这个接口只能使用username password方式验证
     */
    @Test
    public void testGrantsListNotEmpty() {
//        String token = "token c85e09497c232ebad8fa5cc58e3d175bd0e29854";
    }

    /**
     * 测试在username password验证下生成token,如果retuen code是 422说明已经存在
     * 如果
     */
    @Test
    public void testOAuthResultNotNull() {
        netModuleTest = new NetModuleTest(mContext, "https://api.github.com", true);
        Gson                  gson       = new Gson();
        JsonObject            jsonObject = gson.fromJson(requestData, JsonObject.class);
        Response<OAuthResult> response   = null;
        Call<OAuthResult>     call       = netModuleTest.getUserInfoApiService().genOAuthToken(jsonObject);
        try {
            response = call.execute();
        } catch (IOException e) {
            Assert.fail("network not available");
        }
        oAuthResult = response.body();
        Assert.assertNotNull(oAuthResult);
    }
}
