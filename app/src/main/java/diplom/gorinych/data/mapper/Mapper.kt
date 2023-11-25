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
            image = R.drawable.image
        )
    }
}


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

fun mapToHouseDetail(
    houseEntity: HouseEntity,
    feedbacks: List<Feedback>
): HouseDetail {
    return HouseDetail(
        id = houseEntity.id,
        name = houseEntity.name,
        description = houseEntity.description,
        price = houseEntity.price,
        image = R.drawable.image,
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