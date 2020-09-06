package ai.kitt.vnest.basedata.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import ai.kitt.vnest.basedata.database.model.PoiModel;

import java.util.List;

@Dao
public interface PoiDao {
    @Query("SELECT * FROM POIMODEL")
    List<PoiModel> getAll();

    @Insert
    void insert(PoiModel... poiModels);

    @Delete
    void delete(PoiModel... poiModels);
}
