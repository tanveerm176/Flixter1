package com.codepath.bestsellerlistapp

/**
 * This interface is used by the [NowPlayingMoviesRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [NowPlayingMoviesFragment]
 */

//an interface used by the RecyclerView adapter


interface OnListFragmentInteractionListener {
    fun onItemClick(item: NowPlayingMovie)
}
