package dev.arielalvesdutra.calling_tariff.helpers;


import java.util.Collection;

/**
 * Helper functions for dealing with generic objects.
 */
public class ObjectHelper {

    /**
     * Verify if is a empty String.
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(String object) {
        if (object == null || object.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Verify if is a empty Collection.
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Verify if is a empty generic Object.
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null){
            return true;
        }
        return false;
    }
}
