package diplom.gorinych.data.mapper

import diplom.gorinych.data.remote.dto.UserDto
import diplom.gorinych.domain.model.User


fun UserDto.mapDtoToUser(): User {
    return User(
        id = this.id,
        name = this.name,
        password = this.password,
        role = this.role,
        isBlocked = this.isBlocked,
        phone = this.phone,
        email = this.eMail
    )
}