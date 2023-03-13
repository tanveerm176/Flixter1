package com.codepath.bestsellerlistapp

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single book from the NY Times API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the json library.
 */
class NowPlayingMovie {
//    @SerializedName("rank")
//    var rank = 0

    @JvmField
    @SerializedName("title")
    var title: String? = null


    //TODO bookImageUrl
    @SerializedName("poster_path")
    var moviePosterUrl :String? = null

    //TODO description
    //@SerializedName An annotation that indicates this member should be serialized
    // to JSON with the provided name value as its field name
    @SerializedName("overview") //
    var overview : String? = null

    //TODO-STRETCH-GOALS amazonUrl
}