package examples.singleton;

import com.google.common.collect.ImmutableMap;

/**
 * Use the class loading to lazily instantiate a singleton.
 */
public class ClassLoaderSingleton {
    private boolean initialized = false;
    private ImmutableMap<String, Integer> lookupMap;

    /**
     * This pattern does a thread-safe lazy-initialization of the instance without explicit synchronization!
     */
    private static class Loader {
        private static final ClassLoaderSingleton INSTANCE = new ClassLoaderSingleton();
    }

    private ClassLoaderSingleton() {
        lookupMap = ImmutableMap.of("one", 1);
        initialized = true;
    }

    /**
     * Acquire the singleton instance.
     *
     * @return instance.
     */
    public static ClassLoaderSingleton getInstance() {
        return Loader.INSTANCE;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getOrElse(final String key, final int elseValue) {
        if (lookupMap.containsKey(key)) {
            return lookupMap.get(key);
        } else {
            return elseValue;
        }
    }
}
