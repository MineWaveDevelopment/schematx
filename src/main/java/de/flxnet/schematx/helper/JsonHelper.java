package de.flxnet.schematx.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class JsonHelper {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    
    /**
     * @return the gson
     */
    public static Gson getGson() {
    	return gson;
    }
}