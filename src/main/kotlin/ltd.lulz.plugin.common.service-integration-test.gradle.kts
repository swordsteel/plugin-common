plugins {
    id("idea")

    kotlin("jvm")
}

@Suppress("unused")
fun DependencyHandler.integrationTestImplementation(
    dependencyNotation: Any,
): Dependency? = add("integrationTestImplementation", dependencyNotation)

@Suppress("unused")
fun DependencyHandler.integrationTestRuntimeOnly(
    dependencyNotation: Any,
): Dependency? = add("integrationTestRuntimeOnly", dependencyNotation)

sourceSets.create("integration-test") {
    compileClasspath += sourceSets["main"].output
    runtimeClasspath += sourceSets["main"].output
    idea.module {
        testSources.from(sourceSets["integration-test"].kotlin.srcDirs, sourceSets["integration-test"].java.srcDirs)
        testResources.from(sourceSets["integration-test"].resources.srcDirs)
    }
    configurations.let {
        it["integrationTestImplementation"].extendsFrom(configurations.implementation.get())
        it["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())
    }
}

tasks {
    register<Test>("integrationTest") {
        description = "Runs integration tests."
        group = "verification"
        testClassesDirs = sourceSets["integration-test"].output.classesDirs
        classpath = sourceSets["integration-test"].runtimeClasspath
    }
    check { dependsOn(getByName("integrationTest")) }
}
