package com.leomoraes.marvelapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leomoraes.marvelapp.R
import com.leomoraes.marvelapp.data.model.Hero
import com.leomoraes.marvelapp.data.model.ViewState
import com.leomoraes.marvelapp.extensions.invisible
import com.leomoraes.marvelapp.extensions.visible
import com.leomoraes.marvelapp.presentation.adapters.HeroAdapter
import com.leomoraes.marvelapp.presentation.dialogs.GenericErrorDialog
import com.leomoraes.marvelapp.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
    GenericErrorDialog.Listener {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var viewAdapter: HeroAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecycler()
        setupViewModel()

        previousButton.setOnClickListener {
            viewModel.previousPage()
        }
        nextButton.setOnClickListener {
            viewModel.nextPage()
        }
    }

    private fun setupRecycler() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = HeroAdapter(object : HeroAdapter.HeroAdapterListener {
            override fun onClick(id: String) {
                startDetailActivity(id)
            }
        })
        heroRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.heroLiveData.observe(this, Observer {
            handleHeroState(it)
        })
    }

    private fun handleHeroState(viewState: ViewState<List<Hero>>) {
        viewState.handleIt(
            success = {
                viewAdapter.setData(it)
                heroRecyclerView.adapter?.notifyDataSetChanged()
                heroRecyclerView.scrollToPosition(0)
                onStopLoading()
            },
            error = {
                GenericErrorDialog(getString(R.string.dialog_error_generic)).show(
                    this.supportFragmentManager,
                    ""
                )
                onStopLoading()
            },
            loading = {
                loadingProgressBar.visible()
                nextButton.invisible()
                previousButton.invisible()
            }
        )
    }

    private fun onStopLoading() {
        loadingProgressBar.invisible()

        if (viewModel.hasNext())
            nextButton.visible()
        else
            nextButton.invisible()

        if (viewModel.hasPrevious())
            previousButton.visible()
        else
            previousButton.invisible()
    }

    private fun startDetailActivity(id: String) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(HERO_ID, id)
        }
        startActivity(intent)
    }

    companion object {
        const val HERO_ID = "hero_id"
    }

    override fun onDialogOkAction() {
        this.finish()
    }
}
