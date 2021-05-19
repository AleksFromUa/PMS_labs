package ua.kpi.comsys.IO8120.main.navigation_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ua.kpi.comsys.IO8120.core_ui.MainFragment
import ua.kpi.comsys.IO8120.main.R
import ua.kpi.comsys.IO8120.main.databinding.FragmentNavigationBinding
import ua.kpi.comsys.IO8120.main.graph_screen.GraphScreenFragment
import ua.kpi.comsys.IO8120.main.student_info.StudentInfoFragment
import ua.kpi.comsys.IO8120.feature_films.ui.FilmListFragment
import ua.kpi.comsys.IO8120.feature_gallery.ui.GalleryFragment

class NavigationFragment : MainFragment() {
    private var _binding: FragmentNavigationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NavigationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(NavigationViewModel::class.java)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.navigationView.selectedItemId = when (viewModel.state.value) {
            NavigationViewModel.State.Info -> R.id.student_info
            NavigationViewModel.State.Graph -> R.id.chart
            NavigationViewModel.State.Films -> R.id.films
            NavigationViewModel.State.Gallery -> R.id.gallery
            else -> error("Not supported")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 4

            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> StudentInfoFragment()
                1 -> GraphScreenFragment()
                2 -> FilmListFragment()
                3 -> GalleryFragment()
                else -> error("Not supported")
            }
        }

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.navigationView.selectedItemId = when (position) {
                    0 -> R.id.student_info
                    1 -> R.id.chart
                    2 -> R.id.films
                    3 -> R.id.gallery
                    else -> error("Not supported")
                }
            }
        })

        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                NavigationViewModel.State.Info -> binding.pager.currentItem = 0
                NavigationViewModel.State.Graph -> binding.pager.currentItem = 1
                NavigationViewModel.State.Films -> binding.pager.currentItem = 2
                NavigationViewModel.State.Gallery -> binding.pager.currentItem = 3
                else -> error("Not supported: $it")
            }
        })

        binding.navigationView.setOnNavigationItemSelectedListener {
            viewModel.state.value = when (it.itemId) {
                R.id.student_info -> NavigationViewModel.State.Info
                R.id.chart -> NavigationViewModel.State.Graph
                R.id.films -> NavigationViewModel.State.Films
                R.id.gallery -> NavigationViewModel.State.Gallery
                else -> error("Not supported: ${it.itemId}")
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}