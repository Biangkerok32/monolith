package com.runssnail.monolith.event;

import java.util.EventObject;

/**
 * �¼�����
 * 
 * @author zhengwei
 */
public abstract class SystemEvent extends EventObject {

    public SystemEvent(Object source) {
        super(source);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8882808205429352707L;

}
