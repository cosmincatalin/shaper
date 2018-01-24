package com.cosminsanda.shaper.compiler

import com.cosminsanda.shaper.ast.Shape
import com.cosminsanda.shaper.parsing.ShaperParserFacade
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageCompiler {
    fun compile(root: Shape): ByteArray {
        throw UnsupportedOperationException(this.javaClass.canonicalName)
    }
}

fun main(args: Array<String>) {
    val code = FileInputStream(File("/Users/cosmin/test.shape"))

    val parsingResult = ShaperParserFacade.parse(code)

    val root = parsingResult.root!!

    val bytes = ImageCompiler().compile(root)
    val img = FileOutputStream("img.png")
    img.write(bytes)
    img.close()
}