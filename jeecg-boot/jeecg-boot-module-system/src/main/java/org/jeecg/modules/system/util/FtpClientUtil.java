package org.jeecg.modules.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.shiro.web.servlet.Cookie.ROOT_PATH;

/**
 * @ClassName: FtpClientUtil
 * @Description:
 * @Author: lijianwei
 * @DATE: 2020/9/28 10:11
 **/
@Slf4j
//@Configuration
public class FtpClientUtil {
    private static final String FILE_PAKAGE = "/jeecg/upload/";
    private static String timePath = new SimpleDateFormat("yyyyMM/dd/").format(new Date());
    private static int port;//FTP端口
    private static String host;//FTP主机地址
    private static String userName;//FTP用户名
    private static String password;//FTP密码
    private static final int CONNECTTIMEOUT = 3000;//连接超时时间
    private static final String ENCODE = "UTF-8";
    private static final String DEFAULT_ENCODE = "iso-8859-1";

    @Value("${jeecg.ftpclient.port}")
    public static void setPort(int port) {
        FtpClientUtil.port = port;
    }

    @Value("${jeecg.ftpclient.host}")
    public static void setHost(String host) {
        FtpClientUtil.host = host;
    }

    @Value("${jeecg.ftpclient.userName}")
    public static void setUserName(String userName) {
        FtpClientUtil.userName = userName;
    }

    @Value("${jeecg.ftpclient.password}")
    public static void setPassword(String password) {
        FtpClientUtil.password = password;
    }

    private static FTPClient ftpClient = getFtpClient();

    private static FTPClient getFtpClient() {
        if (ftpClient != null) {
            return ftpClient;
        } else {
            ftpClient = new FTPClient();
            ftpClient.setConnectTimeout(CONNECTTIMEOUT);
            try {
                ftpClient.connect(host, port);
                int reply = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log.warn("--------------FTPServer refused connection------------");
                    return null;
                }
                boolean result = ftpClient.login(userName, password);
                if (!result) {
                    log.error("ftpClient登陆失败! userName:{}, password:{}", userName, password);
                    return null;
                }
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.enterLocalPassiveMode();
                return ftpClient;
            } catch (Exception e) {
                log.error(ExceptionUtil.getErrorStack(e));
                return null;
            }
        }
    }

    public static String upload(MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            log.error(ExceptionUtil.getErrorStack(e));
            return null;
        }
        return upload(file.getOriginalFilename(), in);
    }

    public static String upload(String fileName, InputStream in) {
        return upload(FILE_PAKAGE + timePath, fileName, in);
    }

    public static String upload(String direc, String fileName, InputStream input) {
        if (null == input || StringUtils.isEmpty(fileName) || StringUtils.isEmpty(direc)) {
            log.warn("--------------------文件信息不能为空------------------------");
            return null;
        }
        if (ftpClient == null) {
            log.info("--------------ftpclient is null----------------");
            return null;
        }
        try {
            createDirectory(direc);
        } catch (IOException e) {
            log.error("------------------创建ftp目录失败：{}--------------------", direc);
            return null;
        }
        try {
            boolean result = ftpClient.storeFile(new String(fileName.getBytes(ENCODE), DEFAULT_ENCODE), input);
            if (result) {
                return FILE_PAKAGE + timePath + fileName;
            }
        } catch (Exception e) {
            log.error("上传文件{}至ftp失败：{}", fileName, ExceptionUtil.getErrorStack(e));
        } finally {
            try {
                input.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            System.gc();
        }
        return null;
    }

    public boolean removeFile(String url) throws Exception {
        if (ftpClient == null) {
            log.info("--------------ftpclient is null----------------");
            return false;
        }
        try {
            String path = url.substring(url.indexOf(FILE_PAKAGE) + 9);
            boolean suc = ftpClient.deleteFile(new String(path.getBytes(ENCODE), DEFAULT_ENCODE));
            if (!suc) {
                log.info("---------------删除文件：{} 失败！------------------", path);
            } else {
                log.info("-------------------删除文件：{} 成功！----------------", path);
                return true;
            }
        } catch (Exception e) {
            log.error("删除文件异常:{}", ExceptionUtil.getErrorStack(e));
        }
        return false;
    }

    private static void createDirectory(String directory) throws IOException {
        if (!StringUtils.isEmpty(directory)) {
            if (directory.contains("/")) {
                String[] dirs = directory.split("/");
                for (String dir : dirs) {
                    ftpClient.mkd(dir);
                    ftpClient.changeWorkingDirectory(dir);
                }
            } else {
                ftpClient.mkd(directory);
                ftpClient.changeWorkingDirectory(directory);
            }
        }
    }

}
