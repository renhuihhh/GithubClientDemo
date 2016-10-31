package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.entities
 */

public class Commit implements Serializable {
    /**
     * sha : d30a0d0b18b5838a28e20cb7824a807e3d49c5a4
     * author : {"email":"rca.haertlein@gmail.com","name":"RCAHaertlein"}
     * message : changed spotlight to pointlight
     * distinct : true
     * url : https://api.github.com/repos/Sander-Kastelein/Spoorloos/commits/d30a0d0b18b5838a28e20cb7824a807e3d49c5a4
     */
    @SerializedName("sha")
    public String   sha;
    @SerializedName("author")
    public UserInfo author;
    @SerializedName("message")
    public String   message;
    @SerializedName("distinct")
    public boolean  distinct;
    @SerializedName("url")
    public String   url;

}
