plugins {
    alias(libs.plugins.java)
}

group = "it.unibo.pps"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(rootProject.libs.versions.jdk.get())
    }
}

dependencies {
    testImplementation(platform(rootProject.libs.junit.bom))
    testImplementation(rootProject.libs.junit.jupiter)
    testRuntimeOnly(rootProject.libs.junit.launcher)
}

tasks.test {
    useJUnitPlatform()
    javaLauncher = javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(rootProject.libs.versions.jdk.get()))
    }
}

listOf("e2", "e3").forEach { exercise ->
    tasks.register<JavaExec>(exercise) {
        group = "application"
        description = "Run the $exercise exercise"

        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("it.unibo.pps.$exercise.Main")
    }
}
