package com.oratakashi.jetpackacademy.ui.movie.detail

import android.content.Context
import android.os.Build
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toFlowable
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.core.app.ApplicationProvider
import com.oratakashi.jetpackacademy.data.FakeRemoteRepository
import com.oratakashi.jetpackacademy.data.database.Storage
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class DetailViewModelTest {
    lateinit var fakeRemoteRepository: FakeRemoteRepository
    lateinit var db: Storage

    val data: MutableLiveData<PagedList<DataMovie>> by lazy {
        MutableLiveData<PagedList<DataMovie>>()
    }

    val config: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setPageSize(2)
            .setInitialLoadSizeHint(2)
            .setPrefetchDistance(2)
            .setEnablePlaceholders(true)
            .build()
    }

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
    fun testGetData(){

        val getData = fakeRemoteRepository.getMovie()
            .blockingGet()
        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)

        getData.data?.forEachIndexed { index, value ->
            val insertCode = db.movie().add(value)
            println("Inserted data ${index + 1} : ${value.title}")
//            Assert.assertNotNull(insertCode)
//            Assert.assertTrue(insertCode > 0)
        }

        val getDataDb =  db.movie().getData().toLiveData(1)

        println(getDataDb.hasActiveObservers())
        println(getDataDb.hasObservers())


        var pagedList : PagedList<DataMovie>? = null

        val observer : Observer<PagedList<DataMovie>> = Observer<PagedList<DataMovie>>{
            println(it)
            pagedList = it
        }
        getDataDb.observeForever(observer)
        val getDataList = db.movie().getDataList()

        getDataList.forEach {
            println(it)
        }


    }

    @Test
    fun testAddData() {
        val getData = fakeRemoteRepository.getMovie()
            .blockingGet()
        Assert.assertNotNull(getData.data)
        Assert.assertEquals(20, getData.data?.size)

        getData.data?.forEach {
            val insertCode = db.movie().add(it)
            println("Inserted data : ${it.title}")
            Assert.assertNotNull(insertCode)
            Assert.assertTrue(insertCode > 0)
        }
    }
}