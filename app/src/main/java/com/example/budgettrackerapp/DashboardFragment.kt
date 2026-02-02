package com.example.budgettrackerapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {

    private lateinit var transaction: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var balanceTextView: TextView
    private lateinit var budgetTextView: TextView
    private lateinit var expenseTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var addTransactionBtn: Button
    private lateinit var clearTransactionBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Dashboard TextViews
        balanceTextView = view.findViewById(R.id.balance)
        budgetTextView = view.findViewById(R.id.budget)
        expenseTextView = view.findViewById(R.id.expense)

        // RecyclerView
        recyclerView = view.findViewById(R.id.recycleview)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager

        // Buttons
        addTransactionBtn = view.findViewById(R.id.addTransactionBtn)
        clearTransactionBtn = view.findViewById(R.id.clearTransactionBtn)

        // Initialize transaction list and adapter
        transaction = arrayListOf(
            Transaction("Weekend budget", 400.0),
            Transaction("Apple", -4.0),
            Transaction("Gas", -40.0),
            Transaction("Soda", -5.0)
        )
        transactionAdapter = TransactionAdapter(transaction)
        recyclerView.adapter = transactionAdapter

        // Update dashboard values initially
        updateDashboard()

        // Button listeners
        addTransactionBtn.setOnClickListener {
            showAddTransactionDialog()
        }

        clearTransactionBtn.setOnClickListener {
            transaction.clear()
            transactionAdapter.notifyDataSetChanged()
            updateDashboard()
        }

        return view
    }

    private fun updateDashboard() {
        val totalAmount = transaction.sumOf { it.amount }
        val budgetAmount = transaction.filter { it.amount > 0 }.sumOf { it.amount }
        val expenseAmount = transaction.filter { it.amount < 0 }.sumOf { it.amount }

        balanceTextView.text = "$ %.2f".format(totalAmount)
        budgetTextView.text = "$ %.2f".format(budgetAmount)
        expenseTextView.text = "$ %.2f".format(-expenseAmount) // negative because expenses
    }

    private fun showAddTransactionDialog() {
        // Inflate dialog layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_transaction, null)
        val labelInput = dialogView.findViewById<EditText>(R.id.dialogLabelInput)
        val amountInput = dialogView.findViewById<EditText>(R.id.dialogAmountInput)

        // Build dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Transaction")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val label = labelInput.text.toString()
                val amount = amountInput.text.toString().toDoubleOrNull()

                if (label.isNotEmpty() && amount != null) {
                    transaction.add(Transaction(label, amount))
                    transactionAdapter.notifyItemInserted(transaction.size - 1)
                    recyclerView.scrollToPosition(transaction.size - 1) // scroll to new item
                    updateDashboard()
                } else {
                    Toast.makeText(requireContext(), "Enter valid label and amount", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
