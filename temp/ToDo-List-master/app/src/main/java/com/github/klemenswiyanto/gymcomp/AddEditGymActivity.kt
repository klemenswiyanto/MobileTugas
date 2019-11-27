package com.github.klemenswiyanto.gymcomp

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_does.*
import java.util.*


class AddEditGymActivity : AppCompatActivity() {

    private val cal = Calendar.getInstance()

    companion object {
        const val EXTRA_ID = "com.github.klemenswiyanto.todolist.EXTRA_ID"
        const val EXTRA_TITLE = "com.github.klemenswiyanto.todolist.EXTRA_TITLE"
        const val EXTRA_SETS = "com.github.klemenswiyanto.todolist.EXTRA_SETS"
        const val EXTRA_REPS = "com.github.klemenswiyanto.todolist.EXTRA_REPS"
        const val EXTRA_DUE_DATE = "com.github.klemenswiyanto.todolist.EXTRA_DUE_DATE"
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_does)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Gym"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_sets.setText(intent.getStringExtra(EXTRA_SETS))
            edit_text_reps.setText(intent.getStringExtra(EXTRA_REPS))
            edit_text_duedate.text = intent.getStringExtra(EXTRA_DUE_DATE)
        } else {
            title = "Add Gym"
        }

        edit_text_duedate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)

                edit_text_duedate.text = SimpleDateFormat("dd MMM yyyy").format(cal.time)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        savebtn.setOnClickListener {
            saveDoes()
        }

        cancelbtn.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun saveDoes() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_reps.text.toString().trim().isBlank() || edit_text_sets.text.toString().trim().isBlank() || edit_text_duedate.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty does!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_SETS, edit_text_sets.text.toString())
            putExtra(EXTRA_REPS, edit_text_reps.text.toString())
            putExtra(EXTRA_DUE_DATE, edit_text_duedate.text.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
