package com.can_apps.rank_board.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.can_apps.rank_board.R

class RankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, RankFragment())
            .commit()
    }
}
