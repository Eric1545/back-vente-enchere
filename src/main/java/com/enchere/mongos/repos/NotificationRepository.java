package com.enchere.mongos.repos;

import com.enchere.mongos.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    @Query("{'idClient' : ?0}")
    List<Notification> getNotificationsByIdClient(int idClient);

}
