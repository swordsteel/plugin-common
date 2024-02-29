import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF

plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    verbose = true
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/*"))
    }
    reporters {
        reporter(SARIF)
    }
}
