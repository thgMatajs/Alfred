package com.gentalhacode.alfred

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.gentalhacode.alfred.activities.SignInActivity
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.alfred.model.ViewUser
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val createViewModel: CreateShoppingListViewModel by viewModel()
    private val fbAuth: FirebaseAuth by inject()
    private val currentUser: FirebaseUser? by lazy { fbAuth.currentUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogout.setOnClickListener {
            fbAuth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        btnCreate.setOnClickListener {
            val user = ViewUser(
                id = currentUser?.uid ?: "",
                name = "THG_NAME",
                email = currentUser?.email ?: "",
                image = currentUser?.photoUrl.toString(),
                nickName = "THG_GENTALHA"
            )

            val product = ViewProduct(
                id = UUID.randomUUID().toString(),
                image = "pathImage",
                name = "Batata_1",
                price = "5.50",
                amount = "6",
                barcode = "HAHAHAJJJ121231231",
                isInTheCart = false
            )
            val shoppingList = ViewGrocery(
                id = "Nte0j4aDF8SToBFRdgiV",
                isActive = true,
                products = listOf(product),
                users = listOf(user)
            )

            createViewModel.create(shoppingList)
        }

        createViewModel.observeCreateLiveData().observe(this, Observer { viewState ->
            viewState.handle(
                onLoading = { println("THG_LOG:: LOADING") },
                onSuccess = { println("THG_LOG:: SUCCESS") },
                onFailure = { println("THG_LOG:: ERROR --> ${it?.message}")}
            )
        })
    }
}
