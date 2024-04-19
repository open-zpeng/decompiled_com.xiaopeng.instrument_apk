package com.xiaopeng.cluster.backpressure;

import com.xiaopeng.cluster.utils.XILog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class ProcessorFactory {
    public static final int DEFAULT_DOWNSTREAM_BUFFER_SIZE = 1;
    public static final int DEFAULT_UPSTREAM_BUFFER_SIZE = 1;
    public static final int MAX_DOWNSTREAM_BUFFER_SIZE = 128;
    public static final int MAX_UPSTREAM_BUFFER_SIZE = 128;
    private static final String TAG = "ProcessorFactory";
    private static final Map<String, PublishProcessor> sCacheProcessor = new ConcurrentHashMap();
    private static final CompositeDisposable sCompositeDisposable = new CompositeDisposable();

    private void init() {
    }

    public static <T> PublishProcessor getProcessor(String str, Consumer<T> consumer) {
        Map<String, PublishProcessor> map = sCacheProcessor;
        if (map.get(str) == null) {
            XILog.d(TAG, "createProcessorAndSubscribe: " + str);
            PublishProcessor createProcessorAndSubscribe = createProcessorAndSubscribe(str, consumer);
            map.put(str, createProcessorAndSubscribe);
            return createProcessorAndSubscribe;
        }
        return map.get(str);
    }

    private static <T> PublishProcessor createProcessorAndSubscribe(String str, Consumer<T> consumer) {
        if (consumer == null) {
            XILog.d(TAG, "consumer is null");
            return null;
        }
        PublishProcessor create = PublishProcessor.create();
        str.hashCode();
        int i = 1;
        int i2 = 128;
        if (str.equals(JniKey.COMMON_TELLTALE)) {
            i = 128;
        } else {
            i2 = 1;
        }
        sCompositeDisposable.add(create.onBackpressureBuffer(i, (Action) null, BackpressureOverflowStrategy.DROP_OLDEST).subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread(), false, i2).subscribe(consumer));
        return create;
    }

    public static void destroy() {
        Map<String, PublishProcessor> map = sCacheProcessor;
        if (map != null) {
            map.clear();
        }
        CompositeDisposable compositeDisposable = sCompositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
