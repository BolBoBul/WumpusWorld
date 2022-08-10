package Tools;

import java.io.File;

public class ResourcesBrowser {
    public static final String RESOURCESPATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "main" + File.separator + "resources" + File.separator;
    public static final String IMAGEPATH = RESOURCESPATH+"ImageLibrary"+File.separator;
}
