package br.com.smvtech.testetecnico.core.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/***
 * Criar as mascaras de campos de texto (CPF, Telefone, CEP, etc)
 */
class MaskUtils {
    class CpfVisualTransformation : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val cleanText = text.text.filter { it.isDigit() }

            var out = ""
            for (i in cleanText.indices) {
                out += cleanText[i]
                if (i == 2 || i == 5) out += "."
                if (i == 8) out += "-"
            }

            val offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 2) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 8) return offset + 2
                    if (offset <= 11) return offset + 3
                    return out.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 7) return offset - 1
                    if (offset <= 11) return offset - 2
                    if (offset <= 14) return offset - 3
                    return cleanText.length
                }
            }

            return TransformedText(androidx.compose.ui.text.AnnotatedString(out), offsetMapping)
        }
    }

    class PhoneVisualTransformation : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val cleanText = text.text.filter { it.isDigit() }

            var out = ""
            for (i in cleanText.indices) {
                if (i == 0) out += "("
                out += cleanText[i]
                if (i == 1) out += ") "
                if (i == 6) out += "-"
            }

            val offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 0) return offset
                    if (offset <= 2) return offset + 1
                    if (offset <= 7) return offset + 3
                    if (offset <= 11) return offset + 4
                    return out.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 0) return offset
                    if (offset <= 3) return offset - 1
                    if (offset <= 4) return offset - 2
                    if (offset <= 10) return offset - 3
                    if (offset <= 15) return offset - 4
                    return cleanText.length
                }
            }

            return TransformedText(AnnotatedString(out), offsetMapping)
        }
    }
}