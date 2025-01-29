package dev.muthuram.roomdatabase.domain.usecase

import dev.muthuram.roomdatabase.domain.model.User
import dev.muthuram.roomdatabase.domain.repository.UserRepository

class UpdateUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun invoke(user: User) = userRepository.upsert(user)
}