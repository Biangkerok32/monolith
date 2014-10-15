package com.runssnail.monolith.socket.message.header;



/**
 * ����ͷ��������
 * 
 * @author zhengwei
 * @version 2013-10-15 ����2:11:26
 */
public interface HeaderCreateFactory {

    /**
     * ����UpstreamHeader
     * 
     * @return
     */
    UpstreamHeader createUpstreamHeader();

    /**
     * ����DownstreamHeader
     * 
     * @return
     */
    DownstreamHeader createDownstreamHeader();

}
