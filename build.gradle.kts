import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import org.gradle.api.JavaVersion.VERSION_17
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.23.5"

    id("ltd.lulz.plugin.core") version "0.1.0"

    kotlin("jvm") version "1.9.20"

    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation("ltd.lulz.plugin.core:ltd.lulz.plugin.core.gradle.plugin:0.1.0")

    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")

    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.5")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.0")
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
