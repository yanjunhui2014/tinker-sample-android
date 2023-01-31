package tinker.sample.android.app;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareIntentUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerLog;

import me.weishu.reflection.Reflection;
import tinker.sample.android.Log.MyLogImp;
import tinker.sample.android.util.SampleApplicationContext;
import tinker.sample.android.util.TinkerManager;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 2023/1/30
 */
public class SampleApplication extends TinkerApplication {

    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL);
        Log.d("SampleApplication", "structure ..");
    }

    protected SampleApplication(int tinkerFlags) {
        super(tinkerFlags);
    }

    protected SampleApplication(int tinkerFlags, String delegateClassName) {
        super(tinkerFlags, delegateClassName);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        //you must install multiDex whatever tinker is installed!
        Reflection.unseal(base);
        Intent intent = new Intent();
        intent.putExtra(ShareIntentUtil.INTENT_RETURN_CODE, ShareConstants.ERROR_LOAD_OK);

        final long applicationStartElapsedTime = SystemClock.elapsedRealtime();
        final long applicationStartMillisTime = System.currentTimeMillis();

        DefaultApplicationLike defaultApplicationLike = new DefaultApplicationLike(this,
                ShareConstants.TINKER_ENABLE_ALL, false, applicationStartElapsedTime, applicationStartMillisTime, intent);

        SampleApplicationContext.application = this;
        SampleApplicationContext.context = this;
        TinkerManager.setTinkerApplicationLike(defaultApplicationLike);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(defaultApplicationLike);
        Tinker tinker = Tinker.with(this);
    }
}
