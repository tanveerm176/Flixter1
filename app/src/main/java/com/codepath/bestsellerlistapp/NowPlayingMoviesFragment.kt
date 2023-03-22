package com.codepath.bestsellerlistapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

/*
the fragment for our app, responsible for the networking
A Fragment represents a reusable portion of your app's UI.
 A fragment defines and manages its own layout, has its own lifecycle,
 and can handle its own input events.
 Fragments cannot live on their own--
 they must be hosted by an activity or another fragment.
 */

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY
        // Using the client, perform the HTTP request
        client["https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1.json",
                    //"https://api.themoviedb.org/3/books/v3/lists/current/hardcover-fiction.json",
                params,
                object :
                    JsonHttpResponseHandler() {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
//                Get the "results" json out of the response, as another JSONObject
//                        val resultsJSON: JSONObject = json.jsonObject.get("results") as JSONObject
                        val resultsJSON: JSONArray = json.jsonObject.getJSONArray("results") as JSONArray


//                Get the "books" from those results, as a String:
                        val moviesRawJSON: String = resultsJSON.toString()

                        //Gson.fromJson() requires two things:
                        // a raw JSON input, and the type it should convert to.
                        // Since our "books" result is a list of books, we'll tell gson to treat it as
                        // a List of BestSellerBook objects:

//                         The wait for a response is over
                         progressBar.hide()
//
//                //TODO - Parse JSON into Models
                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<NowPlayingMovie>>() {}.type
                        val models: List<NowPlayingMovie> = gson.fromJson(moviesRawJSON, arrayMovieType)

                        recyclerView.adapter =
                            NowPlayingMoviesRecyclerViewAdapter(models, this@NowPlayingMoviesFragment)
//
//                // Look for this in Logcat:
                        Log.d("NowPlayingMovieFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("NowPlayingMovieFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular movie is clicked.
     */
    override fun onItemClick(item: NowPlayingMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
