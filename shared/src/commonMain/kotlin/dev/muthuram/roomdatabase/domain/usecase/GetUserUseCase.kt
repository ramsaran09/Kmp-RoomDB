package dev.muthuram.roomdatabase.domain.usecase

import dev.muthuram.roomdatabase.domain.repository.UserRepository

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun invoke() = userRepository.getAllPeople()
}