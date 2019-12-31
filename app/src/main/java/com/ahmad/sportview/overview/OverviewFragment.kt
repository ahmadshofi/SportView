package com.ahmad.sportview.overview


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmad.sportview.R

import com.ahmad.sportview.databinding.FragmentOverviewBinding
import com.ahmad.sportview.network.SportApiFilter

/**
 * A simple [Fragment] subclass.
 */
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photoGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })


        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it){
                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when(item.itemId){
                R.id.show_teamvsteam -> SportApiFilter.SHOW_TEAMVSTEAM
                R.id.show_EvenSport -> SportApiFilter.SHOW_EVENSPORT
                else -> SportApiFilter.SHOW_ALL
            }
        )
        return true
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateFilter(SportApiFilter.SHOW_ALL)
    }
}
