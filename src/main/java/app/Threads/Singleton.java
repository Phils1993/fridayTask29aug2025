package app.Threads;

public class Singleton {
    // Private static variable of the same class that is the only instance of the class.
    private static Singleton instance;

    // Private constructor to prevent instantiation from other classes.
    private Singleton() {}

    // Public static method that returns the instance of the class.
    public static Singleton getInstance() {
        if (instance == null) { // If the instance hasn't been created yet.
            instance = new Singleton(); // Create a new instance.
        }
        return instance; // Return the existing or new instance.
    }
}
