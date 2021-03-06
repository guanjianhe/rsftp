package org.tuzhao.ftp.entity;

import org.apache.commons.net.ftp.FTPFile;

/**
 * author: tuzhao
 * 2017-08-21 20:39
 */
public final class RsFTPFile implements RsFile<FTPFile> {

    private FTPFile file;
    private boolean selected;
    private String name;

    public RsFTPFile(FTPFile file) {
        this.file = file;
        this.name = file.getName();
        this.selected = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getModifyTimeMillis() {
        return file.getTimestamp().getTimeInMillis();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public boolean isDir() {
        return file.isDirectory();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public boolean canExecute() {
        return this.file.hasPermission(FTPFile.USER_ACCESS, FTPFile.EXECUTE_PERMISSION);
    }

    @Override
    public boolean canRead() {
        return this.file.hasPermission(FTPFile.USER_ACCESS, FTPFile.READ_PERMISSION);
    }

    @Override
    public boolean canWrite() {
        return this.file.hasPermission(FTPFile.USER_ACCESS, FTPFile.WRITE_PERMISSION);
    }

    @Override
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

    @Override
    public boolean getSelected() {
        return selected;
    }

    @Override
    public FTPFile getRealFile() {
        return this.file;
    }

    public void setName(String name) {
        this.name = name;
    }

}
