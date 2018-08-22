package com.nomsa.bbs.Service;

import com.nomsa.bbs.Mapper.BoardMapper;
import com.nomsa.bbs.Model.FileModel;
import com.nomsa.bbs.Model.PostModel;
import com.nomsa.bbs.Model.ReplyModel;
import com.nomsa.bbs.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BoardService {

    @Autowired
    BoardMapper postMapper;

    public List<PostModel> getPosts (String type, String title, String content) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("title", title);
        map.put("content", content);

        List<PostModel> posts = postMapper.getPosts(map);

        return posts;
    }

    public PostModel getPost(int postIndex) {
        PostModel postModel = postMapper.getPost(postIndex);
        postModel.setFiles(postMapper.getFiles(postIndex));
        postModel.setReplies(postMapper.getReplies(postIndex));

        return postModel;
    }

    public FileModel getFile (int fileIndex) {
        return postMapper.getFile(fileIndex);
    }

    public int writePost (UserModel userModel, PostModel postModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserModel", userModel);
        map.put("PostModel", postModel);

        postMapper.writePost(map);
        postModel.setIndex(Math.toIntExact((long) map.get("index")));

        if (postModel.getFiles().size() > 0) postMapper.writeFile(postModel);

        return Math.toIntExact((long) map.get("index"));
    }

    public void writeReply (UserModel userModel, ReplyModel replyModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserModel", userModel);
        map.put("ReplyModel", replyModel);

        postMapper.writeReply(map);
    }

}
