package com.geekbrains.myfirsttests

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class EmailValidatorTest {

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("kisel@mail.ru"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("kisel@mail..ru"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@mail.ru"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    @Test
    fun emailValidator_EmptyDomain() {
        assertFalse(EmailValidator.isValidEmail("name@"))
    }

    @Test
    fun assert_EqualsInt() {
        assertEquals(2, 1 + 1)
    }

    @Test
    fun assertNotNull_Email() {
        assertNotNull(EmailValidator.isValidEmail("kisel@mail.ru"))
    }

    private val a1 = A(0)
    private val a2 = A(0)

    @Test
    fun assertSame_testAssertSame() {
        assertSame(a1, a2)
    }

    @Test
    fun assertSame_testAssertEquals() {
        assertEquals(a1, a2)
    }

}

class A(private val i: Int) {
    override fun equals(o: Any?): Boolean {
        // self check
        return if (this === o) {
            true
        } else
            if (o == null) {
                false
            } else if (javaClass != o.javaClass) {
                    false
                } else {
                    val a = o as A
                    Objects.equals(a, a)
                }
    }
}