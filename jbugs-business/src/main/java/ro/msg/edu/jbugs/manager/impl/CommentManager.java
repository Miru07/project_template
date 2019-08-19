package ro.msg.edu.jbugs.manager.impl;

import ro.msg.edu.jbugs.dao.CommentDao;
import ro.msg.edu.jbugs.interceptors.TimeInterceptors;
import ro.msg.edu.jbugs.manager.remote.CommentManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

@Stateless
@Interceptors(TimeInterceptors.class)
public class CommentManager implements CommentManagerRemote {

    @EJB
    private CommentDao commentDao;

    @Override
    public Integer deleteCommentsOlderThan1Year(){

        return commentDao.deleteCommentsOlderThan1Year();
    }
}
