plugins {
    id("library-convention")
    id("modulith-convention")
}

dependencies {

    api(project(":framework:framework-web"))
    api(project(":framework:framework-jpa"))
    api(project(":framework:framework-kafka"))
    api(project(":framework:framework-cache"))
    api(project(":framework:framework-security"))

    testImplementation(testFixtures(project(":framework:framework-test")))

}