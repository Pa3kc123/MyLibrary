package sk.pa3kc.mylibrary

import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic

const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
@SupportedAnnotationTypes("sk.pa3kc.mylibrary.Configuration")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {
    private lateinit var kaptGeneratedDir: String

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        this.kaptGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        try {
            processImpl(annotations, roundEnv)
        } catch (e: Exception) {
            fatalError(e.stackTraceToString())
        }

        return true
    }

    private fun processImpl(annotations: Set<TypeElement>, roundEnv: RoundEnvironment) {
        roundEnv.getElementsAnnotatedWith(Configuration::class.java).forEach {
            val pack = super.processingEnv.elementUtils.getPackageOf(it).toString()
            processClass(it as TypeElement, pack)
        }
    }

    private fun processClass(element: TypeElement, pack: String) {
        val variables = element.enclosedElements.filterIsInstance<VariableElement>()

        File(this.kaptGeneratedDir, "${pack.replace('.', '/')}/${element.simpleName}.kt").run {
            if (!exists()) {
                parentFile.mkdirs()
                createNewFile()
            }

            outputStream().use {
                with(it) {
                    writeTextLine("package $pack.${element.simpleName}")
                    writeTextLine()
                    writeTextLine("class ${element.simpleName} {")
                    writeTextLine()
                    writeTextLine("}")
                    writeTextLine()
                }
            }
        }
    }

    private fun fatalError(msg: String) = super.processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, msg)
}

private fun FileOutputStream.writeTextLine(text: String = "", charset: Charset = Charsets.UTF_8) = this.write("$text${System.lineSeparator()}".toByteArray(charset))
