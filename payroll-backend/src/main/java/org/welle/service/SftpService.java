package org.welle.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.welle.constants.Configuration;

@ApplicationScoped
public class SftpService {

    final static Logger logger = Logger.getLogger(SftpService.class);

    private String HOST;

    private int PORT;

    private String USER;

    private String PASSWORD;

    private FTPClient ftp;

    public SftpService() {
        // Kinda stupied, but I had issues using defaul thorntail config reading tool, so I wrote my own ...
        Configuration config = new Configuration();
        this.HOST = config.properties.get("payrolls.sftp.host").toString();
        this.PORT = Integer.parseInt(config.properties.get("payrolls.sftp.port").toString());
        this.USER = config.properties.get("payrolls.sftp.user").toString();
        this.PASSWORD = String.valueOf(config.properties.get("payrolls.sftp.password"));
    }

    public void connect() throws IOException {
        logger.info("host: " + HOST);
        logger.info("port: " + PORT);

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