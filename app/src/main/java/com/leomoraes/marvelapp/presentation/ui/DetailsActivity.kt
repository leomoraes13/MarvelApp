package com.leomoraes.marvelapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.leomoraes.marvelapp.R
import com.leomoraes.marvelapp.data.model.Hero
import com.leomoraes.marvelapp.data.model.ViewState
import com.leomoraes.marvelapp.extensions.gone
import com.leomoraes.marvelapp.extensions.invisible
import com.leomoraes.marvelapp.extensions.loadLargeImage
import com.leomoraes.marvelapp.extensions.visible
import com.leomoraes.marvelapp.presentation.dialogs.GenericErrorDialog
import com.leomoraes.marvelapp.presentation.ui.MainActivity.Companion.HERO_ID
import com.leomoraes.marvelapp.presentation.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity(),
    GenericErrorDialog.Listener {
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getStringExtra(HERO_ID)
        id?.let {
            setupViewModel(id)
        }
    }

    private fun setupViewModel(id: String) {
        viewModel.heroLiveData.observe(this, Observer {
            handleHeroState(it)
        })
        viewModel.loadData(id)
    }

    private fun handleHeroState(viewState: ViewState<List<Hero>>) {
        viewState.handleIt(
            success = {
                val item = it[0]
                detailsTextView.text = item.name

                if (item.description.isEmpty())
                    detailsDescriptionTextView.gone()
                else
                    detailsDescriptionTextView.text = item.description

                detailsImageView.loadLargeImage(item.image)

                loadingProgressBar.invisible()
            },
            error = {
                GenericErrorDialog(getString(R.string.dialog_error_generic)).show(
                    this.supportFragmentManager,
                    ""
                )
                loadingProgressBar.invisible()
            },
            loading = {
                loadingProgressBar.visible()
            }
        )
    }

    override fun onDialogOkAction() {
        this.finish()
    }
}
