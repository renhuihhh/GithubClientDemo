package open.hui.ren.githubclientdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-9
 * @desc Permissions实体类
 */

public class Permissions implements Serializable {

    /**
     * admin : true
     * push : true
     * pull : true
     */

    @SerializedName("admin")
    public boolean admin;
    @SerializedName("push")
    public boolean push;
    @SerializedName("pull")
    public boolean pull;
}
