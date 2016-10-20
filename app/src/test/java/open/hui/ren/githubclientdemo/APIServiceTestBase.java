package open.hui.ren.githubclientdemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.File;

import open.hui.ren.githubclientdemo.modules.NetModuleTest;

import static org.mockito.Mockito.when;

/**
 * @author renhui
 * @date 16-9-25
 * @desc open.hui.ren.githubclientdemo
 */

abstract public class APIServiceTestBase extends TestBase {
    protected NetModuleTest netModuleTest;
    protected Context mContext;

    abstract public void start();

    abstract public void end();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mContext = Mockito.mock(Application.class);
        String  temp    = Environment.DIRECTORY_DOWNLOADS + "/temp";
        when(mContext.getCacheDir()).thenReturn(new File(temp));
        start();
    }

    @After
    public void tearDown() throws Exception {
        end();
    }
}
