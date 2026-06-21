plugins {
    id("library-convention")
}

dependencies {

    api(libs.jackson.databind)
    api(libs.jackson.annotations)
    api(libs.jackson.datatype)

}
