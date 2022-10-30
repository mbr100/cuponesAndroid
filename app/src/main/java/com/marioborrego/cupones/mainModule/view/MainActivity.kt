package com.marioborrego.cupones.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.marioborrego.cupones.R
import com.marioborrego.cupones.common.entities.CouponEntity
import com.marioborrego.cupones.common.utils.hideKeyboard
import com.marioborrego.cupones.databinding.ActivityMainBinding
import com.marioborrego.cupones.mainModule.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupObservers()
        setupButtons()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setupObservers() {
        mainViewModel.getResult().observe(this){ coupon ->
            if (coupon == null) {
                binding.tilDescripcion.hint = getString(R.string.main_hint_descripcion)
                binding.tilDescripcion.isEnabled = true
                binding.btnCreate.visibility = View.VISIBLE
            } else {
                binding.etDescipcion.setText(coupon.descripcion)
                val status = getString(if (coupon.isActive) R.string.main_hint_active else R.string.main_hint_descripcion)
                binding.tilDescripcion.hint = status
                binding.tilDescripcion.isEnabled = false
                binding.btnCreate.visibility = if (coupon.isActive) View.GONE else View.VISIBLE
            }
        }

        mainViewModel.getSnackbarMsg().observe(this){ msg ->
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupButtons() {
        binding.btnConsult.setOnClickListener {
            mainViewModel.consultCouponByCode(binding.etCupon.text.toString())
            hideKeyboard(this, binding.root)
        }

        binding.btnCreate.setOnClickListener {
            val coupon = CouponEntity(code = binding.etCupon.text.toString(), descripcion = binding.etDescipcion.text.toString())
            mainViewModel.saveCoupon(coupon)
            hideKeyboard(this, binding.root)
        }
    }
}