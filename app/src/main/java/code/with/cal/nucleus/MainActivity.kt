//package com.example.myapplication
package code.with.cal.nucleus

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import android.os.Bundle
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import code.with.cal.nucleus.R.*

class MainActivity : AppCompatActivity() {

    private lateinit var result: TextView
    private var currentNumber = ""
    private var previousNumber = ""
    private var currentOperator = ""
    private var isOperatorClicked = false
    private var isEqualClicked = false


//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.activity_main_land)
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.activity_main)
//        }
//    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        supportActionBar?.setTitle("Ma super Calco")
        result = findViewById(id.result)

        result.setTextIsSelectable(true)
        result.setOnLongClickListener(OnLongClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            val clip = ClipData.newPlainText("Texte Copié", result.getText())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(applicationContext, "texte bien copié !", Toast.LENGTH_SHORT)

                .show()
            true
        })
        if (savedInstanceState != null) {


            var res = savedInstanceState.getString("res") ?: ""
            currentNumber = savedInstanceState.getString("currentNumber") ?: ""
            currentOperator = savedInstanceState.getString("currentOperator") ?: ""
            previousNumber = savedInstanceState.getString("previousNumber") ?: ""
            isOperatorClicked = savedInstanceState.getBoolean("isOperatorClicked") ?: false
            isEqualClicked = savedInstanceState.getBoolean("isEqualClicked") ?: false

            result.text = res
        }

        val zeroButton: Button = findViewById(id.zero)
        zeroButton.setOnClickListener { appendOnExpression("0", true) }

        val oneButton: Button = findViewById(id.one)
        oneButton.setOnClickListener { appendOnExpression("1", true) }

        val twoButton: Button = findViewById(id.two)
        twoButton.setOnClickListener { appendOnExpression("2", true) }

        val threeButton: Button = findViewById(id.three)
        threeButton.setOnClickListener { appendOnExpression("3", true) }

        val fourButton: Button = findViewById(id.four)
        fourButton.setOnClickListener { appendOnExpression("4", true) }

        val fiveButton: Button = findViewById(id.five)
        fiveButton.setOnClickListener { appendOnExpression("5", true) }

        val sixButton: Button = findViewById(id.six)
        sixButton.setOnClickListener { appendOnExpression("6", true) }

        val sevenButton: Button = findViewById(id.seven)
        sevenButton.setOnClickListener { appendOnExpression("7", true) }

        val eightButton: Button = findViewById(id.eight)
        eightButton.setOnClickListener { appendOnExpression("8", true) }

        val nineButton: Button = findViewById(id.nine)
        nineButton.setOnClickListener { appendOnExpression("9", true) }

        val addButton: Button = findViewById(id.add)
        addButton.setOnClickListener { setOperator("+") }

        val subtractButton: Button = findViewById(id.subtract)
        subtractButton.setOnClickListener { setOperator("-") }

        val multiplyButton: Button = findViewById(id.multipli)
        multiplyButton.setOnClickListener { setOperator("*") }

        val divideButton: Button = findViewById(id.divide)
        divideButton.setOnClickListener { setOperator("/") }
        val moduloButton: Button = findViewById(id.modulo)
        moduloButton.setOnClickListener { setOperator("%") }

        val negateButton: Button = findViewById(id.negate)
        negateButton.setOnClickListener { negate() }

        val pointButton: Button = findViewById(id.point)
        pointButton.setOnClickListener {  }

        val equalButton: Button = findViewById(id.equal)
        equalButton.setOnClickListener { calculate() }

        val clearButton: Button = findViewById(id.clear)
        clearButton.setOnClickListener { clear() }

        val resetButton: Button = findViewById(id.reset)
        resetButton.setOnClickListener { reset() }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var res = result.text.toString()
        outState.putString("currentNumber", currentNumber)
        outState.putString("res", res)
        outState.putString("currentOperator", currentOperator)
        outState.putString("previousNumber", previousNumber)
        outState.putBoolean("isOperatorClicked", isOperatorClicked)
        outState.putBoolean("isEqualClicked", isEqualClicked)

    }
    private fun appendOnExpression(string: String, canClear: Boolean) {
        if (isOperatorClicked || isEqualClicked || result.text.isNotEmpty() && canClear) {
//            result.text = ""
            isOperatorClicked = false
            isEqualClicked = false
        }
        result.append(string)
        currentNumber += string
    }
    private fun setOperator(operator: String) {
        print(currentNumber)
        print(currentOperator)
//        print(result)

        if (currentOperator!=""){

//            val resultCalcul =calc()
//            result.text = resultCalcul.toString()
//            currentNumber = resultCalcul.toString()
            calculate()
//            currentNumber
        }
        if (currentNumber.isNotEmpty()) {
            previousNumber = currentNumber
            currentNumber = ""
            result.append(operator)
            currentOperator = operator
            isOperatorClicked = true
        }
    }
    private fun negate() {
        print(currentNumber)
        print(previousNumber)
        if (currentNumber.isNotEmpty()) {
            if(currentNumber.toIntOrNull()!=null) currentNumber = currentNumber.toInt().times(-1).toString()
            else if (currentNumber.toDoubleOrNull()!=null) currentNumber = currentNumber.toDouble().times(-1).toString()
            else currentNumber = currentNumber.toInt().times(-1).toString()
            if (previousNumber.isNotEmpty()) {
                result.text = previousNumber+currentOperator+ currentNumber

            }
            else{
                result.text = currentNumber
            }
        } else if (previousNumber.isNotEmpty()) {
            if(previousNumber.toIntOrNull()!=null) previousNumber = previousNumber.toInt().times(-1).toString()
            else if (previousNumber.toDoubleOrNull()!=null) previousNumber = previousNumber.toDouble().times(-1).toString()
            else previousNumber = previousNumber.toInt().times(-1).toString()


            result.text = previousNumber
        }
    }
    private fun calculate() {
//        print("test");
        if (currentNumber.isNotEmpty() && previousNumber.isNotEmpty() && currentOperator.isNotEmpty()) {
            val resultCalcul =calc()
            result.text = resultCalcul.toString()
            isEqualClicked = true
            currentNumber = resultCalcul.toString()
            previousNumber = ""
            currentOperator = ""
        }
    }
    private fun calc(): Double {
        return when (currentOperator) {
            "+" -> previousNumber.toDouble() + currentNumber.toDouble()
            "-" -> previousNumber.toDouble() - currentNumber.toDouble()
            "*" -> previousNumber.toDouble() * currentNumber.toDouble()
            "/" -> previousNumber.toDouble() / currentNumber.toDouble()
            "%" -> previousNumber.toDouble() % currentNumber.toDouble()
            else -> 0.0
        }
    }

    private fun clear() {
        val text = result.text.toString()
        if (text.isNotEmpty()) {
            result.text = text.substring(0, text.length - 1)
            if (isOperatorClicked) {
                currentOperator = ""
                isOperatorClicked = false

            } else {
                val lastChar = text[text.length - 1]
                if(text[0] == '-' && text.length == 2 ){
                    reset() // On retire le signe "-" et le chiffre
                    return
                }

                else if(text.length > 2 && text[text.length - 2] == '-' && text[text.length - 1].isDigit()){
                    result.text = text.substring(0, text.length - 2)
                    currentNumber = currentNumber.substring(0, currentNumber.length - 1)
                    isOperatorClicked = false
                    currentOperator = ""


                }

                else {
                    if (currentNumber.length == 0) {
                        currentNumber = "0"
                        isOperatorClicked = false
                    }
                    else  currentNumber = currentNumber.substring(0, currentNumber.length - 1)

            }
        }
    }
    }

    private fun reset() {
        result.text = ""
        currentNumber = ""
        previousNumber = ""
        currentOperator = ""
        isOperatorClicked = false
        isEqualClicked = false
    }

    fun copyAction(view: View) {
        val text = result.text.toString()
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("result", result.text.toString())
        if (text.isNotEmpty()) {

            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Résultat copié dans le presse-papiers", Toast.LENGTH_SHORT).show()
        }
        else{
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Aucun Résultat a copier dans le presse-papiers", Toast.LENGTH_SHORT).show()
        }
    }
}
