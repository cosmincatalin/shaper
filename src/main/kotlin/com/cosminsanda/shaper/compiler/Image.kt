package com.cosminsanda.shaper.compiler

import com.cosminsanda.shaper.ast.Shaper
import com.cosminsanda.shaper.parsing.ShaperParserFacade
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO


class ImageCompiler {
    fun compile(root: Shaper): BufferedImage {
        print(root)
        val width = 250
        val height = 250

        // Constructs a BufferedImage of one of the predefined image types.
        val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        // Create a graphics which can be used to draw into the buffered image
        val g2d = bufferedImage.createGraphics()

        // fill all the image with white
        g2d.color = Color.white
        g2d.fillRect(0, 0, width, height)

        // create a circle with black
        g2d.color = Color.black
        g2d.fillOval(0, 0, width, height)

        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose()


        return bufferedImage
    }
}

fun main(args: Array<String>) {
    val code = FileInputStream(File(".samples/test001.shape"))

    val parsingResult = ShaperParserFacade.parse(code)

    val root = parsingResult.root

    val bi = ImageCompiler().compile(root)
    val file = File("myimage.png")
    ImageIO.write(bi, "jpg", file)
}