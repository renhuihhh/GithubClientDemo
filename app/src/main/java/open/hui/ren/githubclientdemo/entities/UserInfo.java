package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-9-23
 * @desc github 用户信息对象
 */

public class UserInfo implements Serializable{

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
     * name : Hui Ren
     * company : HaierUbic
     * blog : null
     * location : Xi'an China
     * email : null
     * hireable : null
     * bio : null
     * public_repos : 2
     * public_gists : 0
     * followers : 0
     * following : 2
     * created_at : 2016-03-14T14:20:25Z
     * updated_at : 2016-09-14T06:57:30Z
     */
    @SerializedName("login")
    public String login;
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
    @SerializedName("name")
    public String  name;
    @SerializedName("company")
    public String  company;
    @SerializedName("blog")
    public Object  blog;
    @SerializedName("location")
    public String  location;
    @SerializedName("email")
    public Object  email;
    @SerializedName("hireable")
    public Object  hireable;
    @SerializedName("bio")
    public Object  bio;
    @SerializedName("public_repos")
    public int     publicRepos;
    @SerializedName("public_gists")
    public int     publicGists;
    @SerializedName("followers")
    public int     followers;
    @SerializedName("following")
    public int     following;
    @SerializedName("created_at")
    public String  createdAt;
    @SerializedName("updated_at")
    public String  updatedAt;

    //
    @SerializedName("display_login")
    public String displayLogin;
}
