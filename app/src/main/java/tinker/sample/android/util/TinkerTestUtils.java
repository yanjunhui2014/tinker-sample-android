package tinker.sample.android.util;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import tinker.sample.android.R;

/**
 * Title：
 * Describe：
 * Remark：
 * <p>
 * Created by Milo
 * E-Mail : 303767416@qq.com
 * 2023/1/31
 */
public class TinkerTestUtils {

    private static final String TAG = "TinkerTestUtils";

    private static final int[] sPerformDexOptSecondaryTransactionCode = {-1};

    public static void performDexOptSecondaryByTransactionCode(Context context) throws IllegalStateException {
        synchronized (sPerformDexOptSecondaryTransactionCode) {
            if (sPerformDexOptSecondaryTransactionCode[0] == -1) {
                try {
                    final Method getDeclaredFieldMethod = ShareReflectUtil.findMethod(
                            Class.class, "getDeclaredField", String.class);
                    getDeclaredFieldMethod.setAccessible(true);
                    final Field cstField = (Field) getDeclaredFieldMethod.invoke(
                            Class.forName("android.content.pm.IPackageManager$Stub"),
                            "TRANSACTION_performDexOptSecondary"
                    );
                    cstField.setAccessible(true);
                    sPerformDexOptSecondaryTransactionCode[0] = (int) cstField.get(null);
                } catch (Throwable thr) {
                    throw new IllegalStateException("Cannot query transaction code of performDexOptSecondary.", thr);
                }
            }
        }

        ShareTinkerLog.i(TAG, "[+] performDexOptSecondaryByTransactionCode, code: %s",
                sPerformDexOptSecondaryTransactionCode[0]);
    }

    public String performDexOptSecondaryByTransactionCodeOne(Context context) throws IllegalStateException {
        try {
            final Method getDeclaredFieldMethod = ShareReflectUtil.findMethod(
                    Class.class, "getDeclaredFields");
            getDeclaredFieldMethod.setAccessible(true);
            Field[] fields = (Field[]) getDeclaredFieldMethod.invoke(
                    Class.forName("android.content.pm.IPackageManager$Stub"));

            StringBuilder stringBuilder = new StringBuilder("1111111111111");
            for (Field field : fields) {
                field.setAccessible(true);
                stringBuilder.append(field.getName()).append("\n");
                Log.d(TAG, "field: " + field.getName());
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
