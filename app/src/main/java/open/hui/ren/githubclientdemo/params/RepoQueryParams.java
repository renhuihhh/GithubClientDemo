package open.hui.ren.githubclientdemo.params;

import java.io.Serializable;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.apiservices.params
 */

public class RepoQueryParams implements Serializable {
    public String userName;
    public String type;
    public String sort;
    public String direction;

    public RepoQueryParams(String userName, String type, String sort, String direction) {
        this.userName = userName;
        this.type = type;
        this.sort = sort;
        this.direction = direction;
    }
}
