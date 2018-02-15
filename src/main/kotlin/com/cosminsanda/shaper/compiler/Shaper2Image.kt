@file:Suppress("UNCHECKED_CAST")

package com.cosminsanda.shaper.compiler

import com.audienceproject.util.cli.Arguments
import com.cosminsanda.shaper.parsing.ShaperParserFacade
import org.apache.commons.io.FileUtils
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.*
import java.util.concurrent.Executors
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

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val arguments = Arguments(args)
            val executor = Executors.newCachedThreadPool()

            if (arguments.isSet("source-code") && arguments.isSet("out-filename")) {
                val code = ByteArrayInputStream(arguments.arguments()["source-code"].get().get().toByteArray())
                val task = Para(code, arguments.arguments()["out-filename"].get().get())
                executor.submit(task)
            } else if (arguments.isSet("source-file")) {
                val code = FileInputStream(File(arguments.arguments()["source-file"].get().get()))
                val task = Para(code, arguments.arguments()["source-file"].get().get() + ".png")
                executor.submit(task)
            } else if (arguments.isSet("source-dir")) {

                val files = FileUtils.listFiles(File(arguments.arguments()["source-dir"].get().get()), Array(1, { _ -> "shape"}), true) as Collection<File>

                files.forEach {
                    if (it.extension == "shape") {
                        val code = FileInputStream(File(it.absolutePath))
                        val task = Para(code, it.absolutePath + ".png")
                        executor.submit(task)
                    }
                }
            } else {
                println("No valid arguments provided. Please read the README.md file at https://github.com/cosmincatalin/shaper")
            }

            executor.shutdown()
        }

    }
}

class Para(val code: InputStream, val dest: String) : Runnable {
    override fun run() {
        val res = Shaper2Image().compile(code)
        val img = ImageIO.read(ByteArrayInputStream(res))
        val outputfile = File(dest)
        ImageIO.write(img, "png", outputfile)
    }

}