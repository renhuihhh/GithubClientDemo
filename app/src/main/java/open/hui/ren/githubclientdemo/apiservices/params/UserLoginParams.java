package open.hui.ren.githubclientdemo.apiservices.params;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.apiservices.params
 */

public class UserLoginParams {
    public String userName;
    public String passWord;

    public UserLoginParams(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
}
