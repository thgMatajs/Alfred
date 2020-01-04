package com.gentalhacode.alfred

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gentalhacode.alfred.activities.SignInActivity
import com.gentalhacode.alfred.adapters.ShoppingListAdapter
import com.gentalhacode.alfred.bottomsheet.AddUserBottomSheet
import com.gentalhacode.alfred.bottomsheet.ProductBottomSheet
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.alfred.model.ViewUser
import com.gentalhacode.alfred.model.toView
import com.gentalhacode.alfred.presentation.extensions.*
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.DeleteShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetAllShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetShoppingListViewModel
import com.gentalhacode.model.entities.IProduct
import com.gentalhacode.util.onScrollListener
import com.gentalhacode.util.randomUuid
import com.gentalhacode.util.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val createViewModel: CreateShoppingListViewModel by viewModel()
    private val getAllViewModel: GetAllShoppingListViewModel by viewModel()
    private val getViewModel: GetShoppingListViewModel by viewModel()
    private val deleteViewModel: DeleteShoppingListViewModel by viewModel()
    private val fbAuth: FirebaseAuth by inject()
    private val currentUser: ViewUser by inject()
    private lateinit var currentShoppingList: ViewGrocery
    private val adapter: ShoppingListAdapter by lazy { buildAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllViewModel.getAll()
        observeCreateShoppingListLiveData()
        observeGetAllShoppingListLiveData()
//        observeGetShoppingListLiveData()
        observeDeleteShoppingListLiveData()
        initActionsViews()
    }

    private fun initActionsViews() {
        rvProduct.adapter = adapter
        rvProduct.onScrollListener { _, dy ->
            if (dy > 0) {
                fab.hide()
            } else {
                fab.show()
            }
        }
        fab.setOnClickListener { buildProductBottomSheet() }
        addUser.setOnClickListener { buildAddUserInListBottomSheet() }
    }

    private fun buildAdapter(): ShoppingListAdapter {
        return ShoppingListAdapter(
            isCheckedOnClick = { product, checked ->
                val mutableList = currentShoppingList.products.toMutableList()
                mutableList.map {
                    if (it.id == product.id) {
                        it.isInTheCart = checked
                    }
                }
                currentShoppingList.products = mutableList
                createViewModel.create(currentShoppingList)
            },
            deleteOnClick = { viewProduct ->
                val mutableList = currentShoppingList.products.toMutableList()
                mutableList.removeAll { iProduct ->
                    iProduct.id == viewProduct.id
                }
                currentShoppingList.products = mutableList
                createViewModel.create(currentShoppingList)
            })
    }

    private fun observeCreateShoppingListLiveData() {
        createViewModel.observeCreateLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { /*getCurrentShoppingList()*/ },
                onFailure = { loggerE(it?.message ?: "") }
            )
        })
    }

    private fun observeGetAllShoppingListLiveData() {
        getAllViewModel.observeGetAllLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loading.setVisible() },
                onSuccess = { allGrocery ->
                    if (allGrocery != null && allGrocery.isNotEmpty()) {
                        val shoppingList = allGrocery.first()
                        currentShoppingList = shoppingList.toView()
                        adapter.submitList(shoppingList.products.map { it.toView() })
                        loading.setGone()
                    } else {
                        adapter.submitList(emptyList())
                        loading.setGone()
                    }

                },
                onFailure = {
                    loggerE(it?.message ?: "")
                    toast("Não foi possível carregar sua lista de compras.")
                }
            )
        })
    }

    private fun observeGetShoppingListLiveData() {
        getViewModel.observeGetShoppingListLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = {
                    it?.let { iGrocery ->
                        currentShoppingList = iGrocery.toView()
                        val products = it.products.map { product ->
                            product.toView()
                        }
                        adapter.submitList(products)
                    }

                },
                onFailure = { loggerE(it?.message ?: "") }
            )
        })
    }

    private fun observeDeleteShoppingListLiveData() {
        deleteViewModel.observeDeleteLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS(it.toString()) },
                onFailure = { loggerE(it?.message ?: "") }
            )
        })
    }

    private fun makeShoppingList() {
        if (!::currentShoppingList.isInitialized) {
            currentShoppingList = ViewGrocery(
                id = randomUuid(),
                active = true,
                products = mutableListOf(),
                emailUsers = mutableListOf(currentUser.email)
            )
        }
    }

    private fun buildProductBottomSheet() {
        ProductBottomSheet().apply {
            setOnClickAdd { viewProduct ->
                loggerD(viewProduct.toString())
                makeShoppingList()
                val newProducts: MutableList<IProduct> =
                    currentShoppingList.products.toMutableList()
                newProducts.add(viewProduct)
                currentShoppingList.products = newProducts.toList()
                createViewModel.create(currentShoppingList)
                dismiss()
            }
            setOnClickCancel { dismiss() }
            show(supportFragmentManager, ProductBottomSheet.TAG)
        }
    }

    private fun buildAddUserInListBottomSheet() {
        AddUserBottomSheet().apply {
            setOnClickAdd { email ->
                makeShoppingList()
                currentShoppingList.emailUsers.apply {
                    if (!contains(email)) {
                        add(email)
                        createViewModel.create(currentShoppingList)
                    }
                    loggerS(currentShoppingList.emailUsers.toString())
                }
                dismiss()
            }
            setOnClickCancel { dismiss() }
            show(supportFragmentManager, AddUserBottomSheet.TAG)
        }
    }


    private fun signOut() {
        fbAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
