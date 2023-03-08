package src;

import java.beans.PropertyChangeListener;

/**
 * Defines behaviors that allow propertyChangeListeners to be
 * added to the spreadsheet, so it can be listened to.
 */
public interface PropertyChangeEnabledSpreadSheet {
    
    
    /** The property cycle. */
    String PROPERTY_CYCLE = "cycle";
    
    /**
     * Adds a property change listener.
     *
     * @param theListener the listener
     */
    void addPropertyChangeListener(PropertyChangeListener theListener);
    
    /**
     * Adds a property change listener.
     *
     * @param thePropertyName the property name
     * @param theListener the listener
     */
    void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener);
    
    /**
     * Removes a property change listener.
     *
     * @param theListener the listener
     */
    void removePropertyChangeListener(PropertyChangeListener theListener);
    
    /**
     * Removes a property change listener.
     *
     * @param thePropertyName  the property name
     * @param theListener the listener
     */
    void removePropertyChangeListener(String thePropertyName,
                                      PropertyChangeListener theListener);
}