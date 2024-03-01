plugins {
    `maven-publish`
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
