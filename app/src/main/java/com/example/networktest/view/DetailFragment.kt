package com.example.networktest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.networktest.model.presentation.VolumeItem

class DetailFragment: Fragment() {

    companion object{
        const val DETAIL_BOOK = "DetailBook"

        fun newInstance(book: VolumeItem): DetailFragment{
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DETAIL_BOOK, book)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}