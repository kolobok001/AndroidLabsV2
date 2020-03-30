package ru.tpu.courses.lab4.db;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data access object (DAO), содержит методы доступа к данным. В нашим случае - SQL запросы к БД.
 * По аналогии с @Database классом, в случае работы Room мы описываем только сами SQL запросы, а маппинг
 * результатов выполнения запросов в сами объекты (например в методе {@link #getAll()}) выполняется
 * за нас библиотекой. Подробнее о построении DAO можно прочитать в оффициальной документации:
 * https://developer.android.com/training/data-storage/room/accessing-data.html
 */
@Dao
public interface SortingDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Insert
    void insert(@NonNull Category category);

    @Query("SELECT id FROM SortKey")
    List<SortKey> getSortKey();

    @Query(
            "DELETE FROM sortkey"
    )
    void deleteSortKey();

    @Insert
    void insertSortKey(@NonNull SortKey sortKey);

    @Query("UPDATE Category " +
            "SET expanded = :expanded WHERE id=:id")
    void update(@NonNull boolean expanded, @NonNull int id);

    @Query(
            "SELECT COUNT(*) FROM SortKey "
    )
    int countSortKey();

}
