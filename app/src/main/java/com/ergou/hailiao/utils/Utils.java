package com.ergou.hailiao.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;

import static android.view.View.NO_ID;

/**
 * @author xiepeixuan
 * @date 2018/4/24
 */
public class Utils {
    private static final String NAVIGATION = "navigationBarBackground";
    private static final Field TEXT_LINE_CACHED;
    private static Application sApplication;
    public static long lastClickTime;
    private final static int SPACE_TIME = 300;
    private static Pattern EMOJI = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);


    //TextLine 内存泄漏
    static {
        Field textLineCached = null;
        try {
            textLineCached = Class.forName("android.text.TextLine").getDeclaredField("sCached");
            textLineCached.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        TEXT_LINE_CACHED = textLineCached;
    }

    //修复华为GestureBoostManager内存泄漏
    public static void fixHuaWeiMemoryLeak() {
        if (!"HUAWEI".equals(Build.MANUFACTURER)) {
            return;
        }
        try {
            Class<?> GestureBoostManagerClass = Class.forName("android.gestureboost.GestureBoostManager");
            Field sGestureBoostManagerField = GestureBoostManagerClass.getDeclaredField("sGestureBoostManager");
            sGestureBoostManagerField.setAccessible(true);
            Object gestureBoostManager = sGestureBoostManagerField.get(GestureBoostManagerClass);
            Field contextField = GestureBoostManagerClass.getDeclaredField("mContext");
            contextField.setAccessible(true);
            if (contextField.get(gestureBoostManager) != null) {
                contextField.set(gestureBoostManager, null);
            }
        } catch (Throwable t) {

        }
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    //解决TextLine.sCached 泄露
    public static void clearTextLineCache() {
        // If the field was not found for whatever reason just return.
        if (TEXT_LINE_CACHED == null) {
            return;
        }

        Object cached = null;
        try {
            // Get reference to the TextLine sCached array.
            cached = TEXT_LINE_CACHED.get(null);
        } catch (Exception ex) {
            //
        }
        if (cached != null) {
            // Clear the array.
            for (int i = 0, size = Array.getLength(cached); i < size; i++) {
                Array.set(cached, i, null);
            }
        }
    }


    public static void init(@NonNull final Context context) {
        init((Application) context.getApplicationContext());
    }

    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        }
        throw new NullPointerException("u should init first");
    }

    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
    }


    //两次点击间隔要大于1秒
    public static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }

    //rxbinding使用  检测双击事件 规定两次点击小于 500 毫秒则为一次双击事件
//    public static void setRxBDoubleOnclick(View btn, Consumer consumer){
//        Observable<Long> clicks = RxView.clicks(btn)
//                .map(o -> System.currentTimeMillis())
//                .share(); //share 操作符，用来保证点击事件的 Observable 被转为 Hot Observable。
//
//        clicks.zipWith(clicks.skip(1), (t1, t2) -> t2 - t1)
//                .filter(interval -> interval < 500)
//                .subscribe(consumer);
//    ”短时间”内连续多次点击，只能算一次双击操作
//    Observable<Object> clicks = RxView.clicks(btn).share()
//
//          clicks.buffer(clicks.debounce(500, TimeUnit.MILLISECONDS))
//            .filter(events -> events.size >= 2)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(o -> {
//        // handle double click
//    });


//    }


    //rxbinding使用  点击事件防抖动
//    public static void setRxBOnclick(View btn, Consumer consumer){
//        RxView.clicks(btn)
//                .debounce(500, TimeUnit.MILLISECONDS) // debounce 操作符产生一个新的 Observable, 这个 Observable 只发射原 Observable 中时间间隔小于指定阈值的最大子序列的最后一个元素
//                .observerOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//                    // handle clicks
//                })
//    }

    //rxjava点击事件防抖动
//    public static PublishSubject getPublishSubject(Consumer consumer) {
//        PublishSubject likeAction = PublishSubject.create();
//        likeAction.debounce(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
//        return likeAction;
//        btn.setOnClickListener(v -> {
//            likeAction.onNext(like);
//            like = !like;
//        });
//    }

//防止用户输入过快，触发过多网络请求，需要对输入事件做一下防抖动。
//用户在输入关键词过程中可能触发多次请求，那么，如果后一次请求的结果先返回，前一次请求的结果后返回，这种情况应该保证界面展示的是后一次请求的结果。
//用户在输入关键词过程中可能触发多次请求，那么，如果后一次请求的结果返回时，前一次请求的结果尚未返回的情况下，就应该取消前一次请求。
//
//    RxTextView.textChanges(input)
//    .debounce(300, TimeUnit.MILLISECONDS)
//    .switchMap(text -> api.queryKeyword(text.toString()))
//    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(results -> {
//        // handle results
//    });


    public static void setText(TextView textView, String str) {
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        } else {
            textView.setText("");
        }
    }



    /**
     * 保存Bitmap到本地的方法：
     */
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        switch (requestCode) {
//            case CAMERA_REQUEST_CODE:   //调用相机后返回
//                if (resultCode == RESULT_OK) {
//                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Uri contentUri = FileProvider.getUriForFile(MainActivity.this, "BuildConfig.APPLICATION_ID +".fileprovider", tempFile);
//                        cropPhoto(contentUri);
//                    } else {
//                        cropPhoto(Uri.fromFile(tempFile));
//                    }
//                }
//                break;
//            case ALBUM_REQUEST_CODE:    //调用相册后返回
//                if (resultCode == RESULT_OK) {
//                    Uri uri = intent.getData();
//                    cropPhoto(uri);
//                }
//                break;
//            case CROP_REQUEST_CODE:     //调用剪裁后返回
//                Bundle bundle = intent.getExtras();
//                if (bundle != null) {
//                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
//                    Bitmap image = bundle.getParcelable("data");
//                    //设置到ImageView上
//                    mHeader_iv.setImageBitmap(image);
//                    //也可以进行一些保存、压缩等操作后上传
////                    String path = saveImage("crop", image);
//                }
//                break;
//        }
//    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingphone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }

    /**
     * 邮箱用****号隐藏前面的字母
     *
     * @return
     */
    public static String settingemail(String email) {
        String emails = email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
        return emails;
    }

    public static void showDialog(Dialog dialog) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void deleteFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    String[] filePaths = file.list();
                    for (String path : filePaths) {
                        deleteFile(filePath + File.separator + path);
                    }
                    file.delete();
                }
            }
        }
    }



    private Utils() {
    }


    public static String midleReplaceStar(String phone) {
        String result = null;
        if (!StringUtils.isEmpty(phone)) {
            if (phone.length() < 7) {
                result = phone;
            } else {
                String start = phone.substring(0, 3);
                String end = phone.substring(phone.length() - 4, phone.length());
                StringBuilder sb = new StringBuilder();
                sb.append(start).append("****").append(end);
                result = sb.toString();
            }
        }
        return result;
    }
}
