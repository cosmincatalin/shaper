package com.cosminsanda.shaper.compiler

import com.cosminsanda.shaper.parsing.ShaperParserFacade
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO


class Shaper2Image {

    fun compile(input: InputStream): ByteArray {
        val root = ShaperParserFacade.parse(input).root
        val dim = root.dim

        val bufferedImage = BufferedImage(dim, dim, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()
        g2d.color = Color.white
        g2d.fillRect(0, 0, dim, dim)

        g2d.color = Color.black
        var j = 0
        root.rows.forEach{
            var i = 0
            it.shapes.forEach {
                when(it.type) {
                    "square" -> {
                        g2d.fillRect(i * 33, j * 33, 32, 32)
                    }
                    "circle" -> {
                        g2d.fillOval(i * 33, j * 33, 32, 32)
                    }
                    "triangle" -> {
                        val x = intArrayOf(i * 33, i * 33 + 16, i * 33 + 32)
                        val y = intArrayOf(j * 33 + 32, j * 33, j * 33 + 32)
                        g2d.fillPolygon(x, y, 3)
                    }
                }
                i++
            }
            j++
        }

        g2d.dispose()
        val baos = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", baos)
        baos.flush()
        val imageInByte = baos.toByteArray()
        baos.close()
        return imageInByte

    }
}