package ai.kitt.vnest.basedata.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ai.kitt.vnest.basedata.entity.Message;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM Message")
    List<Message> getAll();

    @Insert
    void insert(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM Message")
    void delete() ;

}
