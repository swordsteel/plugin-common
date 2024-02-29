import org.gradle.api.JavaVersion.VERSION_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("ltd.lulz.plugin.core")
    id("ltd.lulz.plugin.common.common-detekt")
    id("ltd.lulz.plugin.common.common-ktlint")
    id("ltd.lulz.plugin.common.common-repository")

    kotlin("jvm")
}

version = git.version()

java {
    sourceCompatibility = VERSION_17
    targetCompatibility = VERSION_17
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    withType<Test> {
        useJUnitPlatform()
    }
}
