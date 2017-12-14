package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author renhui
 * @date 16-10-11
 * @desc payload实体类，可能不同的object会有不同的Payload字段内容
 */
public class Payload implements Serializable {

    /**
     * push_id : 1376731478
     * size : 2
     * distinct_size : 2
     * ref : refs/heads/master
     * head : 2752ba6d0a5d14e3a433c9f7776af6bf75316108
     * before : 4e09154e87eb875b77e5b55725f5bc0c354f7817
     * commits : [{"sha":"d30a0d0b18b5838a28e20cb7824a807e3d49c5a4","author":{"email":"rca.haertlein@gmail.com","name":"RCAHaertlein"},"message":"changed spotlight to pointlight","distinct":true,"url":"https://api.github.com/repos/Sander-Kastelein/Spoorloos/commits/d30a0d0b18b5838a28e20cb7824a807e3d49c5a4"},{"sha":"2752ba6d0a5d14e3a433c9f7776af6bf75316108","author":{"email":"rca.haertlein@gmail.com","name":"RCAHaertlein"},"message":"Merge branch 'master' of https://github.com/Sander-Kastelein/Spoorloos\n\nmerge","distinct":true,"url":"https://api.github.com/repos/Sander-Kastelein/Spoorloos/commits/2752ba6d0a5d14e3a433c9f7776af6bf75316108"}]
     */

    @SerializedName("push_id")
    public long          pushId;
    @SerializedName("size")
    public int          size;
    @SerializedName("distinct_size")
    public int          distinctSize;
    @SerializedName("ref")
    public String       ref;
    @SerializedName("head")
    public String       head;
    @SerializedName("before")
    public String       before;
    @SerializedName("commits")
    public List<Commit> commits;
    @SerializedName("action")
    public String       action;

    /**
     * "ref": null,
     * "ref_type": "repository",
     * "master_branch": "master",
     * "description": null,
     * "pusher_type": "user"
     */
    @SerializedName("ref_type")
    public String refType;
    @SerializedName("master_branch")
    public String masterBranch;
    @SerializedName("description")
    public String description;
    @SerializedName("pusher_type")
    public String pushType;
}
