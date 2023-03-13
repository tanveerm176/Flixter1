package com.codepath.bestsellerlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.bestsellerlistapp.R.id

/**
 * [RecyclerView.Adapter] that can display a [NowPlayingMovie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NowPlayingMoviesRecyclerViewAdapter(
    private val movies: List<NowPlayingMovie>,
    private val mListener: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<NowPlayingMoviesRecyclerViewAdapter.MovieViewHolder>()
    {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NowPlayingMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
//        val mBookAuthor: TextView = mView.findViewById<View>(id.book_author) as TextView
        val mMovieOverview : TextView = mView.findViewById<View>(id.movie_overview) as TextView
//        val mBookRank : TextView = mView.findViewById<View>(id.ranking) as TextView
        val mMoviePoster : ImageView = mView.findViewById<View>(id.movie_poster) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString() + " '" + mMovieOverview.text + "'"
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val posterURLfull = "https://image.tmdb.org/t/p/w500/" + movie.moviePosterUrl.toString()

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
//        holder.mBookAuthor.text = movie.author
        holder.mMovieOverview.text = movie.overview
//        holder.mBookRank.text = movie.rank.toString()

//      This takes the image from the given URL, loads it, and stores it into the ImageView. Neat!

        Glide.with(holder.mView).load(posterURLfull).centerInside().into(holder.mMoviePoster)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}