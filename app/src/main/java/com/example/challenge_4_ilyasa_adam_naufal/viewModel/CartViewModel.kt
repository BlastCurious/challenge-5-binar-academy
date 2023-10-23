package com.example.challenge_4_ilyasa_adam_naufal.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_4_ilyasa_adam_naufal.api.APIClient
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderRequest
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderResponse
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(application: Application) : ViewModel() {
	private val repo: CartRepo = CartRepo(application)

	val allCartItems: LiveData<List<Cart>> = repo.getAllCartItems()

	private val _orderSuccess = MutableLiveData<Boolean>()
	val orderSuccess : LiveData<Boolean> = _orderSuccess

	fun deleteItemCart(cartId: Int) {
		repo.deleteItemCart(cartId)
	}

	fun deleteAllData() {
		repo.deleteAll()
	}

	fun updateQuantityItem(cart: Cart) {
		repo.updateQuantityItem(cart)
	}

	fun increment(cart: Cart) {
		val newTotal = cart.itemQuantity + 1
		cart.itemQuantity = newTotal
		cart.totalPrice = cart.itemPrice!!.times(newTotal)

        updateQuantityItem(cart)
	}

	fun decrement(cart: Cart) {
		var newTotal = cart.itemQuantity - 1
		if (newTotal > 1) {
			newTotal = cart.itemQuantity - 1
		}

		cart.itemQuantity = newTotal
		cart.totalPrice = cart.itemPrice!!.times(newTotal)

        updateQuantityItem(cart)
	}

	fun postData(ordersRequest: OrderRequest) {
		APIClient.instance.postOrder(ordersRequest)
			.enqueue(object : Callback<OrderResponse> {
				override fun onResponse(
					call: Call<OrderResponse>,
					response: Response<OrderResponse>
				) {
					val body = response.body()
					val code = response.code()

					if (response.isSuccessful) {
						_orderSuccess.postValue(true)
						deleteAllData()
					} else {
						_orderSuccess.postValue(false)
					}
				}

				override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
					Log.d("ERROR", "msg: $t")
				}
			})
	}
}
