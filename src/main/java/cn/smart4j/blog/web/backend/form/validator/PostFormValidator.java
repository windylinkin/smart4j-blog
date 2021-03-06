package cn.smart4j.blog.web.backend.form.validator;

import cn.smart4j.blog.core.dal.constants.PostConstants;
import cn.smart4j.blog.core.dal.entity.Post;
import cn.smart4j.blog.core.util.StringUtils;
import cn.smart4j.blog.core.plugin.MapContainer;

public class PostFormValidator{

  public static MapContainer validatePublish(Post post){
    return validatePost(post, true);
  }

  public static MapContainer validateUpdate(Post post){
    MapContainer form = validatePublish(post);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  public static MapContainer validateFastUpdate(Post post){
    MapContainer form = validatePost(post, false);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  private static MapContainer validatePost(Post post, boolean verifyContent){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(post.getTitle())){
      form.put("msg", "文章标题未填写");
    }else if(verifyContent && StringUtils.isBlank(post.getContent())){
      form.put("msg", "请填写文章内容");
    }else if(PostConstants.TYPE_POST.equals(post.getType()) && StringUtils.isBlank(post.getCategoryid())){
      form.put("msg", "请选择文章分类");
    }

    return form;
  }

}
