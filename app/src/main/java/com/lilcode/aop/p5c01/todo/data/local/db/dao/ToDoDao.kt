package com.lilcode.aop.p5c01.todo.data.local.db.dao

import androidx.room.*
import com.lilcode.aop.p5c01.todo.data.entity.ToDoEntity
import org.junit.runners.Parameterized

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoEntity")
    suspend fun getAll(): List<ToDoEntity>

    @Query("SELECT * FROM ToDoEntity WHERE id=:id")
    suspend fun getById(id: Long): ToDoEntity

    @Insert
    suspend fun insert(toDoEntity: ToDoEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntityList: List<ToDoEntity>)

    @Query("DELETE FROM ToDoEntity WHERE id=:id")
    suspend fun delete(id: Long) : Int//the number of deleted rows

    @Query("DELETE FROM ToDoEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(toDoEntity: ToDoEntity):Int
}

/*
UPDATE or DELETE queries can return void or int. If it is an int, the value is the number of rows affected by this query.
 */