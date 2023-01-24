package com.yusufarisoy.composepagingapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yusufarisoy.composepagingapp.R
import com.yusufarisoy.composepagingapp.ui.search.SearchFragment
import com.yusufarisoy.composepagingapp.ui.theme.ComposePagingAppTheme
import com.yusufarisoy.composepagingapp.util.collectIn
import com.yusufarisoy.composepagingapp.view.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            ComposePagingAppTheme {
                HomeScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.eventFlow
            .onEach { event ->
                when (event) {
                    is HomeEvent.OpenSearch -> openSearch()
                }
            }
            .collectIn(viewLifecycleOwner)
    }

    private fun openSearch() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SearchFragment())
            .addToBackStack(null)
            .commit()
    }
}
