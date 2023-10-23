package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.challenge_4_ilyasa_adam_naufal.activity.LoginMenu
import com.example.challenge_4_ilyasa_adam_naufal.database.profile.ProfileDatabase
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentProfileBinding
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.ProfileViewModel
import com.example.challenge_4_ilyasa_adam_naufal.viewmodelfactory.ProfileViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

	private var _binding: FragmentProfileBinding? = null
	private val binding get() = _binding!!
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var profileViewModel: ProfileViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentProfileBinding.inflate(inflater, container, false)

		val db = ProfileDatabase.getInstance(requireContext())
		val profileDAO = db.profileDAO()
		profileViewModel = ViewModelProvider(
			requireActivity(),
			ProfileViewModelFactory(profileDAO)
		)[ProfileViewModel::class.java]
		firebaseAuth = Firebase.auth

		getProfile()

		logout()

		return binding.root
	}

	private fun getProfile() {
		profileViewModel.getUserByEmail()
		profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
			binding.tvEmail.text = it.email
			binding.tvMobileProfile.text = it.mobile

		}


	}

	private fun logout() {
		binding.tvLogout.setOnClickListener {
			firebaseAuth.signOut()
			val intent = Intent(requireContext(), LoginMenu::class.java)
			startActivity(intent)
		}

		binding.logoutButton.setOnClickListener {
			firebaseAuth.signOut()
			val intent = Intent(requireContext(), LoginMenu::class.java)
			startActivity(intent)
		}

	}
}