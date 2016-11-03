package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-11
 * @desc comment实体类
 */
public class Comment implements Serializable {

    /**
     * url : https://api.github.com/repos/evancohen/smart-mirror/issues/comments/233517508
     * html_url : https://github.com/evancohen/smart-mirror/issues/32#issuecomment-233517508
     * issue_url : https://api.github.com/repos/evancohen/smart-mirror/issues/32
     * id : 233517508
     * user : {"login":"renhuihhh","id":17830234,"avatar_url":"https://avatars.githubusercontent.com/u/17830234?v=3","gravatar_id":"","url":"https://api.github.com/users/renhuihhh","html_url":"https://github.com/renhuihhh","followers_url":"https://api.github.com/users/renhuihhh/followers","following_url":"https://api.github.com/users/renhuihhh/following{/other_user}","gists_url":"https://api.github.com/users/renhuihhh/gists{/gist_id}","starred_url":"https://api.github.com/users/renhuihhh/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/renhuihhh/subscriptions","organizations_url":"https://api.github.com/users/renhuihhh/orgs","repos_url":"https://api.github.com/users/renhuihhh/repos","events_url":"https://api.github.com/users/renhuihhh/events{/privacy}","received_events_url":"https://api.github.com/users/renhuihhh/received_events","type":"User","site_admin":false}
     * created_at : 2016-07-19T02:59:12Z
     * updated_at : 2016-07-19T02:59:12Z
     * body :  [https://www.npmjs.com/package/electron-prebuilt](url) look here
     */
    @SerializedName("url")
    public String   url;
    @SerializedName("html_url")
    public String   htmlUrl;
    @SerializedName("issue_url")
    public String   issueUrl;
    @SerializedName("id")
    public int      id;
    /**
     * login : renhuihhh
     * id : 17830234
     * avatar_url : https://avatars.githubusercontent.com/u/17830234?v=3
     * gravatar_id :
     * url : https://api.github.com/users/renhuihhh
     * html_url : https://github.com/renhuihhh
     * followers_url : https://api.github.com/users/renhuihhh/followers
     * following_url : https://api.github.com/users/renhuihhh/following{/other_user}
     * gists_url : https://api.github.com/users/renhuihhh/gists{/gist_id}
     * starred_url : https://api.github.com/users/renhuihhh/starred{/owner}{/repo}
     * subscriptions_url : https://api.github.com/users/renhuihhh/subscriptions
     * organizations_url : https://api.github.com/users/renhuihhh/orgs
     * repos_url : https://api.github.com/users/renhuihhh/repos
     * events_url : https://api.github.com/users/renhuihhh/events{/privacy}
     * received_events_url : https://api.github.com/users/renhuihhh/received_events
     * type : User
     * site_admin : false
     */

    @SerializedName("user")
    public UserBean user;
    @SerializedName("created_at")
    public String   createdAt;
    @SerializedName("updated_at")
    public String   updatedAt;
    @SerializedName("body")
    public String   body;

    public static class UserBean {
        @SerializedName("login")
        public String  login;
        @SerializedName("id")
        public int     id;
        @SerializedName("avatar_url")
        public String  avatarUrl;
        @SerializedName("gravatar_id")
        public String  gravatarId;
        @SerializedName("url")
        public String  url;
        @SerializedName("html_url")
        public String  htmlUrl;
        @SerializedName("followers_url")
        public String  followersUrl;
        @SerializedName("following_url")
        public String  followingUrl;
        @SerializedName("gists_url")
        public String  gistsUrl;
        @SerializedName("starred_url")
        public String  starredUrl;
        @SerializedName("subscriptions_url")
        public String  subscriptionsUrl;
        @SerializedName("organizations_url")
        public String  organizationsUrl;
        @SerializedName("repos_url")
        public String  reposUrl;
        @SerializedName("events_url")
        public String  eventsUrl;
        @SerializedName("received_events_url")
        public String  receivedEventsUrl;
        @SerializedName("type")
        public String  type;
        @SerializedName("site_admin")
        public boolean siteAdmin;
    }
}
