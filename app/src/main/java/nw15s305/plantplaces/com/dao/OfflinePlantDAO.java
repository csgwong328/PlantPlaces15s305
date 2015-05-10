package nw15s305.plantplaces.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nw15s305.plantplaces.com.dto.PlantDTO;

/**
 * Created by jonesb on 4/27/2015.
 */
public class OfflinePlantDAO extends SQLiteOpenHelper implements IOfflinePlantDAO {

    public static final String PLANTS = "PLANTS";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String GENUS = "GENUS";
    public static final String SPECIES = "SPECIES";
    public static final String GUID = "GUID";
    public static final String CULTIVAR = "CULTIVAR";
    public static final String COMMON = "COMMON";

    public OfflinePlantDAO(Context ctx) {
        super(ctx, "plantplaces.db", null, 1);
    }

    @Override
    public List<PlantDTO> fetchPlants(String searchTerm) throws IOException, JSONException {
        return null;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPlants = "CREATE TABLE " + PLANTS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GUID + " INTEGER, " + GENUS + " TEXT, " + SPECIES + " TEXT, " + CULTIVAR + " TEXT, " + COMMON + " TEXT " + " );";
        db.execSQL(createPlants);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void insert(PlantDTO plant){
        // create our COntent Values
        ContentValues cv = new ContentValues();
        cv.put(GUID, plant.getGuid() );
        cv.put(GENUS, plant.getGenus());
        cv.put(SPECIES, plant.getSpecies());
        cv.put(CULTIVAR, plant.getCultivar());
        cv.put(COMMON, plant.getCommon());

        // insert the record into the database
        long cacheID = getWritableDatabase().insert(PLANTS, GENUS, cv);

        // store the cache ID back in our DTO.
        plant.setCacheID(cacheID);


    }

    @Override
    public int countPlants() {
        int plantCount = 0;

        // our SQL statement
        String sql = "SELECT COUNT(*) FROM " + PLANTS;

        // run the query
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // did we get a result?
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            plantCount = cursor.getInt(0);
        }

        // close the cursor
        cursor.close();

        return plantCount;
    }


    @Override
    public Set<Integer> fetchAllGuids(){
        // declare the return type.
        Set<Integer> allGuids = new HashSet<Integer>();

        // assemble SQL Statement.
        String sql = "SELECT " + GUID + " FROM " + PLANTS;

        // run the query.
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        // did we get results?
        if (cursor.getCount() > 0) {
            // move to the first result.
            cursor.moveToFirst();
            //iterate over the results.
            while (!cursor.isAfterLast()) {
                // get the value.
                int guid = cursor.getInt(cursor.getColumnIndex(GUID));

                // add this GUID to our set of GUIDs.
                allGuids.add(Integer.valueOf(guid));

                // go to the next row.
                cursor.moveToNext();

            }

        }

        cursor.close();

        return allGuids;

    }




}
