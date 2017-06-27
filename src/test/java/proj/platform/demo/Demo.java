package proj.platform.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Demo {
	public static Logger log = Logger.getLogger(Demo.class);

	@Test
	public void testSortList() {
		List<Integer> numberList = new ArrayList<Integer>();
		numberList.add(2);
		numberList.add(9);
		numberList.add(11);
		numberList.add(7);

		System.out.println(numberList);

		Collections.sort(numberList);
		System.out.println(numberList);
	}

	@Test
	public void testStringSortList() {
		// 如果是以字符串表示的数字，若要实现数字的排序，则直接调用Collections.sort()是不可行的，需要自己实现Comparor()
		List<String> stringList1 = new ArrayList<String>();
		stringList1.add("2");
		stringList1.add("9");
		stringList1.add("11");
		stringList1.add("7");

		System.out.println(stringList1);

		Collections.sort(stringList1);
		System.out.println(stringList1);

		List<String> stringList2 = new ArrayList<String>();
		stringList2.add("2");
		stringList2.add("9");
		stringList2.add("11");
		stringList2.add("7");

		System.out.println(stringList2);

		Collections.sort(stringList2, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return new Double(o1).compareTo(new Double(o2));
			}
		});
		System.out.println(stringList2);

		List<String> stringList3 = new ArrayList<String>();
		stringList3.add("2");
		stringList3.add("9");
		stringList3.add("11");
		stringList3.add("7");

		System.out.println(stringList3);

		Collections.sort(stringList3, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return new Double(o2).compareTo(new Double(o1));
			}
		});
		System.out.println(stringList3);
	}

	@Test
	public void testZipFile() {
		listAllFile(new File("D:/resources"));
		File file1 = new File("D:\\resources\\word文档.docx");
		File file2 = new File("D:\\resources\\好用的PDF格式文件.pdf");
		File file3 = new File("D:\\resources\\文本文件.txt");
		File file4 = new File("D:\\resources\\这是一份PPT.pptx");
		List<File> files = new ArrayList<File>();
		files.add(file1);
		files.add(file2);
		files.add(file3);
		files.add(file4);
		ZipOutputStream zipOut = null;
		FileInputStream input = null;
		BufferedInputStream buff = null;

		try {
			zipOut = new ZipOutputStream(new FileOutputStream("D:\\resources\\my2.zip"));
			for (File file : files) {
				input = new FileInputStream(file);
				buff = new BufferedInputStream(input);
				zipOut.putNextEntry(new ZipEntry(file.getName()));
				int b;
				// while((b = input.read()) != -1){
				// zipOut.write(b);
				// }
				while ((b = buff.read()) != -1) {
					zipOut.write(b);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void listAllFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listAllFile(files[i]);
			}
		} else {
			System.out.println(file);
		}
	}
}
