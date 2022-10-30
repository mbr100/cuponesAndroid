package com.marioborrego.cupones.common.dataAccess

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marioborrego.cupones.common.entities.CouponEntity

@Dao
interface CouponDao {
    @Query("SELECT * FROM CouponEntity WHERE code = :code")
    suspend fun consultCouponByCode(code: String): CouponEntity?

    @Insert
    suspend fun addCoupon(couponEntity: CouponEntity): Long
}