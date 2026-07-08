# Consumer Proguard rules for core module

# Prevent obfuscation of all classes in the core package for consuming modules (like :app)
-keep class com.dicoding.kisahrasa.core.** { *; }

# Room Database
-dontwarn androidx.room.**
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>(...);
}
-keep class * extends androidx.room.RoomDatabase

# Retrofit & Gson
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod
-keep class retrofit2.** { *; }
-keep class com.google.gson.** { *; }

# SQLCipher
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }
-dontwarn net.sqlcipher.**
