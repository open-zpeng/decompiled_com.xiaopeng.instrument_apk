package com.xiaopeng.instrument.utils;

import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes.dex */
public class FileUtil {
    /* JADX WARN: Removed duplicated region for block: B:38:0x0051 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String loadFromAssets(java.lang.String r5) {
        /*
            r0 = 0
            com.xiaopeng.instrument.App r1 = com.xiaopeng.instrument.App.getInstance()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3e
            android.content.Context r1 = r1.getApplicationContext()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3e
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3e
            java.io.InputStream r5 = r1.open(r5)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3e
            if (r5 == 0) goto L33
            int r1 = r5.available()     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            byte[] r1 = new byte[r1]     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            r5.read(r1)     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            r5.close()     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            java.lang.String r2 = new java.lang.String     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            java.nio.charset.Charset r3 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            r2.<init>(r1, r3)     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L4e
            if (r5 == 0) goto L30
            r5.close()     // Catch: java.io.IOException -> L2c
            goto L30
        L2c:
            r5 = move-exception
            r5.printStackTrace()
        L30:
            return r2
        L31:
            r1 = move-exception
            goto L40
        L33:
            if (r5 == 0) goto L4d
            r5.close()     // Catch: java.io.IOException -> L49
            goto L4d
        L39:
            r5 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
            goto L4f
        L3e:
            r1 = move-exception
            r5 = r0
        L40:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L4e
            if (r5 == 0) goto L4d
            r5.close()     // Catch: java.io.IOException -> L49
            goto L4d
        L49:
            r5 = move-exception
            r5.printStackTrace()
        L4d:
            return r0
        L4e:
            r0 = move-exception
        L4f:
            if (r5 == 0) goto L59
            r5.close()     // Catch: java.io.IOException -> L55
            goto L59
        L55:
            r5 = move-exception
            r5.printStackTrace()
        L59:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.instrument.utils.FileUtil.loadFromAssets(java.lang.String):java.lang.String");
    }

    private static String inputStream2String(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                sb.append(new String(bArr, 0, read));
            } else {
                return sb.toString();
            }
        }
    }
}
