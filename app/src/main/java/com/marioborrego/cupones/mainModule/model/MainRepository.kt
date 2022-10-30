package com.marioborrego.cupones.mainModule.model

import com.marioborrego.cupones.common.entities.CouponEntity
import com.marioborrego.cupones.common.utils.Constant
import com.marioborrego.cupones.common.utils.validateTextCode

class MainRepository {
    private val roomDatabase = RoomDatabase()

    suspend fun consultCouponByCode(code: String) = roomDatabase.consultCouponByCode(code)

    suspend fun saveCoupon(couponEntity: CouponEntity) {
        if(validateTextCode(couponEntity.code)){
            roomDatabase.saveCoupon(couponEntity)
        } else {
            throw Exception(Constant.ERROR_LENGTH)
        }
    }
}