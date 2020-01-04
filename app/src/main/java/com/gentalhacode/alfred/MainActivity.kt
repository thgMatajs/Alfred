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
import com.gentalhacode.alfred.model.ViewProduct
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
    private var currentShoppingList: ViewGrocery? = null
    private val adapter: ShoppingListAdapter by lazy { buildAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllViewModel.getAll()
        observeCreateShoppingListLiveData()
        observeGetAllShoppingListLiveData()
//        observeGetShoppingListLiveData()
//        observeDeleteShoppingListLiveData()
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
        btnFinishProductList.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply {
                setTitle("Deseja finalizar está lista?")
                setMessage("Ao finalizar está lista não será mais possivel interagir com ela, deseja prosseguir?")
                setPositiveButton("Sim") { _, _ ->
                    finalizeProductList()
                }
                setNegativeButton("Cancelar") { _, _ -> }
                show()
            }
        }
    }

    private fun buildAdapter(): ShoppingListAdapter {
        return ShoppingListAdapter(
            isCheckedOnClick = { product, checked ->
                currentShoppingList?.apply {
                    val mutableList = products.toMutableList()
                    mutableList.map {
                        if (it.id == product.id) {
                            it.isInTheCart = checked
                        }
                    }
                    products = mutableList
                    createViewModel.create(this)
                }
            },
            deleteOnClick = { viewProduct ->
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Deseja deletar este item?")
                    setMessage("Realmente deseja deletar este item de sua lista de compras?")
                    setPositiveButton("Sim") { _, _ ->
                        currentShoppingList?.apply {
                            val mutableList = products.toMutableList()
                            mutableList.removeAll { iProduct ->
                                iProduct.id == viewProduct.id
                            }
                            products = mutableList
                            createViewModel.create(this)
                            fab.show()
                        }
                    }
                    setNegativeButton("Cancelar") { _, _ -> }
                    show()
                }
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
                        handledProductList(shoppingList.products.map { it.toView() })
                    } else {
                        handledProductList(emptyList())
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

    private fun handledProductList(products: List<ViewProduct>) {
        if (products.isEmpty()) {
            emptyView.setVisible()
            btnFinishProductList.setGone()
            loading.setGone()
            addUser.setGone()
            adapter.submitList(products)
        } else {
            adapter.submitList(products)
            btnFinishProductList.setVisible()
            loading.setGone()
            addUser.setVisible()
            emptyView.setGone()
        }
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
        if (currentShoppingList == null) {
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
                currentShoppingList?.let { shoppingList ->
                    val newProducts: MutableList<IProduct> =
                        shoppingList.products.toMutableList()
                    newProducts.add(viewProduct)
                    shoppingList.products = newProducts.toList()
                    createViewModel.create(shoppingList)
                }
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
                currentShoppingList?.let { shoppingList ->
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
                }
                dismiss()
            }
            setOnClickCancel { dismiss() }
            show(supportFragmentManager, AddUserBottomSheet.TAG)
        }
    }

    private fun finalizeProductList() {
        makeShoppingList()
        currentShoppingList?.apply {
            active = false
            createViewModel.create(this)
        }
        currentShoppingList = null
        toast("Lista finalizada com sucesso.")
    }

    private fun signOut() {
        fbAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
