package ro.msg.edu.jbugs.manager.remote;

import javax.ejb.Remote;

@Remote
public interface CommentManagerRemote {

    Integer deleteCommentsOlderThan1Year();
}
