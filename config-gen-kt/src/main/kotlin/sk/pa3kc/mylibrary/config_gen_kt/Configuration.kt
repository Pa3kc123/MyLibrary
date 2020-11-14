package sk.pa3kc.mylibrary.config_gen_kt

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Configuration(
    val configFilePath: String
)
