package com.kongur.monolith.socket.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kongur.monolith.socket.message.header.DownstreamHeader;

/**
 * ���������Ӧ�����
 * 
 * @author zhengwei
 */
public class DownstreamMessageSet<DM extends DownstreamMessage> extends AbstractDownstreamMessage implements DownstreamMessage {

    /**
     * 
     */
    private static final long serialVersionUID = -4519281024638522998L;

    /**
     * �������һ����¼��Ӧ����һ��Ԫ��
     */
    private List<DM>         downstreamMessageList;

    public DownstreamMessageSet(DownstreamHeader header) {
        super(header);
    }

    public DownstreamMessageSet(DownstreamHeader header, int size) {
        super(header);
        this.downstreamMessageList = new ArrayList<DM>(size);
    }

    public DownstreamMessageSet(int size) {
        this(null, size);
    }

    public int size() {
        return downstreamMessageList != null ? downstreamMessageList.size() : 0;
    }

    @SuppressWarnings("unchecked")
    public List<DM> getDownstreamMessageList() {
        return downstreamMessageList == null ? Collections.EMPTY_LIST : downstreamMessageList;
    }

    public void setDownstreamMessageList(List<DM> downstreamMessageList) {
        this.downstreamMessageList = downstreamMessageList;
    }

    public void addDownstreamMessage(DM dso) {
        if (this.downstreamMessageList == null) {
            this.downstreamMessageList = new ArrayList<DM>();
        }

        this.downstreamMessageList.add(dso);
    }

}
