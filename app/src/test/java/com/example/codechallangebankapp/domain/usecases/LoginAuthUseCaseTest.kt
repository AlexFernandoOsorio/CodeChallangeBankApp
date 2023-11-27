package com.example.codechallangebankapp.domain.usecases

import com.example.codechallangebankapp.domain.repositories.LoginRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginAuthUseCaseTest {

    @MockK
    private lateinit var loginRepository: LoginRepository
    private lateinit var loginAuthUseCase: LoginAuthUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        loginAuthUseCase = LoginAuthUseCase(loginRepository)
    }

    @Test
    fun `invoke should return user error when username is blank`() = runBlocking {
        // When
        val result = loginAuthUseCase("", "password")

        // Then
        assertEquals("El usuario no puede estar vacio", result.userError)
        assertEquals(null, result.passwordError)
        assertEquals(null, result.result)
    }

    @Test
    fun `invoke should return password error when password is blank`() = runBlocking {
        // When
        val result = loginAuthUseCase("username", "")

        // Then
        assertEquals(null, result.userError)
        assertEquals("El passwword no puede estar vacio", result.passwordError)
        assertEquals(null, result.result)
    }

}

