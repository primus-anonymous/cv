# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/nemo/Library/Android/sdk/tools/proguard/proguard-android.txt
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

-keepattributes Signature
-keepattributes *Annotation*

#Firebase
-keepclassmembers class com.neocaptainnemo.cv.model.** {
   *;
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Nav Controller
-keep class * extends androidx.fragment.app.Fragment{}
-keep class com.neocaptainnemo.cv.model.Project

#Share
-keep class androidx.appcompat.widget.ShareActionProvider { *; }
