package com.nomsa.bbs.Mapper;

import com.nomsa.bbs.Model.FileModel;
import com.nomsa.bbs.Model.PostModel;
import com.nomsa.bbs.Model.ReplyModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    List<PostModel> getPosts(Map map);

    PostModel getPost(int postIndex);

    List<FileModel> getFiles(int postIndex);

    FileModel getFile(int fileIndex);

    List<ReplyModel> getReplies(int postIndex);

    boolean writePost(Map map);

    boolean writeFile(PostModel postModel);

    boolean writeReply(Map map);

}
