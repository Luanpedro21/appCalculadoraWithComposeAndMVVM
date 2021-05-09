package com.example.appcalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcalculadora.ui.theme.AppCalculadoraTheme


class TelaViewModel:ViewModel()
{
    private val _valorA = MutableLiveData("")
    val valorA: LiveData<String> = _valorA

    private val _valorB = MutableLiveData("")
    val valorB: LiveData<String> = _valorB

    private val _resultado = MutableLiveData("")
    val resultado: LiveData<String> = _resultado

    fun onChangeValorA(newValue:String)
    {
        _valorA.value = newValue;
    }

    fun onChangeValorB(newValue:String)
    {
        _valorB.value = newValue;
    }

    fun onChangeResultado(newValue:String)
    {
        _resultado.value = newValue;
    }

    fun btSomar()
    {

        val vA:Double = _valorA.value!!.toDouble()
        val vB:Double = _valorB.value!!.toDouble()

        val soma = vA+vB;

        onChangeResultado(soma.toString())


    }


}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCalculadoraTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                        TelaScreen()
                }
            }
        }
    }
}

@Composable
fun TelaScreen(telaViewModel: TelaViewModel = viewModel())
{
    val valorA:String by telaViewModel.valorA.observeAsState("")
    val valorB:String by telaViewModel.valorB.observeAsState("")
    val resultado:String by telaViewModel.resultado.observeAsState("")

    TelaCalculadora(valorA = valorA,valorB = valorB,resultado = resultado,
        onChangeValorA = {telaViewModel.onChangeValorA(it)},
        onChangeValorB = {telaViewModel.onChangeValorB(it)},
        onChangeResultado = {telaViewModel.onChangeResultado(it)},
        btSomar = {telaViewModel.btSomar()}
        )

}



@Composable
fun TelaCalculadora(valorA:String,valorB:String,resultado:String,
                    onChangeValorA: (String) -> Unit,
                    onChangeValorB: (String) -> Unit,
                    onChangeResultado: (String) -> Unit,
                    btSomar:()->Unit
                    )
{


    Scaffold(
        topBar = {
            TopAppBar(title={Text("Calculadora")});
            },
        content = {
            Column(

                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



                OutlinedTextField(
                    value = valorA,
                    label = {Text("Informe o valor A")},
                    onValueChange = onChangeValorA,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = valorB,
                    label = {Text("Informe o valor B")},
                    onValueChange = onChangeValorB,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { btSomar() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Calcular a Soma")
                }
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = resultado,
                    label = {Text("")},
                    onValueChange = onChangeResultado,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    )
}