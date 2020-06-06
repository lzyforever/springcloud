package com.jack.mongodb.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * GridFS操作
 */
@RestController
public class GridFsController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 上传文件
     */
    @GetMapping("/image/upload")
    public String uploadFile() throws Exception {
        File file = new File("E:\\fw.jpg");
        InputStream content = new FileInputStream(file);
        // 存储文件的额外信息，比如用户ID，后面要查询某个用户的所有文件时就可以直接查询
        DBObject metadata = new BasicDBObject("userId", "1001");
        ObjectId fileId = gridFsTemplate.store(content, file.getName(), "image/jpg", metadata);
        System.out.println(fileId.toString());
        return "success";
    }

    /**
     * 查询文件
     */
    @GetMapping("/image/get/{fileId}")
    public String getFile(@PathVariable String fileId) throws Exception{
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        System.out.println(file.getFilename());
        return "success";
    }

    /**
     * 删除文件
     */
    @GetMapping("/image/remove/{fileId}")
    public String removeFile(@PathVariable  String fileId) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
        return "success";
    }

    /**
     * 下载文件
     * Controller中想直接访问存储的文件非常简单，直接通过文件ID查询该文件，然后直接输出
     * 到response就可以了，记得要设置ContentType
     */
    @GetMapping("/image/{fileId}")
    public void getImage(@PathVariable String fileId, HttpServletResponse response) throws Exception{
        GridFSFile gridfs = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        response.setHeader("Content-Disposition", "attachment;filename=\""+ gridfs.getFilename()+"\"");
        GridFSBucket gridFSBucket = GridFSBuckets.create(mongoTemplate.getDb());
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridfs.getObjectId());
        GridFsResource resource = new GridFsResource(gridfs, gridFSDownloadStream);
        InputStream ins = resource.getInputStream();
        OutputStream ous = response.getOutputStream();
        IOUtils.copy(ins, ous);
    }
}
