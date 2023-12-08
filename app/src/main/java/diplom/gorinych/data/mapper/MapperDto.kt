package diplom.gorinych.data.mapper

import diplom.gorinych.data.remote.dto.HistoryDto
import diplom.gorinych.data.remote.dto.UserDto
import diplom.gorinych.domain.model.Reserve
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

fun User.mapDtoToUserDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name,
        password = this.password,
        role = this.role,
        isBlocked = this.isBlocked,
        phone = this.phone,
        eMail = this.email
    )
}

fun HistoryDto.mapToReserve(): Reserve {
    return Reserve(
        id = this.id,
        idUser = this.idUser,
        idHouse = this.idHouse,
        dateBegin = this.dataBegin,
        dateEnd = this.dataEnd,
        dateCreate = this.dateCreate,
        amount = this.amount,
        additions = this.additions,
        confirmReservation = this.confirmReservation
    )
}