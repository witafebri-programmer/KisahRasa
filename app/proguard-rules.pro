# Proguard rules for Kisah Rasa App

# Dagger Hilt
# Hilt and Dagger bundle their own Proguard rules.
# We only need to keep our own EntryPoints if they are accessed via reflection.
-keep interface com.dicoding.kisahrasa.di.FavoriteModuleDependencies { *; }

# Data Models (Domain & Response)
# Protecting only fields to ensure GSON and Room can map data correctly.
# This avoids the "Overly broad keep rule" by targeting only members.
-keepclassmembers class com.dicoding.kisahrasa.core.domain.model.** { <fields>; }
-keepclassmembers class com.dicoding.kisahrasa.core.data.remote.response.** { <fields>; }
-keepclassmembers class com.dicoding.kisahrasa.core.data.local.entity.** { <fields>; }

# Retrofit, Gson, and OkHttp bundle their own rules in the AAR.
# We only add specific workarounds if needed.
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
