/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.antx.util;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * ����һ��<code>File</code>�ĸ�����, ����ȡ�þ��Ժ����·��.
 *
 * @author Michael Zhou
 *
 */
public class FileObject {
    private static final String  CURRENT_DIR    = ".";
    private static final char    COLON_CHAR     = ':';
    private static final String  UNC_PREFIX     = "\\\\";
    private static final String  FILE_SEP       = File.separator;
    private static final String  SLASH          = "/";
    private static final String  BACKSLASH      = "\\";
    private static final char    SLASH_CHAR     = '/';
    private static final char    BACKSLASH_CHAR = '\\';
    private static final String  UP_LEVEL_DIR   = ".." + SLASH;
    private static final boolean IS_WINDOWS     = System.getProperty("os.name").toLowerCase()
                                                        .indexOf("windows") >= 0;
    private String               abspath;
    private String               relpath;

    /**
     * ����һ���յ�file object��
     */
    public FileObject() {
    }

    /**
     * ����һ��<code>FileObject</code>.
     *
     * @param file �ļ�
     */
    public FileObject(File file) {
        this((file == null) ? null
                            : file.getAbsolutePath());
    }

    /**
     * ����һ��<code>FileObject</code>.
     *
     * @param path �ļ���
     */
    public FileObject(String path) {
        setPath(path);
    }

    /**
     * ����һ��<code>FileObject</code>.
     *
     * @param abspath ����·��
     * @param relpath ���·��
     */
    private FileObject(String abspath, String relpath) {
        this(abspath);
        this.relpath = normalizePath(relpath);
    }

    /**
     * ����path��
     *
     * @param path path
     */
    public void setPath(String path) {
        path = normalizePath(path);

        boolean endsWithSlash = endsWithSlash(path);

        try {
            abspath = new File(path).getCanonicalPath();
        } catch (IOException e) {
            abspath = new File(path).getAbsolutePath();
        }

        if (endsWithSlash && !endsWithSlash(abspath)) {
            abspath += FILE_SEP;
        }
    }

    /**
     * ȡ�þ���·��.
     *
     * @return ��ǰ<code>FileObject</code>�ľ���·��
     */
    public String getAbsolutePath() {
        return toString(false, SLASH);
    }

    /**
     * ȡ�þ���·��.
     *
     * @param sep �ָ���
     *
     * @return ��ǰ<code>FileObject</code>�ľ���·��
     */
    public String getAbsolutePath(String sep) {
        return toString(false, sep);
    }

    /**
     * ȡ�����·��.
     *
     * @return ��ǰ<code>FileObject</code>�����·��
     */
    public String getRelativePath() {
        return toString(true, SLASH);
    }

    /**
     * ȡ�����·��.
     *
     * @param sep �ָ���
     *
     * @return ��ǰ<code>FileObject</code>�����·��
     */
    public String getRelativePath(String sep) {
        return toString(true, sep);
    }

    /**
     * ȡ��<code>File</code>����.
     *
     * @return <code>File</code>����
     */
    public File getFile() {
        return new File(abspath);
    }

    /**
     * ȡ������ڵ�ǰ<code>FileObject</code>��·��.
     *
     * @param basedir ��Ŀ¼
     * @param path �ļ�
     *
     * @return ����ڵ�ǰ<code>FileObject</code>��·��
     */
    public FileObject newFileObject(FileObject basedir, String path) {
        return newFileObject(basedir.newFileObject(path).getFile());
    }

    /**
     * ȡ������ڵ�ǰ<code>FileObject</code>��·��.
     *
     * @param file �ļ�
     *
     * @return ����ڵ�ǰ<code>FileObject</code>��·��
     */
    public FileObject newFileObject(File file) {
        return newFileObject((file == null) ? null
                                            : file.getAbsolutePath());
    }

    /**
     * ȡ������ڵ�ǰ<code>FileObject</code>��·��.
     *
     * @param path ·��
     *
     * @return ����ڵ�ǰ<code>FileObject</code>��·��
     */
    public FileObject newFileObject(String path) {
        path = normalizePath(path);

        boolean endsWithSlash = endsWithSlash(path);
        File    pathFile = new File(path);

        if (!pathFile.isAbsolute()) {
            pathFile = new File(abspath, path);
        }

        try {
            path = pathFile.getCanonicalPath();
        } catch (IOException e) {
            path = pathFile.getAbsolutePath();
        }

        if (endsWithSlash && !endsWithSlash(path)) {
            path += FILE_SEP;
        }

        String thisPrefix = getSystemDependentPrefix(abspath);
        String prefix = getSystemDependentPrefix(path);

        if (!prefix.equals(thisPrefix)) {
            return new FileObject(path); // �������ת�����·��, �򷵻ؾ���·��
        }

        String[]     thisParts = getPathParts(abspath, thisPrefix, isFile(abspath));
        String[]     parts = getPathParts(path, prefix, false);

        StringBuffer buffer = new StringBuffer();
        int          i      = 0;

        if (IS_WINDOWS) {
            while ((i < thisParts.length) && (i < parts.length)
                        && thisParts[i].equalsIgnoreCase(parts[i])) {
                i++;
            }
        } else {
            while ((i < thisParts.length) && (i < parts.length) && thisParts[i].equals(parts[i])) {
                i++;
            }
        }

        if ((i < thisParts.length) && (i < parts.length)) {
            for (int j = i; j < thisParts.length; j++) {
                buffer.append(UP_LEVEL_DIR);
            }
        }

        for (; i < parts.length; i++) {
            buffer.append(parts[i]);

            if (i < (parts.length - 1)) {
                buffer.append(SLASH_CHAR);
            }
        }

        String relpath = buffer.toString();

        if (endsWithSlash && !endsWithSlash(relpath)) {
            relpath += SLASH;
        }

        return new FileObject(path, relpath);
    }

    /**
     * ��ָ���������е�·��, ת��������ڵ�ǰ<code>FileObject</code>����.
     *
     * @param basedir ��Ŀ¼
     * @param paths ·������
     *
     * @return ��
     */
    public Map tree(FileObject basedir, String[] paths) {
        return tree(basedir, Arrays.asList(paths));
    }

    /**
     * ��ָ���ļ����е�·��, ת��������ڵ�ǰ<code>FileObject</code>����.
     *
     * @param basedir ��Ŀ¼
     * @param paths ·������
     *
     * @return ��
     */
    public Map tree(FileObject basedir, Collection paths) {
        Map tree = new HashMap();

        for (Iterator i = paths.iterator(); i.hasNext();) {
            String          abspath       = i.next().toString();
            String          pathToBasedir = basedir.newFileObject(abspath).getRelativePath();
            String          path          = newFileObject(abspath).getRelativePath();
            StringTokenizer tokenizer     = new StringTokenizer(pathToBasedir, SLASH);
            Map             node          = tree;

            while (tokenizer.hasMoreTokens()) {
                String s = tokenizer.nextToken();

                if (tokenizer.hasMoreTokens()) {
                    Map tmp = (Map) node.get(s);

                    if (tmp == null) {
                        tmp = new HashMap();
                        node.put(s, tmp);
                    }

                    node = tmp;
                } else {
                    node.put(s, path);
                }
            }
        }

        return tree;
    }

    /**
     * ȡ�þ���·�����ַ���.
     *
     * @return ����·�����ַ���
     */
    public String toString() {
        return toString(false, SLASH);
    }

    /**
     * ȡ����Ի����·�����ַ���.
     *
     * @param relative �Ƿ�Ϊ���·��
     * @param sep ʹ��ָ���ķָ���(��UNC·����Ч)
     *
     * @return ��Ի����·�����ַ���
     */
    private String toString(boolean relative, String sep) {
        String path;

        if (relative) {
            path = (relpath == null) ? abspath
                                     : relpath;
        } else {
            path = abspath;
        }

        if (isUncPath(path)) {
            return path;
        }

        if (BACKSLASH.equals(sep)) {
            return path.replace(SLASH_CHAR, BACKSLASH_CHAR);
        } else {
            return path.replace(BACKSLASH_CHAR, SLASH_CHAR);
        }
    }

    /**
     * �ж�һ��·���Ƿ�Ϊ�ļ�.
     *
     * @param path Ҫ����·��
     *
     * @return ������ļ�, �򷵻�<code>true</code>
     */
    private boolean isFile(String path) {
        if (path == null) {
            return false;
        }

        File file = new File(path);

        return file.isFile() && file.exists();
    }

    /**
     * ���·��, ȷ��·���ǿ�.
     *
     * @param path Ҫ��񻯵�·��
     *
     * @return ��񻯵�·��
     */
    private String normalizePath(String path) {
        if (path == null) {
            return CURRENT_DIR;
        } else {
            path = path.trim();

            if (path.length() == 0) {
                return CURRENT_DIR;
            }

            return path;
        }
    }

    /**
     * ���ָ��·���Ƿ�ΪUNC·��.
     *
     * @param path Ҫ����·��.
     *
     * @return �����UNC·��, �򷵻�<code>true</code>
     */
    private boolean isUncPath(String path) {
        return path.startsWith(UNC_PREFIX);
    }

    /**
     * ���ָ��·���Ƿ���"/"��"\\"��β.
     *
     * @param path Ҫ����·��.
     *
     * @return �����"/"��"\\"��β, �򷵻�<code>true</code>
     */
    private boolean endsWithSlash(String path) {
        return path.endsWith(SLASH) || path.endsWith(BACKSLASH);
    }

    /**
     * ȡ�ú�ϵͳ��ص��ļ���ǰ׺.  ����Windowsϵͳ, ����������������UNC·��ǰ׺"\\". ���������ǰ׺, �򷵻ؿ��ַ���.
     *
     * @param path ����·��
     *
     * @return ��ϵͳ��ص��ļ���ǰ׺
     */
    private String getSystemDependentPrefix(String path) {
        if (IS_WINDOWS) {
            if (isUncPath(path)) {
                int index = path.indexOf(FILE_SEP, UNC_PREFIX.length());

                if (index != -1) {
                    return path.substring(0, index);
                } else {
                    return path;
                }
            } else if ((path.length() > 1) && (path.charAt(1) == COLON_CHAR)) {
                return path.substring(0, 2).toLowerCase();
            }
        }

        return "";
    }

    /**
     * ��path������ɲ���, ������������.
     *
     * @param path ����·��
     * @param prefix ·��ǰ׺
     * @param treatAsFile �����ļ�
     *
     * @return ָ������·����Ƭ������
     */
    private String[] getPathParts(String path, String prefix, boolean treatAsFile) {
        StringTokenizer tokenizer = new StringTokenizer(path.substring(prefix.length()), FILE_SEP);
        List            parts = new ArrayList();

        while (tokenizer.hasMoreTokens()) {
            parts.add(tokenizer.nextToken());
        }

        if (treatAsFile) {
            parts.remove(parts.size() - 1);
        }

        return (String[]) parts.toArray(new String[parts.size()]);
    }
}
