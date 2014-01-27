package com.kongur.monolith.socket.message;

import java.util.ArrayList;
import java.util.List;

import com.kongur.monolith.socket.message.header.UpstreamHeader;

/**
 * �Է�����������������Ӧ�����
 * 
 * @author zhengwei
 */
public class UpstreamMessageSet<UM extends UpstreamMessage> extends AbstractUpstreamMessage {

    /**
     * 
     */
    private static final long serialVersionUID = 6698260930944439893L;
    /**
     * �������һ����¼��Ӧ����һ��Ԫ��
     */
    private List<UM>          upstreamMessageList;

    public UpstreamMessageSet(UpstreamHeader header) {
        super(header);
    }

    public int size() {
        return upstreamMessageList != null ? upstreamMessageList.size() : 0;
    }

    public List<UM> getUpstreamMessageList() {
        return upstreamMessageList;
    }

    public void setUpstreamMessageList(List<UM> upstreamMessageList) {
        this.upstreamMessageList = upstreamMessageList;
    }

    public void addUpstreamMessage(UM uso) {
        if (this.upstreamMessageList == null) {
            this.upstreamMessageList = new ArrayList<UM>();
        }
        this.upstreamMessageList.add(uso);
    }

}
