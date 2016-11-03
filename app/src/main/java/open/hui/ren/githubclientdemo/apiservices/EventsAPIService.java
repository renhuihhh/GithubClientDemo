package open.hui.ren.githubclientdemo.apiservices;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.Event;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author renhui
 * @date 16-10-31
 * @desc 获取events消息列表的apiservice类
 */

public interface EventsAPIService {
    /**
     * 获取当前登陆用户的 events 列表
     */
    @GET("/events")
    Call<ArrayList<Event>> getEvents();
}
