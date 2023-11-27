package com.example.codechallangebankapp.domain.usecases

import com.example.codechallangebankapp.core.utils.FlowResult
import com.example.codechallangebankapp.domain.models.AccountMovementModel
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.domain.repositories.AccountsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AccountsUseCaseTest{

    @MockK
    private lateinit var accountsRepository: AccountsRepository
    private lateinit var accountsUseCase: AccountsUseCase
    private val mockAccountsModel = AccountsModel(listOf(), "username")
    private val mockAccountMovements = listOf<AccountMovementModel>()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        accountsUseCase = AccountsUseCase(accountsRepository)
    }

    @Test
    fun `getAccountsListApi should emit Success`() = runBlocking {
        //Given
        coEvery { accountsRepository.getAccountsListApi(any()) } returns mockAccountsModel

        //When
        val result = mutableListOf<FlowResult<AccountsModel>>()
        accountsUseCase.getAccountsListApi("username").collect {
            result.add(it)
        }

        //Then
        assertEquals(2, result.size)
        assertEquals(mockAccountsModel, (result[1] as FlowResult.Success).data)
    }

    @Test
    fun `getUpdatedAccountsListApi should emit Success`() = runBlocking {
        // Given
        coEvery { accountsRepository.getUpdatedAccountsListApi(any()) } returns mockAccountsModel

        // When
        val result = mutableListOf<FlowResult<AccountsModel>>()
        accountsUseCase.getUpdatedAccountsListApi("username").collect {
            result.add(it)
        }

        //
        //Then
        assertEquals(2, result.size)
        assertEquals(mockAccountsModel, (result[1] as FlowResult.Success).data)
    }

    @Test
    fun `getAccountMovementsApi should emit Success`() = runBlocking {
        // Given
        coEvery { accountsRepository.getAccountMovementsApi(any()) } returns mockAccountMovements

        // When
        val result = mutableListOf<FlowResult<List<AccountMovementModel>>>()
        accountsUseCase.getAccountMovementsApi("accountNumber").collect {
            result.add(it)
        }

        // Then
        assertEquals(2, result.size)
        assertEquals(mockAccountMovements, (result[1] as FlowResult.Success).data)
    }
}