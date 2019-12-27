package com.gentalhacode.alfred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gentalhacode.alfred.activities.SignInActivity
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.alfred.model.ViewUser
import com.gentalhacode.alfred.presentation.extensions.loggerE
import com.gentalhacode.alfred.presentation.extensions.loggerL
import com.gentalhacode.alfred.presentation.extensions.loggerS
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.DeleteShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetAllShoppingListViewModel
import com.gentalhacode.alfred.presentation.shopping_list.GetShoppingListViewModel
import com.gentalhacode.model.entities.IGrocery
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
    private val currentUser: FirebaseUser? by lazy { fbAuth.currentUser }
    private lateinit var deleteShoppingList: IGrocery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeCreateShoppingListLiveData()
        observeGetAllShoppingListLiveData()
        observeGetShoppingListLiveData()
        observeDeleteShoppingListLiveData()
        initActionsViews()

    }

    private fun initActionsViews() {
        btnLogout.setOnClickListener { signOut() }
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
        }
    }

    private fun observeCreateShoppingListLiveData() {
        createViewModel.observeCreateLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS() },
                onFailure = { loggerE(it?.message ?: "")}
            )
        })
    }

    private fun observeGetAllShoppingListLiveData() {
        getAllViewModel.observeGetAllLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS(it.toString()) },
                onFailure = { loggerE(it?.message ?: "")}
            )
        })
    }

    private fun observeGetShoppingListLiveData() {
        getViewModel.observeGetShoppingListLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS(it.toString()) },
                onFailure = { loggerE(it?.message ?: "")}
            )
        })
    }

    private fun observeDeleteShoppingListLiveData() {
        deleteViewModel.observeDeleteLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { loggerL() },
                onSuccess = { loggerS(it.toString()) },
                onFailure = { loggerE(it?.message ?: "")}
            )
        })
    }

    private fun makeShoppingList(): IGrocery {

        val product = ViewProduct(
            id = UUID.randomUUID().toString(),
            image = "pathImage",
            name = "Batata_1",
            price = "5.50",
            amount = "6",
            barcode = "HAHAHAJJJ121231231",
            isInTheCart = false
        )

        val product1 = ViewProduct(
            id = UUID.randomUUID().toString(),
            image = "pathImage",
            name = "Pera_2",
            price = "3.50",
            amount = "2",
            barcode = "HAHAHAJJJ121231231",
            isInTheCart = false
        )
        return ViewGrocery(
            id = UUID.randomUUID().toString(),
            isActive = true,
            products = listOf(product1, product),
            users = listOf(currentUser?.uid ?: "")
        )
    }

    private fun signOut() {
        fbAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
