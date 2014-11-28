package q8388415.brero_massimiliano.PTNetEditor.utils;

import java.io.File;
import java.io.FileFilter;

import q8388415.brero_massimiliano.PTNetEditor.types.AllowedFileTypes;

public class PTNFileFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        
        /**
         *  Filter returns tru if we get a lder. Otherwise it's not possible to
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

    @Override
    public String getDescription() {
        return "Zulässige Dateitypen";
    }

}
