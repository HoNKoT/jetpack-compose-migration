package com.example.jetpackcomposemigration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.example.jetpackcomposemigration.CustomApplication
import com.example.jetpackcomposemigration.databinding.MainFragmentBinding
import com.example.jetpackcomposemigration.ext.baseMarginDp
import com.example.jetpackcomposemigration.ext.halfMarginDp
import com.example.jetpackcomposemigration.models.binding.DataBindingModel
import com.example.jetpackcomposemigration.utils.autoCleared
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
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

    @Composable
    fun PlantDetailDescription() {
        Text("TEST")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false).apply {
            composeView.setContent { MainContent() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerMainFragment.adapter = adapter
        viewModel.initialize()
    }

    @Composable
    fun MainContent() {
        val listModels = remember { mutableStateListOf<DataBindingModel>() }
        viewModel.displayBindingModels.observe(viewLifecycleOwner) { listModels.addAll(it) }
        viewModel.onAddedModels.observe(viewLifecycleOwner) { listModels.add(it) }
        viewModel.onRemovedModels.observe(viewLifecycleOwner) { listModels.remove(it) }

        MaterialTheme {
            Box(
                modifier = Modifier
                    .background(Color(0xFFEDEAE0))
                    .fillMaxSize()
            ) {
                LazyColumn {
                    items(listModels) { bindingModel ->
                        Item(
                            data = bindingModel,
                            onClickedRemove = { viewModel.removeModel(it) }
                        )
                    }
                }

                FloatingActionButton(
                    onClick = { viewModel.addNewModel() },
                    modifier = Modifier
                        .padding(end = baseMarginDp, bottom = baseMarginDp)
                        .align(Alignment.BottomEnd),
                    backgroundColor = Color(0xFFFE4164)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
        }
    }

    @Composable
    fun Item(data: DataBindingModel, onClickedRemove: (data: DataBindingModel) -> Unit) {
        Surface(
            elevation = Dp(2f),
            color = Color.White,
            shape = RoundedCornerShape(Dp(4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = halfMarginDp,
                    bottom = halfMarginDp,
                    start = baseMarginDp,
                    end = baseMarginDp
                ),
        ) {
            Row(
                modifier = Modifier.padding(
                    top = halfMarginDp,
                    bottom = halfMarginDp,
                    start = baseMarginDp,
                    end = baseMarginDp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.foundation.Image(
                    painter = rememberImagePainter(data.thumbnail),
                    contentDescription = null,
                    modifier = Modifier
                        .height(Dp(48f))
                        .padding(Dp(4f))
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            start = halfMarginDp,
                            end = halfMarginDp
                        )
                ) {
                    Text(
                        color = Color.Black,
                        text = data.id.toString()
                    )
                    Text(
                        color = Color.Gray,
                        text = data.message
                    )
                }
                TextButton(
                    onClick = { onClickedRemove.invoke(data) },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color(0xFF960018),
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Remove")
                }
            }
//            Divider(
//                modifier = Modifier.padding(start = baseMarginDp),
//                thickness = Dp(1f),
//                color = Color.LightGray
//            )
        }
    }
}