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
    private val result = MutableLiveData<CouponEntity>()
    private val snackbarMsg = MutableLiveData<Int>()

    fun getResult() = result

    fun getSnackbarMsg() = snackbarMsg

    fun consultCouponByCode(code: String) {
        viewModelScope.launch {
            result.value = repository.consultCouponByCode(code)
        }
    }

    fun saveCoupon(couponEntity: CouponEntity){
        viewModelScope.launch {
            try {
                repository.saveCoupon(couponEntity)
                consultCouponByCode(couponEntity.code)
                snackbarMsg.value = R.string.main_save_success
            } catch (e: Exception) {
                snackbarMsg.value = getMsgErrorByCode(e.message)
            }
        }
    }
}