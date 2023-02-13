//****************************************************************************
/**@methodName:
 * @param:
 * @return:
 * @description:
 * */

package edu.msudenver.kotlinlogin

import android.content.*
import android.database.Cursor
import android.database.sqlite.*
import java.io.Serializable
import java.security.MessageDigest
import kotlin.random.Random

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    Serializable
{
    
    
    companion object
    {
        const val DATABASE_NAME = "User.db"
        const val DATABASE_VERSION = 2
        const val SYMBOLS = "abcdefghijklmnopqrstuvwxyz0123456789$%&@!?_-"
        
        
        fun getSalt(): String
        {
            var salt = ""
            while (salt.length < 16) salt += SYMBOLS[Random.nextInt(
                SYMBOLS.length)]
            return salt
        } // End getSalt
        
        fun getHash(input: String): String
        {
            return MessageDigest.getInstance("SHA256")
                .digest(input.toByteArray()).decodeToString()
        } // End getHash
    } // End companion object
    
    //************************************************************************
    /**@methodName: onCreate
     * @param: db (a sqlite database)
     * @return: void
     * @description: This is called the first time a data base is created (there should be
     * code here to create a new database).
     * */
    
    override fun onCreate(db: SQLiteDatabase?)
    {
        val createTableStatement =
            """CREATE TABLE USER_DATABASE (column_Email TEXT PRIMARY KEY, column_Username TEXT NOT NULL, column_Password TEXT NOT NULL)"""
        
        db?.execSQL(createTableStatement)
    } // End onCreate
    
    //************************************************************************
    /**@methodName: onUpgrade
     * @param: oldDB (a sqlite database)
     * @param: oldVersion (an initial version number for a database)
     * @param: newVersion ""
     * @return: null
     * @description: This is called when the version number changes. It prevents
     * previous user apps from breaking when you change the data base design.
     * */
    override fun onUpgrade(oldDB: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        // Drop table...
        oldDB?.execSQL("""
            DROP TABLE IF EXISTS USER_DATABASE
        """)
        
        // ...then call "onCreate" again
        onCreate(oldDB)
    } // End onUpgrade
    
    //************************************************************************
    /**@methodName: addOne
     * @return: bool
     * @description: This method adds one user to the database
     * */
    fun addOne(userModel: UserModel): Boolean
    {
        // Variables
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        
        // Enter data into data base
        cv.put("column_Email", userModel.email)
        cv.put("column_Username", userModel.username)
        cv.put("column_Password", userModel.password)
        val insert = db.insert("USER_DATABASE", null, cv)
        
        // Return stuff
        if (insert.equals(-1)) return false
        return true
    } // End addOne
    
    //************************************************************************
    /**@methodName: getEveryone
     * @return: List<UserModel>
     * @description: This method returns all database users and their
     * respective information.
     * @status: works as expected.
     * */
    fun getEveryone(): List<UserModel>
    {
        // List to be returned at the end of the method
        val returnList = mutableListOf<UserModel>()
        
        // Get data from data base
        val queryString = "SELECT * FROM USER_DATABASE"
        val db: SQLiteDatabase = this.writableDatabase
        val cursor = db.rawQuery(queryString, null)
        
        // If the cursor has an element in it...
        if (cursor.moveToFirst())
        {
            do
            {
                val email = cursor.getString(0).toString()
                val userName = cursor.getString(1).toString()
                val password = cursor.getString(2).toString()
                val newUser = UserModel(email, userName, password)
                
                returnList.add(newUser)
            } while (cursor.moveToNext()) // End do while
        } // End if
        
        
        // Close variables
        cursor.close()
        db.close()
        
        // Return list
        return returnList
    } // End getEveryone
    
    
    //************************************************************************
    /**@methodName: viewEntry
     * @return: ArrayList<UserModel>
     * @description: This method returns all database users and their
     * respective information.
     * */
    fun viewEntry():ArrayList<UserModel>
    {
        val userList: ArrayList<UserModel> = ArrayList()
        val selectQuery = "SELECT * FROM USER_DATABASE"
        
        val db = this.readableDatabase
        var cursor: Cursor? = null
        
        try
        {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        
        // Necessary variables
        var email: String
        var userName: String
        var password: String
        
        if (cursor.moveToFirst())
        {
            do
            {
                email = cursor.getString(0)
                userName = cursor.getString(1)
                password = cursor.getString(2)
                val user = UserModel(email, userName, password)
                userList.add(user)
            }while (cursor.moveToNext()) // End do while
        } // End if
    
        cursor.close()
        return userList
    } // End viewEntry
    

    
    // Todo: write getPassword method that does a query of data, finds a row w/
    //  an email, and returns the password.
    fun getPassword(user: UserModel): String
    {
        val db:SQLiteDatabase = this.writableDatabase
        val queryString = "SELECT column_Password FROM USER_DATABASE WHERE column_Username = $user.username"
        val cursor = db.rawQuery(queryString, null)
        
        
        cursor.close()
        return cursor.getString(3)
    }
    
    
    
    
    fun updateUser(user: UserModel):Int
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("column_Email", user.email)
        
        // Update row
        val success = db.update("USER_DATABASE",contentValues, "column_Email = ${user.email}", null)
        db.close()
        return success
    }
    fun getCursor(): Cursor
    {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM USER_DATABASE", null)
     
        return cursor
    }
} // End DataBaseHelper class