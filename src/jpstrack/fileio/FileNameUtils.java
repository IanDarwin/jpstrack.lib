package jpstrack.fileio;

public class FileNameUtils {
	
	private static final String FILENAME_FORMAT = "YYYYMMDDHHMM";
	public static final String GPX_FILENAME_EXTENSION = ".gpx";
	private final static String filenameFormat = "%1$tY%1$tm%1$td%1$tH%1$tM";

	/** This may become a facility for interpreting filename formats */
	//	private static Map<String,String> formatMap = new HashMap<String,String>();
	//	static {
	//		formatMap.put("YYYY", "$tY");
	//	}

	public static String getDefaultFilenameFormat() {
		return FILENAME_FORMAT;
		
	}
	public static String getDefaultFilenameFormatWithExt() {
		return FILENAME_FORMAT + "." + GPX_FILENAME_EXTENSION;
	}
	public static String getNextFilename() {
		return getNextFilename(GPX_FILENAME_EXTENSION);
	}
	public static String getNextFilename(String ext) {
		return String.format(filenameFormat, System.currentTimeMillis() + ext);		
	}
}
