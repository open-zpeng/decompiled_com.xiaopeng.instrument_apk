package com.xiaopeng.instrument.utils;

import com.xiaopeng.instrument.App;
import java.io.IOException;
/* loaded from: classes.dex */
public class AssetUtil {
    private static final String ASSET_FOLDER_EN = "en";

    /* JADX WARN: Removed duplicated region for block: B:61:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String loadAsset(java.lang.String r5) {
        /*
            java.lang.String r0 = "en"
            r1 = 0
            boolean r2 = com.xiaopeng.instrument.utils.LanguageUtil.isEn()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            if (r2 == 0) goto L26
            boolean r2 = existFile(r0, r5)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            if (r2 == 0) goto L26
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            r2.<init>()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.lang.String r2 = java.io.File.separator     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.lang.StringBuilder r5 = r0.append(r5)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
        L26:
            com.xiaopeng.instrument.App r0 = com.xiaopeng.instrument.App.getInstance()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            android.content.Context r0 = r0.getApplicationContext()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            android.content.res.AssetManager r0 = r0.getAssets()     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            java.io.InputStream r5 = r0.open(r5)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L77
            if (r5 == 0) goto L6f
            int r0 = r5.available()     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            int r2 = r5.read(r0)     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            r5.close()     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            if (r2 <= 0) goto L59
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            r2.<init>(r0, r3)     // Catch: java.lang.Throwable -> L66 java.io.IOException -> L6a
            if (r5 == 0) goto L58
            r5.close()     // Catch: java.io.IOException -> L54
            goto L58
        L54:
            r5 = move-exception
            r5.printStackTrace()
        L58:
            return r2
        L59:
            java.lang.String r0 = ""
            if (r5 == 0) goto L65
            r5.close()     // Catch: java.io.IOException -> L61
            goto L65
        L61:
            r5 = move-exception
            r5.printStackTrace()
        L65:
            return r0
        L66:
            r0 = move-exception
            r1 = r5
            r5 = r0
            goto L89
        L6a:
            r0 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
            goto L79
        L6f:
            if (r5 == 0) goto L86
            r5.close()     // Catch: java.io.IOException -> L82
            goto L86
        L75:
            r5 = move-exception
            goto L89
        L77:
            r5 = move-exception
            r0 = r1
        L79:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L87
            if (r0 == 0) goto L86
            r0.close()     // Catch: java.io.IOException -> L82
            goto L86
        L82:
            r5 = move-exception
            r5.printStackTrace()
        L86:
            return r1
        L87:
            r5 = move-exception
            r1 = r0
        L89:
            if (r1 == 0) goto L93
            r1.close()     // Catch: java.io.IOException -> L8f
            goto L93
        L8f:
            r0 = move-exception
            r0.printStackTrace()
        L93:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.instrument.utils.AssetUtil.loadAsset(java.lang.String):java.lang.String");
    }

    public static boolean existFile(String str, String str2) {
        try {
            String[] list = App.getInstance().getApplicationContext().getAssets().list(str);
            if (list == null) {
                return false;
            }
            for (String str3 : list) {
                if (str3.equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
