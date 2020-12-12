package com.oratakashi.jetpackacademy.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(data : DataTv)

    @Query("SELECT * from tv where id = :id")
    fun getDataById(id : String) : List<DataTv>

    @Query("SELECT * from tv")
    fun getData() : DataSource.Factory<Int, DataTv>

    @Query("SELECT * From tv where name like :query")
    fun searchData(query : String) : DataSource.Factory<Int, DataTv>

    @Delete
    fun delete(data : DataTv)
}