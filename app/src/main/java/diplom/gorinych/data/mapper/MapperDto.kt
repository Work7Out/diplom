package diplom.gorinych.data.mapper

import diplom.gorinych.data.remote.dto.AddonDto
import diplom.gorinych.data.remote.dto.CallDto
import diplom.gorinych.data.remote.dto.FeedbackDto
import diplom.gorinych.data.remote.dto.HistoryDto
import diplom.gorinych.data.remote.dto.HouseDto
import diplom.gorinych.data.remote.dto.NewsDto
import diplom.gorinych.data.remote.dto.PromoDto
import diplom.gorinych.data.remote.dto.UserDto
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
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

fun HistoryDto.mapFromDtoToReserve(): Reserve {
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

fun AddonDto.mapFromDtoToAddon(): Addon {
    return Addon(
        id = id,
        title = title,
        price = price
    )
}

fun PromoDto.mapFromDtoToPromo(): Promo {
    return Promo(
        id = id,
        description = description,
        valueDiscount = valueDiscount,
        isActive = isActive
    )
}

fun FeedbackDto.mapFromDtoToFeedback(): Feedback {
    return Feedback(
        id = this.id,
        content = this.content,
        dateFeedback = this.dateCreate,
        idUser = this.idUser,
        idHouse = this.idHouse,
        isBlocked = this.isBlocked,
        rang = this.rang
    )
}

fun NewsDto.mapFromDtoToNote(): Note {
    return  Note(
        id = id,
        title = title,
        content = content,
        dateCreate = dateCreate
    )
}

fun CallDto.mapFromDtoToCall(): Call {
    return Call(
        id = id,
        name = name,
        phone = phone
    )
}

fun HouseDto.mapFromDtoToHouse(): House {
    return House(
        id = id,
        name = name,
        description = description,
        price = price,
        image = pathImage
    )
}


