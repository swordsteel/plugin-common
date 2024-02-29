import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    buildUponDefaultConfig = true
    basePath = projectDir.path
    source.from(
        DEFAULT_SRC_DIR_KOTLIN,
        DEFAULT_TEST_SRC_DIR_KOTLIN,
    )
}

tasks.withType<Detekt> {
    reports {
        html.required = false
        md.required = false
        sarif.required = true
        txt.required = false
        xml.required = false
    }
}
