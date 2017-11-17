package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author renhui
 * @date 16-9-25
 * @desc OAuthResult实体类，这个类对应的是hithub个人授权许可列表的item，一般很少用
 */

public class OAuthResult implements Serializable {
    /**
     * scopes : ["repo","user"]
     * token : 5199831f4dd3b79e7c5b7e0ebe75d67aa66e79d4
     * updated_at : 2012-11-14T14:04:24Z
     * url : https://api.github.com/authorizations/2
     * app : {"url":"https://developer.github.com/v3/oauth/#oauth-authorizations-api","name":"GitHub API"}
     * created_at : 2012-11-14T14:04:24Z
     * note_url : null
     * id : 2
     * note : getting-started
     */

    @SerializedName("token")
    public String       token;
    @SerializedName("updated_at")
    public String       updatedAt;
    @SerializedName("url")
    public String       url;
    /**
     * url : https://developer.github.com/v3/oauth/#oauth-authorizations-api
     * name : GitHub API
     */

    @SerializedName("app")
    public AppBean      app;
    @SerializedName("created_at")
    public String       createdAt;
    @SerializedName("note_url")
    public Object       noteUrl;
    @SerializedName("id")
    public int          id;
    @SerializedName("note")
    public String       note;
    @SerializedName("scopes")
    public List<String> scopes;

    public static class AppBean {
        @SerializedName("url")
        public String url;
        @SerializedName("name")
        public String name;
    }
}
