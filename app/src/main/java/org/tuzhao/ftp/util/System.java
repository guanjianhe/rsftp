package org.tuzhao.ftp.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.net.ftp.FTPFile;
import org.tuzhao.ftp.entity.RsFTPFile;
import org.tuzhao.ftp.entity.RsFile;
import org.tuzhao.ftp.entity.RsLocalFile;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * zhaotu
 * 17-8-4
 */
public final class System {

    public static final String ACTION_SERVER_CURRENT_UPDATE = "action_server_current_update";
    public static final String ACTION_SERVER_CURRENT_PATH = "action_server_current_path";
    public static final String ACTION_SERVER_EXCEPTION_CONNECT = "action_server_connect_exception";
    public static final String ACTION_SERVER_EXCEPTION_LOGIN = "action_server_login_exception";
    public static final String ACTION_SERVER_FAILED_LOGIN = "action_server_failed_login";
    public static final String ACTION_SERVER_RENAME_FILE = "action_server_rename_file";

    private static final String EXTRA_CURRENT_PATH = "extra_current_path";
    private static final String EXTRA_ERROR_CONNECT_MSG = "extra_error_msg";
    private static final String EXTRA_ERROR_LOGIN_MSG = "extra_login_msg";
    private static final String EXTRA_RENAME_FILE = "extra_rename_file";

    public static final String SHARED_CONFIG_FILE = "config";
    public static final String SHARED_WIFI_KEY = "wifi_select";

    private static final String TAG = "System";

    public static void sendServerRenameBroadcast(Context context, boolean result) {
        Intent intent = new Intent(ACTION_SERVER_RENAME_FILE);
        intent.putExtra(EXTRA_RENAME_FILE, result);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendServerCurrentPathBroadcast(Context context, String path) {
        Intent intent = new Intent(ACTION_SERVER_CURRENT_PATH);
        intent.putExtra(EXTRA_CURRENT_PATH, path);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendServerConnectExceptionBroadcast(Context context, String errorMsg) {
        Intent intent = new Intent(ACTION_SERVER_EXCEPTION_CONNECT);
        intent.putExtra(EXTRA_ERROR_CONNECT_MSG, errorMsg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendServerLoginExceptionBroadcast(Context context, String errorMsg) {
        Intent intent = new Intent(ACTION_SERVER_EXCEPTION_LOGIN);
        intent.putExtra(EXTRA_ERROR_LOGIN_MSG, errorMsg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendServerLoginFailed(Context context) {
        Intent intent = new Intent(ACTION_SERVER_FAILED_LOGIN);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static boolean getServerRenameResult(Intent intent) {
        return intent.getBooleanExtra(EXTRA_RENAME_FILE, false);
    }

    public static String getServerCurrentPath(Intent intent) {
        String path = null;
        if (null != intent) {
            path = intent.getStringExtra(EXTRA_CURRENT_PATH);
        }
        return path;
    }

    public static String getServerErrorConnectMsg(Intent intent) {
        String msg = null;
        if (null != intent) {
            msg = intent.getStringExtra(EXTRA_ERROR_CONNECT_MSG);
        }
        return msg;
    }

    public static String getServerErrorLoginMsg(Intent intent) {
        String msg = null;
        if (null != intent) {
            msg = intent.getStringExtra(EXTRA_ERROR_LOGIN_MSG);
        }
        return msg;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        );
        for (ResolveInfo info : list) {
            String name = info.activityInfo.packageName;
            log("resolve package: " + name);
        }
        return list.size() > 0;
    }

    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        log("language locale: " + language);
        return language.endsWith("zh");
    }

    public static void threadInfo() {
        Thread thread = Thread.currentThread();
        long id = thread.getId();
        String name = thread.getName();
        int priority = thread.getPriority();
        ThreadGroup group = thread.getThreadGroup();
        log("thread info[id: " + id + " name: " + name + " priority: " + priority + " group: " + group + " ]");
    }

    public static ArrayList<RsFile> convertFTPFileToRsFile(ArrayList<FTPFile> srcList) {
        ArrayList<RsFile> files = new ArrayList<>();
        for (int i = 0; i < srcList.size(); i++) {
            FTPFile ftpFile = srcList.get(i);
            files.add(new RsFTPFile(ftpFile));
        }
        return files;
    }

    public static ArrayList<RsFile> convertFileToRsFile(File[] srcList) {
        ArrayList<RsFile> files = new ArrayList<>();
        if (null != srcList) {
            for (File file : srcList) {
                files.add(new RsLocalFile(file));
            }
        }
        return files;
    }

    public static boolean isAndroidO() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
    }

    public static String listToString(ArrayList<String> list) {
        String str = "";
        try {
            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            ObjectOutputStream outObject = new ObjectOutputStream(outByte);
            outObject.writeObject(list);
            str = new String(Base64.encode(outByte.toByteArray(), Base64.DEFAULT));
            outObject.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<String> stringToList(String string) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            if (null != string && !TextUtils.isEmpty(string)) {
                byte[] bytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
                ByteArrayInputStream inByte = new ByteArrayInputStream(bytes);
                ObjectInputStream inObj = new ObjectInputStream(inByte);
                ArrayList<String> read = (ArrayList<String>) inObj.readObject();
                list.addAll(read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void log(String msg) {
        Log.i(TAG, msg);
    }

}
