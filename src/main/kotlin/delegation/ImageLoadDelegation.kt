package delegation

/**
 * Created by Rajendhiran Easu on 07/10/22.
 * Description: Delegation `by` Trails with image source loader
 */

fun main() {
    val url = "http://yourapp.photos.now/photo/0"

    ImageLoad(GlideImpl(url, "")).apply {
        println("ImageLoad: $loaderName")
        onLoad()
        transition()
    }

    ImageLoad(CoilImpl(url, "")).apply {
        println("ImageLoad: $loaderName")
        onLoad()
        transition()
    }
}

interface ImgSrcLoader {
    val loaderName: String
    fun onLoad()
    fun transition()
}

class GlideImpl(private val url: String, private val view: Any) : ImgSrcLoader {
    override val loaderName: String
        get() = "Glide"

    override fun onLoad() {
        println("Initialize $loaderName: with $url")
    }

    override fun transition() {
        println("Implementation with $loaderName transition Works...")
    }
}

class CoilImpl(private val url: String, private val view: Any) : ImgSrcLoader {
    override val loaderName: String
        get() = "COIL"

    override fun onLoad() {
        println("Initialize $loaderName: with $url")
    }

    override fun transition() {
        println("Implementation with $loaderName transition Works...")
    }
}

class ImageLoad(private val iLoader: ImgSrcLoader) : ImgSrcLoader by iLoader {
    // This property change will not reflect/accessed on
    // the super class implementations.

    /* override val loaderName: String
         get() = "NA"*/

    override fun transition() {
        if (iLoader is CoilImpl) {
            iLoader.transition()
            return
        }
        println("Transition with ${iLoader.loaderName} is restricted")
    }
}


/*class ImagesTest : ImgSrcLoader by CoilImpl("", ""), ImgDecode by PNGDecoder() {

        fun tester() {
            onLoad()
            transition()
            parse("")
        }

interface ImgDecode {
    fun parse(bitmap: Any)
}

class GIFDecoder : ImgDecode {
    override fun parse(bitmap: Any) {
        println("Decoder GIF Image")
    }
}

class PNGDecoder : ImgDecode {
    override fun parse(bitmap: Any) {
        println("Decoder PNG Image")
    }
}
 */