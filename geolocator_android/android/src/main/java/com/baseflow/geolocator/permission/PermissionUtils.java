package com.baseflow.geolocator.permission;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionUtils {

  private final static String SHARED_PREFERENCES_NAME = "com.baseflow.geolocator";
  private final static String PERMISSION_ANSWERED_BEFORE_KEY = "PERMISSION_ANSWERED_BEFORE";
  private final static String PERMISSION_DENIED_BEFORE_KEY = "PERMISSION_DENIED_BEFORE";

  public static boolean hasPermissionInManifest(Context context, String permission) {
    try {
      PackageInfo info = getPackageInfo(context);
      if (info.requestedPermissions != null) {
        for (String p : info.requestedPermissions) {
          if (p.equals(permission)) {
            return true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  @SuppressWarnings("deprecation")
  private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
    final PackageManager packageManager = context.getPackageManager();
    final String packageName = context.getPackageName();

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
      return packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
    }
    
    return packageManager.getPackageInfo(packageName,
        PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS));
  }

  public static boolean wasPermissionAnsweredBefore(Context context) {
    final SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getBoolean(PERMISSION_ANSWERED_BEFORE_KEY, false);
  }

  public static void setPermissionAnsweredBefore(Context context) {
    final SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(PERMISSION_ANSWERED_BEFORE_KEY, true);
    editor.apply();
  }

  public static boolean wasPermissionDeniedBefore(final Context context) {
    final SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getBoolean(PERMISSION_DENIED_BEFORE_KEY, false);
  }
  public static void setPermissionDenied(final Context context) {
    final SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    sharedPreferences.edit().putBoolean(PERMISSION_DENIED_BEFORE_KEY, true).apply();
  }
}
