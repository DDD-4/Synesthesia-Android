import java.io.File
import java.util.*

object PropertiesExt {
    private const val FILE_SCRURE = "secure.properties"
    const val KAKAO = "kakao"

    private fun secure() : Properties {
        val secureProperties = Properties()
        File(FILE_SCRURE).run {
            println("${exists()}")
            if(exists()) {
                secureProperties.load(inputStream())
            }
        }
        return secureProperties
    }

    fun getKakaoKey() : String = secure().getProperty(KAKAO) ?: ""

}
