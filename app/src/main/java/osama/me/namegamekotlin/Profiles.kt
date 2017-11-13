package osama.me.namegamekotlin

data class Profiles(var profiles: List<Person>)

data class Person(var id: String,
                  var slug: String? = null,
                  var type: String? = null,
                  var jobTitle: String? = null,
                  var firstName: String? = null,
                  var lastName: String? = null,
                  var headshot: Headshot? = null,
                  var socialLinks: List<SocialLink>? = null)

data class Headshot(var type: String? = null,
                    var mimeType: String? = null,
                    var id: String? = null,
                    var url: String? = null,
                    var alt: String? = null,
                    var height: Int? = null,
                    var width: Int? = null)


data class SocialLink(var type: String? = null,
                 var callToAction: String? = null,
                 var url: String? = null)