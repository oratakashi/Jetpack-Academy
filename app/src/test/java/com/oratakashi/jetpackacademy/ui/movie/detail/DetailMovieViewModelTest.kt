package com.oratakashi.jetpackacademy.ui.movie.detail

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.oratakashi.jetpackacademy.data.FakeRemoteRepository
import com.oratakashi.jetpackacademy.data.database.Storage
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class DetailMovieViewModelTest {
    lateinit var fakeRemoteRepository: FakeRemoteRepository
    lateinit var db: Storage

    @Before
    fun setUp() {
        fakeRemoteRepository = FakeRemoteRepository()

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context, Storage::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Test
    fun testGetData() {

        val getData = fakeRemoteRepository.getMovie()
            .blockingGet()
        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)

        getData.data?.forEachIndexed { index, value ->
            val insertCode = db.movie().add(value)
            println("Inserted data ${index + 1} : ${value.title}")
            Assert.assertNotNull(insertCode)
            Assert.assertTrue(insertCode > 0)
        }


        val getDataList = db.movie().getDataList()

        println("=====================================")
        println("Getting data from Room")
        println("=====================================")

        getDataList.forEachIndexed { index, value ->
            println("Get data ${index + 1} : ${value.title}")
        }

        Assert.assertNotNull(getDataList)
        Assert.assertEquals(20, getDataList.size)
    }

    @Test
    fun testAddData() {
        val getData = fakeRemoteRepository.getMovie()
            .blockingGet()
        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)

        getData.data?.forEachIndexed { index, value ->
            val insertCode = db.movie().add(value)
            println("Inserted data ${index + 1} : ${value.title}")
            Assert.assertNotNull(insertCode)
            Assert.assertTrue(insertCode > 0)
        }
    }
}