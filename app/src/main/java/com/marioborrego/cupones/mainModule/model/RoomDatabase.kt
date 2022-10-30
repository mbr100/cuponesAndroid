package com.marioborrego.cupones.mainModule.model

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.marioborrego.cupones.CouponsAplication
import com.marioborrego.cupones.common.dataAccess.CouponDao
import com.marioborrego.cupones.common.entities.CouponEntity
import com.marioborrego.cupones.common.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDatabase {
    private val dao: CouponDao by lazy { CouponsAplication.database.couponDao()}

    suspend fun consultCouponByCode(code: String) = dao.consultCouponByCode(code)

    suspend fun saveCoupon(couponEntity: CouponEntity) = withContext(Dispatchers.IO) {
        try {
            dao.addCoupon(couponEntity)
        } catch (e: java.lang.Exception) {
            (e as? SQLiteConstraintException)?.let { throw Exception(Constant.ERROR_EXIST) }
            throw Exception(e.message ?: Constant.ERROR_UNKNOW)
        }
    }
}