package com.ivzb.craftlog.feature.addeditnote.usecase

import android.webkit.URLUtil
import com.ivzb.craftlog.domain.model.Link
import com.ivzb.craftlog.util.NetworkUtils
import org.jsoup.Jsoup
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import javax.inject.Inject

class FetchLinkUseCase @Inject constructor(
    private val networkUtils: NetworkUtils
) {

    suspend fun fetchLinkMetaData(url: String?): Link? {
        if (url == null) {
            return null
        }

        if (!networkUtils.hasNetworkConnection()) {
            return null
        }

        return try {
            fetch(url)
        } catch (e: Exception) {
            null
        }
    }

    @Throws(IOException::class)
    private fun fetch(url: String): Link {
        val doc = Jsoup.connect(url)
            .timeout(10 * 1000)
            .get()

        val elements = doc.getElementsByTag("meta")

        var title: String? = doc.select("meta[property=og:title]").attr("content")

        if (title.isNullOrEmpty()) {
            title = doc.title()
        }

        val imageElements = doc.select("meta[property=og:image]")
        var imageUrl: String? = null

        if (imageElements.size > 0) {
            val image = imageElements.attr("content")
            if (!image.isEmpty()) {
                imageUrl = resolveURL(url, image)
            }
        }

        if (imageUrl == null) {
            var src = doc.select("link[rel=image_src]").attr("href")

            if (src.isNotEmpty()) {
                imageUrl = resolveURL(url, src)
            } else {
                src = doc.select("link[rel=apple-touch-icon]").attr("href")

                if (src.isNotEmpty()) {
                    imageUrl = resolveURL(url, src)
                } else {
                    src = doc.select("link[rel=icon]").attr("href")

                    if (src.isNotEmpty()) {
                        imageUrl = resolveURL(url, src)
                    }
                }
            }
        }

        var site: String? = null

        for (element in elements) {
            if (element.hasAttr("property")) {
                val property = element.attr("property").toString().trim { it <= ' ' }

                if (property == "og:site_name") {
                    site = element.attr("content").toString()
                }
            }
        }

        return Link(
            url = url,
            site = site,
            title = title,
            imageUrl = imageUrl
        )
    }

    private fun resolveURL(url: String, part: String): String {
        if (URLUtil.isValidUrl(part)) {
            return part
        }

        return try{
            URI(url).resolve(part).toString()
        } catch (e: URISyntaxException) {
            ""
        }
    }
}
