package com.example.lab3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

// 用 enum 取代 0/1/2
enum class Mora(val label: String) {
    SCISSOR("剪刀"),
    STONE("石頭"),
    PAPER("布");

    // 誰贏誰
    fun beats(other: Mora): Boolean = when (this) {
        SCISSOR -> other == PAPER
        STONE   -> other == SCISSOR
        PAPER   -> other == STONE
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Step1 定義元件變數，並通過 findViewById 取得元件
        val edName = findViewById<EditText>(R.id.edName)
        val tvText = findViewById<TextView>(R.id.tvText)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnMora = findViewById<Button>(R.id.btnMora)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvWinner = findViewById<TextView>(R.id.tvWinner)
        val tvMyMora = findViewById<TextView>(R.id.tvMyMora)
        val tvTargetMora = findViewById<TextView>(R.id.tvTargetMora)

        // Step2 設定 btnMora 的點擊事件
        btnMora.setOnClickListener {
            // Step3 如果 edName 為空，則顯示提示文字
            if (edName.text.isEmpty()) {
                tvText.text = "請輸入玩家姓名"
                return@setOnClickListener
            }
            // Step4 從 edName 取得玩家姓名
            val playerName = edName.text.toString()
            val targetMora: Mora = Mora.values().random()
            val myMora: Mora = when (radioGroup.checkedRadioButtonId) {
                R.id.btnScissor -> Mora.SCISSOR
                R.id.btnStone   -> Mora.STONE
                else            -> Mora.PAPER
            }
            // Step8 設定玩家姓名、我方出拳、電腦出拳的文字
            tvName.text = "名字\n$playerName"
            tvMyMora.text = "我方出拳\n${getMoraString(myMora)}"
            tvTargetMora.text = "電腦出拳\n${getMoraString(targetMora)}"

            // Step9 判斷勝負
            when {
                myMora == targetMora -> {
                    tvWinner.text = "勝利者\n平手"
                    tvText.text = "平局，請再試一次！"
                }
                myMora.beats(targetMora) -> {
                    tvWinner.text = "勝利者\n$playerName"
                    tvText.text = "恭喜你獲勝了！！！"
                }
                else -> {
                    tvWinner.text = "勝利者\n電腦"
                    tvText.text = "可惜，電腦獲勝了！"
                }
            }
        }
    }

    private fun getMoraString(mora: Mora): String = mora.label
}
