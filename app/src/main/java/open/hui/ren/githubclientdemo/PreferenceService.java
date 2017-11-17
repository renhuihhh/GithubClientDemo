package open.hui.ren.githubclientdemo;

import com.baoyz.treasure.Preferences;

/**
 * @author renhui
 * @date 16-9-26
 * @desc 简化Sharepreference的serviec类
 */
@Preferences(edit = Preferences.Edit.COMMIT)
public interface PreferenceService {
    String getUsername();

    void setUsername(String username);

    String getToken();

    void setToken(String token);

    String getUserLoginAccount();

    void setLoginAccount(String account);

    String getBasicCredential();

    void setBasicCredential(String basicCredential);
}
