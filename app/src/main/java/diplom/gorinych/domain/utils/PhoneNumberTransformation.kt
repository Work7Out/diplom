package diplom.gorinych.domain.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return phoneNumFilterBel(text)
    }
}

private fun phoneNumFilterBel(text: AnnotatedString): TransformedText {

    // +XXX(XX)_XXX_XX_XX
    val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text
    var out = ""
    for (i in trimmed.indices) {
        if (i==0) out += "+"
        if (i==3) out += "("
        out += trimmed[i]
        if (i==4) out +=") "
        if (i==7 || i==9 ) out += " "
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return offset
            if (offset <= 3) return offset +1
            if (offset <= 4) return offset +2
            if (offset <= 8) return offset +4
            if (offset <= 10) return offset +5
            if (offset <= 12) return offset +6
            return 18

        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=0) return offset
            if (offset <=4) return offset -1
            if (offset <=8) return offset -2
            if (offset <=13) return offset -4
            if (offset <=16) return offset -5
            if (offset <=19) return offset -6
            return 12
        }
    }

    return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
}

private fun phoneNumFilterRus(text: AnnotatedString): TransformedText {

    // +X(XXX)_XXX_XX_XX
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
    var out = ""
    for (i in trimmed.indices) {
        if (i==0) out += "+"
        if (i==1) out += "("
        out += trimmed[i]
        if (i==3) out +=") "
        if (i==6 || i==8 ) out += " "

    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return offset
            if (offset <= 1) return offset +1
            if (offset <= 3) return offset +2
            if (offset <= 7) return offset +4
            if (offset <= 9) return offset +5
            if (offset <= 11) return offset +6
            return 17

        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=0) return offset
            if (offset <=2) return offset -1
            if (offset <=7) return offset -2
            if (offset <=12) return offset -4
            if (offset <=15) return offset -5
            if (offset <=18) return offset -6
            return 11
        }
    }

    return TransformedText(AnnotatedString(out), phoneNumberOffsetTranslator)
}