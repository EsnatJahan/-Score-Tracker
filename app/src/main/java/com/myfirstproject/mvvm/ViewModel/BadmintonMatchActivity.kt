package com.myfirstproject.mvvm.ViewModel

import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.myfirstproject.mvvm.R





class BadmintonMatchActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    private lateinit var tvTeamOneScore: TextView
    private lateinit var tvTeamTwoScore: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_badminton_match)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        tvTeamOneScore = findViewById(R.id.tvOnescore)
        tvTeamTwoScore = findViewById(R.id.tvTwoscore)
        mainViewModel.count1.observe(this){ count ->
            tvTeamOneScore.text = count.toString()
        }
        mainViewModel.count2.observe(this){ count ->
            tvTeamTwoScore.text = count.toString()
        }

        var ss = mainViewModel.showDialog   // ss is used to hold the dialog box
        if(ss != ""){                       // using View Model
            createPopupWindow(ss.toString())
        }


        findViewById<Button>(R.id.tv1button).setOnClickListener {
            mainViewModel.increment1()
            mainViewModel.count1.observe(this){ count ->
                tvTeamOneScore.text = count.toString()
            }
            val score1 = tvTeamOneScore.text.toString().toIntOrNull() ?: throw IllegalArgumentException("Invalid number format")
            if (score1 == 20) {
                Toast.makeText(this, "Team One Won ", Toast.LENGTH_SHORT).show()
                createPopupWindow("One")
            }
        }
        findViewById<Button>(R.id.tv2button).setOnClickListener {
            mainViewModel.increment2()
            mainViewModel.count2.observe(this){ count ->
                tvTeamTwoScore.text = count.toString()
            }
            val score2 = tvTeamTwoScore.text.toString().toIntOrNull() ?: throw IllegalArgumentException("Invalid number format")
            if (score2 == 20) {
                Toast.makeText(this, "Team Twe Won ", Toast.LENGTH_SHORT).show()
                createPopupWindow("Two")
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun createPopupWindow(str : String) {
        mainViewModel.setValue(str)
        var dialog = Dialog(this)
        var dialogView = LayoutInflater.from(this).inflate(R.layout.winner, null)
        var winnername = dialogView.findViewById<TextView>(R.id.WinnerName)
        winnername.text = "Team $str has won the match"
        dialog.setContentView(dialogView)
        val newmatchbutton: Button = dialogView.findViewById(R.id.newmatchbtn)
           newmatchbutton.setOnClickListener {
               mainViewModel.resets()
               mainViewModel.count1.observe(this){ count ->
                   tvTeamOneScore.text = count.toString()
               }
               mainViewModel.count2.observe(this){ count ->
                   tvTeamTwoScore.text = count.toString()
               }
            dialog.dismiss()
        }
        dialog.show()
    }

}