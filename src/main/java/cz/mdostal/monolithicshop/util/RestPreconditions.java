package cz.mdostal.monolithicshop.util;

import java.util.MissingResourceException;

public class RestPreconditions {

    /**
     * Check if some value was found, otherwise throw exception.
     * @param resource has value true if found, otherwise false
     * @throws MissingResourceException if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new MissingResourceException("Missing resource", "", "");
        }

        return resource;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.     *
     * @param reference an object reference
     * @return the non-null reference that was validated     *
     * @throws MissingResourceException if {@code reference} is null
     */
    public static <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new MissingResourceException("Missing resource", "", "");
        }
        return reference;
    }
}
