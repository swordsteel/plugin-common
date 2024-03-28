import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(lulz.plugins.io.gitlab.arturbosch.detekt)

    alias(lulz.plugins.ltd.lulz.plugin.core)

    alias(lulz.plugins.kotlin.jvm)

    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(lulz.ltd.lulz.plugin.core)

    implementation(lulz.org.jetbrains.kotlin.gradle.plugin)

    implementation(lulz.com.bmuschko.docker.gradle.plugin)
    implementation(lulz.io.gitlab.arturbosch.detekt.gradle.plugin)
    implementation(lulz.org.jlleitschuh.ktlint.gradle.plugin)
}

description = "Lulz Ltd Test Plugin Common"
group = "ltd.lulz.plugin"
version = git.version()

detekt {
    buildUponDefaultConfig = true
    basePath = projectDir.path
    source.from(DEFAULT_SRC_DIR_KOTLIN)
}

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
    withSourcesJar()
}

publishing {
    repositories {
        // TODO configuration for publishing packages
        // maven {
        //     url = uri("https://")
        //     credentials {
        //         username =
        //         password =
        //     }
        // }
        publications.register("mavenJava", MavenPublication::class) { from(components["java"]) }
    }
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
}

tasks {
    withType<Detekt> {
        reports {
            html.required = false
            md.required = false
            sarif.required = true
            txt.required = false
            xml.required = false
        }
    }
    withType<Jar> {
        manifest.attributes.apply {
            put("Implementation-Title", project.name.uppercaseFirstChar())
            put("Implementation-Version", project.version)
            put("Implementation-Vendor", core.vendor)
            put("Built-By", System.getProperty("user.name"))
            put("Built-Git", "${git.currentBranch()} #${git.currentShortHash()}")
            put("Built-Gradle", project.gradle.gradleVersion)
            put("Built-JDK", System.getProperty("java.version"))
            put("Built-OS", "${System.getProperty("os.name")} v${System.getProperty("os.version")}")
            put("Built-Time", core.timestamp)
        }
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}
