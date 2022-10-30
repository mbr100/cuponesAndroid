package com.marioborrego.cupones.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marioborrego.cupones.R
import com.marioborrego.cupones.common.entities.CouponEntity
import com.marioborrego.cupones.common.utils.getMsgErrorByCode
import com.marioborrego.cupones.mainModule.model.MainRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repository = MainRepository()
    private val snackbarMsg = MutableLiveData<Int>()
    val coupon = MutableLiveData(CouponEntity())
    private val hideKeyboard = MutableLiveData<Boolean>()

    fun isHideKeyboard() = hideKeyboard

    fun getSnackbarMsg() = snackbarMsg

    fun consultCouponByCode() {
        coupon.value?.code?.let { code ->
            viewModelScope.launch {
                hideKeyboard.value = true
                coupon.value = repository.consultCouponByCode(code)
            }
        }
    }

    fun saveCoupon(){
        coupon.value?.let { couponEntity ->
            viewModelScope.launch {
                hideKeyboard.value = true
                try {
                    repository.saveCoupon(couponEntity)
                    consultCouponByCode()
                    snackbarMsg.value = R.string.main_save_success
                } catch (e: Exception) {
                    snackbarMsg.value = getMsgErrorByCode(e.message)
                }
            }
        }
    }
}