package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.io.File;

import q8388415.brero_massimiliano.PTNetEditor.types.AllowedFileTypes;

/**
 * Extends {@link javax.swing.filechooser.FileFilter}. 
 * Only file of types defined in {@link AllowedFileTypes} are ...
 * errr ... well ... allowed.
 * 
 * @author Laptop
 *
 */
public class PTNFileFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        
        /**
         *  Filter returns true if we get a folder. Otherwise it's not possible to
         *  navigate to subfolders.
         */
        if (file.isDirectory()) {
            return true;            
        }
        else {
            
            for(AllowedFileTypes type : AllowedFileTypes.values()) {
                if (file.getName().toLowerCase().endsWith("." + type))
                    return true;
            }
            
        }
            
        return false;
    }

    /**
     * @return String
     */
    @Override
    public String getDescription() {
        return "Zulässige Dateitypen";
    }

}
