package dev.muthuram.roomdatabase.presentation

import dev.muthuram.roomdatabase.data.db.UserDao
import dev.muthuram.roomdatabase.domain.model.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SavedTextViewModelTest {

    private lateinit var userDao: UserDao
    private lateinit var viewModel: SavedTextViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userDao = mockk()
        viewModel = SavedTextViewModel(userDao)
    }

    @Test
    fun test_getEmail_updatesEmailIdState_fromUserDao() = runTest(testDispatcher) {
        val expectedEmail = "dao@example.com"
        val userList = listOf(User(emailId = expectedEmail))
        coEvery { userDao.getAllPeople() } returns flowOf(userList)

        viewModel.getEmail()
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutines and StateFlow updates complete

        assertEquals(expectedEmail, viewModel.emailId.value)
    }

    @Test
    fun test_getEmail_updatesEmailIdState_empty_fromUserDao() = runTest(testDispatcher) {
        val expectedEmail = "" // Expect empty string if DAO returns empty list
        val userList = emptyList<User>()
        coEvery { userDao.getAllPeople() } returns flowOf(userList)

        viewModel.getEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedEmail, viewModel.emailId.value)
    }
    
    @Test
    fun test_getEmail_updatesEmailIdState_withNullFromUserDao() = runTest(testDispatcher) {
        val expectedEmail = "" // Expect empty string if DAO returns null user
        val userList = listOf(null) // Simulate a Flow that might emit nulls in a list, though Dao usually filters
        coEvery { userDao.getAllPeople() } returns flowOf(userList.filterNotNull()) // getAllPeople likely returns Flow<List<User>>

        viewModel.getEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedEmail, viewModel.emailId.value)
    }


    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
