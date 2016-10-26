package open.hui.ren.githubclientdemo;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo
 */

public class ConstConfig {
    public static final String S_COMMON_SHAREPREFERENCE_KEY = "share_preference_common";

    public static final String S_TOKEN_STR   = "authorization_token_str";
    public static final String S_BASIC_STR   = "authorization_username_password_str";
    public static final String GIT_HUB_SCOPE = "{\"scopes\": [\"repo\", \"user\",\"admin:org\",\"admin:public_key\"," +
        "\"admin:repo_hook\",\"admin:org_hook\",\"gist\",\"notifications\",\"user\",\"delete_repo\"]" +
        ", \"note\": \"getting-started\"}";

    public static final String S_USER_INFO = "user_info_obj";

    public static final String S_APP_TOKEN = "Authorization: token 49931685f0b8ea5ee39ce63efc8d09e22451fa2a";

    //signal
    public static final String S_BAD_PARAMS  = "bad parameters";
    public static final String S_INIT_PARAMS = "initial parameters";

    //acache
    public static final String S_FOLLOWERS  = "followers_list";
    public static final String S_FOLLOWINGS = "following_list";
    public static final String S_STARRED    = "starred_list";
    public static final String S_REPOES     = "repo_list";
}
