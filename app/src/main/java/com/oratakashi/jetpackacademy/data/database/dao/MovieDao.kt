package com.oratakashi.jetpackacademy.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import io.reactivex.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(data : DataMovie) : Long

    @Query("SELECT * from movie where id = :id")
    fun getDataById(id : String) : List<DataMovie>

    @Query("SELECT * from movie")
    fun getData() : DataSource.Factory<Int, DataMovie>

    @Query("SELECT * from movie")
    fun getDataList() : List<DataMovie>

    @Query("SELECT * From movie where title like :query")
    fun searchData(query : String) : DataSource.Factory<Int, DataMovie>

    @Delete
    fun delete(data : DataMovie)
}