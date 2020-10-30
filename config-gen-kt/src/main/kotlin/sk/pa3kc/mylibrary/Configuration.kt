package sk.pa3kc.mylibrary

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Configuration(
    val configFilePath: String
)
