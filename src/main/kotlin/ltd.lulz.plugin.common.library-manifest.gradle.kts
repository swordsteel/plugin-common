import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("ltd.lulz.plugin.core")
}

tasks {
    withType<Jar> {
        manifest.attributes.apply {
            put(
                "Implementation-Title",
                project.name.split("-").joinToString(" ") { word -> word.uppercaseFirstChar() },
            )
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
}
