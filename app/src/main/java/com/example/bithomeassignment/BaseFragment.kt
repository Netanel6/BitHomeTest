package com.example.bithomeassignment

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Netanel Amar on 07/03/2022.
 * NetanelCA2@gmail.com
 */
abstract class BaseFragment : Fragment() {
    private val  TAG = this::class.simpleName.toString()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNetworkState()
        initViews()
        initClicks()
        onFragmentReady()
    }

    abstract fun observeNetworkState()
    abstract fun initViews()
    abstract fun initClicks()
    abstract fun onFragmentReady()
    val mainActivity: MainActivity
        get() = this.activity as MainActivity

}