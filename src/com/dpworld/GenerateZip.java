package com.dpworld;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenerateZip {
    public GenerateZip() {
    }

    public void generateZip(String fileName) {
        String path = "\\\\192.168.6.29\\edi\\PRODUCTION\\OUT\\INVOICE\\MSC\\InvoiceGenerator\\";
        String ediFile = String.format("%s%s.edi", path, fileName);
        String ediPdf = String.format("%s%s.pdf", path, fileName);
        final List<String> srcFiles = new ArrayList<>();
        srcFiles.add(ediFile);
        srcFiles.add(ediPdf);
        //
        String zipFileName = String.format("\\\\192.168.6.29\\edi\\PRODUCTION\\OUT\\INVOICE\\MSC\\InvoiceGenerator\\%s.zip", fileName);

        try {
            final FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            if (zipOut != null) {
                for (String srcFile : srcFiles) {
                    File fileToZip = new File(srcFile);
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
            }
            //
            zipOut.close();
            fos.close();
        } catch (Exception ex) {
            System.out.println("Method generateZip " + ex.getMessage());
        }
    }
}
