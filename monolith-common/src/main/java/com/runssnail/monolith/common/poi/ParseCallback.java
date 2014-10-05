package com.runssnail.monolith.common.poi;

/**
 * ����excel�ص��ӿ�
 * 
 * @author zhengwei
 */
public interface ParseCallback<E> {

    /**
     * ��������(һ�б�ʾһ�����ݶ���)
     * 
     * @return
     */
    public E createObj();

    /**
     * �����Ƿ���Ч����Ч�Ż���뵽��Ч�б��������
     * 
     * @param e
     * @return
     */
    boolean isValid(E e);

}
