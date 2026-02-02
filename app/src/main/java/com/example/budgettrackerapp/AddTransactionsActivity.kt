package com.example.budgettrackerapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.ImageButton




class AddTransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_transactions)

        val labelInput = findViewById<TextInputEditText>(R.id.labelInput)
        val addTransactionBtn = findViewById<Button>(R.id.addTransactionBtn)
        val amountInput = findViewById<TextInputEditText>(R.id.amountInput)
        val LabelLayout = findViewById<TextInputLayout>(R.id.LabelLayout)
        val amountLayout = findViewById<TextInputLayout>(R.id.amountLayout)
        val closeBtn = findViewById<ImageButton>(R.id.closeBtn)



        labelInput.addTextChangedListener {
            if (it!!.count() > 0)
                LabelLayout.error = null
        }
         amountInput.addTextChangedListener {
            if (it!!.count() > 0)
                amountLayout.error = null
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }



        addTransactionBtn.setOnClickListener {
            val label = labelInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()

            if(label.isEmpty())
                LabelLayout.error = "Pleas enter a valid label"

            if(amount == null)
                amountLayout.error = "Pleas enter a valid amount"

        }
        closeBtn.setOnClickListener {
            finish()
        }
    }
}
