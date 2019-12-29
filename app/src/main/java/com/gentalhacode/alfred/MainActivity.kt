package com.gentalhacode.alfred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gentalhacode.alfred.activities.SignInActivity
import com.gentalhacode.alfred.adapters.ShoppingListAdapter
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.alfred.model.ViewUser
import com.gentalhacode.alfred.model.toView
import com.gentalhacode.alfred.presentation.extensions.loggerE
import com.gentalhacode.alfred.presentation.extensions.loggerL
import com.gentalhacode.alfred.presentation.extensions.loggerS
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.DeleteShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetAllShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetShoppingListViewModel
import com.gentalhacode.model.entities.IGrocery
import com.gentalhacode.model.entities.IUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val createViewModel: CreateShoppingListViewModel by viewModel()
    private val getAllViewModel: GetAllShoppingListViewModel by viewModel()
    private val getViewModel: GetShoppingListViewModel by viewModel()
    private val deleteViewModel: DeleteShoppingListViewModel by viewModel()
    private val fbAuth: FirebaseAuth by inject()
    private val currentUser: ViewUser by inject()
    private lateinit var currentShoppingList: IGrocery
    private lateinit var deleteShoppingList: IGrocery

    private val adapter: ShoppingListAdapter by lazy {
        ShoppingListAdapter { product, isChecked ->
            currentShoppingList.products.first { product.id == it.id }.isInTheCart = isChecked
            createViewModel.create(currentShoppingList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getViewModel.get("c2cc8c55-882d-4957-8fb3-88c09bbb1ae8")
//        observeCreateShoppingListLiveData()
//        observeGetAllShoppingListLiveData()
        observeGetShoppingListLiveData()
        observeDeleteShoppingListLiveData()
        initActionsViews()
    }

    private fun initActionsViews() {
        rvProduct.adapter = adapter
        /*btnLogout.setOnClickListener { signOut() }
        btnCreate.setOnClickListener {
            deleteShoppingList = makeShoppingList()
            createViewModel.create(deleteShoppingList)
        }
        btnGetAll.setOnClickListener {
            getAllViewModel.getAll()
        }
        btnGetOne.setOnClickListener {
            getViewModel.get("c2cc8c55-882d-4957-8fb3-88c09bbb1ae8")
        }
        btnDelete.setOnClickListener {
            deleteViewModel.delete(deleteShoppingList)
        }*/
    }

    private fun observeCreateShoppingListLiveData() {
        createViewModel.observeCreateLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS() },
                onFailure = { loggerE(it?.message ?: "") }
            )
        })
    }

    private fun observeGetAllShoppingListLiveData() {
        getAllViewModel.observeGetAllLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS(it.toString()) },
                onFailure = { loggerE(it?.message ?: "") }
            )
        })
    }

    private fun observeGetShoppingListLiveData() {
        getViewModel.observeGetShoppingListLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = {
                    loggerS(it.toString())
                    it?.let { iGrocery ->
                        currentShoppingList = iGrocery
                        val products = it.products.map { product ->
                            product.toView()
                        }
                        adapter.submitList(products)
                        loggerS(products.toString())
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

    private fun makeShoppingList(): IGrocery {
        return ViewGrocery(
            id = UUID.randomUUID().toString(),
            isActive = true,
            products = listOf(),
            emailUsers = listOf(currentUser.email)
        )
    }

    private fun signOut() {
        fbAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
