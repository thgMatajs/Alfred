package com.gentalhacode.alfred.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.adapters.ShoppingListAdapter
import com.gentalhacode.alfred.bottomsheet.AddUserBottomSheet
import com.gentalhacode.alfred.bottomsheet.ProductBottomSheet
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.alfred.model.ViewUser
import com.gentalhacode.alfred.model.toView
import com.gentalhacode.alfred.presentation.extensions.*
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetAllShoppingListViewModel
import com.gentalhacode.model.entities.IProduct
import com.gentalhacode.util.onScrollListener
import com.gentalhacode.util.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShoppingListFragment : Fragment(R.layout.fragment_shopping_list) {

    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var loading: LottieAnimationView
    private val getAllViewModel: GetAllShoppingListViewModel by viewModel()
    private val createViewModel: CreateShoppingListViewModel by viewModel()
    private val adapter: ShoppingListAdapter by lazy { buildAdapter() }
    private val currentUser: ViewUser by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setupRv()
        initActionsViews()
        getAllViewModel.getAll()
        observeGetAllShoppingListLiveData()
        observeCreateShoppingListLiveData()
    }

    private fun buildAdapter(): ShoppingListAdapter {
        return ShoppingListAdapter(
            addProductOnClick = {
                buildProductBottomSheet(it)
            },
            addUserOnClick = {
                buildAddUserInListBottomSheet(it)
            }
        )
    }

    private fun findViews(view: View) {
        view.run {
            rv = findViewById(R.id.shoppingListRv)
            fab = findViewById(R.id.shoppingListFab)
            loading = findViewById(R.id.loading)
        }
    }

    private fun setupRv() {
        rv.adapter = adapter
        rv.onScrollListener { _, dy ->
            if (dy > 0) {
                fab.hide()
            } else {
                fab.show()
            }
        }
    }

    private fun initActionsViews() {
        fab.setOnClickListener {
        }
    }

    private fun observeGetAllShoppingListLiveData() {
        getAllViewModel.observeGetAllLiveData().observe(viewLifecycleOwner, Observer { viewState ->
            viewState.handle(
                onLoading = { loading.setVisible() },
                onSuccess = { allGrocery ->
                    adapter.submitList(allGrocery?.map { it.toView() })
                    loading.setGone()
                },
                onFailure = {
                    loggerE(it?.message ?: "")
                    it.sendCrashlytics()
                    toast("Não foi possível carregar suas listas de compras.")
                    loading.setGone()
                }
            )
        })
    }

    private fun observeCreateShoppingListLiveData() {
        createViewModel.observeCreateLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { /*getCurrentShoppingList()*/ },
                onFailure = {
                    it.sendCrashlytics()
                    loggerE(it?.message ?: "")
                }
            )
        })
    }

    private fun buildAddUserInListBottomSheet(shoppingList: ViewGrocery) {
        AddUserBottomSheet().apply {
            setOnClickAdd { email ->
                    shoppingList.emailUsers.apply {
                        if (!contains(email)) {
                            add(email)
                            createViewModel.create(shoppingList)
                            toast("Colaborador adicionado com sucesso.")
                        } else {
                            toast("Este colaborador já tem acesso a está lista.")
                        }
                        loggerS(shoppingList.emailUsers.toString())
                    }

                dismiss()
            }
            setOnClickCancel { dismiss() }
            show(requireActivity().supportFragmentManager, AddUserBottomSheet.TAG)
        }
    }

    private fun buildProductBottomSheet(shoppingList: ViewGrocery) {
        ProductBottomSheet().apply {
            setOnClickAdd { viewProduct ->
                loggerD(viewProduct.toString())
                    val newProducts: MutableList<IProduct> =
                        shoppingList.products.toMutableList()
                    newProducts.add(viewProduct)
                    shoppingList.products = newProducts.toList()
                    createViewModel.create(shoppingList)
                dismiss()
            }
            setOnClickCancel { dismiss() }
            show(requireFragmentManager(), ProductBottomSheet.TAG)
        }
    }
}
