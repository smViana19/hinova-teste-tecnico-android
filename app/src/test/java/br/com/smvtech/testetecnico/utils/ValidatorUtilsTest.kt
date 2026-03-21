package br.com.smvtech.testetecnico.utils

import br.com.smvtech.testetecnico.core.utils.ValidatorUtils
import org.junit.Test
import org.junit.Assert.*


class ValidatorUtilsTest {
    @Test
    fun when_document_is_not_valid_return_false() {
        val document = "123.123.123-02"
        assertEquals(false, ValidatorUtils().isValidCpf(document))
    }
    @Test
    fun when_document_is_blank_return_false() {
        val document = ""
        assertEquals(false, ValidatorUtils().isValidCpf(document))

    }

    @Test
    fun when_document_is_valid_return_true() {
        val document = "704.680.916-02"
        assertEquals(true, ValidatorUtils().isValidCpf(document))

    }


}