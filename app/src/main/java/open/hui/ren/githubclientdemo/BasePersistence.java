package open.hui.ren.githubclientdemo;

/**
 * @author renhui
 * @date 16-10-9
 * @desc open.hui.ren.githubclientdemo
 */

public interface BasePersistence<T> {
    public void saveData(T data);
    public void deleteData(T data);
    public T retrieveData();
}
