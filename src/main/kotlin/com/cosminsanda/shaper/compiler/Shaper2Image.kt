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
        val img_dim = root.img_dim
        val shp_dim = root.shp_dim

        val bufferedImage = BufferedImage(img_dim, img_dim, BufferedImage.TYPE_INT_RGB)
        val g2d = bufferedImage.createGraphics()
        g2d.color = Color.white
        g2d.fillRect(0, 0, img_dim, img_dim)

        g2d.color = Color.black
        var j = 0
        root.rows.forEach{
            var i = 0
            it.shapes.forEach {
                when(it.type) {
                    "square" -> {
                        g2d.fillRect(i * (shp_dim + 1), j * (shp_dim + 1), shp_dim, shp_dim)
                    }
                    "circle" -> {
                        g2d.fillOval(i * (shp_dim + 1), j * (shp_dim + 1), shp_dim, shp_dim)
                    }
                    "triangle" -> {
                        val x = intArrayOf(i * (shp_dim + 1), i * (shp_dim + 1) + shp_dim / 2, i * (shp_dim + 1) + shp_dim)
                        val y = intArrayOf(j * (shp_dim + 1) + shp_dim, j * (shp_dim + 1), j * (shp_dim + 1) + shp_dim)
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
            val threads = if(arguments.isSet("threads")) arguments.arguments()["threads"].get().get().toInt() else 4
            val executor = Executors.newFixedThreadPool(threads)

            if (arguments.isSet("source-code") && arguments.isSet("out-filename")) {
                val code = ByteArrayInputStream(arguments.arguments()["source-code"].get().get().toByteArray())
                val res = Shaper2Image().compile(code)
                val img = ImageIO.read(ByteArrayInputStream(res))
                val outputfile = File(arguments.arguments()["out-filename"].get().get())
                ImageIO.write(img, "png", outputfile)
            } else if (arguments.isSet("source-file")) {
                val code = FileInputStream(File(arguments.arguments()["source-file"].get().get()))
                val res = Shaper2Image().compile(code)
                val img = ImageIO.read(ByteArrayInputStream(res))
                val outputfile = File(arguments.arguments()["source-file"].get().get() + ".png")
                ImageIO.write(img, "png", outputfile)
            } else if (arguments.isSet("source-dir")) {

                val files = FileUtils.listFiles(File(arguments.arguments()["source-dir"].get().get()), Array(1, { _ -> "shape"}), true) as Collection<File>

                files.forEach {
                    if (it.extension == "shape") {
                        val task = Para(it.absolutePath)
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

class Para(val path: String) : Runnable {
    override fun run() {
        val code = FileInputStream(File(path))
        val res = Shaper2Image().compile(code)
        code.close()
        val img = ImageIO.read(ByteArrayInputStream(res))
        val outputfile = File("$path.png")
        ImageIO.write(img, "png", outputfile)
    }

}