package ru.effectiveMobile.socialMedia.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.effectiveMobile.socialMedia.model.Message;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String tag);

    @Query(value = "select * from Messages where tag =?1 and user_id =?2", nativeQuery = true)
    List<Message> findByTagAndUseId(String taf, long userId);
}
