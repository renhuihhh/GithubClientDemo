package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author renhui
 * @date 16-10-11
 * @desc issue实体类
 */
public class Issue implements Serializable {
    /**
     * url : https://api.github.com/repos/evancohen/smart-mirror/issues/32
     * repository_url : https://api.github.com/repos/evancohen/smart-mirror
     * labels_url : https://api.github.com/repos/evancohen/smart-mirror/issues/32/labels{/name}
     * comments_url : https://api.github.com/repos/evancohen/smart-mirror/issues/32/comments
     * events_url : https://api.github.com/repos/evancohen/smart-mirror/issues/32/events
     * html_url : https://github.com/evancohen/smart-mirror/issues/32
     * id : 125851863
     * number : 32
     * title : electron not found/install issues
     * user : {"login":"markparise","id":16629918,"avatar_url":"https://avatars.githubusercontent.com/u/16629918?v=3","gravatar_id":"","url":"https://api.github.com/users/markparise","html_url":"https://github.com/markparise","followers_url":"https://api.github.com/users/markparise/followers","following_url":"https://api.github.com/users/markparise/following{/other_user}","gists_url":"https://api.github.com/users/markparise/gists{/gist_id}","starred_url":"https://api.github.com/users/markparise/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/markparise/subscriptions","organizations_url":"https://api.github.com/users/markparise/orgs","repos_url":"https://api.github.com/users/markparise/repos","events_url":"https://api.github.com/users/markparise/events{/privacy}","received_events_url":"https://api.github.com/users/markparise/received_events","type":"User","site_admin":false}
     * labels : []
     * state : closed
     * locked : false
     * assignee : null
     * assignees : []
     * milestone : null
     * comments : 11
     * created_at : 2016-01-10T23:02:19Z
     * updated_at : 2016-07-19T02:59:12Z
     * closed_at : 2016-01-11T18:58:33Z
     * body : Hey evan, So I got npm installed on my pi, but when I ran the "npm start" I get a whole slew of errors the first of which reads "sh: 1: electron: not found"
     * <p>
     * The rest of the error prompt is as follows:
     * npm ERR! smart-mirror@0.0.1 start: 'electron main,js'
     * npm ERR! Exit status 127
     * npm ERR!
     * npm ERR! Failed at the smart-mirror@0.0.1 start script.
     * npm ERR! This is most likely a problem with the smart-mirror package,
     * npm ERR! not with npm itself.
     * npm ERR! Error: EACCES, open ' npm-debug.log'
     * npm ERR! { [Error: EACCES, open 'npm-debug.log'] errno: 3, code: 'EACCES' : 'npm-debug.log; }
     * <p>
     * npm ERR! System Linux 4.1.7-v7+
     * npm ERR! command "/usr/bin/nodejs" "/usr/bin/npm" "start"
     * npm ERR! cwd /git/smart-mirror
     * npm ERR! node -v v0.10.29
     * npm ERR! cod ELIFECYCLE
     * npm WARN This failure might be due to the use of legacy binary "node"
     * npm ERR! npm -v 1.4.21
     * npm ERR! path npm-debug.log
     * npm ERR! code EACCES
     * npm ERR! errno 3
     * npm ERR! stack Error: EACCES, open 'npm-debug.log'
     * <p>
     * <p>
     * I know it is a bit overkill to put the whole error prompt but i don't see why it would hurt. Anything helps. Thanks in advance
     */
    @SerializedName("url")
    public String   url;
    @SerializedName("repository_url")
    public String   repositoryUrl;
    @SerializedName("labels_url")
    public String   labelsUrl;
    @SerializedName("comments_url")
    public String   commentsUrl;
    @SerializedName("events_url")
    public String   eventsUrl;
    @SerializedName("html_url")
    public String   htmlUrl;
    @SerializedName("id")
    public int      id;
    @SerializedName("number")
    public int      number;
    @SerializedName("title")
    public String   title;
    @SerializedName("user")
    public UserInfo user;
    @SerializedName("state")
    public String   state;
    @SerializedName("locked")
    public boolean  locked;
    @SerializedName("assignee")
    public Object   assignee;
    @SerializedName("milestone")
    public Object   milestone;
    @SerializedName("comments")
    public int      comments;
    @SerializedName("created_at")
    public String   createdAt;
    @SerializedName("updated_at")
    public String   updatedAt;
    @SerializedName("closed_at")
    public String   closedAt;
    @SerializedName("body")
    public String   body;
    @SerializedName("labels")
    public List<?>  labels;
    @SerializedName("assignees")
    public List<?>  assignees;

}
