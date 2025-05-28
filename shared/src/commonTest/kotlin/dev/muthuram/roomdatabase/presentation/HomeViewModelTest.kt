package dev.muthuram.roomdatabase.presentation

import dev.muthuram.roomdatabase.domain.model.User
import dev.muthuram.roomdatabase.domain.usecase.GetUserUseCase
import dev.muthuram.roomdatabase.domain.usecase.UpdateUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : KoinTest {

    private val getUserUseCase: GetUserUseCase by inject()
    private val updateUserUseCase: UpdateUserUseCase by inject()
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(
                module {
                    single { mockk<GetUserUseCase>() }
                    single { mockk<UpdateUserUseCase>(relaxed = true) }
                }
            )
        }
        // ViewModel is instantiated after Koin setup so it picks up the mocks
        viewModel = HomeViewModel()
    }

    @Test
    fun test_onEmailChange_updatesEmailStateFlow() = runTest(testDispatcher) {
        val testEmail = "test@example.com"
        viewModel.onEmailIdChange(testEmail)
        assertEquals(testEmail, viewModel.emailId.first())
    }

    @Test
    fun test_getEmail_updatesEmailStateFlow_fromUseCase() = runTest(testDispatcher) {
        val expectedEmail = "usecase@example.com"
        val userList = listOf(User(emailId = expectedEmail))
        // Mock the behavior of GetUserUseCase
        coEvery { getUserUseCase.invoke() } returns flowOf(userList)

        viewModel.getEmail()
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutines and StateFlow updates complete

        assertEquals(expectedEmail, viewModel.emailId.first())
    }

    @Test
    fun test_getEmail_updatesEmailStateFlow_empty_fromUseCase() = runTest(testDispatcher) {
        val expectedEmail = "" // Expect empty string if use case returns empty list
        val userList = emptyList<User>()
        coEvery { getUserUseCase.invoke() } returns flowOf(userList)

        viewModel.getEmail()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedEmail, viewModel.emailId.first())
    }
    
    @Test
    fun test_onSaveUser_callsUpdateUserUseCase() = runTest(testDispatcher) {
        val emailToSave = "saveme@example.com"
        viewModel.onEmailIdChange(emailToSave)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure StateFlow updates before saving

        // updateUserUseCase is relaxed, but we can still verify calls
        // No need for coEvery { updateUserUseCase.invoke(any()) } returns Unit due to relaxed = true

        viewModel.onSaveUser()
        testDispatcher.scheduler.advanceUntilIdle() // Ensure the save coroutine completes

        coVerify { updateUserUseCase.invoke(User(emailId = emailToSave)) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }
}
