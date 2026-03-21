package br.com.smvtech.testetecnico.core.utils


/**
 * Criar validacao de CPF com logica
 *
 */
class ValidatorUtils {

    fun isValidCpf(document: String): Boolean {
        val digits = document.filter { it.isDigit() }.map {
            it.toString().toInt()
        }
        if (digits.size != 11) {
            return false
        }
        if (digits.all { it == digits[0] }) {
            return false
        }

        val digitValid1 = ((0..8).sumOf { (it + 1) * digits[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val digitValid2 =
            ((0..8).sumOf { it * digits[it] }.let { (it + (digitValid1 * 9)).rem(11) }).let {
                if (it >= 10) 0 else it
            }

        return digits[9] == digitValid1 && digits[10] == digitValid2

    }


}