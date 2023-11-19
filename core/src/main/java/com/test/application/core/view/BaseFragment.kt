package com.test.application.core.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.test.application.core.utilities.AppState

typealias Inflate<F> = (LayoutInflater, ViewGroup?, Boolean) -> F

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<T: AppState, I, VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    protected lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        progressBar = findProgressBar()
        return binding.root
    }

    abstract fun findProgressBar(): ProgressBar

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                showWorkingView()
                val data = appState.data as I
                setupData(data)
            }

            is AppState.Loading -> {
                showViewLoading()
            }

            is AppState.Error -> {
                showWorkingView()
                showErrorDialog(appState.error.message)
            }
        }
    }

    abstract fun setupData(data: I)

    private fun showViewLoading() {
        progressBar.animate().cancel()
        progressBar.apply {
            alpha = 1.0f
            visibility = View.VISIBLE
        }
    }

    abstract fun showErrorDialog(message: String?)

    private fun showWorkingView() {
        progressBar.animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    progressBar.visibility = View.GONE
                }
            })
    }
}