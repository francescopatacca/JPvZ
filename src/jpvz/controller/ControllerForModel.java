package jpvz.controller;

public class ControllerForModel implements IControllerForModel {
    
    // ---------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------
    private static ControllerForModel instance = null;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------
    private ControllerForModel() {
        
    }

    // ---------------------------------------------------------------
    // STATIC SINGLETON METHODS
    // ---------------------------------------------------------------
    public static IControllerForModel getInstance() {
        if (instance == null)
            instance = new ControllerForModel();
        return instance;
    }
}
