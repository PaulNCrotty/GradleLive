plugins {
    kotlin("jvm") version "1.5.10"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

//adapted from https://docs.gradle.org/6.5/userguide/build_environment.html#sec:gradle_configuration_properties (2021-08-11)
//run with:
//  gradle -q -PgradleProjectProp="gradleProjectProp: Value from CLI" -DlocalValue="System's localValue: Value from CLI" -PoptionalProjectProp="optionalProjectProp: Value from CLI"  printProps
//
//    OR
//  gradle -q -PgradleProjectProp="gradleProjectProp: Value from CLI" -DlocalValue="System's localValue: Value from CLI" -Dorg.gradle.project.optionalProjectProp="optionalProjectProp: Value from CLI"  printProps
//
// (et sim)

// It appears that project-level properties can be accessed via `-D` if the property name is prepended with org.gradle.project
val commandLineProjectProp: String by project  //must be provided by command line
val gradleProjectProp: String by project  //provided by gradle.properties
val optionalProjectProp: String by project
val optionalSystemProp: String by project

tasks.register("printProps") {
    doLast {
        if(project.hasProperty("commandLineProjectProp")) {
            println(commandLineProjectProp)
        }
        println(gradleProjectProp)
        if(project.hasProperty("optionalProjectProp")) {
            println(optionalProjectProp)
        }
        println(System.getProperty("someVariable"))
        println(System.getProperty("localValue"))
    }
}