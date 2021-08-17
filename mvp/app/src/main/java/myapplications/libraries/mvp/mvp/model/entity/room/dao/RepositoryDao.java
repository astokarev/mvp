package myapplications.libraries.mvp.mvp.model.entity.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import myapplications.libraries.mvp.mvp.model.entity.room.RoomRepository;
import myapplications.libraries.mvp.mvp.model.entity.room.RoomUser;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RepositoryDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomRepository user);

    @Insert(onConflict = REPLACE)
    void insert(RoomRepository... users);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomRepository> users);


    @Update
    void update(RoomRepository user);

    @Update
    void update(RoomRepository... users);

    @Update
    void update(List<RoomRepository> users);


    @Delete
    void delete(RoomUser user);

    @Delete
    void delete(RoomUser... users);

    @Delete
    void delete(List<RoomUser> users);


    @Query("SELECT * FROM roomrepository")
    List<RoomRepository> getAll();

    @Query("SELECT * FROM roomrepository WHERE userLogin = :login LIMIT 1")
    RoomRepository findForUser(String login);

}
