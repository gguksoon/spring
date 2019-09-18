package kr.or.ddit.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import kr.or.ddit.util.model.FileInfo;

public class FileUtil {

	public static FileInfo getFileInfo(String originalFilename) {
		String path = getPath();
		
		String uuidFileName = UUID.randomUUID().toString();
		String extName = getExtension(originalFilename);
		File file = new File(path + "/" + uuidFileName + extName);
		FileInfo fileInfo = new FileInfo(file, originalFilename, extName);
		
		return fileInfo;
	}

	private static String getPath() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String yyyyMM = sdf.format(new Date());
		String yyyy = yyyyMM.substring(0, 4);
		String mm = yyyyMM.substring(4, 6);
		
		String path = "e:/springUpload/" + yyyy + "/" + mm;
		
		File pathFile = new File(path);
		
		if(!pathFile.exists()) pathFile.mkdirs();
		
		return path;
	}

	private static String getExtension(String originalFilename) {
		String extName = "";
		if(originalFilename.contains("."))
			extName = originalFilename.substring(originalFilename.lastIndexOf("."));
		
		return extName;
	}

}
