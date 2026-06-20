plugins {
    // todo: SonarQube
}


tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}