package com.example.mbcapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbcapp.databinding.FragmentSurveyListBinding
import com.example.mbcapp.viewmodels.SurveyListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyListFragment : Fragment() {

    private lateinit var binding : FragmentSurveyListBinding
    private val viewModel by viewModels<SurveyListViewModel>()
    private lateinit var mAdapter : SurveyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurveyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSurveyListBinding.bind(view)

        binding.surveysRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mAdapter = SurveyListAdapter(
            onItemClick = {
                //val action = SearchArtistFragmentDirections.actionListFragmentToDetailFragment(it)
                //view.findNavController().navigate(action)
            }
        )

        binding.surveysRv.adapter = mAdapter

        viewModel.surveyList.observe(viewLifecycleOwner) {
            mAdapter.setItems(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    }

}