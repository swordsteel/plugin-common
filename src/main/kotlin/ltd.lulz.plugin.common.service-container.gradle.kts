import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("com.bmuschko.docker-spring-boot-application")
}

tasks {
    register("buildContainer", DockerBuildImage::class) {
        doNotTrackState("disable file checks for this task")
        group = "container"
        inputDir.set(file("./"))
        images.add("${project.name}:${project.version}")
    }
    register("createContainer", DockerCreateContainer::class) {
        group = "container"
        targetImageId("${project.name}:${project.version}")
        containerName.set(project.name)
        hostConfig.portBindings.set(listOf("${project.findProperty("dockerPort") ?: "8080"}:8080"))
        hostConfig.autoRemove.set(true)
        hostConfig.network.set("develop")
        withEnvVar("SPRING_PROFILES_ACTIVE", "docker")
    }
    register("startContainer", DockerStartContainer::class) {
        group = "container"
        dependsOn(findByPath("createContainer"))
        targetContainerId(project.name)
    }
    register("stopContainer", DockerStopContainer::class) {
        group = "container"
        targetContainerId(project.name)
    }
}
