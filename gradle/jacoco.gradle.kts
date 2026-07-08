apply {
    plugin("jacoco")
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

val fileFilter = mutableSetOf(
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/di/**",
    "**/*_Factory*.*",
    "**/*_MembersInjector*.*",
    "**/*Hilt*.*",
    "**/*Module*.*",
    "**/*Activity*.*",
    "**/*Fragment*.*",
    "**/*Adapter*.*",
    "**/*ViewHolder*.*",
    "**/*Binding*.*"
)

val debugTree = fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
    exclude(fileFilter)
}

val mainSrc = "${project.projectDir}/src/main/java"

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(project.buildDir) {
        include("jacoco/testDebugUnitTest.exec")
    })
}
