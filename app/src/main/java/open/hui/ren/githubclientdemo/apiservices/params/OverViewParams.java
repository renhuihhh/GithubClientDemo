package open.hui.ren.githubclientdemo.apiservices.params;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.apiservices.params
 */

public class OverViewParams {
    public String tabName;
    public String index;
    public static final String DIV = "_";

    public OverViewParams(String tabName, String index) {
        this.tabName = tabName;
        this.index = index;
    }

    @Override
    public String toString() {
        return tabName + DIV + index;
    }
}
