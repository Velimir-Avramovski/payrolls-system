package org.welle.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class SftpService {

    // Move these in properties file!

    private final String HOST = "localhost";
    private final int PORT = 21;
    private final String USER = "test";
    private String PASSWORD = "1234";
    private String FILE_OUTPUT = "C:\\Payrolls\\Downloads";

    private FTPClient ftp;

    public SftpService() {

    }

    public void connect() throws IOException {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(HOST, PORT);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(USER, PASSWORD);
    }

    public void upload(File file, String path) throws IOException {
        ftp.storeFile(path, new FileInputStream(file));
    }

    public File download(String source, String destination) throws IOException {
        File outFile = new File(destination);
        FileOutputStream out = new FileOutputStream(outFile);
        ftp.retrieveFile(source, out);
        return outFile;
    }

    public void disconnect() throws IOException {
        ftp.disconnect();
    }
}