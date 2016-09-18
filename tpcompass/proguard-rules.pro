# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\android-sdk_r20-windows\android-sdk_r20-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep public class com.android.vcard.* { *; }

-dontwarn android.support.v7.**

-keep class android.support.v4.* { *; }
-keep class android.support.v4.*.* { *; }
-keep class android.support.v7.* { *; }
-keep class android.support.v7.*.* { *; }
-keep class android.support.v13.* { *; }
-keep class android.support.v13.*.* { *; }
-keep class android.support.design.* { *; }
-keep class android.support.design.*.* { *; }

-keep class de.greenrobot.dao.** {*;}
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keepclassmembers class **.R$* {
    public static <fields>;
}