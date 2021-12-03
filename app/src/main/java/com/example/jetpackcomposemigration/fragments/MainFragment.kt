package com.example.jetpackcomposemigration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.jetpackcomposemigration.CustomApplication
import com.example.jetpackcomposemigration.R
import com.example.jetpackcomposemigration.databinding.ItemSampleBinding
import com.example.jetpackcomposemigration.databinding.MainFragmentBinding
import com.example.jetpackcomposemigration.ext.dp
import com.example.jetpackcomposemigration.ext.loadImage
import com.example.jetpackcomposemigration.models.binding.DataBindingModel
import com.example.jetpackcomposemigration.utils.autoCleared
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { vmFactory }
    private var binding by autoCleared<MainFragmentBinding>()
    private var adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hackApplication = requireActivity().application as CustomApplication
        hackApplication.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerMainFragment.adapter = adapter

        viewModel.displayBindingModels.observe(viewLifecycleOwner) { bindingModels ->
            adapter.updateAsync(bindingModels.map { Item(it) })
        }
        viewModel.initialize()
    }

    private class Item(private val data: DataBindingModel) : BindableItem<ItemSampleBinding>() {
        override fun getLayout(): Int = R.layout.item_sample

        override fun initializeViewBinding(view: View): ItemSampleBinding = ItemSampleBinding.bind(view)

        override fun bind(viewBinding: ItemSampleBinding, position: Int) {
            viewBinding.apply {
                imageItemThumbnail.loadImage(
                    path = data.thumbnail,
                    width = 50.dp,
                    height = 50.dp
                )
                textItemId.text = data.id.toString()
                textItemMessage.text = data.message
            }
        }
    }
}