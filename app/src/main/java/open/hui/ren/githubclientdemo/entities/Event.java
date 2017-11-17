package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-11
 * @desc event实体类
 */

public class Event implements Serializable {
    /**
     * id : 4294853443
     * type : IssueCommentEvent
     * actor : {"id":17830234,"login":"renhuihhh","display_login":"renhuihhh","gravatar_id":"","url":"https://api.github.com/users/renhuihhh","avatar_url":"https://avatars.githubusercontent.com/u/17830234?"}
     * repo : {"id":42341942,"name":"evancohen/smart-mirror","url":"https://api.github.com/repos/evancohen/smart-mirror"}
     * payload : {"action":"created","issue":{"url":"https://api.github.com/repos/evancohen/smart-mirror/issues/32","repository_url":"https://api.github.com/repos/evancohen/smart-mirror","labels_url":"https://api.github.com/repos/evancohen/smart-mirror/issues/32/labels{/name}","comments_url":"https://api.github.com/repos/evancohen/smart-mirror/issues/32/comments","events_url":"https://api.github.com/repos/evancohen/smart-mirror/issues/32/events","html_url":"https://github.com/evancohen/smart-mirror/issues/32","id":125851863,"number":32,"title":"electron not found/install issues","user":{"login":"markparise","id":16629918,"avatar_url":"https://avatars.githubusercontent.com/u/16629918?v=3","gravatar_id":"","url":"https://api.github.com/users/markparise","html_url":"https://github.com/markparise","followers_url":"https://api.github.com/users/markparise/followers","following_url":"https://api.github.com/users/markparise/following{/other_user}","gists_url":"https://api.github.com/users/markparise/gists{/gist_id}","starred_url":"https://api.github.com/users/markparise/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/markparise/subscriptions","organizations_url":"https://api.github.com/users/markparise/orgs","repos_url":"https://api.github.com/users/markparise/repos","events_url":"https://api.github.com/users/markparise/events{/privacy}","received_events_url":"https://api.github.com/users/markparise/received_events","type":"User","site_admin":false},"labels":[],"state":"closed","locked":false,"assignee":null,"assignees":[],"milestone":null,"comments":11,"created_at":"2016-01-10T23:02:19Z","updated_at":"2016-07-19T02:59:12Z","closed_at":"2016-01-11T18:58:33Z","body":"Hey evan, So I got npm installed on my pi, but when I ran the \"npm start\" I get a whole slew of errors the first of which reads \"sh: 1: electron: not found\" \r\n\r\nThe rest of the error prompt is as follows: \r\nnpm ERR! smart-mirror@0.0.1 start: 'electron main,js'\r\nnpm ERR! Exit status 127\r\nnpm ERR!\r\nnpm ERR! Failed at the smart-mirror@0.0.1 start script.\r\nnpm ERR! This is most likely a problem with the smart-mirror package,\r\nnpm ERR! not with npm itself.\r\nnpm ERR! Error: EACCES, open ' npm-debug.log'\r\nnpm ERR! { [Error: EACCES, open 'npm-debug.log'] errno: 3, code: 'EACCES' : 'npm-debug.log; }\r\n\r\nnpm ERR! System Linux 4.1.7-v7+\r\nnpm ERR! command \"/usr/bin/nodejs\" \"/usr/bin/npm\" \"start\"\r\nnpm ERR! cwd /git/smart-mirror\r\nnpm ERR! node -v v0.10.29\r\nnpm ERR! cod ELIFECYCLE\r\nnpm WARN This failure might be due to the use of legacy binary \"node\"\r\nnpm ERR! npm -v 1.4.21\r\nnpm ERR! path npm-debug.log\r\nnpm ERR! code EACCES\r\nnpm ERR! errno 3\r\nnpm ERR! stack Error: EACCES, open 'npm-debug.log'\r\n\r\n\r\nI know it is a bit overkill to put the whole error prompt but i don't see why it would hurt. Anything helps. Thanks in advance"},"comment":{"url":"https://api.github.com/repos/evancohen/smart-mirror/issues/comments/233517508","html_url":"https://github.com/evancohen/smart-mirror/issues/32#issuecomment-233517508","issue_url":"https://api.github.com/repos/evancohen/smart-mirror/issues/32","id":233517508,"user":{"login":"renhuihhh","id":17830234,"avatar_url":"https://avatars.githubusercontent.com/u/17830234?v=3","gravatar_id":"","url":"https://api.github.com/users/renhuihhh","html_url":"https://github.com/renhuihhh","followers_url":"https://api.github.com/users/renhuihhh/followers","following_url":"https://api.github.com/users/renhuihhh/following{/other_user}","gists_url":"https://api.github.com/users/renhuihhh/gists{/gist_id}","starred_url":"https://api.github.com/users/renhuihhh/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/renhuihhh/subscriptions","organizations_url":"https://api.github.com/users/renhuihhh/orgs","repos_url":"https://api.github.com/users/renhuihhh/repos","events_url":"https://api.github.com/users/renhuihhh/events{/privacy}","received_events_url":"https://api.github.com/users/renhuihhh/received_events","type":"User","site_admin":false},"created_at":"2016-07-19T02:59:12Z","updated_at":"2016-07-19T02:59:12Z","body":" [https://www.npmjs.com/package/electron-prebuilt](url) look here"}}
     * public : true
     * created_at : 2016-07-19T02:59:12Z
     */

    @SerializedName("id")
    public String   id;
    @SerializedName("type")
    public String   type;
    @SerializedName("actor")
    public UserInfo actor;
    @SerializedName("repo")
    public Repo     repo;
    @SerializedName("payload")
    public Payload  payload;
    @SerializedName("public")
    public boolean  publicX;
    @SerializedName("created_at")
    public String   createdAt;
}
