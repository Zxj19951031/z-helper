package org.zipper.helper.util.server;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.exception.HelperException;

import java.io.InputStream;
import java.util.Properties;

/**
 * ftp 工具，支持sftp
 *
 * @author zhuxj
 * @since 2020/06/30
 */
public class ServerUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServerUtil.class);

    /**
     * 上传文件
     *
     * @param host         主机地址
     * @param port         端口sftp默认22
     * @param username     用户名
     * @param password     密码
     * @param inputStream  文件流
     * @param pathWithName 目标文件名称
     */
    public static void sftp(String host, int port, String username, String password, InputStream inputStream, String pathWithName) {
        try {
            Session session = buildSession(host, port, username, password);
            session.connect(60000);
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect(60000);
            sftp.put(inputStream, pathWithName);
            sftp.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            logger.error("Error connecting:" + e.getMessage());
            throw HelperException.newException(ServerError.CONNECT_ERROR, "连接目标服务器异常:" + e.getMessage());
        } catch (SftpException e) {
            logger.error("Error put file:" + e.getMessage());
            throw HelperException.newException(ServerError.PUT_ERROR, "上传文件至服务器异常:" + e.getMessage());
        }
    }

    /**
     * 创建实例
     *
     * @return 实例
     */
    public static Session buildSession(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("userauth.gssapi-with-mic", "no");

            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);

            return session;
        } catch (JSchException e) {
            logger.error("Build sftp error:" + e.getMessage());
            throw HelperException.newException(ServerError.BUILD_ERROR, "构建实例异常:" + e.getMessage());
        }
    }

}
