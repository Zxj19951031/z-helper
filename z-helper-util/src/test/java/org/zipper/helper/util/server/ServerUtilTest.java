package org.zipper.helper.util.server;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.Test;
import org.zipper.helper.util.json.JsonObject;

import java.io.ByteArrayInputStream;

public class ServerUtilTest {

    @Test
    public void sftp() throws JSchException, SftpException {
        String json = "{\"swagger\":\"2.0\",\"info\":{\"description\":\"文档描述\",\"version\":\"版本号1.0\",\"title\":\"接口文档标题\",\"contact\":{\"name\":\"联系人\",\"url\":\"联系地址\",\"email\":\"联系邮箱\"}},\"host\":\"localhost:8080\",\"basePath\":\"/\",\"tags\":[{\"name\":\"hello-controller\",\"description\":\"Hello Controller\"}],\"paths\":{\"/hello/\":{\"get\":{\"tags\":[\"hello-controller\"],\"summary\":\"中文\",\"description\":\"Notes\",\"operationId\":\"helloUsingGET\",\"produces\":[\"*/*\"],\"parameters\":[{\"name\":\"name\",\"in\":\"query\",\"description\":\"name\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"OK\",\"schema\":{\"$ref\":\"#/definitions/ResponseEntity«string»\"}},\"401\":{\"description\":\"Unauthorized\"},\"403\":{\"description\":\"Forbidden\"},\"404\":{\"description\":\"Not Found\"}},\"deprecated\":false}}},\"definitions\":{\"ResponseEntity«string»\":{\"type\":\"object\",\"properties\":{\"code\":{\"type\":\"integer\",\"format\":\"int32\"},\"data\":{\"type\":\"string\"},\"message\":{\"type\":\"string\"}},\"title\":\"ResponseEntity«string»\"}}}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(JsonObject.from(json).beautify().getBytes());
        ServerUtil.sftp("172.16.25.222", 22, "root", "HZdsj@123", inputStream, "/tmp/temp.json");
    }
}