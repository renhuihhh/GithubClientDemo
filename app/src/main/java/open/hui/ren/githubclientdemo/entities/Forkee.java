package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.entities
 */

public class Forkee implements Serializable{

    /**
     * id : 72405554
     * name : neural-enhance
     * full_name : fujianhai/neural-enhance
     * owner : {"login":"fujianhai","id":6189742,"avatar_url":"https://avatars.githubusercontent.com/u/6189742?v=3","gravatar_id":"","url":"https://api.github.com/users/fujianhai","html_url":"https://github.com/fujianhai","followers_url":"https://api.github.com/users/fujianhai/followers","following_url":"https://api.github.com/users/fujianhai/following{/other_user}","gists_url":"https://api.github.com/users/fujianhai/gists{/gist_id}","starred_url":"https://api.github.com/users/fujianhai/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/fujianhai/subscriptions","organizations_url":"https://api.github.com/users/fujianhai/orgs","repos_url":"https://api.github.com/users/fujianhai/repos","events_url":"https://api.github.com/users/fujianhai/events{/privacy}","received_events_url":"https://api.github.com/users/fujianhai/received_events","type":"User","site_admin":false}
     * private : false
     * html_url : https://github.com/fujianhai/neural-enhance
     * description : Super Resolution for images using deep learning.
     * fork : true
     * url : https://api.github.com/repos/fujianhai/neural-enhance
     * forks_url : https://api.github.com/repos/fujianhai/neural-enhance/forks
     * keys_url : https://api.github.com/repos/fujianhai/neural-enhance/keys{/key_id}
     * collaborators_url : https://api.github.com/repos/fujianhai/neural-enhance/collaborators{/collaborator}
     * teams_url : https://api.github.com/repos/fujianhai/neural-enhance/teams
     * hooks_url : https://api.github.com/repos/fujianhai/neural-enhance/hooks
     * issue_events_url : https://api.github.com/repos/fujianhai/neural-enhance/issues/events{/number}
     * events_url : https://api.github.com/repos/fujianhai/neural-enhance/events
     * assignees_url : https://api.github.com/repos/fujianhai/neural-enhance/assignees{/user}
     * branches_url : https://api.github.com/repos/fujianhai/neural-enhance/branches{/branch}
     * tags_url : https://api.github.com/repos/fujianhai/neural-enhance/tags
     * blobs_url : https://api.github.com/repos/fujianhai/neural-enhance/git/blobs{/sha}
     * git_tags_url : https://api.github.com/repos/fujianhai/neural-enhance/git/tags{/sha}
     * git_refs_url : https://api.github.com/repos/fujianhai/neural-enhance/git/refs{/sha}
     * trees_url : https://api.github.com/repos/fujianhai/neural-enhance/git/trees{/sha}
     * statuses_url : https://api.github.com/repos/fujianhai/neural-enhance/statuses/{sha}
     * languages_url : https://api.github.com/repos/fujianhai/neural-enhance/languages
     * stargazers_url : https://api.github.com/repos/fujianhai/neural-enhance/stargazers
     * contributors_url : https://api.github.com/repos/fujianhai/neural-enhance/contributors
     * subscribers_url : https://api.github.com/repos/fujianhai/neural-enhance/subscribers
     * subscription_url : https://api.github.com/repos/fujianhai/neural-enhance/subscription
     * commits_url : https://api.github.com/repos/fujianhai/neural-enhance/commits{/sha}
     * git_commits_url : https://api.github.com/repos/fujianhai/neural-enhance/git/commits{/sha}
     * comments_url : https://api.github.com/repos/fujianhai/neural-enhance/comments{/number}
     * issue_comment_url : https://api.github.com/repos/fujianhai/neural-enhance/issues/comments{/number}
     * contents_url : https://api.github.com/repos/fujianhai/neural-enhance/contents/{+path}
     * compare_url : https://api.github.com/repos/fujianhai/neural-enhance/compare/{base}...{head}
     * merges_url : https://api.github.com/repos/fujianhai/neural-enhance/merges
     * archive_url : https://api.github.com/repos/fujianhai/neural-enhance/{archive_format}{/ref}
     * downloads_url : https://api.github.com/repos/fujianhai/neural-enhance/downloads
     * issues_url : https://api.github.com/repos/fujianhai/neural-enhance/issues{/number}
     * pulls_url : https://api.github.com/repos/fujianhai/neural-enhance/pulls{/number}
     * milestones_url : https://api.github.com/repos/fujianhai/neural-enhance/milestones{/number}
     * notifications_url : https://api.github.com/repos/fujianhai/neural-enhance/notifications{?since,all,participating}
     * labels_url : https://api.github.com/repos/fujianhai/neural-enhance/labels{/name}
     * releases_url : https://api.github.com/repos/fujianhai/neural-enhance/releases{/id}
     * deployments_url : https://api.github.com/repos/fujianhai/neural-enhance/deployments
     * created_at : 2016-10-31T05:43:57Z
     * updated_at : 2016-10-31T05:43:44Z
     * pushed_at : 2016-10-31T02:29:56Z
     * git_url : git://github.com/fujianhai/neural-enhance.git
     * ssh_url : git@github.com:fujianhai/neural-enhance.git
     * clone_url : https://github.com/fujianhai/neural-enhance.git
     * svn_url : https://github.com/fujianhai/neural-enhance
     * homepage :
     * size : 3364
     * stargazers_count : 0
     * watchers_count : 0
     * language : null
     * has_issues : false
     * has_downloads : true
     * has_wiki : false
     * has_pages : false
     * forks_count : 0
     * mirror_url : null
     * open_issues_count : 0
     * forks : 0
     * open_issues : 0
     * watchers : 0
     * default_branch : master
     * public : true
     */

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("full_name")
    public String fullName;

    @SerializedName("owner")
    public UserInfo owner;
    @SerializedName("private")
    public boolean privateX;
    @SerializedName("html_url")
    public String  htmlUrl;
    @SerializedName("description")
    public String  description;
    @SerializedName("fork")
    public boolean fork;
    @SerializedName("url")
    public String  url;
    @SerializedName("forks_url")
    public String  forksUrl;
    @SerializedName("keys_url")
    public String  keysUrl;
    @SerializedName("collaborators_url")
    public String  collaboratorsUrl;
    @SerializedName("teams_url")
    public String  teamsUrl;
    @SerializedName("hooks_url")
    public String  hooksUrl;
    @SerializedName("issue_events_url")
    public String  issueEventsUrl;
    @SerializedName("events_url")
    public String  eventsUrl;
    @SerializedName("assignees_url")
    public String  assigneesUrl;
    @SerializedName("branches_url")
    public String  branchesUrl;
    @SerializedName("tags_url")
    public String  tagsUrl;
    @SerializedName("blobs_url")
    public String  blobsUrl;
    @SerializedName("git_tags_url")
    public String  gitTagsUrl;
    @SerializedName("git_refs_url")
    public String  gitRefsUrl;
    @SerializedName("trees_url")
    public String  treesUrl;
    @SerializedName("statuses_url")
    public String  statusesUrl;
    @SerializedName("languages_url")
    public String  languagesUrl;
    @SerializedName("stargazers_url")
    public String  stargazersUrl;
    @SerializedName("contributors_url")
    public String  contributorsUrl;
    @SerializedName("subscribers_url")
    public String  subscribersUrl;
    @SerializedName("subscription_url")
    public String  subscriptionUrl;
    @SerializedName("commits_url")
    public String  commitsUrl;
    @SerializedName("git_commits_url")
    public String  gitCommitsUrl;
    @SerializedName("comments_url")
    public String  commentsUrl;
    @SerializedName("issue_comment_url")
    public String  issueCommentUrl;
    @SerializedName("contents_url")
    public String  contentsUrl;
    @SerializedName("compare_url")
    public String  compareUrl;
    @SerializedName("merges_url")
    public String  mergesUrl;
    @SerializedName("archive_url")
    public String  archiveUrl;
    @SerializedName("downloads_url")
    public String  downloadsUrl;
    @SerializedName("issues_url")
    public String  issuesUrl;
    @SerializedName("pulls_url")
    public String  pullsUrl;
    @SerializedName("milestones_url")
    public String  milestonesUrl;
    @SerializedName("notifications_url")
    public String  notificationsUrl;
    @SerializedName("labels_url")
    public String  labelsUrl;
    @SerializedName("releases_url")
    public String  releasesUrl;
    @SerializedName("deployments_url")
    public String  deploymentsUrl;
    @SerializedName("created_at")
    public String  createdAt;
    @SerializedName("updated_at")
    public String  updatedAt;
    @SerializedName("pushed_at")
    public String  pushedAt;
    @SerializedName("git_url")
    public String  gitUrl;
    @SerializedName("ssh_url")
    public String  sshUrl;
    @SerializedName("clone_url")
    public String  cloneUrl;
    @SerializedName("svn_url")
    public String  svnUrl;
    @SerializedName("homepage")
    public String  homepage;
    @SerializedName("size")
    public int     size;
    @SerializedName("stargazers_count")
    public int     stargazersCount;
    @SerializedName("watchers_count")
    public int     watchersCount;
    @SerializedName("language")
    public Object  language;
    @SerializedName("has_issues")
    public boolean hasIssues;
    @SerializedName("has_downloads")
    public boolean hasDownloads;
    @SerializedName("has_wiki")
    public boolean hasWiki;
    @SerializedName("has_pages")
    public boolean hasPages;
    @SerializedName("forks_count")
    public int     forksCount;
    @SerializedName("mirror_url")
    public Object  mirrorUrl;
    @SerializedName("open_issues_count")
    public int     openIssuesCount;
    @SerializedName("forks")
    public int     forks;
    @SerializedName("open_issues")
    public int     openIssues;
    @SerializedName("watchers")
    public int     watchers;
    @SerializedName("default_branch")
    public String  defaultBranch;
    @SerializedName("public")
    public boolean publicX;
}
