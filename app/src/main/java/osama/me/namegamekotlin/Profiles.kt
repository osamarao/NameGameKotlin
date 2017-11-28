package osama.me.namegamekotlin

import com.google.gson.annotations.SerializedName

data class Person(var id: String,
                  var slug: String? = null,
                  var type: String? = null,
                  var jobTitle: String? = null,
                  var firstName: String? = null,
                  var lastName: String? = null,
                  var headshot: Headshot,
                  var socialLinks: List<SocialLink>? = null) {

    val viewmodel: PersonViewModel
        get() = PersonViewModel(id, "$firstName $lastName", socialLinks, headshot)
}

data class Headshot(var type: String? = null,
                    var mimeType: String? = null,
                    var id: String? = null,
                    @SerializedName("url")
                    private var url_: String?,
                    var alt: String? = null,
                    var height: Int? = null,
                    var width: Int? = null) {
    var trueUrl: String = ""
        get() = "http:${url_ ?: "placeholder"}"
}


data class SocialLink(@SerializedName("type") var typeInString: String,
                      var callToAction: String? = null,
                      var url: String? = null) {

    var SLtype: SocialLinkType? = null
        get() = when (typeInString) {
            "facebook" -> FacebookSocialLink
            "twitter" -> TwitterSocialLink
            "linkedin" -> LinkedInSocialLink
            else -> NotKnownSocialLink(typeInString)
        }
}


sealed class SocialLinkType

object FacebookSocialLink : SocialLinkType()
object TwitterSocialLink : SocialLinkType()
object LinkedInSocialLink : SocialLinkType()
data class NotKnownSocialLink(val actual: String) : SocialLinkType()


class PersonViewModel(val id: String, val name: String, val socialLinks: List<SocialLink>?, val headshot: Headshot)
