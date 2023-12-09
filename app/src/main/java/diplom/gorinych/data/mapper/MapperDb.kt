package diplom.gorinych.data.mapper

import diplom.gorinych.R
import diplom.gorinych.data.db.AddonEntity
import diplom.gorinych.data.db.CallHistory
import diplom.gorinych.data.db.FeedBackEntity
import diplom.gorinych.data.db.HistoryEntity
import diplom.gorinych.data.db.HouseEntity
import diplom.gorinych.data.db.NoteEntity
import diplom.gorinych.data.db.PromoEntity
import diplom.gorinych.data.db.UserEntity
import diplom.gorinych.domain.model.Addon
import diplom.gorinych.domain.model.Call
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.Promo
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.domain.model.User


fun UserEntity.mapToUser(): User {
    return User(
        id = this.id,
        name = this.name,
        password = this.password,
        role = this.role,
        isBlocked = this.isBlocked,
        phone = this.phone,
        email = this.email
    )
}


fun User.mapToUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        password = this.password,
        role = this.role,
        isBlocked = this.isBlocked,
        phone = this.phone,
        email = this.email
    )
}


fun List<HouseEntity>.mapToHouses(): List<House> {
    return this.map { houseEntity ->
        House(
            id = houseEntity.id,
            name = houseEntity.name,
            description = houseEntity.description,
            price = houseEntity.price,
            image = "images[houseEntity.id - 1]"
        )
    }
}

val images = listOf(
    R.drawable.classic180_thumb,
    R.drawable.cruise115_thumb,
    R.drawable.cruise140_thumb,
    R.drawable.cruise165_thumb,
    R.drawable.evo_thumb,
    R.drawable.fisher90_thumb,
    R.drawable.futura180_thumb,
    R.drawable.cruise80_4,
    R.drawable.evo_40,
    R.drawable.freedom50_4
)

fun FeedBackEntity.mapToFeedback(): Feedback {
    return Feedback(
        id = this.id,
        content = this.content,
        dateFeedback = this.dateFeedback,
        idUser = this.idUser,
        idHouse = this.idHouse,
        isBlocked = this.isBlocked,
        rang = this.rang
    )
}

fun Feedback.mapToFeedBackEntity(): FeedBackEntity {
    return FeedBackEntity(
        id = this.id,
        content = this.content,
        dateFeedback = this.dateFeedback,
        idUser = this.idUser,
        idHouse = this.idHouse,
        isBlocked = this.isBlocked,
        rang = this.rang
    )
}

fun HouseEntity.mapToHouseDetail(): HouseDetail {
    return HouseDetail(
        id = id,
        name = name,
        description = description,
        price = price,
        image = images[id - 1],
    )
}

fun HistoryEntity.mapFromDtoToReserve(): Reserve {
    return Reserve(
        id = this.id,
        idUser = this.idUser,
        idHouse = this.idHouse,
        dateBegin = this.dateBegin,
        dateEnd = this.dateEnd,
        dateCreate = this.dateCreate,
        amount = this.amount,
        additions = this.additions,
        confirmReservation = this.confirmReservation
    )
}

fun Reserve.mapToHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        id = this.id,
        idUser = this.idUser,
        idHouse = this.idHouse,
        dateBegin = this.dateBegin,
        dateEnd = this.dateEnd,
        dateCreate = this.dateCreate,
        amount = this.amount,
        additions = this.additions,
        confirmReservation = this.confirmReservation
    )
}

fun AddonEntity.mapToAddon(): Addon {
    return Addon(
        id = id,
        title = title,
        price = price
    )
}

fun PromoEntity.mapToPromo(): Promo {
    return Promo(
        id = id,
        description = description,
        valueDiscount = valueDiscount,
        isActive = isActive
    )
}

fun Promo.mapToPromoEntity(): PromoEntity {
    return PromoEntity(
        id = id,
        description = description,
        valueDiscount = valueDiscount,
        isActive = isActive
    )
}

fun NoteEntity.mapToNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        dateCreate = dateCreate
    )
}

fun Note.mapToNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        dateCreate = dateCreate
    )
}

fun CallHistory.mapToCall(): Call {
    return Call(
        id = id,
        name = name,
        phone = phone
    )
}