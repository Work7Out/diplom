package diplom.gorinych.data.mapper

import diplom.gorinych.R
import diplom.gorinych.data.db.FeedBackEntity
import diplom.gorinych.data.db.HistoryEntity
import diplom.gorinych.data.db.HouseEntity
import diplom.gorinych.data.db.UserEntity
import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.House
import diplom.gorinych.domain.model.HouseDetail
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
            image = images.random()
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
    R.drawable.freedom50_4)

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

fun mapToHouseDetail(
    houseEntity: HouseEntity,
    feedbacks: List<Feedback>
): HouseDetail {
    return HouseDetail(
        id = houseEntity.id,
        name = houseEntity.name,
        description = houseEntity.description,
        price = houseEntity.price,
        image = images.random(),
        feedbacks = feedbacks
    )
}

fun HistoryEntity.mapToReserve(): Reserve {
    return Reserve(
        id = this.id,
        idUser = this.idUser,
        idHouse = this.idHouse,
        dateBegin = this.dateBegin,
        dateEnd = this.dateEnd,
        dateCreate = this.dateCreate,
        amount = this.amount,
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
        confirmReservation = this.confirmReservation
    )
}