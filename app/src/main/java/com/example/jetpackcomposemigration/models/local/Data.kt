package com.example.jetpackcomposemigration.models.local

import android.provider.BaseColumns
import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Table

@Table("data")
class Data {
    @Column(value = BaseColumns._ID)
    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var message: String = ""

    @Column
    var imageThumbnail: String = ""
}
