package com.marioborrego.cupones.common.utils

import org.junit.Assert.*
import org.junit.Test

class CouponsUtilKtTest {
    @Test
    fun validateTextCodeSuccessTest() {
        val code ="Welcome"
        assertTrue(validateTextCode(code))
    }
}