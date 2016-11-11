package tom.hui.ren.core;

/**
 * @author renhui
 * @date 16-10-9
 * @desc 抽象的持久化操作接口
 */

public interface BasePersistence<T> {
    public void saveData(T data);
    public void deleteData(T data);
    public T retrieveData();
}
