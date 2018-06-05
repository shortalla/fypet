package com.example.andrew.myapplication;

import android.provider.BaseColumns;

/**
 * Created by Andrew on 04/03/2016.
 */
public class PetData {

    public static abstract class PetInfo implements BaseColumns {
        public static final String PET_NAME = "pet_name";
        public static final String PET_HEAD = "pet_head";
        public static final String PET_BODY = "pet_body";
        public static final String PET_ARMS = "pet_arms";
        public static final String PET_LEGS = "pet_legs";
        public static final String PET_FACE = "pet_face";
        public static final String DATABASE_NAME = "pet_info.db";
        public static final String TABLE_NAME = "pet_info";
    }

    public static abstract class FoodInfo implements BaseColumns {
        public static final String FOOD_NAME = "food_name";
        public static final String FOOD_CONTENTS = "food_contents";
        public static final String TABLE_NAME = "food_info";
    }
}
