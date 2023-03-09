package com.example.unitconvertermvvm.compose.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.unitconvertermvvm.data.Conversion
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun TopScreen(
    list: List<Conversion>,
    selectedConversion: MutableState<Conversion?>,
    inputText: MutableState<String>,
    typedValue: MutableState<String>,
    isLandscape:Boolean,
    save: (String, String) -> Unit
) {

    var toSave by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


        ConversionMenu(list = list, isLandScape = isLandscape) {
            selectedConversion.value = it
        }

        selectedConversion.value?.let {

            InputBlock(conversion = it, inputText = inputText, isLandScape = isLandscape) { input ->
                //  Log.d("TAG", "TopScreen: click  ${it}")
                typedValue.value = input
                toSave = true
            }
        }

        if (typedValue.value != "0.0") {
            val input = typedValue.value.toDouble()
            val multiplyBy = selectedConversion.value!!.multiplyBy
            val result = input * multiplyBy


            // round result to 4 decimal places
            val df = DecimalFormat("#.####")
            df.roundingMode = RoundingMode.DOWN
            val roundedResult = df.format(result)
            val message1 =
                "${typedValue.value} ${selectedConversion.value!!.conversionFrom} is Equal to"
            val message2 = "${roundedResult} ${selectedConversion.value!!.convertTo}"
            if (toSave) {
                save(message1, message2)
                toSave = false
            }

            ResultBlock(message1 = message1, message2 = message2)
        }
    }
}