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

package com.alibaba.antx.util.configuration;

import org.xml.sax.Locator;

/**
 * �������������ļ��е�λ��.
 *
 * @author Michael Zhou
 *
 */
public class Location implements Locator {
    private final String publicId;
    private final String systemId;
    private final int    lineNumber;
    private final int    columnNumber;

    /** ����յ�location. */
    public static final Location EMPTY_LOCATION = new Location(null, null, -1, -1);

    /**
     * ����λ����Ϣ.
     *
     * @param locator XML�ļ��е�λ����Ϣ
     */
    public Location(Locator locator) {
        this(locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(),
            locator.getColumnNumber());
    }

    /**
     * ����λ����Ϣ.
     *
     * @param publicId public ID
     * @param systemId system ID, Ҳ�����ļ�·����URL
     * @param lineNumber �к�
     * @param columnNumber �к�
     */
    public Location(String publicId, String systemId, int lineNumber, int columnNumber) {
        if (publicId != null) {
            publicId = publicId.trim();

            if ((publicId != null) && (publicId.length() == 0)) {
                publicId = null;
            }
        }

        if (systemId != null) {
            systemId = systemId.trim();

            if ((systemId != null) && (systemId.length() == 0)) {
                systemId = null;
            }
        }

        if (lineNumber <= 0) {
            lineNumber = -1;
        }

        if (columnNumber <= 0) {
            columnNumber = -1;
        }

        this.publicId         = publicId;
        this.systemId         = systemId;
        this.lineNumber       = lineNumber;
        this.columnNumber     = columnNumber;
    }

    /**
     * ȡ��public ID.
     *
     * @return public ID, ���������, �򷵻�<code>null</code>
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * ȡ��system ID.
     *
     * @return system ID, ���������, �򷵻�<code>null</code>
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * ȡ���к�.
     *
     * @return �к�, ���������, �򷵻�<code>-1</code>
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * ȡ���к�.
     *
     * @return �к�, ���������, �򷵻�<code>-1</code>
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * ת�����ַ�����ʾ.
     *
     * @return �ַ�����ʾ
     */
    public String toString() {
        if (systemId == null) {
            return "Unknown location";
        }

        StringBuffer buffer = new StringBuffer(systemId);

        if (lineNumber > 0) {
            buffer.append(':').append(lineNumber);

            if (columnNumber > 0) {
                buffer.append(':').append(columnNumber);
            }
        }

        return buffer.toString();
    }
}
