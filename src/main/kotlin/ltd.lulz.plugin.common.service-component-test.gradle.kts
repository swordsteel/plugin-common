plugins {
    id("idea")

    kotlin("jvm")
}

@Suppress("unused")
fun DependencyHandler.componentTestImplementation(
    dependencyNotation: Any,
): Dependency? = add("componentTestImplementation", dependencyNotation)

@Suppress("unused")
fun DependencyHandler.componentTestRuntimeOnly(
    dependencyNotation: Any,
): Dependency? = add("componentTestRuntimeOnly", dependencyNotation)

sourceSets.create("component-test") {
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
    idea.module {
        testSources.from(sourceSets["component-test"].kotlin.srcDirs, sourceSets["component-test"].java.srcDirs)
        testResources.from(sourceSets["component-test"].resources.srcDirs)
    }
    configurations.let {
        it["componentTestImplementation"].extendsFrom(configurations.implementation.get())
        it["componentTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
    }
}

tasks {
    register<Test>("componentTest") {
        description = "Runs component tests."
        group = "verification"
        testClassesDirs = sourceSets["component-test"].output.classesDirs
        classpath = sourceSets["component-test"].runtimeClasspath
    }
    check { dependsOn(getByName("componentTest")) }
}
