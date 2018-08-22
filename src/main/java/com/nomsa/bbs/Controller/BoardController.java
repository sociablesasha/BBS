package com.nomsa.bbs.Controller;

import com.nomsa.bbs.Model.FileModel;
import com.nomsa.bbs.Model.PostModel;
import com.nomsa.bbs.Model.ReplyModel;
import com.nomsa.bbs.Model.UserModel;
import com.nomsa.bbs.Service.BoardService;
import com.nomsa.bbs.Util.JWT;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Path;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    private JWT jwtService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<PostModel> getPosts (@RequestParam(value = "type", required = false, defaultValue = "all") String type,
                                     @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                     @RequestParam(value = "content", required = false, defaultValue = "") String content) {
        return boardService.getPosts(type, title, content);
    }

    @RequestMapping(value = "/posts/{postIndex}", method = RequestMethod.GET)
    public PostModel getPost (@RequestHeader(value = "infranics", defaultValue = "") String jwt,
                              @PathVariable int postIndex) {
        jwtService.isUsable(jwt);

        return boardService.getPost(postIndex);
    }

    @RequestMapping(value = "/posts/{postIndex}/files/{fileIndex}", method = RequestMethod.GET)
    public void getFile (@RequestHeader(value = "infranics", defaultValue = "") String jwt,
                           @PathVariable int postIndex,
                           @PathVariable int fileIndex,
                           HttpServletResponse httpServletResponse) {
//        jwtService.isUsable(jwt);

        try {
            FileModel fileModel = boardService.getFile(fileIndex);

            httpServletResponse.setContentType("aplication/download");
            httpServletResponse.setContentLengthLong(fileModel.getSize());
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileModel.getName() + "\";");

            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(fileModel.getBytes());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public int writePost (@RequestHeader(value = "infranics", defaultValue = "") String jwt,
                                @RequestParam(value = "type") String type,
                                @RequestParam(value = "title") String title,
                                @RequestParam(value = "content") String content,             
                                @RequestParam(value = "files", required = false) MultipartFile[] files) {
        jwtService.isUsable(jwt);
        UserModel userModel = jwtService.getUser();

        PostModel postModel = new PostModel();
        postModel.setType(type);
        postModel.setTitle(title);
        postModel.setContent(content);

        ArrayList<FileModel> fileModels = new ArrayList<>();
        try {
            for (int beforeIndex = 0; beforeIndex < files.length; beforeIndex++) {
                try {
                    MultipartFile beforeFile = files[beforeIndex];
                    if (beforeFile != null && !beforeFile.isEmpty()) {
                        FileModel fileModel = new FileModel();
                        fileModel.setName(beforeFile.getOriginalFilename());
                        fileModel.setSize(beforeFile.getSize());
                        fileModel.setBytes(beforeFile.getBytes());
                        fileModels.add(fileModel);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        postModel.setFiles(fileModels);

        return boardService.writePost(userModel, postModel);
    }

    @RequestMapping(value = "/posts/{postIndex}/replies", method = RequestMethod.POST)
    public void writeReply (@RequestHeader(value = "infranics", defaultValue = "") String jwt,
                            @PathVariable int postIndex,
                            @RequestParam("content") String content) {
        jwtService.isUsable(jwt);
        UserModel userModel = jwtService.getUser();

        ReplyModel replyModel = new ReplyModel();
        replyModel.setPost(postIndex);
        replyModel.setContent(content);

        boardService.writeReply(userModel, replyModel);
    }

}
