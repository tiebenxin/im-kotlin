package com.abc.im.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FileMessage extends RealmObject implements IMsgContent{

    @PrimaryKey
    private String msgId;
    private String url; // 下载地址
    private String fileName; // 原始文件名
    private String format; // 格式描述
    private long size = 0; // 文件大小
    private String localPath;//文件本地路径
    private boolean isFromOther;//true 从别人那里转发过来的文件，则需从下载地址里找  false 本地文件/自己转发自己的文件，则需要在本地路径找
    private String realFileRename;//若存在文件重名，此值为新名称；若不重名，则为原文件名

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public boolean isFromOther() {
        return isFromOther;
    }

    public void setFromOther(boolean fromOther) {
        isFromOther = fromOther;
    }

    public String getRealFileRename() {
        return realFileRename;
    }

    public void setRealFileRename(String realFileRename) {
        this.realFileRename = realFileRename;
    }
}
