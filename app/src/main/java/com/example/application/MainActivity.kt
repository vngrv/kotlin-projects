package com.example.application

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var displayScrollView: HorizontalScrollView
    private lateinit var displayTextView: TextView
    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonCopy: Button
    private lateinit var buttonDot: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonOpeningBrace: Button
    private lateinit var buttonClosingBrace: Button
    private lateinit var buttonResult: Button
    private lateinit var buttonReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewsById()
        configureScrollView()
        setOnClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_DISPLAY_EXPRESSION, displayTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        displayTextView.text = savedInstanceState.getString(KEY_DISPLAY_EXPRESSION)
    }

    private fun findViewsById() {
        displayScrollView = findViewById(R.id.displayScrollView)
        displayTextView = findViewById(R.id.displayTextView)
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonCopy = findViewById(R.id.buttonCopy)
        buttonDot = findViewById(R.id.buttonDot)
        buttonPlus = findViewById(R.id.buttonPlus)
        buttonMinus = findViewById(R.id.buttonMinus)
        buttonMultiply = findViewById(R.id.buttonMultiply)
        buttonDivide = findViewById(R.id.buttonDivide)
        buttonOpeningBrace = findViewById(R.id.buttonOpeningBrace)
        buttonClosingBrace = findViewById(R.id.buttonClosingBrace)
        buttonResult = findViewById(R.id.buttonResult)
        buttonReset = findViewById(R.id.buttonReset)
    }

    private fun configureScrollView() {
        displayScrollView.isSmoothScrollingEnabled = false
    }

    private fun setOnClickListeners() {
        button0.setOnClickListener {
            enterSymbol("0")
        }
        button1.setOnClickListener {
            enterSymbol("1")
        }
        button2.setOnClickListener {
            enterSymbol("2")
        }
        button3.setOnClickListener {
            enterSymbol("3")
        }
        button4.setOnClickListener {
            enterSymbol("4")
        }
        button5.setOnClickListener {
            enterSymbol("5")
        }
        button6.setOnClickListener {
            enterSymbol("6")
        }
        button7.setOnClickListener {
            enterSymbol("7")
        }
        button8.setOnClickListener {
            enterSymbol("8")
        }
        button9.setOnClickListener {
            enterSymbol("9")
        }
        buttonCopy.setOnClickListener {
            copyDisplayExpressionToClipboard()
        }
        buttonDot.setOnClickListener {
            enterDot()
        }
        buttonPlus.setOnClickListener {
            enterSymbol("+")
        }
        buttonMinus.setOnClickListener {
            enterSymbol("-")
        }
        buttonMultiply.setOnClickListener {
            enterSymbol("*")
        }
        buttonDivide.setOnClickListener {
            enterSymbol("/")
        }
        buttonOpeningBrace.setOnClickListener {
            enterSymbol("(")
        }
        buttonClosingBrace.setOnClickListener {
            enterSymbol(")")
        }
        buttonResult.setOnClickListener {
            calculateExpression()
        }
        buttonReset.setOnClickListener {
            resetTextView()
        }
    }

    private fun enterSymbol(symbol: String) {
        displayTextView.text.toString().let {
            val newText = if (it == "0") {
                symbol
            } else {
                it + symbol
            }
            displayTextView.text = newText
            displayTextView.post { displayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) }
        }
    }

    private fun enterDot() {
        displayTextView.text.toString().let {
            if (it.contains('.')) {
                return
            }
            val newText = displayTextView.text.toString() + "."
            displayTextView.text = newText
            displayTextView.post { displayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) }
        }
    }

    private fun calculateExpression() {
        val expressionString = displayTextView.text.toString()
        val expression = Expression(expressionString)
        val result = expression.calculate()
        displayTextView.text = result.toString()
    }

    private fun copyDisplayExpressionToClipboard() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData =
            ClipData.newPlainText(CLIP_DATA_LABEL, displayTextView.text.toString())
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun resetTextView() {
        displayTextView.text = 0.toString()
    }

    companion object {

        private const val KEY_DISPLAY_EXPRESSION = "DISPLAY_EXPRESSION"
        private const val CLIP_DATA_LABEL = "calculator_expression"
    }
}
