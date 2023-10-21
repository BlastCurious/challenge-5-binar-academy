package com.example.challenge_4_ilyasa_adam_naufal.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDao
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailViewModel(application: Application) : ViewModel() {

	private val _counter = MutableLiveData(1)
	val counter: LiveData<Int> = _counter

	private val _totalPrice = MutableLiveData<Int>()
	val totalPrice: LiveData<Int> = _totalPrice

	private val _selectedItem = MutableLiveData<DataListMenu>()

	private val cartDao: CartDao

	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = CartDatabase.getInstance(application)
		cartDao = db.cartDao
	}

	private fun insert(cart: Cart) {
		executorService.execute {
			cartDao.insert(cart)
		}
	}


	fun initSelectedItem(data: DataListMenu) {
		_selectedItem.value = data
	}

	fun increment() {
		_counter.value = (_counter.value ?: 1) + 1
	}

	fun decrement() {
		val currentValues = _counter.value ?: 1
		if (currentValues > 1) {
			_counter.value = currentValues - 1
		}
	}

	fun addToCart(note: String) {
		val selectedItem = _selectedItem.value
		selectedItem?.let {
			val itemKeranjang = Cart(
				itemName = it.nama.toString(),
				itemNote = note,
				itemPrice = it.harga,
				totalPrice = it.harga!!.times(counter.value!!.toInt()),
				itemQuantity = counter.value!!.toInt(),
				imgId = it.imageUrl.toString()
			)
			insert(itemKeranjang)
		}
	}

}


