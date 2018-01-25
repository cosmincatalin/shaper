### How to use it

Sample `.shape` file content

```256>>>circle,square,square,triangle,triangle|triangle,circle|square,circle,triangle,square<<<```

Sample `Kotlin` code that useses an external `.shape` file to produce a `.png` file

```kotlin
val code = FileInputStream(File("test001.shape"))
val res = Shaper2Image().compile(code)
val img = ImageIO.read(ByteArrayInputStream(res))
val outputfile = File("myshape.png")
ImageIO.write(img, "png", outputfile)
```