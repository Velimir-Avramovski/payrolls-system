package org.welle.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.eclipse.microprofile.config.inject.ConfigProperty;

public class SftpService {

    @ConfigProperty(name = "payrolls.sftp.host", defaultValue = "localhost")
    private final String HOST = "localhost";

    @ConfigProperty(name = "payrolls.sftp.port", defaultValue = "21")
    private final int PORT = 21;

    @ConfigProperty(name = "payrolls.sftp.user", defaultValue = "")
    private final String USER = "test";

    @ConfigProperty(name = "payrolls.sftp.password", defaultValue = "")
    private String PASSWORD = "1234";

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
        FileOutputStream out = new FileOutputStream(destination);
        ftp.retrieveFile(source, out);
        File outFile = new File(destination);
        return outFile;
    }

    public void disconnect() throws IOException {
        ftp.disconnect();
    }
}