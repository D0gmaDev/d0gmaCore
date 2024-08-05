plugins {
    java
    `maven-publish`
    id("io.github.goooler.shadow") version "8.1.8"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

group = "fr.d0gma"
version = "0.1-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "fr.d0gma"
            artifactId = "core"
            version = "0.1"

            from(components["java"])
        }
    }
}