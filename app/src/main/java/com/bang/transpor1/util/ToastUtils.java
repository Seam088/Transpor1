package com.bang.transpor1.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 12457 on 2018/10/22.
 */

public class ToastUtils {
    public static void showToast(final Context context, final String str) {
        Toast.makeText(context,""+str,Toast.LENGTH_SHORT).show();

    }
}
