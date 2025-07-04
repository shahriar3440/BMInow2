package com.safdev.bminow

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var bmi_value: TextView
    private lateinit var weight_input: EditText
    private lateinit var hi_ft: EditText
    private lateinit var hi_inc: EditText
    private lateinit var status: TextView
    private lateinit var btn_calculate: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        bmi_value = findViewById(R.id.bmi_value)
        weight_input = findViewById(R.id.weight_input)
        hi_ft = findViewById(R.id.hi_ft)
        hi_inc = findViewById(R.id.hi_inc)
        status = findViewById(R.id.status)
        btn_calculate = findViewById(R.id.btn_calculate)

        btn_calculate.setOnClickListener {
            if (!weight_input.text.toString().equals("") && !hi_ft.text.toString().equals("")
                && !hi_inc.text.toString().equals("")) {

                val wt = weight_input.text.toString().toDouble()
                wt != 0.0 // Ensure weight is not zero
                val ft = hi_ft.text.toString().toDouble()
                val inc = hi_inc.text.toString().toDouble()

                // Convert feet and inches to meters
                val height = (ft * 12 + inc) * 0.0254

                // Calculate BMI
                val bmi = wt / (height * height)

                // Set BMI value
                bmi_value.text = String.format("%.2f", bmi)
                // Determine status
                when {
                    bmi < 18.5 -> status.text = "Underweight"
                    bmi in 18.5..24.9 -> status.text = "Healthy weight"
                    bmi in 25.0..29.9 -> status.text = "Overweight"
                    else -> status.text = "Obesity!!"
                }
                // Change text color based on status
                when {
                    bmi < 18.5 -> status.setTextColor(resources.getColor(R.color.underweight, null))
                    bmi in 18.5..24.9 -> status.setTextColor(resources.getColor(R.color.healthy, null))
                    bmi in 25.0..29.9 -> status.setTextColor(resources.getColor(R.color.overweight, null))
                    else -> status.setTextColor(resources.getColor(R.color.obesity, null))
                }
                // change bmi value text color
                when {
                    bmi < 18.5 -> bmi_value.setTextColor(resources.getColor(R.color.underweight, null))
                    bmi in 18.5..24.9 -> bmi_value.setTextColor(resources.getColor(R.color.healthy, null))
                    bmi in 25.0..29.9 -> bmi_value.setTextColor(resources.getColor(R.color.overweight, null))
                    else -> bmi_value.setTextColor(resources.getColor(R.color.obesity, null))
                }
                // change llmain background color
                val llmain = findViewById<LinearLayout>(R.id.llmain)
                when {
                    bmi < 18.5 -> llmain.setBackgroundColor(resources.getColor(R.color.underweight_bg, null))
                    bmi in 18.5..24.9 -> llmain.setBackgroundColor(resources.getColor(R.color.healthy_bg, null))
                    bmi in 25.0..29.9 -> llmain.setBackgroundColor(resources.getColor(R.color.overweight_bg, null))
                    else -> llmain.setBackgroundColor(resources.getColor(R.color.obesity_bg, null))
                }
                // Show success message
                Toast.makeText(this@MainActivity, "BMI calculated successfully", Toast.LENGTH_LONG).show()
                
            } else if (weight_input.text.toString() == "0" || hi_ft.text.toString() == "0"
                || hi_inc.text.toString() == "0") {
                Toast.makeText(this@MainActivity, "Please fill all fields properly.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }
        // if user inputs weight 00kg, height 0ft 0in, then show error message
        weight_input.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && weight_input.text.toString() == "00") {
                Toast.makeText(this@MainActivity, "Weight cannot be 0", Toast.LENGTH_SHORT).show()
            }
        }
        hi_ft.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && hi_ft.text.toString() == "0") {
                Toast.makeText(this@MainActivity, "Height cannot be 0", Toast.LENGTH_SHORT).show()
            }
        }






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llmain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}