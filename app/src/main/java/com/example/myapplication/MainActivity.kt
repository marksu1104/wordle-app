package com.example.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import java.util.Scanner



class MainActivity : AppCompatActivity() {

    private var curRow = 0
    private var curCol = 0
    var answer = "00000"
    var dict = HashSet<String>()

    // add your button ids here
    private val buttons: List<Button> by lazy {
        listOf(R.id.buttonQ, R.id.buttonW, R.id.buttonE, R.id.buttonR, R.id.buttonT, R.id.buttonY, R.id.buttonU,R.id.buttonI,R.id.buttonO,R.id.buttonP,
            R.id.buttonA,R.id.buttonS,R.id.buttonD,R.id.buttonF,R.id.buttonG,R.id.buttonH,R.id.buttonJ,R.id.buttonK,R.id.buttonL,
            R.id.buttonZ,R.id.buttonX,R.id.buttonC,R.id.buttonV,R.id.buttonB,R.id.buttonN,R.id.buttonM,
            R.id.buttonEnter,R.id.buttonRestart, R.id.buttonDelete).map{ findViewById(it) }
    }

    // add your textview ids here
    private val input: List<List<TextView>> by lazy {
        listOf(
            listOf(R.id.input_r1_c1, R.id.input_r1_c2, R.id.input_r1_c3, R.id.input_r1_c4, R.id.input_r1_c5).map{ findViewById(it) },
            listOf(R.id.input_r2_c1, R.id.input_r2_c2, R.id.input_r2_c3, R.id.input_r2_c4, R.id.input_r2_c5).map{ findViewById(it) },
            listOf(R.id.input_r3_c1, R.id.input_r3_c2, R.id.input_r3_c3, R.id.input_r3_c4, R.id.input_r3_c5).map{ findViewById(it) },
            listOf(R.id.input_r4_c1, R.id.input_r4_c2, R.id.input_r4_c3, R.id.input_r4_c4, R.id.input_r4_c5).map{ findViewById(it) },
            listOf(R.id.input_r5_c1, R.id.input_r5_c2, R.id.input_r5_c3, R.id.input_r5_c4, R.id.input_r5_c5).map{ findViewById(it) },
            listOf(R.id.input_r6_c1, R.id.input_r6_c2, R.id.input_r6_c3, R.id.input_r6_c4, R.id.input_r6_c5).map{ findViewById(it) }
        )
    }




    private fun push(c: String) {
        // AlertDialog.Builder(this).setMessage("$c is pressed!").show()
        when {

            c == "ENTER" -> { // you need to modify this block
                if (curCol == 5) {

                    val guess = input[curRow].map{ it.text }.joinToString("").lowercase()
                    var temp = 0
                    var count1 = answer


                    if(guess in dict){
                        for(i in 0 until 5){
                            if(guess[i] == answer[i]){
                                temp++
                                input[curRow][i].setBackgroundColor(android.graphics.Color.GREEN)
                                count1 = count1.replaceFirst(guess[i],'#')
                            }
                            else {
                                input[curRow][i].setBackgroundColor(android.graphics.Color.DKGRAY)
                            }
                        }

                        for(i in 0 until 5){
                            if(guess[i] in count1 && guess[i]!=answer[i]){
                                input[curRow][i].setBackgroundColor(android.graphics.Color.YELLOW)
                                count1=count1.replaceFirst(guess[i],'#')
                            }
                        }

                        if(temp == 5){
                            AlertDialog.Builder(this).setMessage("Congrat! Press RESTART to restart the game!")
                                .show()
                        }
                        else{

                            if(curRow == 5){
                                AlertDialog.Builder(this).setMessage("You loss! Press RESTART to restart the game!")
                                    .show()
                            }
                            else{
                                AlertDialog.Builder(this).setMessage("Wrong answer" )
                                    .show()
                            }
                            curRow++
                            curCol=0
                        }
                    }
                    else{
                        AlertDialog.Builder(this).setMessage("Please input a word!" )
                            .show()
                        for(j in 0 until 5){
                            input[curRow][j].text = ""
                        }
                        curCol=0
                    }

                }
                else{
                    AlertDialog.Builder(this).setMessage("Please input 5 letter!" )
                        .show()
                    for(j in 0 until 5){
                        input[curRow][j].text = ""
                    }
                    curCol=0
                }
            }

            c == "DELETE" -> {
                if (curCol > 0) {
                    curCol--
                    input[curRow][curCol].text = ""
                }
            }

            c == "RESTART" -> {
                //重啟遊戲
                for(i in 0 until 6){
                    for(j in 0 until 5){
                        input[i][j].text = ""
                        input[i][j].setBackgroundColor(android.graphics.Color.WHITE)
                    }
                }
                curRow=0
                curCol=0

                answer = dict.random()
                AlertDialog.Builder(this)
                    .setMessage(answer)
                    .show()
            }


            else -> {
                if (curCol < 5 && curRow < 6) {
                    input[curRow][curCol].text = c
                    curCol++
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (button in buttons) {
            button.setOnClickListener {
                push(button.text.toString())
            }
        }


        //生成答案
        val scanner = Scanner(resources.openRawResource(R.raw.dict))

        while (scanner.hasNext())
            dict.add(scanner.next())
        answer = dict.random()
        AlertDialog.Builder(this)
            .setMessage(answer)
            .show()
    }
}
